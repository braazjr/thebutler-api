package br.com.onsmarttech.thebutler.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Document("moradores")
data class Morador(
        @Id var id: String?,
        @get:NotBlank val documento: String?,
        val ativo: Boolean?,
        @get:NotBlank val celular: String?,
        val email: String?,
        @get:NotBlank val nome: String?,
        val telefone: String?,
        val placaCarro: String?,
        val observacao: String?,
        val parentesco: String?,
        @get:NotNull val tipoDocumento: TipoDocumento?,
        val tipoMorador: TipoMorador?,
        val foto64: String?,
        var registradoPor: UsuarioSub?,
        var dataCriacao: LocalDate?,
        var dataAlteracao: LocalDate?,
        var apartamento: ApartamentoSub?,
        var fichaId: String?
)

data class ApartamentoSub(
        @Id var id: String?,
        val numero: String?,
        val bloco: BlocoSub?
)

fun convertApartamentoToSub(apartamento: Apartamento) = ApartamentoSub(apartamento.id, apartamento.numero,
        apartamento.bloco)

enum class TipoMorador {
    JURIDICO, PROPRIETARIO, LOCATARIO, DEPENDENTE, TEMPORADA, EMPREGO, OUTROS
}

enum class TipoDocumento {
    IDENTIDADE, CARTEIRAMOTORISTA, CARTEIRATRABALHO, MENOR_IDADE, OUTROS
}
