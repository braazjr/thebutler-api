package br.com.onsmarttech.thebutler.repositories.viagem

import br.com.onsmarttech.thebutler.documents.Viagem
import br.com.onsmarttech.thebutler.dtos.ViagemFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class ViagemRepositoryImpl : ViagemRepositoryQuery {

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    override fun find(filter: ViagemFilter, pageable: Pageable): Page<Viagem> {
        val query = Query()
        query.with(pageable)

        filterCheck(query, filter)

        val viagens = mongoTemplate.find(query, Viagem::class.java)
        val count = mongoTemplate.count(query, Viagem::class.java)

        return PageImpl<Viagem>(viagens, pageable, count)
    }

    private fun filterCheck(query: Query, filter: ViagemFilter) {
        if (!filter.empresaId.isNullOrBlank() && filter.empresaId != "0") {
            query.addCriteria(Criteria.where("motorista.empresaId").`is`(filter.empresaId))
        }
        if (!filter.motoristaId.isNullOrBlank() && filter.motoristaId != "0") {
            query.addCriteria(Criteria.where("motorista.id").`is`(filter.motoristaId))
        }
        if (!filter.rotaId.isNullOrBlank() && filter.rotaId != "0") {
            query.addCriteria(Criteria.where("rota.id").`is`(filter.rotaId))
        }
        if (!filter.moradorId.isNullOrBlank() && filter.moradorId != "0") {
            query.addCriteria(Criteria.where("moradores").elemMatch(Criteria.where("morador.id").`is`(filter.moradorId)))
        }
        if (filter.dataHoraInicioDe != null && filter.dataHoraInicioPara != null) {
            query.addCriteria(
                    Criteria.where("dataHoraInicio").gte(filter.dataHoraInicioDe).lte(filter.dataHoraInicioPara)
            )
        } else if (filter.dataHoraInicioDe != null) {
            query.addCriteria(Criteria.where("dataHoraInicio").gte(filter.dataHoraInicioDe))
        } else if (filter.dataHoraInicioPara != null) {
            query.addCriteria(Criteria.where("dataHoraInicio").lte(filter.dataHoraInicioPara))
        }
        if (filter.dataHoraFimDe != null && filter.dataHoraFimPara != null) {
            query.addCriteria(
                    Criteria.where("dataHoraFim").gte(filter.dataHoraFimDe).lte(filter.dataHoraFimPara)
            )
        } else if (filter.dataHoraFimDe != null) {
            query.addCriteria(Criteria.where("dataHoraFim").gte(filter.dataHoraFimDe))
        } else if (filter.dataHoraFimPara != null) {
            query.addCriteria(Criteria.where("dataHoraFim").lte(filter.dataHoraFimPara))
        }
        if (!filter.moradorNome.isNullOrBlank()) {
            query.addCriteria(Criteria.where("moradores").elemMatch(Criteria.where("morador.nome").regex(filter.moradorNome, "i")))
        }
        if (!filter.moradorEmail.isNullOrBlank()) {
            query.addCriteria(Criteria.where("moradores").elemMatch(Criteria.where("morador.email").regex(filter.moradorEmail, "i")))
        }
    }
}