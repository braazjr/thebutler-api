package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.services.MoradorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("rasp")
class RaspController(val moradorService: MoradorService) {

    @GetMapping("/moradores")
    fun buscarUsuarios() = ResponseEntity.ok(moradorService.find())
}
