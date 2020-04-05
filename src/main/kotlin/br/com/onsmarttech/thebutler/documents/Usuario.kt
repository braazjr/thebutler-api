package br.com.onsmarttech.thebutler.documents

import br.com.onsmarttech.thebutler.enums.Permissao
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank

@Document
data class Usuario(
        @Id
        val id: String = "",
        @get:NotBlank val email: String = "",
        @get:NotBlank val nome: String = "",
        @JsonIgnore var senha: String = "",
        val ativo: Boolean = true,
        val permissoes: List<Permissao> = listOf(Permissao.OPERADOR),
        var empresa: Empresa?
) {
    fun isAdmin() = permissoes.stream().filter { it == Permissao.ADMIN }.count() > 0
}