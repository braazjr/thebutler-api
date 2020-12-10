package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Morador
import br.com.onsmarttech.thebutler.dtos.MoradorSimple
import br.com.onsmarttech.thebutler.repositories.morador.MoradorRepositoryQuery
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MoradorRepository : MongoRepository<Morador, String>, MoradorRepositoryQuery {

    @Query("{'id': {\$in: ?0}}")
    fun findInIds(map: List<String?>): List<Morador>

    @Query(value = "{\$or: [{'foto64': {\$exists: false}}, {'foto64': ''}]}")
    fun moradoresSemFoto(): List<Morador>

    fun deleteByIdIn(ids: List<String?>)

    @Query(value = "{'documento': ''}")
    fun moradoresSemDocumento(): List<Morador>

    @Query("{'apartamento.bloco.condominio.empresa.id': ?0}")
    fun findSimpleByEmpresaId(empresaId: String?): List<MoradorSimple>

    @Query("{'id': ?0, 'documentos': {'\$elemMatch': {'id': ?1} } }")
    fun findByIdAndDocumentoId(id: String, documentoId: String): Optional<Morador>

    @Query("{\$or: [{'fichaId': {\$exists: false}}, {'fichaId': ''}], 'apartamento.bloco.condominio.empresa.id': ?0}")
    fun findMoradoresSemFicha(empresaId: String): List<Morador>

    @Query("{'qrCodeId': ?0}")
    fun findByQrCodeId(passageiroId: Int): Optional<Morador>
}
