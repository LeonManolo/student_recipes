package de.hsflensburg.recipe_backend.files.dto

data class FileUploadResponseDto(
    val fileName: String,
    val publicUrl: String,
    val fileType: String,
    val timeCreated: String,
    val size: Long
)