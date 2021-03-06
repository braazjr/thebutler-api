package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Rota
import br.com.onsmarttech.thebutler.documents.convertUsuarioToSub
import br.com.onsmarttech.thebutler.repositories.RotaRepository
import com.amazonaws.services.mq.model.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RotaService {

    @Autowired
    private lateinit var rotaRepository: RotaRepository

    @Autowired
    private lateinit var usuarioService: UsuarioService

    fun save(rota: Rota): Rota {
        rota.registradoPor = convertUsuarioToSub(usuarioService.getUsuarioLogado())
        return rotaRepository.save(rota)
    }

    fun list() =
        if (usuarioService.getUsuarioLogado().isAdmin()) {
            rotaRepository.findAll()
        } else {
            rotaRepository.findByEmpresaId(usuarioService.getUsuarioLogado().empresa!!.id)
        }

    fun findById(id: String) = rotaRepository.findById(id)
            .orElseThrow { BadRequestException("Rota não encontrada") }
}
