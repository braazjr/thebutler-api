package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.enums.Permissao
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/permissoes")
class PermissaoController {

    @GetMapping
    fun list() = Permissao.values()
}