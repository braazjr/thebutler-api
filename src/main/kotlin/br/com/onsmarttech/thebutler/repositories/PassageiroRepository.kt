package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Passageiro
import br.com.onsmarttech.thebutler.repositories.passageiro.PassageiroRepositoryQuery
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PassageiroRepository : MongoRepository<Passageiro, String>, PassageiroRepositoryQuery {

    @Query("{'id': ?0, 'documentos': {'\$elemMatch': {'id': ?1} } }")
    fun findByIdAndDocumentoId(id: String, documentoId: String): Optional<Passageiro>

    @Query("{'responsavelId': ?0}")
    fun findByResponsavelId(id: String): MutableList<Passageiro>

}
