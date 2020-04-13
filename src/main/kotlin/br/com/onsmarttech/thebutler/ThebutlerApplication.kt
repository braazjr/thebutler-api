package br.com.onsmarttech.thebutler

import br.com.onsmarttech.thebutler.config.TheButlerProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(TheButlerProperties::class)
class ThebutlerApplication

fun main(args: Array<String>) {
    runApplication<ThebutlerApplication>(*args)
}
