package br.com.onsmarttech.thebutler.repositories.ficha

import br.com.onsmarttech.thebutler.documents.Ficha
import br.com.onsmarttech.thebutler.dtos.FichaFilter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FichaRepositoryQuery {

    fun find(filter: FichaFilter, pageable: Pageable): Page<Ficha>
}
