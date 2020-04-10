package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Apartamento
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ApartamentoRepository : MongoRepository<Apartamento, String> {

    @Query("{'bloco.condominio.empresa': ?0}")
    fun findByEmpresa(id: String?, pageable: Pageable): Page<Apartamento>
}
