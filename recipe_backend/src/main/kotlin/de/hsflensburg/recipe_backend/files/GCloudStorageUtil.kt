package de.hsflensburg.recipe_backend.files

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.Acl
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.StorageOptions
import de.hsflensburg.recipe_backend.files.dto.FileUploadResponseDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.InputStream
import java.util.*


@Component
class GCloudStorageUtil {

    @Value("\${gcp.config.file}")
    private lateinit var gcpConfigFile: String

    @Value("\${gcp.project.id}")
    private lateinit var gcpProjectId: String

    @Value("\${gcp.bucket.id}")
    private lateinit var gcpBucketId: String

    @Value("\${gcp.dir.name}")
    private lateinit var gcpDirectoryName: String

    fun uploadFile(fileInBytes: ByteArray, fileName: String): FileUploadResponseDto {
        val inputStream: InputStream = ClassPathResource(gcpConfigFile).inputStream

        val options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
            .setCredentials(GoogleCredentials.fromStream(inputStream)).build()
        // If the destination already exists in your bucket, instead set a generation-match
        // precondition:
        // Storage.BlobTargetOption precondition = Storage.BlobTargetOption.generationMatch();

        // If the destination already exists in your bucket, instead set a generation-match
        // precondition:
        // Storage.BlobTargetOption precondition = Storage.BlobTargetOption.generationMatch();
        //val outputFileName = "$gcpDirectoryName/$uuid-$fileName"

        val storage = options.service
        val blobId: BlobId = BlobId.of(gcpBucketId, fileName)
        val blobInfo: BlobInfo = BlobInfo.newBuilder(blobId).build()
        val blob = storage.create(blobInfo, fileInBytes)
        storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))

        println(
            "File uploaded to bucket $gcpBucketId as $fileName"
        )


        return FileUploadResponseDto(
            fileName = blob.name,
            publicUrl = "https://storage.googleapis.com/$gcpBucketId/${blob.name}",
            fileType = blob.contentType,
            timeCreated = blob.createTimeOffsetDateTime.toString(),
            size = blob.size
        )
    }
}