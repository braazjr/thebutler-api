package br.com.onsmarttech.thebutler.repositories.morador

import br.com.onsmarttech.thebutler.documents.Morador
import br.com.onsmarttech.thebutler.dtos.MoradorFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class MoradorRepositoryImpl : MoradorRepositoryQuery {

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    override fun find(filter: MoradorFilter, pageable: Pageable): PageImpl<Morador> {
        val query = Query()
        query.with(pageable)

        filterCheck(query, filter)

        val moradores = mongoTemplate.find(query, Morador::class.java)
        val count = mongoTemplate.count(query, Morador::class.java)

        return PageImpl<Morador>(moradores, pageable, count)
    }

    private fun filterCheck(query: Query, filter: MoradorFilter) {
        if (!filter.blocoId.isNullOrBlank() && filter.blocoId != "0") {
            query.addCriteria(Criteria.where("apartamento.bloco.id").`is`(filter.blocoId))
        } else if (!filter.condominioId.isNullOrBlank() && filter.condominioId != "0") {
            query.addCriteria(Criteria.where("apartamento.bloco.condominio.id").`is`(filter.condominioId))
        } else if (!filter.empresaId.isNullOrBlank() && filter.empresaId != "0") {
            query.addCriteria(Criteria.where("apartamento.bloco.condominio.empresa.id").`is`(filter.empresaId))
        }
        if (!filter.nome.isNullOrBlank()) {
            query.addCriteria(Criteria.where("nome").`is`(filter.nome))
        }
        if (!filter.documento.isNullOrBlank()) {
            query.addCriteria(Criteria.where("documento").`is`(filter.documento))
        }
        if (!filter.apartamentoNumero.isNullOrBlank()) {
            query.addCriteria(Criteria.where("apartamento.numero").`is`(filter.apartamentoNumero))
        }
    }
}