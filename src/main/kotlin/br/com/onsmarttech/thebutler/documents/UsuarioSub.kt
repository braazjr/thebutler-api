package br.com.onsmarttech.thebutler.documents

data class UsuarioSub(
        val id: String?,
        val email: String?,
        val nome: String?
)

fun convertUsuarioToSub(usuario: Usuario) = UsuarioSub(usuario.id, usuario.email, usuario.nome)