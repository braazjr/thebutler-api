package br.com.onsmarttech.thebutler.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Document("moradores")
data class Morador(
        @Id
        var id: String?,
        @get:NotNull
        val documento: String?,
        @get:NotBlank
        val nome: String?,
        @get:NotNull
        val tipoDocumento: TipoDocumento?
) {
    var ativo: Boolean = true
    var email: String = ""
    var telefone: String = ""
    var placaCarro: String = ""
    var observacao: String = ""
    var parentesco: String = ""
    var tipoMorador: TipoMorador? = null
    var foto64: String? = null
    var apartamento: ApartamentoSub? = null
    var fichaId: String = ""
    var dataInicio: LocalDate? = null
    var dataFim: LocalDate? = null
    var registradoPor: UsuarioSub? = null
    var dataCriacao: LocalDateTime? = null
    var dataAlteracao: LocalDateTime? = null
    var documentos: MutableList<Documento> = mutableListOf()
    var qrCodeId: Int? = null
    var celular: String? = null

    @PrePersist
    fun onCreate() {
        dataCriacao = LocalDateTime.now()
        dataAlteracao = LocalDateTime.now()
    }

    @PreUpdate
    fun onUpdate() {
        dataAlteracao = LocalDateTime.now()
    }
}

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
