package org.shuyuan.schoolres.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.time.Duration;
import java.util.Locale;

@Configuration
public class CustomConfig implements WebMvcConfigurer
{
    @Value("${shuyuan.dishes.images}")
    private String imagePath;

    @Value("${shuyuan.locale.resolver.type}")
    private String resolveType;

    @Value("${shuyuan.locale.param.name}")
    private String localeParam;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/schoolres/images/**")
                .addResourceLocations(imagePath);
    }

    @Bean
    public LocaleResolver localeResolver()
    {
        if (resolveType.equals("session"))
        {
            SessionLocaleResolver resolver = new SessionLocaleResolver();
            resolver.setDefaultLocale(Locale.CHINA);
            return resolver;
        }
        else if (resolveType.equals("cookie"))
        {
            CookieLocaleResolver resolver = new CookieLocaleResolver("cookie");
            resolver.setDefaultLocale(Locale.CHINA);
            resolver.setCookieMaxAge(Duration.ofDays(1));
            return resolver;
        }
        else
        {
            return new AcceptHeaderLocaleResolver();
        }
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor()
    {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        // 应用要根据哪个请求参数来该更改Locale
        interceptor.setParamName(localeParam);
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        // 添加确定Locale的拦截器
        registry.addInterceptor(localeChangeInterceptor());
    }

}
