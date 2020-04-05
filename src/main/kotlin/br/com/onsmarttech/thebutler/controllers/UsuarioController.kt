package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.documents.Usuario
import br.com.onsmarttech.thebutler.services.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("/usuarios")
class UsuarioController(val usuarioService: UsuarioService) {

    @PostMapping
    fun salvar(principal: Principal?, @RequestBody @Valid usuario: Usuario) =
            ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvar(principal, usuario))

    @GetMapping
    fun listar(principal: Principal?) = ResponseEntity.ok(usuarioService.listar(principal))
}