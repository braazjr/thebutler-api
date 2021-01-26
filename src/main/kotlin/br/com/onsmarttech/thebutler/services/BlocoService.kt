package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Bloco
import br.com.onsmarttech.thebutler.documents.BlocoSub
import br.com.onsmarttech.thebutler.documents.convertBlocoToSub
import br.com.onsmarttech.thebutler.documents.convertCondominioToSub
import br.com.onsmarttech.thebutler.dtos.BlocoDto
import br.com.onsmarttech.thebutler.dtos.convertDtoToBloco
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.repositories.BlocoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BlocoService {

    @Autowired
    private lateinit var condominioService: CondominioService

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var blocoRepository: BlocoRepository

    fun save(blocoDto: BlocoDto): Bloco {
        val condominio = condominioService.getById(blocoDto.idCondominio)
        val usuarioLogado = usuarioService.getUsuarioLogado()
        val bloco = convertDtoToBloco(blocoDto, condominio, usuarioLogado)

        return blocoRepository.save(bloco)
    }

    fun list(): List<Bloco> {
        val usuarioLogado = usuarioService.getUsuarioLogado()
        if (!usuarioLogado.isAdmin()) {
            return blocoRepository.findByEmpresa(usuarioLogado.empresa!!.id)
        }

        return blocoRepository.findAll()
    }

    fun findById(idBloco: String?) = blocoRepository.findById(idBloco!!)
            .orElseThrow { BadRequestException("Bloco não encontrado") }

    fun delete(id: String) {
        blocoRepository.findById(id)
                .orElseThrow { BadRequestException("Bloco não encontrado") }

        blocoRepository.deleteById(id)
    }

    fun update(id: String, blocoDto: BlocoDto): Bloco {
        if (id != blocoDto.id) {
            throw BadRequestException("Id do path e body não conferem")
        }
        findById(id)

        return save(blocoDto)
    }

    fun findByIdAndUpdated(id: String?): BlocoSub? {
        val bloco = findById(id)
        bloco.condominio = convertCondominioToSub(condominioService.getById(bloco.condominio!!.id))
        return convertBlocoToSub(bloco)
    }

}
