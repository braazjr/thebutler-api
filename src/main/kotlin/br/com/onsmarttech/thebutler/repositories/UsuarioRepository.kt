package br.com.onsmarttech.thebutler.repositories

import br.com.onsmarttech.thebutler.documents.Usuario
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository : MongoRepository<Usuario, String> {

    @Query("{'email': ?0}")
    fun findByEmail(username: String): Optional<Usuario>

    @Query(value = "{'empresa._id': ?0}", sort = "{'nome': 1}")
    fun findByEmpresaAndOrderByNomeAsc(empresaId: String, pageable: Pageable): Page<Usuario>

    @Query(sort = "{'nome': 1}")
    fun findAllByOrderByNomeAsc(pageable: Pageable): Page<Usuario>

    @Query("{'id': ?0, 'permissoes': {\$in: ['MOTORISTA']}}")
    fun findMotoristaById(motoristaId: String?): Optional<Usuario>

    @Query("{'permissoes': {\$in: ['MOTORISTA']}, 'empresa._id': ?0}")
    fun findByMotoristas(empresaId: String?): List<Usuario>
}