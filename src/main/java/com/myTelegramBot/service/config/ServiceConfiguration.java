package com.myTelegramBot.service.config;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ServiceConfiguration {

    private static final String DOZER_MAPPING_FILES_TEMPLATE = "classpath*:/dozer/mappings/*_mapping.xml";

    @Bean
    public DozerBeanMapperFactoryBean dozerMapper(@Value(DOZER_MAPPING_FILES_TEMPLATE) Resource[] mappingFiles) {
        DozerBeanMapperFactoryBean dozerMapper = new DozerBeanMapperFactoryBean();
        dozerMapper.setMappingFiles(mappingFiles);

        return dozerMapper;
    }
}
