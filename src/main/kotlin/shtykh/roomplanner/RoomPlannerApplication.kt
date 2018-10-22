package shtykh.roomplanner

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class RoomPlannerApplication


fun main(args: Array<String>) {
    SpringApplication.run(RoomPlannerApplication::class.java, *args)
}
