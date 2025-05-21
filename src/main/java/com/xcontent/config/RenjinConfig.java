package com.xcontent.config;

import org.renjin.script.RenjinScriptEngine;
import org.renjin.script.RenjinScriptEngineFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RenjinConfig {

    @Bean
    public RenjinScriptEngine renjinScriptEngine() {
        return new RenjinScriptEngineFactory().getScriptEngine();
    }
} 