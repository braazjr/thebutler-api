package br.com.onsmarttech.thebutler.security

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer

class CustomTokenEnhancer : TokenEnhancer {

    override fun enhance(accessToken: OAuth2AccessToken?, authentication: OAuth2Authentication?): OAuth2AccessToken {
        val user = authentication!!.name

        val additionalInfo = HashMap<String, Any>()
        additionalInfo.put("username", user)
        (accessToken as DefaultOAuth2AccessToken).additionalInformation = additionalInfo

        return accessToken
    }
}