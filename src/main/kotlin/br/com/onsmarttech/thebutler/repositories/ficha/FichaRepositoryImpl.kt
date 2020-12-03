package br.com.onsmarttech.thebutler.repositories.ficha

import br.com.onsmarttech.thebutler.documents.Ficha
import br.com.onsmarttech.thebutler.dtos.FichaFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.time.LocalDate

class FichaRepositoryImpl : FichaRepositoryQuery {

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    override fun find(filter: FichaFilter, pageable: Pageable): PageImpl<Ficha> {
        val query = Query()
        query.with(pageable)
        val queryCount = Query()

        filterCheck(query, filter)
        filterCheck(queryCount, filter)

        val fichas = mongoTemplate.find(query, Ficha::class.java)
        val count = mongoTemplate.count(queryCount, Ficha::class.java)

        return PageImpl<Ficha>(fichas, pageable, count)
    }

    private fun filterCheck(query: Query, filter: FichaFilter) {
        if (!filter.codigo.isNullOrBlank()) {
            query.addCriteria(Criteria.where("id").`is`(filter.codigo))
        }
        if (!filter.numeroApartamento.isNullOrBlank()) {
            query.addCriteria(Criteria.where("apartamento.numero").`is`(filter.numeroApartamento))
        }
        if (!filter.idBloco.isNullOrBlank() && filter.idBloco != "0") {
            query.addCriteria(Criteria.where("apartamento.bloco.id").`is`(filter.idBloco))
        } else if (!filter.idCondominio.isNullOrBlank() && filter.idCondominio != "0") {
            query.addCriteria(Criteria.where("apartamento.bloco.condominio.id").`is`(filter.idCondominio))
        }
        if (!filter.empresaId.isNullOrBlank() && filter.empresaId != "0") {
            query.addCriteria(Criteria.where("apartamento.bloco.condominio.empresa.id").`is`(filter.empresaId))
        }
        if (!filter.dataInicioDe.isNullOrBlank() && !filter.dataInicioPara.isNullOrBlank()) {
            query.addCriteria(
                    Criteria.where("dataInicio").gte(LocalDate.parse(filter.dataInicioDe)).lte(LocalDate.parse(filter.dataInicioPara))
            )
        } else if (!filter.dataInicioDe.isNullOrBlank()) {
            query.addCriteria(Criteria.where("dataInicio").gte(LocalDate.parse(filter.dataInicioDe)))
        } else if (!filter.dataInicioPara.isNullOrBlank()) {
            query.addCriteria(Criteria.where("dataInicio").lte(LocalDate.parse(filter.dataInicioPara)))
        }
        if (!filter.dataFimDe.isNullOrBlank() && !filter.dataFimPara.isNullOrBlank()) {
            query.addCriteria(
                    Criteria.where("dataFim").gte(LocalDate.parse(filter.dataFimDe)).lte(LocalDate.parse(filter.dataFimPara))
            )
        } else if (!filter.dataFimDe.isNullOrBlank()) {
            query.addCriteria(Criteria.where("dataFim").gte(LocalDate.parse(filter.dataInicioDe)))
        } else if (!filter.dataFimPara.isNullOrBlank()) {
            query.addCriteria(Criteria.where("dataFim").lte(LocalDate.parse(filter.dataInicioPara)))
        }
    }
}