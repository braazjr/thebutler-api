package br.com.onsmarttech.thebutler.security

import br.com.onsmarttech.thebutler.repositories.UsuarioRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
        val usuarioRepository: UsuarioRepository,
        val logger: Logger = LoggerFactory.getLogger("UserDetailsServiceImpl")
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = usuarioRepository.findByUsername(username!!)
                .orElseThrow { UsernameNotFoundException("Usuário e/ou senha incorretos") }

        logger.info("---> Usuário $username acabou de logar")

        return User(username, usuario.senha, listOf())
    }
}