package br.com.onsmarttech.convitesimplesapi.util

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class PasswordGenerate

fun main(args: Array<String>) {
    val encoder = BCryptPasswordEncoder()
    println(encoder.encode("thebutler_angular"))
}