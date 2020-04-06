package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.dtos.CondominioDto
import br.com.onsmarttech.thebutler.dtos.convertDtoToCondominio
import br.com.onsmarttech.thebutler.repositories.CondominioRepository
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class CondominioService(
        val usuarioService: UsuarioService,
        val condominioRepository: CondominioRepository
) {

    fun salvar(principal: Principal, condominioDto: CondominioDto): Any {
        val usuarioLogado = usuarioService.getUsuario(principal)
        val condominio = convertDtoToCondominio(condominioDto, usuarioLogado)

        return condominioRepository.save(condominio)
    }

}
