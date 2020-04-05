package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.services.EmpresaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/empresas")
class EmpresaController(val empresaService: EmpresaService) {

    @GetMapping
    fun listar(principal: Principal) = ResponseEntity.ok(empresaService.listar(principal))
}