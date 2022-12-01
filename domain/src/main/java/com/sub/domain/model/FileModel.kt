package com.sub.domain.model

data class FileModel(
    val filePath: String?,
    val fileName: String?
) {
    class Builder(
        private var filePath: String? = null,
        private var fileName: String? = null
    ) {
        fun setFilePath(v: String?) = apply { this.filePath = v }
        fun setFileName(v: String?) = apply { this.fileName = v }
        fun build() = FileModel(filePath = filePath, fileName = fileName)
    }
}