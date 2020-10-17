package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.documents.Rota
import br.com.onsmarttech.thebutler.services.RotaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/rotas")
class RotaController(val rotaService: RotaService) {

    @PostMapping
    fun save(@Valid @RequestBody rota: Rota) = ResponseEntity.status(HttpStatus.CREATED).body(rotaService.save(rota))

    @GetMapping
    fun list() = ResponseEntity.ok(rotaService.list())

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: String) = rotaService.findById(id)

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: String, @Valid @RequestBody rota: Rota) =
            ResponseEntity.status(HttpStatus.CREATED).body(rotaService.save(rota))
}