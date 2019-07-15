package com.money.transfer.config;

import com.money.transfer.rest.AccountRestServiceImpl;
import org.glassfish.jersey.message.MessageProperties;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This Class is to configure account service jersey configuration
 * and to support swagger
 * Created by Vishwanath on 15/07/2019.
 */
@EnableSwagger2
public class AccountJerseyConfig extends ResourceConfig {

    private static final Logger log = LoggerFactory.getLogger(AccountJerseyConfig.class);

    public AccountJerseyConfig() {
        packages("com.money.transfer");
        register(AccountRestServiceImpl.class);

        property(MessageProperties.XML_FORMAT_OUTPUT, true);
        property(ServerProperties.TRACING, "ALL");
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.money.transfer"))
                .paths(PathSelectors.any())
                .build();
    }
}
