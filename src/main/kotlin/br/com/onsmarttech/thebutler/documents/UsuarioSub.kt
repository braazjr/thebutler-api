package br.com.onsmarttech.thebutler.documents

data class UsuarioSub(
        val id: String?,
        val email: String?,
        val nome: String?,
        val empresaId: String?
)

fun convertUsuarioToSub(usuario: Usuario) = UsuarioSub(usuario.id, usuario.email, usuario.nome, usuario.empresa?.id)