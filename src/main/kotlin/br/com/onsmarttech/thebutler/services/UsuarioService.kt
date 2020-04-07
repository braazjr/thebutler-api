package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Usuario
import br.com.onsmarttech.thebutler.documents.convertEmpresaToSub
import br.com.onsmarttech.thebutler.dtos.UsuarioDto
import br.com.onsmarttech.thebutler.dtos.convertDtoToUsuario
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.exception.NotFoundException
import br.com.onsmarttech.thebutler.repositories.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.security.Principal
import java.util.*

@Service
class UsuarioService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var empresaService: EmpresaService

    fun salvar(principal: Principal?, usuarioDto: UsuarioDto): Any {
        var usuario: Usuario = convertDtoToUsuario(usuarioDto)

        if (usuarioRepository.findByEmail(usuario.email!!).isPresent) {
            throw BadRequestException("O login já existe em um usuário")
        }

        if (principal != null) {
            val usuarioLogado: Optional<Usuario> = usuarioRepository.findByEmail(principal.name)

            if (usuarioLogado.get().isAdmin() && !usuarioDto.idEmpresa.isNullOrBlank()) {
                val empresa = empresaService.getById(usuarioDto.idEmpresa!!)
                        .orElseThrow { BadRequestException("Empresa não encontrada") }

                usuario.empresa = convertEmpresaToSub(empresa)
            } else {
                usuario.empresa = usuarioLogado.get().empresa
            }
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
            usuarioRepository.findByEmpresaAndOrderByNomeAsc(usuarioOptional.get().empresa!!.id!!)
        } else usuarioRepository.findAllByOrderByNomeAsc()

    }

    fun getUsuario() = usuarioRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().name)
            .orElseThrow { NotFoundException("Usuário não encontrado") }

    fun deletar(id: String) {
        if (!usuarioRepository.findById(id).isPresent) {
            throw BadRequestException("Usuário não encontrada")
        }

        usuarioRepository.deleteById(id)
    }

}
