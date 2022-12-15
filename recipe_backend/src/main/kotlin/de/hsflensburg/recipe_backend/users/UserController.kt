package de.hsflensburg.recipe_backend.users

import de.hsflensburg.recipe_backend.files.FileService
import de.hsflensburg.recipe_backend.recipes.dto.CreateRecipeRequestDto
import de.hsflensburg.recipe_backend.shared.getIdOfAuthenticatedUser
import de.hsflensburg.recipe_backend.users.dto.UpdateUserDto
import de.hsflensburg.recipe_backend.users.entity.User
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.validation.Valid

/**
 * A RestController that handles requests for the User resource
 *
 * @property userService
 */
@RestController
@RequestMapping("/api/users")
class UserController(val userService: UserService, val fileService: FileService) {
    @PostMapping
    fun createUser(@RequestBody user: User) {
        userService.createUser(user)
    }

    /**
     * Updates a user.
     *
     * @param file the file to be uploaded( The profile picture)
     * @param user the Recipe object to be created
     */
    @PostMapping(consumes = ["multipart/form-data"])
    fun updateUser(@RequestPart("file") file: MultipartFile, @RequestPart("user") @Valid user: UpdateUserDto) {
        val image = fileService.uploadFile(file.bytes, file.originalFilename!!);
        user.imageUrl = image.publicUrl
        userService.updateUser(getIdOfAuthenticatedUser(), user)
    }

    /**
     * Gets a user.
     *
     * @return USer the current logged-in user
     */
    @GetMapping
    fun getUser(): User {
        return userService.getUser(getIdOfAuthenticatedUser())
    }

    /**
     * Deletes a user.
     *
     */
    @DeleteMapping
    fun deleteUser() {
        userService.deleteUser(getIdOfAuthenticatedUser())
    }
}