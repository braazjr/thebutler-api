package br.com.onsmarttech.thebutler.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Document("documentos")
data class Documento(
        @Id val id: String?,
        @get:NotBlank val nome:  String?,
        @get:NotBlank val url:  String?,
        @get:NotNull val registradoPor:  UsuarioSub?
)
