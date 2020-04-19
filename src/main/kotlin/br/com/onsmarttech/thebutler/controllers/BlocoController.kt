package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.dtos.BlocoDto
import br.com.onsmarttech.thebutler.services.BlocoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/blocos")
class BlocoController(val blocoService: BlocoService) {

    @PostMapping
    fun save(@Valid @RequestBody blocoDto: BlocoDto) = blocoService.save(blocoDto)

    @GetMapping
    fun list() = blocoService.list()

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: String) = ResponseEntity.ok(blocoService.findById(id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String) = blocoService.delete(id)

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: String, @Valid @RequestBody blocoDto: BlocoDto) =
            ResponseEntity.ok(blocoService.update(id, blocoDto))
}