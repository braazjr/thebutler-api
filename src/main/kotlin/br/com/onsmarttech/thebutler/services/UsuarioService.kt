package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Usuario
import br.com.onsmarttech.thebutler.enums.Permissao
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.exception.NotFoundException
import br.com.onsmarttech.thebutler.repositories.UsuarioRepository
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.security.Principal
import java.util.*
import java.util.stream.Collectors

@Service
class UsuarioService(val usuarioRepository: UsuarioRepository) {

    fun salvar(principal: Principal?, usuario: Usuario): Any {
        if (principal != null) {
            val usuarioOptional: Optional<Usuario> = usuarioRepository.findByEmail(principal.getName())

            if (!usuarioOptional.get().isAdmin()) {
                usuario.empresa = usuarioOptional.get().empresa
            }

            if (usuario.empresa == null) {
                throw BadRequestException("A empresa é obrigatória")
            }
        }

        if (usuarioRepository.findByEmail(usuario.email).isPresent()) {
            throw BadRequestException("O login já existe em um usuário")
        }

        if (!StringUtils.hasText(usuario.senha)) {
            val encoder = BCryptPasswordEncoder()
            usuario.senha = encoder.encode(usuario.email)
        }

        return usuarioRepository.save(usuario)
    }

    fun listar(principal: Principal?): List<Usuario> {
        val usuarioOptional: Optional<Usuario> = usuarioRepository.findByEmail(principal!!.name)

        return if (!usuarioOptional.get().isAdmin()) {
            usuarioRepository.findByEmpresaAndOrderByNomeAsc(usuarioOptional.get().empresa!!.id)
        } else usuarioRepository.findAllByOrderByNomeAsc()

    }

    fun getUsuario(principal: Principal) = usuarioRepository.findByEmail(principal.name)
            .orElseThrow { NotFoundException("Usuário não encontrado") }

}
