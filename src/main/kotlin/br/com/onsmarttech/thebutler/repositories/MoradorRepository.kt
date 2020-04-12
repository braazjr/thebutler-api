package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Morador
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MoradorRepository : MongoRepository<Morador, String> {

}
