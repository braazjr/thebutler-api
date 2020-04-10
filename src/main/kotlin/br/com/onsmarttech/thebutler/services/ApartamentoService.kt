package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.dtos.ApartamentoDto
import br.com.onsmarttech.thebutler.dtos.convertDtoToApartamento
import br.com.onsmarttech.thebutler.repositories.ApartamentoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ApartamentoService {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var blocoService: BlocoService

    @Autowired
    private lateinit var apartamentoRepository: ApartamentoRepository

    fun salvar(apartamentoDto: ApartamentoDto): Any {
        val usuarioLogado = usuarioService.getUsuario()
        val bloco = blocoService.findById(apartamentoDto.idBloco)
        val apartamento = convertDtoToApartamento(apartamentoDto, bloco, usuarioLogado)

        return apartamentoRepository.save(apartamento)
    }

}
