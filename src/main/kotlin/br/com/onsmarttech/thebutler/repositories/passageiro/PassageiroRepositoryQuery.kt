package br.com.onsmarttech.thebutler.repositories.passageiro

import br.com.onsmarttech.thebutler.documents.Passageiro
import br.com.onsmarttech.thebutler.dtos.PassageiroFilter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PassageiroRepositoryQuery {

    fun find(passageiroFilter: PassageiroFilter, pageable: Pageable): Page<Passageiro>

}
