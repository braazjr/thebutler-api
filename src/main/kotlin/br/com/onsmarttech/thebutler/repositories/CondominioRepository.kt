package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Condominio
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CondominioRepository : MongoRepository<Condominio, String> {

}
