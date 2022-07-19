package com.eventcafecloud;

import com.eventcafecloud.config.properties.AppProperties;
import com.eventcafecloud.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties({
        CorsProperties.class,
        AppProperties.class
})
public class EventcafecloudApplication {

    @PostConstruct
    public void started() {
        // timezone UTC 셋팅
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
    public static void main(String[] args) {
        SpringApplication.run(EventcafecloudApplication.class, args);
    }

}
