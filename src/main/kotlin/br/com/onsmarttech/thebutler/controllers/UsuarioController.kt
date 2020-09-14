package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.dtos.UsuarioDto
import br.com.onsmarttech.thebutler.services.UsuarioService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("/usuarios")
class UsuarioController(val usuarioService: UsuarioService) {

    @PostMapping
    fun salvar(principal: Principal?, @RequestBody @Valid usuarioDto: UsuarioDto) =
            ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvar(principal, usuarioDto))

    @GetMapping
    fun list(pageable: Pageable) = ResponseEntity.ok(usuarioService.list(pageable))

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable("id") id: String) = usuarioService.deletar(id)

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: String) = usuarioService.getById(id)

    @GetMapping("/motoristas")
    fun getMotoristas() = ResponseEntity.ok(usuarioService.getMotoristas())

    @GetMapping("/motorista/{id}")
    fun getMotoristaByID(@PathVariable("id") id: String) =
            ResponseEntity.ok(usuarioService.getMotoristaById(id))

    @PutMapping("/{id}")
    fun atualizar(principal: Principal?, @PathVariable("id") id: String, @RequestBody @Valid usuarioDto: UsuarioDto) =
            ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.atualizar(principal, id, usuarioDto))
}