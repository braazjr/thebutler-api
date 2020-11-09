package br.com.onsmarttech.thebutler.security

import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.repositories.EmpresaRepository
import br.com.onsmarttech.thebutler.repositories.UsuarioRepository
import br.com.onsmarttech.thebutler.services.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.stereotype.Component

@Component
class CustomTokenEnhancer : TokenEnhancer {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var empresaRepository: EmpresaRepository

    override fun enhance(accessToken: OAuth2AccessToken?, authentication: OAuth2Authentication?): OAuth2AccessToken {
        val username = authentication!!.name
        val usuario = usuarioRepository.findByEmail(username)
                .orElseThrow { BadRequestException("Usuário não encontrado") }

        if (usuario.empresa != null) {
            val empresa = empresaRepository.findById(usuario.empresa!!.id!!)
                    .orElseThrow { BadRequestException("Empresa não encontrada") }

            empresa.perfis.forEach { usuario.permissoes!!.add(it) }
        }

        val additionalInfo = HashMap<String, Any>()
        additionalInfo["usuario"] = usuario
        (accessToken as DefaultOAuth2AccessToken).additionalInformation = additionalInfo

        return accessToken
    }
}