package ru.banana.calc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@ComponentScan("ru.banana.calc")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {
    /* интерфейс WebMvcConfigurer необходим для индивидуальной настройки шаблонизатора
в нашем случае - Thymeleaf */
    private final ApplicationContext applicationContext;
    /* Spring сам внедрит за нас applicationContext при помощи @Autowired */
    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        //задаём папку где будут лежать наши представления
        templateResolver.setSuffix(".html");
        //задаём расширения наших представлений
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        //задаём конфигурацию представлений
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        //ссылаемся на выбранный нами шаблонизатор Thymeleaf
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }
}

