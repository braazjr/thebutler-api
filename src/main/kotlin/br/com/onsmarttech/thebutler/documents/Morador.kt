package br.com.onsmarttech.thebutler.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Document
data class Morador(
        @Id val id: String?,
        @get:NotBlank val documento: String?,
        val ativo: Boolean?,
        @get:NotBlank val celular: String?,
        @get:NotBlank val email: String?,
        @get:NotBlank val nome: String?,
        val telefone: String?,
        val placaCarro: String?,
        val observacao: String?,
        val parentesco: String?,
        @get:NotNull val tipoDocumento: TipoDocumento?,
        val tipoMorador: TipoMorador?,
        val foto64: String?,
        val registradoPor: UsuarioSub?
)

data class ApartamentoSub(
        var id: String?,
        val numero: String?,
        val bloco: BlocoSub?
)

enum class TipoMorador {
    JURIDICO, PROPRIETARIO, LOCATARIO, DEPENDENTE, TEMPORADA, EMPREGO, OUTROS
}

enum class TipoDocumento {
    IDENTIDADE, CARTEIRAMOTORISTA, CARTEIRATRABALHO, OUTROS
}
