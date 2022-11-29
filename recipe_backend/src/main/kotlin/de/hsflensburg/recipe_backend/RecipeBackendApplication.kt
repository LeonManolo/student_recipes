package de.hsflensburg.recipe_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.context.annotation.Bean


@SpringBootApplication
class RecipeBackendApplication {
}

fun main(args: Array<String>) {
    runApplication<RecipeBackendApplication>(*args)
}
