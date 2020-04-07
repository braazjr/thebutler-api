package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.dtos.BlocoDto
import br.com.onsmarttech.thebutler.services.BlocoService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/blocos")
class BlocoController(val blocoService: BlocoService) {

    @PostMapping
    fun salvar(@Valid @RequestBody blocoDto: BlocoDto) = blocoService.salvar(blocoDto)

    @GetMapping
    fun listar() = blocoService.listar()
}