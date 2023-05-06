package com.example.hierarchical_infolearn.global.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val message: String,
    val status: HttpStatus,
    val data: String? = null,
) {
    //File
    FILE_NOT_FOUND("File not Found", HttpStatus.NOT_FOUND),
    FILE_SHOULD_BE_IMAGE_TYPE("File should be image type", HttpStatus.BAD_REQUEST),
    INVALID_FILE_EXTENSION("Invalid File Extension", HttpStatus.BAD_REQUEST),
    FAIL_FILE_UPLOAD("Failed to upload file", HttpStatus.NOT_FOUND),
    S3_ERROR("S3 작업 과정 중 에러가 발생했습니다.", HttpStatus.BAD_GATEWAY),

    //Auth
    USER_NOT_FOUND("User Not Found", HttpStatus.NOT_FOUND),
    EMAIL_NOT_FOUND("Email Not Found", HttpStatus.NOT_FOUND),
    INVALID_TOKEN("Invalid Token", HttpStatus.BAD_REQUEST),
    EXPIRED_TOKEN("token Expired", HttpStatus.BAD_REQUEST),
    EMAIL_ERROR("Error Occurred while Sending Email", HttpStatus.BAD_GATEWAY),
    INCORRECT_AUTH_CODE("Incorrect Auth Code", HttpStatus.BAD_REQUEST),
    INCORRECT_TEACHER_CODE("Incorrect Teacher Code", HttpStatus.BAD_REQUEST),
    CHECK_PASSWORD_CODE_ERROR("Error Occurred while Checking Password Code", HttpStatus.NOT_FOUND),
    UUID_NOT_FOUND("UUID '{0}' Not Found", HttpStatus.NOT_FOUND),
    INCORRECT_PASSWORD("Incorrect Password", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS("User Already Exists", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS("Email Already Exists", HttpStatus.BAD_REQUEST),
    ACCOUNT_ID_ALREADY_EXISTS("AccountId Already Exists", HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER("{0} parameter inputted null or invalid value.", HttpStatus.BAD_REQUEST),
    NO_AUTHENTICATION("You have not Authentication", HttpStatus.FORBIDDEN),

    //Lecture
    LECTURE_NOT_FOUND("Lecture Not Found", HttpStatus.NOT_FOUND),
    INCORRECT_SEARCH_TYPE("Incorrect Search Type", HttpStatus.BAD_REQUEST),
    LECTURE_TAG_NOT_FOUND("Lecture Tag Not Found", HttpStatus.NOT_FOUND),

    //CHAPTER
    ALREADY_USING_SEQUENCE("Already Using Sequence", HttpStatus.BAD_REQUEST),
    CHAPTER_NOT_FOUND("Chapter Not Found", HttpStatus.NOT_FOUND),
    INCORRECT_CHAPTER("Incorrect Chapter", HttpStatus.BAD_REQUEST),

    //VIDEO
    VIDEO_NOT_FOUND("Video Not Found", HttpStatus.NOT_FOUND),
    INCORRECT_VIDEO("Incorrect Video", HttpStatus.BAD_REQUEST),

    //TIL
    TIL_NOT_FOUND("Til Not Found", HttpStatus.NOT_FOUND),
}