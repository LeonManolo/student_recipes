package de.hsflensburg.recipe_backend.files

import de.hsflensburg.recipe_backend.files.dto.FileUploadResponseDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class FileService(
    private val gcloudStorage: GCloudStorageUtil,
) {

    @Value("\${gcp.dir.name}")
    private lateinit var gcpDirectoryName: String

    fun uploadFile(fileInBytes: ByteArray, fileName: String, folderName: String? = null): FileUploadResponseDto {
        var outputFileName = gcpDirectoryName
        outputFileName += if (folderName == null) "" else "/$folderName"
        outputFileName += "/${UUID.randomUUID()}" + getFileExtension(fileName)
        return gcloudStorage.uploadFile(fileInBytes, outputFileName)
    }

    private fun getFileExtension(fileName: String): String {
        return "." + fileName.substringAfterLast('.', "")
    }

}