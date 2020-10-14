package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.dtos.FichaJasperDto
import com.google.gson.Gson
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.data.JsonDataSource
import net.sf.jasperreports.engine.util.JRLoader
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

@Service
class JasperReportsService {

    fun fichaGenerate(ficha: FichaJasperDto): ByteArray {
        val inputStream = this.javaClass.getResourceAsStream("/reports/FichaApartamento.jasper")
        val subReport = this.javaClass.getResourceAsStream("/reports/FichaMoradores.jasper")

        val json = Gson().toJson(ficha)
        val dataSource = JsonDataSource(ByteArrayInputStream(json.toByteArray()))

        val paramters = mutableMapOf("SUBREPORT_PATH" to JRLoader.loadObject(subReport))

        val print = JasperFillManager.fillReport(inputStream, paramters as Map<String, Any>?, dataSource)
        val bos = ByteArrayOutputStream()
        JasperExportManager.exportReportToPdfStream(print, bos)
        return bos.toByteArray()
    }
}