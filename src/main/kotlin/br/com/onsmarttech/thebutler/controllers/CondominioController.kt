package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.dtos.CondominioDto
import br.com.onsmarttech.thebutler.services.CondominioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("/condominios")
class CondominioController(val condominioService: CondominioService) {

    @PostMapping
    fun save(principal: Principal, @Valid @RequestBody condominioDto: CondominioDto) =
            ResponseEntity.status(HttpStatus.CREATED).body(condominioService.save(principal, condominioDto))

    @GetMapping
    fun list() = ResponseEntity.ok(condominioService.list())

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: String) = ResponseEntity.ok(condominioService.getById(id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String) = condominioService.delete(id)
}