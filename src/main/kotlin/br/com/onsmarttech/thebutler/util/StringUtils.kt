package br.com.onsmarttech.thebutler.util

fun onlyAlphanumerics(s: String) = Regex("[^A-Za-z0-9]").replace(s, "")