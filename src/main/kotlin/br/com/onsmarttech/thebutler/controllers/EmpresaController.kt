package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.dtos.EmpresaDto
import br.com.onsmarttech.thebutler.services.EmpresaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("/empresas")
class EmpresaController(val empresaService: EmpresaService) {

    @GetMapping
    fun listar(principal: Principal) = ResponseEntity.ok(empresaService.listar(principal))

    @PostMapping
    fun salvar(principal: Principal, @Valid @RequestBody empresaDto: EmpresaDto) =
            ResponseEntity.status(HttpStatus.CREATED).body(empresaService.salvar(principal, empresaDto))

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable("id") id: String) = empresaService.deletar(id)
}