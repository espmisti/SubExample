package com.sub.domain.model

data class CodeModel(
    val url: String?,
    val code: String?
) {
    class Builder(private val code: String?, private val url: String?) {
        fun build(): CodeModel = CodeModel(code = code, url = url)
    }
}