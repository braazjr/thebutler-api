package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Empresa
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface EmpresaRepository : MongoRepository<Empresa, String> {

}
