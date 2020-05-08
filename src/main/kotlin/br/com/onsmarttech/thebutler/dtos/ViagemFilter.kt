package br.com.onsmarttech.thebutler.dtos

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class ViagemFilter(
        val rotaId: String?,
        val motoristaId: String?,
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") val dataHoraInicioDe: LocalDateTime?,
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") val dataHoraInicioPara: LocalDateTime?,
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") val dataHoraFimDe: LocalDateTime?,
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") val dataHoraFimPara: LocalDateTime?,
        var empresaId: String?,
        var moradorId: String?
)
