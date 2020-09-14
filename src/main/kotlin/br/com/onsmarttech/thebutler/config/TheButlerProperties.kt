package br.com.onsmarttech.thebutler.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "thebutler")
class TheButlerProperties {
    lateinit var originsPermitidas: List<String?>
    var s3: S3 = S3()

    class S3 {
        lateinit var profile: String
        lateinit var accessKeyId: String
        lateinit var secretAccessKey: String
        lateinit var bucketPhotos: String
        lateinit var bucketDocuments: String
    }
}