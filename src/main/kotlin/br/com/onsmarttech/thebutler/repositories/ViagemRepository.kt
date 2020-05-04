package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Viagem
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ViagemRepository : MongoRepository<Viagem, String> {

}
