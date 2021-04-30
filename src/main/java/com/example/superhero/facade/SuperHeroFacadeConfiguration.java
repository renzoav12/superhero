package com.example.superhero.facade;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;

@Configuration
public class SuperHeroFacadeConfiguration {

    @Bean
    public ConversionService conversionService() {
        ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();
        factoryBean.setConverters(SuperHeroConverter.all());
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }
}
