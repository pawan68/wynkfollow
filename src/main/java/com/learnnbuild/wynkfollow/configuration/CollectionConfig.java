package com.learnnbuild.wynkfollow.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class CollectionConfig {
    @Bean
    public CollectionsBean getCollectionsBean() {
        return new CollectionsBean(new HashMap<>());
    }
}
