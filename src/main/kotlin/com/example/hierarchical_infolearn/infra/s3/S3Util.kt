package com.example.hierarchical_infolearn.infra.s3

import com.example.hierarchical_infolearn.global.file.dto.FileDto
import com.example.hierarchical_infolearn.global.file.type.FileContentType
import com.example.hierarchical_infolearn.global.file.type.ImageExt
import com.example.hierarchical_infolearn.global.file.type.VideoExt
import com.example.hierarchical_infolearn.infra.s3.env.S3Property
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration


@Component
class S3Util(
    private val s3Property: S3Property,
    private val presigner: S3Presigner,
) {

    fun getPreSignedUrl(originalFileName: String, contentType: String, rootPathName: String, middlePathName: String): FileDto {
        val fileName = getFileName(rootPathName, middlePathName, originalFileName)
        val ext = getExt(originalFileName)
        val fileType = getFileType(contentType, ext)


        val presignedURL = getGeneratePreSignedUrlRequest(fileName, contentType)

        return FileDto(
            presignedURL,
            fileType,
            ext,
            originalFileName
        )
    }

    private fun getExt(originalFileName: String): String {
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1)
    }

    private fun getFileType(type: String, ext: String): FileContentType {

        var contentType = type
        var fileType: FileContentType = FileContentType.IMAGE

        ImageExt.values().filter { it.extension ==  ext }.map {
            contentType = it.contentType
        }.ifEmpty {
            VideoExt.values().filter { it.extension == ext }.map {
                contentType = it.contentType
                fileType = FileContentType.VIDEO
            }
        }.ifEmpty {
            fileType = FileContentType.UNKNOWN
        }
        return fileType
    }

    private fun getGeneratePreSignedUrlRequest(fileName: String, contentType: String): String {

        val objectRequest = PutObjectRequest.builder()
            .bucket(s3Property.bucketName)
            .key(fileName)
            .contentType(contentType)
            .build()

        val presignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(2))
            .putObjectRequest(objectRequest)
            .build()

        return presigner.presignPutObject(presignRequest).url().toString()
    }
    private fun getFileName(rootPathName: String, middlePathName: String, originalFileName: String): String {
        return "${s3Property.bucketName}/${rootPathName}/${middlePathName}/${originalFileName}"
    }

}