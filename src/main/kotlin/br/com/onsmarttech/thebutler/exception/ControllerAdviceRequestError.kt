package br.com.onsmarttech.thebutler.exception

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import kotlin.streams.toList

@ControllerAdvice
class ControllerAdviceRequestError(
        val messageSource: MessageSource
) : ResponseEntityExceptionHandler() {

    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, headers: HttpHeaders,
                                              status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val mensagemUsuario: String = "Ocorreu um erro na serialização"
        val mensagemDesenvolvedor: String? = ex.cause?.toString() ?: ex.toString()

        return super.handleExceptionInternal(ex, listOf(Erro(mensagemUsuario, mensagemDesenvolvedor)), headers, status,
                request)
    }

    @ExceptionHandler(HttpMessageConversionException::class)
    fun handleHttpMessageConversionException(ex: HttpMessageConversionException, request: WebRequest): ResponseEntity<Any> {
        val mensagemUsuario: String = "Ocorreu um erro na conversão do objeto"
        val mensagemDesenvolvedor: String? = ex.cause?.toString() ?: ex.toString()

        return super.handleExceptionInternal(ex, listOf(Erro(mensagemUsuario, mensagemDesenvolvedor)), HttpHeaders.EMPTY,
                HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequetException(ex: BadRequestException, request: WebRequest): ResponseEntity<Any> {
        val mensagemUsuario: String = ex.message
        val mensagemDesenvolvedor: String? = ex.toString()

        return super.handleExceptionInternal(ex, listOf(Erro(mensagemUsuario, mensagemDesenvolvedor)), HttpHeaders.EMPTY,
                HttpStatus.BAD_REQUEST, request)
    }

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders,
                                              status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return super.handleExceptionInternal(ex, criarListaErros(ex.bindingResult), headers, status,
                request)
    }

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleEmptyResultDataAccessException(ex: EmptyResultDataAccessException, request: WebRequest): ResponseEntity<Any> {
        val mensagemUsuario: String = "Objeto não encontrado"
        val mensagemDesenvolvedor: String? = ex.cause?.toString() ?: ex.toString()

        return super.handleExceptionInternal(ex, listOf(Erro(mensagemUsuario, mensagemDesenvolvedor)), HttpHeaders.EMPTY,
                HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException, request: WebRequest): ResponseEntity<Any> {
        return super.handleExceptionInternal(ex, criarListaErros(ex.constraintViolations), HttpHeaders.EMPTY,
                HttpStatus.BAD_REQUEST, request)
    }

    private fun criarListaErros(errors: BindingResult): List<Erro> {
        return errors
                .fieldErrors
                .stream()
                .map { Erro(messageSource.getMessage(it, LocaleContextHolder.getLocale()), it.toString()) }
                .toList()
    }

    private fun criarListaErros(constraintViolations: Set<ConstraintViolation<*>>): Any? {
        return constraintViolations
                .stream()
                .map { Erro(it.message, it.toString()) }
                .toList()
    }
}

class Erro(
        val mensagemUsuario: String?,
        val mensagemDesenvolvedor: String?
)