package br.com.onsmarttech.thebutler.repositories.passageiro

import br.com.onsmarttech.thebutler.documents.Passageiro
import br.com.onsmarttech.thebutler.dtos.PassageiroFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class PassageiroRepositoryImpl : PassageiroRepositoryQuery {

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    override fun find(filter: PassageiroFilter, pageable: Pageable): PageImpl<Passageiro> {
        val query = Query()
        query.with(pageable)

        filterCheck(query, filter)

        val passageiros = mongoTemplate.find(query, Passageiro::class.java)
        val count = mongoTemplate.count(query, Passageiro::class.java)

        return PageImpl<Passageiro>(passageiros, pageable, count)
    }

    private fun filterCheck(query: Query, filter: PassageiroFilter) {
        if (!filter.empresaId.isNullOrBlank() && filter.empresaId != "0") {
            query.addCriteria(Criteria.where("empresa.id").`is`(filter.empresaId))
        }
        if (!filter.nome.isNullOrBlank()) {
            query.addCriteria(Criteria.where("nome").regex(".*${filter.nome}*.", "i"))
        }
        if (!filter.documento.isNullOrBlank()) {
            query.addCriteria(Criteria.where("documento").regex(".*${filter.documento}*.", "i"))
        }
        if (!filter.email.isNullOrBlank()) {
            query.addCriteria(Criteria.where("email").regex(".*${filter.email}*.", "i"))
        }
        if (filter.ativo != null) {
            query.addCriteria(Criteria.where("ativo").`is`(filter.ativo))
        }
        if (filter.dataInicio != null) {
            query.addCriteria(Criteria.where("dataInicio").gte(filter.dataInicio))
        }
        if (filter.dataFim != null) {
            query.addCriteria(Criteria.where("dataFim").gte(filter.dataFim))
        }
    }
}