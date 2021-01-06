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
        @get:NotBlank val username: String?,
        @get:NotBlank val nome: String?,
        @JsonIgnore var senha: String?,
        val ativo: Boolean? = true,
        @get:NotEmpty var permissoes: MutableList<Any>? = mutableListOf(Permissao.OPERADOR),
        var empresa: EmpresaSub?,
        val foto64: String?
) {
    constructor(id: String?) : this(id, null, null, null, null, null, null, null)
    constructor() : this(null, null, null, null, null, null, null, null)

    fun isAdmin() = permissoes!!.stream().filter { it.toString() == Permissao.ADMIN.toString() }.count() > 0
}