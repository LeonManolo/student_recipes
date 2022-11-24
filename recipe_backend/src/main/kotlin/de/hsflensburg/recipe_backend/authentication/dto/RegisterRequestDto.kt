package de.hsflensburg.recipe_backend.authentication.dto


import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class RegisterRequestDto(
    //val username: String,

    @field:NotBlank
    val firstName: String,

    @field:NotBlank
    val lastName: String,

    @field:NotBlank
    @field:Email // Anscheind muss kein .com oder so angegeben werden nur +@+
    val email: String,

    @field:Size(min = 2, max = 50)
    val password: String,

    @NotEmpty
    val imageUrl: String? = null // TODO: mal schauen wie man das am besten macht

)
