package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.BravaSoftConfiguration
import br.com.onsmarttech.thebutler.documents.Empresa
import br.com.onsmarttech.thebutler.dtos.EmpresaDto
import br.com.onsmarttech.thebutler.dtos.convertDtoToEmpresa
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.repositories.ApartamentoRepository
import br.com.onsmarttech.thebutler.repositories.EmpresaRepository
import br.com.onsmarttech.thebutler.util.onlyAlphanumerics
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmpresaService {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var empresaRepository: EmpresaRepository

    @Autowired
    private lateinit var apartamentoRepository: ApartamentoRepository

    fun listar(): List<Empresa> {
        if (!usuarioService.getUsuarioLogado().isAdmin()) {
            throw BadRequestException("Você não tem permissão para esse conteúdo")
        }

        return empresaRepository.findAll()
    }

    fun save(empresaDto: EmpresaDto): Empresa {
        val usuarioLogado = usuarioService.getUsuarioLogado()

        if (
                (empresaDto.id.isNullOrBlank() && empresaRepository.findByCnpj(onlyAlphanumerics(empresaDto.cnpj!!)).isPresent)
                ||
                (!empresaDto.id.isNullOrBlank() && empresaRepository.findByCnpjAndIdNot(onlyAlphanumerics(empresaDto.cnpj!!), empresaDto.id).isPresent)
        ) {
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

    fun getById(empresaId: String): Empresa? {
        var empresa = empresaRepository.findById(empresaId)
                .orElseThrow { BadRequestException("Empresa não encontrada") }

        val countApartamentos = apartamentoRepository.count()
        empresa.empresaConfig!!.apartamentosCadastrados = countApartamentos

        return empresa
    }

    fun update(id: String, empresaDto: EmpresaDto): Empresa {
        if (id != empresaDto.id) {
            throw BadRequestException("Id do path e body não conferem")
        }
        getById(id)

        return save(empresaDto)
    }

    fun saveBravaSoftConfiguration(id: String, bravaSoftConfiguration: BravaSoftConfiguration): Empresa {
        val empresa = getById(id)
        empresa!!.empresaConfig!!.bravaSoftConfiguration = bravaSoftConfiguration

        return empresaRepository.save(empresa)
    }

}
