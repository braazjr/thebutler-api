package br.com.onsmarttech.thebutler.security

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

    override fun enhance(accessToken: OAuth2AccessToken?, authentication: OAuth2Authentication?): OAuth2AccessToken {
        val username = authentication!!.name
        val usuario = usuarioRepository.findByEmail(username)

        val additionalInfo = HashMap<String, Any>()
        additionalInfo.put("usuario", usuario.get())
        (accessToken as DefaultOAuth2AccessToken).additionalInformation = additionalInfo

        return accessToken
    }
}