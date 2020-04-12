package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.documents.TipoDocumento
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tipos-documentos")
class TipoDocumentos {

    @GetMapping
    fun list() = TipoDocumento.values()
}