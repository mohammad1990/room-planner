package shtykh.roomplanner.config


import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "shtykh.room")
@Configuration
open class RoomConfig {
    val minPremiumPayment = 100
}

