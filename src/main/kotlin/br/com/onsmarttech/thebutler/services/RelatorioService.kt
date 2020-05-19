package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.ViagemMorador
import br.com.onsmarttech.thebutler.repositories.MoradorRepository
import br.com.onsmarttech.thebutler.repositories.ViagemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RelatorioService {

    @Autowired
    private lateinit var moradorRepository: MoradorRepository

    @Autowired
    private lateinit var viagemRepository: ViagemRepository

    fun moradoresSemFoto() = moradorRepository.moradoresSemFoto()

    fun moradoresSemDocumento() = moradorRepository.moradoresSemDocumento()

    fun viagensMes(): List<ViagemMorador> {
        return viagemRepository.mes()
    }

}
