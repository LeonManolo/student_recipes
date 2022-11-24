package de.hsflensburg.recipe_backend.shared

import javax.validation.Validation

/**
 * Checks if the object is valid according to the validation annotations.
 */
fun Any.isValid(): Boolean {
    val factory = Validation.buildDefaultValidatorFactory()
    val violations = factory.validator.validate(this)
    return violations.isEmpty()
}