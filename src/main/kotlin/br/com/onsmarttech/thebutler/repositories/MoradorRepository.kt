package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Morador
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MoradorRepository : MongoRepository<Morador, String> {

    @Query("{'id': {\$in: ?0}}")
    fun findInIds(map: List<String?>): List<Morador>

}
