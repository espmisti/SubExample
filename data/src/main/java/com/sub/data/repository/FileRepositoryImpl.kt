package com.sub.data.repository

import android.content.Context
import android.util.Log
import com.sub.common.Constants
import com.sub.domain.model.FileModel
import com.sub.domain.repository.FileRepository
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class FileRepositoryImpl(private val context: Context) : FileRepository {
    override suspend fun sendFile(filePath: String, fileName: String) : Boolean{
        val client = FTPClient()
        with(client) {
            connect(Constants.FTP.IP, 21)
            if(FTPReply.isPositiveCompletion(replyCode)) {
                login(Constants.FTP.LOGIN, Constants.FTP.PASSWORD)
                setFileType(FTP.BINARY_FILE_TYPE)
                enterLocalPassiveMode()
                val inputStream: InputStream = FileInputStream(filePath)
                storeFile(fileName, inputStream)
                inputStream.close()
                logout()
                disconnect()
                return true
            } else return false
        }
    }

    override suspend fun createFile(code: String, flowkey: String) : FileModel {
        val sdf = SimpleDateFormat("dd.M.yyyy hh:mm:ss (SSSS)", Locale.getDefault())
        val currentDate = sdf.format(Date())
        val path = context.getExternalFilesDir(null)
        val folder = File(path, "log")
        folder.mkdirs()
        val fileName = "[$flowkey][$currentDate].txt"
        val file = File(folder, fileName)
        file.appendText(code)
        return FileModel.Builder()
            .setFileName(v = fileName)
            .setFilePath(v = file.path)
            .build()
    }
}