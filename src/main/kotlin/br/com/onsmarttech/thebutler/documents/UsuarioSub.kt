package br.com.onsmarttech.thebutler.documents

data class UsuarioSub(
        val id: String?,
        val username: String?,
        val nome: String?,
        val empresaId: String?
)

fun convertUsuarioToSub(usuario: Usuario) = UsuarioSub(usuario.id, usuario.username, usuario.nome, usuario.empresa?.id)