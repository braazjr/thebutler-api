package br.com.onsmarttech.thebutler.util

import br.com.onsmarttech.thebutler.config.TheButlerProperties
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths


@Component
class S3Util {

    private var logger: Logger = LoggerFactory.getLogger("S3Util")

    @Autowired
    private lateinit var amazonS3: AmazonS3

    @Autowired
    private lateinit var property: TheButlerProperties

    fun saveDocument(name: String, file: MultipartFile): String {
        val nameSplit = name.split("/")
        val documentName = nameSplit.get(nameSplit.size - 1) + "." + file.contentType!!.split("/").toTypedArray()[1]
        val targetFile = File(javaClass.getResource("/uploads").path + "/$documentName")
        convertInputStreamToFile(file.inputStream, targetFile)
        logger.info("---> AQUIVO TEMP SALVO!")

        saveFileOnS3(property.s3.bucketDocumentos, name, targetFile)

        try {
            deleteTempFile(targetFile)
        } catch (e: IOException) {
            e.printStackTrace()
            throw Exception("Ocorreu um erro ao salvar o documento no S3!")
        }

        return urlConfigure(property.s3.bucketDocumentos, name)
    }

    fun saveFileOnS3(bucket: String?, nomeArquivoFoto: String, arquivo: File?) {
        amazonS3.putObject(PutObjectRequest(bucket, nomeArquivoFoto, arquivo)
                .withCannedAcl(CannedAccessControlList.PublicRead))
        logger.info("---> ARQUIVO '$nomeArquivoFoto' NO AWS S3!")
    }

    @Throws(IOException::class)
    private fun deleteTempFile(arquivo: File) {
        Files.delete(Paths.get(arquivo.path))
        logger.info("---> ARQUIVO TEMP DELETADO!")
    }

    fun urlConfigure(bucket: String, objeto: String) = "\\\\$bucket.s3.amazonaws.com/$objeto"

    fun convertInputStreamToFile(inputStream: InputStream, targetFile: File) {
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)

        val outStream: OutputStream = FileOutputStream(targetFile)
        outStream.write(buffer)
    }
}