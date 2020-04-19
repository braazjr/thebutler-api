package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Rota
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RotaRepository : MongoRepository<Rota, String> {

    @Query("{'registradoPor.empresaId': ?0}")
    fun findByEmpresaId(empresaId: String?): List<Rota>
}
