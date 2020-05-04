package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Viagem
import br.com.onsmarttech.thebutler.documents.ViagemMorador
import br.com.onsmarttech.thebutler.documents.convertMoradorToSub
import br.com.onsmarttech.thebutler.dtos.ViagemDto
import br.com.onsmarttech.thebutler.dtos.ViagemUsuarioDto
import br.com.onsmarttech.thebutler.dtos.convertDtoToViagem
import br.com.onsmarttech.thebutler.repositories.ViagemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ViagemService {

    @Autowired
    private lateinit var viagemRepository: ViagemRepository

    @Autowired
    private lateinit var rotaService: RotaService

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var moradorService: MoradorService

    fun save(dto: ViagemDto): Viagem {
        println(dto)

        val rota = rotaService.findById(dto.rotaId!!)
        val motorista = usuarioService.getMotoristaById(dto.motoristaId)
        val passageiros = getPassageiros(dto.passageiros?: mutableListOf())
        val viagem = convertDtoToViagem(dto, rota, motorista, passageiros)

        return viagemRepository.save(viagem)
    }

    private fun getPassageiros(passageiros: MutableList<ViagemUsuarioDto>) =
            passageiros
                    .map {
                        val morador = moradorService.findById(it.passageiroId!!)
                        ViagemMorador(convertMoradorToSub(morador), it.horario!!)
                    }
}

