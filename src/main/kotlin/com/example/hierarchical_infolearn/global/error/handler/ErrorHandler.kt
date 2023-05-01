package com.example.hierarchical_infolearn.global.error.handler

import com.example.hierarchical_infolearn.global.error.data.ErrorResponse
import com.example.hierarchical_infolearn.global.error.data.GlobalError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.multipart.MultipartException
import javax.validation.ConstraintViolationException
import javax.validation.ValidationException

@RestControllerAdvice
class ErrorHandler{

    @ExceptionHandler(GlobalError::class)
    fun globalExceptionHandler(error: GlobalError): ResponseEntity<*> {
        return ResponseEntity.status(error.errorCode.status).body(
            ErrorResponse(
                error.errorCode.message,
                error.data
            )
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidExceptionHandler(e: MethodArgumentNotValidException): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse(
                e.bindingResult.allErrors[0].defaultMessage.toString(),
                e.fieldError?.rejectedValue.toString()
            )
        )
    }

    @ExceptionHandler(ValidationException::class)
    fun validationExceptionHandler(
        e: ConstraintViolationException,
        request: WebRequest?
    ): ResponseEntity<Any?>? {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse(
                e.message.toString(),
                e.stackTrace.toString()
            )
        )
    }

    @ExceptionHandler(MultipartException::class)
    fun maxUploadSizeExceededException(
        e: MaxUploadSizeExceededException
    ): ResponseEntity<*> {
        return ResponseEntity.badRequest().body(
            ErrorResponse(
                e.message.toString(),
                e.cause.toString()
            )
        )
    }

}