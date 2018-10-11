package com.xiaojun.core.swagger;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationCodeGrant;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.ant;


/**
 * @author f00lish
 * @version 2018/7/16
 * Created By IntelliJ IDEA.
 * Qun:530350843
 */
@Configuration
@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
@ConditionalOnClass(Swagger2DocumentationConfiguration.class)
@Import({
        Swagger2DocumentationConfiguration.class
})
public class Swagger2Configuration {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Bean
    @RefreshScope
    @ConditionalOnMissingBean
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties(discoveryClient);
    }


    @Bean
//    @RefreshScope
    @ConditionalOnMissingBean
    public Docket docketApi(SwaggerProperties swaggerProperties) {

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .contact(new Contact(swaggerProperties.getContact().getName(),
                        swaggerProperties.getContact().getUrl(),
                        swaggerProperties.getContact().getEmail()))
                .build();

        if (swaggerProperties.getBasePath().isEmpty()) {
            swaggerProperties.getBasePath().add("/**");
        }
        List<Predicate<String>> basePath = new ArrayList();
        for (String path : swaggerProperties.getBasePath()) {
            basePath.add(PathSelectors.ant(path));
        }

        List<Predicate<String>> excludePath = new ArrayList<>();
        for (String path : swaggerProperties.getExcludePath()) {
            excludePath.add(PathSelectors.ant(path));
        }

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .host(swaggerProperties.getHost())
                .apiInfo(apiInfo)
                .securitySchemes(Collections.singletonList(oAuthBuilder()))
                .securityContexts(Collections.singletonList(securityContext()))
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(
                        Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(basePath)
                        )
                ).build();

        return docket;
    }

    SecurityContext securityContext() {
        AuthorizationScope[] scopes = new AuthorizationScope[]{new AuthorizationScope(swaggerProperties().getAuthorization().getScopeCode(), swaggerProperties().getAuthorization().getScopeDesc())};

        SecurityReference securityReference = SecurityReference
                .builder()
                .reference(swaggerProperties().getAuthorization().getType())
                .scopes(scopes)
                .build();

        return SecurityContext
                .builder()
                .securityReferences(newArrayList(securityReference))
                .forPaths(ant("/**"))
                .build();
    }

    @Bean
    @RefreshScope
    public OAuth oAuthBuilder() {
        return new OAuthBuilder()
                .name(swaggerProperties().getAuthorization().getType())
                .grantTypes(grantTypes())
                .scopes(scopes())
                .build();
    }

    List<AuthorizationScope> scopes() {
        return newArrayList(new AuthorizationScope(swaggerProperties().getAuthorization().getScopeCode(), swaggerProperties().getAuthorization().getScopeDesc()));
    }

    @Bean
    @RefreshScope
    public List<GrantType> grantTypes() {
        List<GrantType> grants = newArrayList(new AuthorizationCodeGrant(
                new TokenRequestEndpoint(swaggerProperties().getAuthorization().getAuthorizationUrl(), swaggerProperties().getAuthorization().getClientId(), swaggerProperties().getAuthorization().getClientSecret()),
                new TokenEndpoint(swaggerProperties().getAuthorization().getTokenUrl(), swaggerProperties().getAuthorization().getTokenName())));
        return grants;
    }

    @Bean
    public SecurityConfiguration securityInfo() {
        return new SecurityConfiguration(swaggerProperties().getAuthorization().getClientId(), swaggerProperties().getAuthorization().getClientSecret(), swaggerProperties().getAuthorization().getScopeCode(),
                "xcloud", "xcloud", ApiKeyVehicle.HEADER, "", ",");
    }

}

