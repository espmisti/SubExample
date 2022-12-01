package com.sub.domain.model

data class CodeModel(
    val url: String?,
    val code: String?,
    val log_active: Int?
) {
    class Builder(
        private var code: String? = null,
        private var url: String? = null,
        private var log_active: Int? = null
        ) {
        fun setUrl(v: String?) = apply { this.url = v }
        fun setCode(v: String?) = apply { this.code = v }
        fun setLog(v: Int?) = apply { this.log_active = v }

        fun build(): CodeModel = CodeModel(code = code, url = url, log_active = log_active)
    }
}