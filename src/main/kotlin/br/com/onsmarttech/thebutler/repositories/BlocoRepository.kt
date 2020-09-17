package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Bloco
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BlocoRepository : MongoRepository<Bloco, String> {

    @Query("{'condominio.empresa.id': ?0}")
    fun findByEmpresa(empresaId: String?): List<Bloco>

}
