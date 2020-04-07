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

    fun salvar(principal: Principal, condominioDto: CondominioDto): Any {
        val usuarioLogado = usuarioService.getUsuario()
        val condominio = convertDtoToCondominio(condominioDto, usuarioLogado)

        return condominioRepository.save(condominio)
    }

    fun listar(): List<Condominio> {
        val usuarioLogado = usuarioService.getUsuario()
        if (!usuarioLogado.isAdmin()) {
            return condominioRepository.findByEmpresa(usuarioLogado.empresa!!.id)
        }

        return condominioRepository.findAll()
    }

    fun getById(idCondominio: String?) = condominioRepository.findById(idCondominio!!)
            .orElseThrow { BadRequestException("Condominínio não encontrado") }

}
