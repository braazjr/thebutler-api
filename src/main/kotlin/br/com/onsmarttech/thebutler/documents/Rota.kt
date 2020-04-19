package br.com.onsmarttech.thebutler.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank

@Document("rotas")
data class Rota(
        @Id val id: String?,
        @get:NotBlank val nome: String?,
        val ativo: Boolean = true,
        var registradoPor: UsuarioSub?
)
