package de.hsflensburg.recipe_backend.files

import de.hsflensburg.recipe_backend.files.dto.FileUploadResponseDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.awt.PageAttributes.MediaType

@RestController
@RequestMapping("/api/files")
class FileController(
    private val fileService: FileService,
) {

    @PostMapping()
    fun uploadFile(@RequestParam("file") file: MultipartFile): FileUploadResponseDto {
        //fileService.uploadFile()
        //TODO: nur Bilder erlauben lassen
        return fileService.uploadFile(file.bytes, file.originalFilename!!)
    }

}