package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Documento
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DocumentRepository : MongoRepository<Documento, String> {

}
