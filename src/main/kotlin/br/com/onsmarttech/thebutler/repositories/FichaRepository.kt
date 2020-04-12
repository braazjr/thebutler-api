package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Ficha
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface FichaRepository : MongoRepository<Ficha, String> {

}
