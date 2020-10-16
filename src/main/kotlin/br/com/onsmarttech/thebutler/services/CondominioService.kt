package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Condominio
import br.com.onsmarttech.thebutler.documents.convertEmpresaToSub
import br.com.onsmarttech.thebutler.dtos.CondominioDto
import br.com.onsmarttech.thebutler.dtos.convertDtoToCondominio
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.repositories.CondominioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CondominioService {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var condominioRepository: CondominioRepository

    @Autowired
    private lateinit var empresaService: EmpresaService

    fun save(condominioDto: CondominioDto): Condominio {
        val usuarioLogado = usuarioService.getUsuarioLogado()
        val condominio = convertDtoToCondominio(condominioDto, usuarioLogado)

        if (usuarioService.getUsuarioLogado().isAdmin() && !condominioDto.empresaId.isNullOrBlank()) {
            condominio.empresa = convertEmpresaToSub(empresaService.getById(condominioDto.empresaId)!!)
        }

        return condominioRepository.save(condominio)
    }

    fun list(): List<Condominio> {
        val usuarioLogado = usuarioService.getUsuarioLogado()
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
