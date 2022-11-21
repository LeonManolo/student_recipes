package de.hsflensburg.recipe_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import org.springframework.security.crypto.password.PasswordEncoder




@SpringBootApplication
class RecipeBackendApplication {


}

fun main(args: Array<String>) {
    runApplication<RecipeBackendApplication>(*args)
}
