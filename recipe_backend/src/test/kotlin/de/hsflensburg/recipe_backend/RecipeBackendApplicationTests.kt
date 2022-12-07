package de.hsflensburg.recipe_backend

import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureEmbeddedDatabase(refresh = AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD) // Für in memory postgresql db, DOCKER muss installiert sein
class RecipeBackendApplicationTests {

    @Test
    fun contextLoads() {
    }

}
