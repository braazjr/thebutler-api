package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Empresa
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmpresaRepository : MongoRepository<Empresa, String> {

    @Query("{'cnpj': ?0}")
    fun findByCnpj(cnpj: String?): Optional<Empresa>

}
