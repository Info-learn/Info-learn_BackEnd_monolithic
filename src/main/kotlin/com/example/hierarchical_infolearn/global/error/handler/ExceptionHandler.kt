package com.example.hierarchical_infolearn.global.error.handler

import com.example.hierarchical_infolearn.global.error.data.BindErrorResponse
import com.example.hierarchical_infolearn.global.error.data.ErrorResponse
import com.example.hierarchical_infolearn.global.error.data.GlobalException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.multipart.MultipartException
import javax.validation.ConstraintViolationException
import javax.validation.ValidationException

@RestControllerAdvice
class ExceptionHandler{

    @ExceptionHandler(GlobalException::class)
    fun globalExceptionHandler(e: GlobalException) = ResponseEntity
        .status(e.errorCode.status)
        .body(ErrorResponse.of(e.errorCode))

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException::class)
    protected fun handleBindException(e: BindException): BindErrorResponse = ErrorResponse.of(e)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidExceptionHandler(e: MethodArgumentNotValidException) = ResponseEntity
        .badRequest()
        .body(e.bindingResult.allErrors[0].defaultMessage)

    @ExceptionHandler(ValidationException::class)
    fun validationExceptionHandler(
        e: ConstraintViolationException,
        request: WebRequest?
    ) = ResponseEntity.badRequest().body(e.message)

    @ExceptionHandler(MultipartException::class)
    fun maxUploadSizeExceededException(
        e: MaxUploadSizeExceededException
    ) = ResponseEntity.badRequest().body(e.message)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun handleHttpMessageNotReadable(e: HttpMessageNotReadableException) =
        ResponseEntity(
            ErrorResponse.of(e),
            HttpStatus.BAD_REQUEST
        )
}