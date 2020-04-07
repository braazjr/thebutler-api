package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Bloco
import br.com.onsmarttech.thebutler.dtos.BlocoDto
import br.com.onsmarttech.thebutler.dtos.convertDtoToBloco
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

    fun salvar(blocoDto: BlocoDto): Bloco {
        val condominio = condominioService.getById(blocoDto.idCondominio)
        val usuarioLogado = usuarioService.getUsuario()
        val bloco = convertDtoToBloco(blocoDto, condominio, usuarioLogado)

        return blocoRepository.save(bloco)
    }

}
