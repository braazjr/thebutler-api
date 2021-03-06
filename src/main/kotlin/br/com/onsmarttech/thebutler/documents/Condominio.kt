package br.com.onsmarttech.thebutler.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotNull

@Document("condominios")
data class Condominio(
        @Id var id: String?,
        @get:NotNull val nome: String?,
        val ativo: Boolean = false,
        @get:NotNull val bairro: String?,
        @get:NotNull val cep: String?,
        @get:NotNull val cidade: String?,
        val complemento: String?,
        val email: String?,
        @get:NotNull val estado: String?,
        val numero: Int?,
        @get:NotNull val rua: String?,
        @get:NotNull val telefone: String?,
        var empresa: EmpresaSub?,
        @get:NotNull val registradoPor: UsuarioSub?
)

fun convertCondominioToSub(condominio: Condominio) = CondominioSub(condominio.id, condominio.nome, condominio.email, condominio.empresa)