package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Usuario
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository : MongoRepository<Usuario, String> {

    @Query("{'email': ?0}")
    fun findByEmail(username: String): Optional<Usuario>

    @Query(value = "{'empresa._id': ?0}", sort = "{'nome': 1}")
    fun findByEmpresaAndOrderByNomeAsc(idEmpresa: String): List<Usuario>

    @Query(sort = "{'nome': 1}")
    fun findAllByOrderByNomeAsc(): List<Usuario>
}