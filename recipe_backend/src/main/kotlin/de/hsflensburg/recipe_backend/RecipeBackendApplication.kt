package de.hsflensburg.recipe_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RecipeBackendApplication

fun main(args: Array<String>) {
    runApplication<RecipeBackendApplication>(*args)
}
