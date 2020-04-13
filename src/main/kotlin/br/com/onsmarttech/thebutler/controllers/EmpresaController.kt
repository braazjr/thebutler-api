package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.dtos.EmpresaDto
import br.com.onsmarttech.thebutler.services.EmpresaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/empresas")
class EmpresaController(val empresaService: EmpresaService) {

    @GetMapping
    fun listar() = ResponseEntity.ok(empresaService.listar())

    @PostMapping
    fun salvar(@Valid @RequestBody empresaDto: EmpresaDto) =
            ResponseEntity.status(HttpStatus.CREATED).body(empresaService.salvar(empresaDto))

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable("id") id: String) = empresaService.deletar(id)

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable("id") id: String) = ResponseEntity.ok(empresaService.getById(id))
}