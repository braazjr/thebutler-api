package br.com.onsmarttech.thebutler.repositories.viagem

import br.com.onsmarttech.thebutler.documents.Viagem
import br.com.onsmarttech.thebutler.documents.ViagemMorador
import br.com.onsmarttech.thebutler.dtos.ViagemFilter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ViagemRepositoryQuery {

    fun find(filter: ViagemFilter, pageable: Pageable): Page<Viagem>

    fun mes(): List<ViagemMorador>
}