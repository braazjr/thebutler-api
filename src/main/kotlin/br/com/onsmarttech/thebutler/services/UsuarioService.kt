package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Usuario
import br.com.onsmarttech.thebutler.documents.convertEmpresaToSub
import br.com.onsmarttech.thebutler.dtos.UsuarioDto
import br.com.onsmarttech.thebutler.dtos.convertDtoToUsuario
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.exception.NotFoundException
import br.com.onsmarttech.thebutler.repositories.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

        preencheEmpresa(principal, usuarioDto, usuario)

        if (!usuario.senha.isNullOrBlank()) {
            val encoder = BCryptPasswordEncoder()
            usuario.senha = encoder.encode(usuario.email)
        }

        return usuarioRepository.save(usuario)
    }

    private fun preencheEmpresa(principal: Principal?, usuarioDto: UsuarioDto, usuario: Usuario) {
        if (principal != null) {
            val usuarioLogado: Optional<Usuario> = usuarioRepository.findByEmail(principal.name)

            if (usuarioLogado.get().isAdmin()) {
                val empresa = empresaService.getById(usuarioDto.empresaId!!)
                usuario.empresa = empresa?.let { convertEmpresaToSub(it) }
            } else {
                usuario.empresa = usuarioLogado.get().empresa
            }
        }
    }

    fun list(pageable: Pageable): Page<Usuario> {
        val usuarioOptional: Optional<Usuario> = usuarioRepository.findByEmail(getUsuarioLogado().email!!)

        return if (!usuarioOptional.get().isAdmin()) {
            usuarioRepository.findByEmpresaAndOrderByNomeAsc(usuarioOptional.get().empresa!!.id!!, pageable)
        } else usuarioRepository.findAllByOrderByNomeAsc(pageable)
    }

    fun getUsuarioLogado() = usuarioRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().name)
            .orElseThrow { NotFoundException("Usuário não encontrado") }

    fun deletar(id: String) {
        getById(id)
        usuarioRepository.deleteById(id)
    }

    fun getById(id: String) = usuarioRepository.findById(id)
            .orElseThrow { BadRequestException("Usuário não encontrado") }

    fun getMotoristaById(motoristaId: String?) = usuarioRepository.findMotoristaById(motoristaId)
            .orElseThrow { BadRequestException("Motorista não encontrado") }

    fun getMotoristas(): Any {
        val usuarioLogado = getUsuarioLogado()
        return usuarioRepository.findByMotoristas(usuarioLogado.empresa!!.id)
    }

    fun atualizar(principal: Principal?, id: String, usuarioDto: UsuarioDto): Any {
        getById(id)
        val usuario: Usuario = convertDtoToUsuario(usuarioDto)
        preencheEmpresa(principal, usuarioDto, usuario)
        return usuarioRepository.save(usuario)
    }

}
