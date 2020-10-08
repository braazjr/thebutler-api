package br.com.onsmarttech.thebutler.repositories.morador

import br.com.onsmarttech.thebutler.documents.Morador
import br.com.onsmarttech.thebutler.dtos.MoradorFilter
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

interface MoradorRepositoryQuery {

    fun find(filter: MoradorFilter, pageable: Pageable): PageImpl<Morador>
}