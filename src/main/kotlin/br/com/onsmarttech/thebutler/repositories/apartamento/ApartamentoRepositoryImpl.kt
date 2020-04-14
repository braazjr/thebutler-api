package br.com.onsmarttech.thebutler.repositories.apartamento

import br.com.onsmarttech.thebutler.documents.Apartamento
import br.com.onsmarttech.thebutler.dtos.ApartamentoFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class ApartamentoRepositoryImpl : ApartamentoRepositoryQuery {

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    override fun find(filter: ApartamentoFilter, pageable: Pageable): PageImpl<Apartamento> {
        val query = Query()
        query.with(pageable)

        filterCheck(query, filter)

        val apartamentos = mongoTemplate.find(query, Apartamento::class.java)
        val count = mongoTemplate.count(query, Apartamento::class.java)

        return PageImpl<Apartamento>(apartamentos, pageable, count)
    }

    private fun filterCheck(query: Query, filter: ApartamentoFilter) {
        if (!filter.numero.isNullOrBlank()) {
            query.addCriteria(Criteria.where("numero").`is`(filter.numero))
        }
        if (!filter.idBloco.isNullOrBlank() && filter.idBloco != "0") {
            query.addCriteria(Criteria.where("bloco.id").`is`(filter.idBloco))
        } else if (!filter.idCondominio.isNullOrBlank() && filter.idCondominio != "0") {
            query.addCriteria(Criteria.where("bloco.condominio.id").`is`(filter.idCondominio))
        }
    }
}