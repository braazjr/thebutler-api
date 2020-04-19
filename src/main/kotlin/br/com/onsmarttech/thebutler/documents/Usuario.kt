package br.com.onsmarttech.thebutler.documents

import br.com.onsmarttech.thebutler.enums.Permissao
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@Document("usuarios")
data class Usuario(
        @Id
        val id: String?,
        @get:NotBlank val email: String?,
        @get:NotBlank val nome: String?,
        @JsonIgnore var senha: String?,
        val ativo: Boolean? = true,
        @get:NotEmpty val permissoes: List<Permissao>? = listOf(Permissao.OPERADOR),
        var empresa: EmpresaSub?
) {
    constructor(id: String?) : this(id, null, null, null, null, null, null)
    constructor() : this(null, null, null, null, null, null, null)

    fun isAdmin() = permissoes!!.stream().filter { it == Permissao.ADMIN }.count() > 0
}