package br.com.onsmarttech.thebutler.documents

data class EmpresaSub(
        val id: String?,
        val cnpj: String?,
        val nomeFantasia: String?,
        val razaoSocial: String?,
        val email: String?
)

fun convertEmpresaToSub(empresa: Empresa) = EmpresaSub(empresa.id, empresa.cnpj, empresa.nomeFantasia, empresa.razaoSocial,
        empresa.email)