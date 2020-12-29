package br.com.onsmarttech.thebutler.dtos

import br.com.onsmarttech.thebutler.documents.ApartamentoSub
import org.springframework.data.annotation.Id
import javax.validation.constraints.NotBlank

class MoradorSimple(
        @Id var id: String?,
        @get:NotBlank val documento: String?,
        val ativo: Boolean?,
        val email: String?,
        @get:NotBlank val nome: String?,
        val foto64: String?,
        var apartamento: ApartamentoSub?,
        var qrCodeId: String? = null
) {}
