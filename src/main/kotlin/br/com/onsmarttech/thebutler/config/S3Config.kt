package br.com.onsmarttech.thebutler.config

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration
import com.amazonaws.services.s3.model.CreateBucketRequest
import com.amazonaws.services.s3.model.Tag
import com.amazonaws.services.s3.model.lifecycle.LifecycleFilter
import com.amazonaws.services.s3.model.lifecycle.LifecycleTagPredicate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config {

    @Autowired
    private lateinit var property: TheButlerProperties

    @Bean
    fun amazonS3(): AmazonS3 {
        val credentials: AWSCredentials = BasicAWSCredentials(property.s3.accessKeyId, property.s3.secretAccessKey)
        val amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build()

        if (!amazonS3.doesBucketExistV2(property.s3.bucketPhotos)) {
            amazonS3.createBucket(CreateBucketRequest(property.s3.bucketPhotos))
            val expirationRule = BucketLifecycleConfiguration.Rule()
                    .withId("Regra de expiração de arquivos temporários")
                    .withFilter(LifecycleFilter(LifecycleTagPredicate(Tag("expirar", "true"))))
                    .withExpirationInDays(1)
                    .withStatus(BucketLifecycleConfiguration.ENABLED)

            val configuration = BucketLifecycleConfiguration()
                    .withRules(expirationRule)

            amazonS3.setBucketLifecycleConfiguration(property.s3.bucketPhotos, configuration)
        }

        if (!amazonS3.doesBucketExistV2(property.s3.bucketDocuments)) {
            amazonS3.createBucket(CreateBucketRequest(property.s3.bucketDocuments))
            val expirationRule = BucketLifecycleConfiguration.Rule()
                    .withId("Regra de expiração de arquivos temporários")
                    .withFilter(LifecycleFilter(LifecycleTagPredicate(Tag("expirar", "true"))))
                    .withExpirationInDays(1)
                    .withStatus(BucketLifecycleConfiguration.ENABLED)

            val configuration = BucketLifecycleConfiguration()
                    .withRules(expirationRule)

            amazonS3.setBucketLifecycleConfiguration(property.s3.bucketDocuments, configuration)
        }

        return amazonS3
    }
}