package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Empresa
import br.com.onsmarttech.thebutler.dtos.EmpresaDto
import br.com.onsmarttech.thebutler.dtos.convertDtoToEmpresa
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.repositories.EmpresaRepository
import br.com.onsmarttech.thebutler.util.onlyAlphanumerics
import org.springframework.stereotype.Service

@Service
class EmpresaService(
        private val usuarioService: UsuarioService,
        private val empresaRepository: EmpresaRepository
) {

    fun listar(): List<Empresa> {
        if (!usuarioService.getUsuario().isAdmin()) {
            throw BadRequestException("Você não tem permissão para esse conteúdo")
        }

        return empresaRepository.findAll()
    }

    fun salvar(empresaDto: EmpresaDto): Any {
        val usuarioLogado = usuarioService.getUsuario()

        if (empresaRepository.findByCnpj(onlyAlphanumerics(empresaDto.cnpj!!)).isPresent) {
            throw BadRequestException("Já possui uma empresa cadastrada com o CNPJ informado")
        }

        val empresa = convertDtoToEmpresa(empresaDto, usuarioLogado)

        return empresaRepository.save(empresa)
    }

    fun deletar(id: String) {
        if (!empresaRepository.findById(id).isPresent) {
            throw BadRequestException("Empresa não encontrada")
        }

        empresaRepository.deleteById(id)
    }

    fun getById(idEmpresa: String) = empresaRepository.findById(idEmpresa)

}