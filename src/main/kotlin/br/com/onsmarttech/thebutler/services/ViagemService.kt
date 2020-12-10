package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Viagem
import br.com.onsmarttech.thebutler.documents.ViagemMorador
import br.com.onsmarttech.thebutler.documents.convertMoradorToSub
import br.com.onsmarttech.thebutler.dtos.ViagemDto
import br.com.onsmarttech.thebutler.dtos.ViagemFilter
import br.com.onsmarttech.thebutler.dtos.ViagemUsuarioDto
import br.com.onsmarttech.thebutler.dtos.convertDtoToViagem
import br.com.onsmarttech.thebutler.repositories.ViagemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
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
        val viagem = convertViagemForSave(dto)
        return viagemRepository.save(viagem)
    }

    fun saveMultiples(dtos: List<ViagemDto>): MutableList<Viagem> {
        val viagens = convertViagensForSave(dtos)
        return viagemRepository.saveAll(viagens)
    }

    private fun convertViagensForSave(dtos: List<ViagemDto>) = dtos.map { convertViagemForSave(it) }

    private fun convertViagemForSave(dto: ViagemDto): Viagem {
        val rota = rotaService.findById(dto.rotaId!!)
        val motorista = usuarioService.getMotoristaById(dto.motoristaId)
        val passageiros = getPassageiros(dto.passageiros ?: mutableListOf())
        return convertDtoToViagem(dto, rota, motorista, passageiros)
    }

    private fun getPassageiros(passageiros: MutableList<ViagemUsuarioDto>) =
            passageiros
                    .map {
                        val morador = moradorService.findByQrCodeId(it.passageiroId!!)
                        ViagemMorador(convertMoradorToSub(morador), it.horario!!)
                    }

    fun getAll(filter: ViagemFilter, pageable: Pageable): Any {
        val userLogged = usuarioService.getUsuarioLogado()
        filter.empresaId = userLogged.empresa!!.id!!

        return viagemRepository.find(filter, pageable)
    }
}

