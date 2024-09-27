package com.redhat.service;

import org.jbpm.springboot.security.SpringSecurityUserGroupCallback;
import org.kie.api.task.UserGroupCallback;
import org.kie.internal.identity.IdentityProvider;
import org.kie.server.services.api.KieServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KieServerApplication {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(KieServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KieServerApplication.class, args);
    }
      
    @Bean
    CommandLineRunner deployAndValidate() {
        return new CommandLineRunner() {
            
            @Autowired            
            private KieServer kieServer;
            
            @Override
            public void run(String... strings) throws Exception {                
                LOGGER.info("KieServer {} started", kieServer);
            }
        };
    }
}

