package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Ficha
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FichaRepository : MongoRepository<Ficha, String> {

    @Query("{'apartamento.id': ?0}")
    fun findByApartamentoId(apartamentoId: String): List<Ficha>

    @Query("{'moradores': {'\$elemMatch': {'id': ?0} } }")
    fun findByMoradorId(moradorId: String): List<Ficha>

    @Query("{'apartamento.bloco.condominio.empresa.id': ?0}")
    fun findByEmpresa(empresaId: String?, pageable: Pageable): Page<Ficha>

    @Query("{'documentos': {'\$elemMatch': {'id': ?0} } }")
    fun findByDocumentoId(documentoId: String): Ficha

}
