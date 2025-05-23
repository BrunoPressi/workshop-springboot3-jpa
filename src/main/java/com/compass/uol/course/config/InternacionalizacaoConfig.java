package com.compass.uol.course.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class InternacionalizacaoConfig {

    @Bean
    MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("ISO-8859-1");
		messageSource.setDefaultLocale(Locale.getDefault());
		return messageSource;
	}
    
    @Bean
    LocalValidatorFactoryBean validatorFactoryBean() {
    	LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
    	factoryBean.setValidationMessageSource(messageSource());
    	return factoryBean;
    }
	
}
