package br.com.onsmarttech.thebutler.repositories.apartamento

import br.com.onsmarttech.thebutler.documents.Apartamento
import br.com.onsmarttech.thebutler.dtos.ApartamentoFilter
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

interface ApartamentoRepositoryQuery {

    fun find(filter: ApartamentoFilter, pageable: Pageable): PageImpl<Apartamento>
}