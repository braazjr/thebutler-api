package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Ficha
import br.com.onsmarttech.thebutler.repositories.ficha.FichaRepositoryQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FichaRepository : MongoRepository<Ficha, String>, FichaRepositoryQuery {

    @Query("{'apartamento.id': ?0}")
    fun findByApartamentoId(apartamentoId: String): List<Ficha>

    @Query("{'moradores': {'\$elemMatch': {'id': ?0} } }")
    fun findByMoradorId(moradorId: String): List<Ficha>

    @Query("{'documentos': {'\$elemMatch': {'id': ?0} } }")
    fun findByDocumentoId(documentoId: String): Ficha

    @Query("{'id': ?0, 'documentos': {'\$elemMatch': {'id': ?1} } }")
    fun findByIdAndDocumentoId(id: String, documentoId: String): Optional<Ficha>

}
