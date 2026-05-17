package ru.altaiensb.service_desk.config;

import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import ru.altaiensb.service_desk.annotation.AllFieldsRequired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer allFieldsRequiredCustomiser() throws Exception {
        Set<String> requiredDtoNames = new HashSet<>();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory();

        // Сканируем все .class файлы в пакете dto и его подпакетах
        Resource[] resources = resolver.getResources("classpath*:ru/altaiensb/service_desk/dto/**/*.class");
        for (Resource resource : resources) {
            MetadataReader reader = readerFactory.getMetadataReader(resource);
            String className = reader.getClassMetadata().getClassName();
            try {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(AllFieldsRequired.class)) {
                    requiredDtoNames.add(clazz.getSimpleName());
                }
            } catch (ClassNotFoundException e) {
                // игнорируем
            }
        }

        return openApi -> {
            if (openApi.getComponents() == null) return;
            var schemas = openApi.getComponents().getSchemas();
            for (String dtoName : requiredDtoNames) {
                Schema<?> schema = schemas.get(dtoName);
                if (schema != null && schema.getProperties() != null) {
                    schema.setRequired(new ArrayList<>(schema.getProperties().keySet()));
                }
            }
        };
    }
}