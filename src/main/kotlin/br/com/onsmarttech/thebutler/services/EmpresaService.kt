package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Empresa
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.repositories.EmpresaRepository
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class EmpresaService(
        val usuarioService: UsuarioService,
        val empresaRepository: EmpresaRepository
) {

    fun listar(principal: Principal): List<Empresa> {
        if (!usuarioService.getUsuario(principal).isAdmin()) {
            throw BadRequestException("Você não tem permissão para esse conteúdo")
        }

        return empresaRepository.findAll()
    }

}
