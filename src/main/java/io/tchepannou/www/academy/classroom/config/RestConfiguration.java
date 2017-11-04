package io.tchepannou.www.academy.classroom.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tchepannou.rest.RestClient;
import io.tchepannou.rest.RestConfig;
import io.tchepannou.rest.impl.DefaultRestClient;
import io.tchepannou.rest.impl.JsonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfiguration {
    @Bean
    public RestConfig restConfig(ObjectMapper objectMapper){
        return new RestConfig()
                .setSerializer(new JsonSerializer(objectMapper));
    }

    @Bean
    public RestClient restClient (RestConfig restConfig) {
        return new DefaultRestClient(restConfig);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
