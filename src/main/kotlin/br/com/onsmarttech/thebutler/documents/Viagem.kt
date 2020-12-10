package br.com.onsmarttech.thebutler.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.validation.constraints.NotNull

@Document("viagens")
data class Viagem(
        @Id val id: String? = "",
        @get:NotNull val rota: Rota,
        @get:NotNull val motorista: UsuarioSub,
        @get:NotNull val dataHoraInicio: LocalDateTime,
        @get:NotNull val dataHoraFim: LocalDateTime,
        val moradores: MutableList<ViagemMorador>
)

data class ViagemMorador(
        @get:NotNull val morador: MoradorSub,
        @get:NotNull val horario: LocalDateTime
)
