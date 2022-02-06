package com.inventorymanager.g5.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableSwagger2
@EnableWebMvc
class BackendApplication

fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)
}
