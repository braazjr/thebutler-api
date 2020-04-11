package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Apartamento
import br.com.onsmarttech.thebutler.dtos.ApartamentoDto
import br.com.onsmarttech.thebutler.dtos.convertDtoToApartamento
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.repositories.ApartamentoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ApartamentoService {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var blocoService: BlocoService

    @Autowired
    private lateinit var apartamentoRepository: ApartamentoRepository

    fun save(apartamentoDto: ApartamentoDto): Apartamento {
        val usuarioLogado = usuarioService.getUsuario()
        val bloco = blocoService.findById(apartamentoDto.idBloco)
        val apartamento = convertDtoToApartamento(apartamentoDto, bloco, usuarioLogado)

        return apartamentoRepository.save(apartamento)
    }

    fun list(pageable: Pageable): Page<Apartamento> {
        val usuarioLogado = usuarioService.getUsuario()
        if (!usuarioLogado.isAdmin()) {
            return apartamentoRepository.findByEmpresa(usuarioLogado.empresa!!.id, pageable)
        }

        return apartamentoRepository.findAll(pageable)
    }

    fun findById(id: String) = apartamentoRepository.findById(id)
            .orElseThrow { BadRequestException("Apartamento não encontrado") }

    fun delete(id: String) {
        apartamentoRepository.findById(id)
                .orElseThrow { BadRequestException("Apartamento não encontrada") }

        apartamentoRepository.deleteById(id)
    }
}
