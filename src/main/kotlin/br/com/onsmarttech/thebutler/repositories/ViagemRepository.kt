package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Viagem
import br.com.onsmarttech.thebutler.repositories.viagem.ViagemRepositoryQuery
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ViagemRepository : MongoRepository<Viagem, String>, ViagemRepositoryQuery {

    @Query("{\$expr: {\$eq: [{\$month}]}}")
    fun mes(mes: Int, ano: Int)
}
