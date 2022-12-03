package de.hsflensburg.recipe_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@SpringBootApplication
class RecipeBackendApplication {
}

@Configuration
class SimpleConfiguration {
}

fun main(args: Array<String>) {
    runApplication<RecipeBackendApplication>(*args)
}
