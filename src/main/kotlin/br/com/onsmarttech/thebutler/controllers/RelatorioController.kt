package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.services.RelatorioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/relatorios")
class RelatoriosController(val relatorioService: RelatorioService) {

    @GetMapping("/moradores-sem-foto")
    fun MoradoresSemFoto() = ResponseEntity.ok(relatorioService.moradoresSemFoto())

    @GetMapping("/moradores-sem-documento")
    fun MoradoresSemDocumento() = ResponseEntity.ok(relatorioService.moradoresSemDocumento())

    @GetMapping("/viagens-mes")
    fun viagensMes() = ResponseEntity.ok(relatorioService.viagensMes())
}