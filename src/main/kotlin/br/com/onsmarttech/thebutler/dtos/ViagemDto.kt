package br.com.onsmarttech.thebutler.dtos

import br.com.onsmarttech.thebutler.documents.*
import org.springframework.data.annotation.Id
import java.time.LocalDateTime
import java.time.LocalTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class ViagemDto(
        @Id val id: String?,
        @get:NotBlank val rotaId: String?,
        @get:NotBlank val motoristaId: String?,
        @get:NotNull val dataHoraInicio: LocalDateTime?,
        @get:NotNull val dataHoraFim: LocalDateTime?,
        val passageiros: MutableList<ViagemUsuarioDto>?
)

data class ViagemUsuarioDto(
        @get:NotBlank val passageiroId: String?,
        @get:NotNull val horario: LocalTime?
)

fun convertDtoToViagem(dto: ViagemDto, rota: Rota, motorista: Usuario, passageiros: List<ViagemMorador>) =
        Viagem(dto.id?: null, rota, convertUsuarioToSub(motorista), dto.dataHoraInicio!!, dto.dataHoraFim!!,
                passageiros as MutableList<ViagemMorador>)