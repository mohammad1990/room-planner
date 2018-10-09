package shtykh.roomplanner.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "shtykh.room")
@Configuration
@Getter
public class RoomConfig {
    private int minPremiumPayment = 100;
}

