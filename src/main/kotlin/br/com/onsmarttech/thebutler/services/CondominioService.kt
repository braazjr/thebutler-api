package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Condominio
import br.com.onsmarttech.thebutler.dtos.CondominioDto
import br.com.onsmarttech.thebutler.dtos.convertDtoToCondominio
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.repositories.CondominioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class CondominioService {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var condominioRepository: CondominioRepository

    fun save(condominioDto: CondominioDto): Condominio {
        val usuarioLogado = usuarioService.getUsuario()
        val condominio = convertDtoToCondominio(condominioDto, usuarioLogado)

        return condominioRepository.save(condominio)
    }

    fun list(): List<Condominio> {
        val usuarioLogado = usuarioService.getUsuario()
        if (!usuarioLogado.isAdmin()) {
            return condominioRepository.findByEmpresa(usuarioLogado.empresa!!.id)
        }

        return condominioRepository.findAll()
    }

    fun getById(idCondominio: String?) = condominioRepository.findById(idCondominio!!)
            .orElseThrow { BadRequestException("Condominínio não encontrado") }

    fun delete(id: String) {
        condominioRepository.findById(id)
                .orElseThrow { BadRequestException("Condomínio não encontrado") }

        condominioRepository.deleteById(id)
    }

    fun update(id: String, condominioDto: CondominioDto): Condominio {
        if (id != condominioDto.id) {
            throw BadRequestException("Id do path e body não conferem")
        }
        getById(id)

        return save(condominioDto)
    }

}
