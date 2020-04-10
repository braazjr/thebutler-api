package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Apartamento
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ApartamentoRepository : MongoRepository<Apartamento, String> {

}
