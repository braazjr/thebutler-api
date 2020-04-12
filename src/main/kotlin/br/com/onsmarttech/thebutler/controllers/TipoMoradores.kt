package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.documents.TipoMorador
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tipos-moradores")
class TipoMoradores {

    @GetMapping
    fun list() = TipoMorador.values()
}