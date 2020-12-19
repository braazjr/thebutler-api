package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.enums.Permissao
import br.com.onsmarttech.thebutler.services.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/permissoes")
class PermissaoController {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @GetMapping
    fun list(): List<String> {
        val permissoes = Permissao.values()
        if (usuarioService.getUsuarioLogado().isAdmin()) {
            return permissoes
                .filter { it.name != "ADMIN" }
                .map { it.name }
        } else {
            return listOf("OPERADOR", "MOTORISTA")
        }
    }
}