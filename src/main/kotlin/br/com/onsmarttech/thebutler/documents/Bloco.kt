package br.com.onsmarttech.thebutler.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotNull

@Document("blocos")
data class Bloco(
        @Id var id: String?,
        val ativo: Boolean = true,
        @get:NotNull val nome: String?,
        val numero: Int?,
        @get:NotNull val condominio: CondominioSub?,
        @get:NotNull val registradoPor: UsuarioSub?
)

data class CondominioSub(
        @Id var id: String?,
        @get:NotNull val nome: String?,
        val email: String?,
        val empresa: EmpresaSub?
)