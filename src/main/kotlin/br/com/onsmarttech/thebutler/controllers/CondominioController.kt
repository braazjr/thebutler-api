package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.dtos.CondominioDto
import br.com.onsmarttech.thebutler.services.CondominioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("/condominios")
class CondominioController(val condominioService: CondominioService) {

    @PostMapping
    fun salvar(principal: Principal, @Valid @RequestBody condominioDto: CondominioDto) =
            ResponseEntity.status(HttpStatus.CREATED).body(condominioService.salvar(principal, condominioDto))
}