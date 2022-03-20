package com.inventorymanager.g5.backend

import com.inventorymanager.g5.backend.authentication.JWTAuthorizationFilter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@SpringBootApplication
class BackendApplication

fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)


	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig : WebSecurityConfigurerAdapter() {
		@Throws(Exception::class)
		override fun configure(http: HttpSecurity) {

		}
	}

}
