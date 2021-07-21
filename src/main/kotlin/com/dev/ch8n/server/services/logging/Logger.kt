package com.dev.ch8n.server.services.logging

import com.dev.ch8n.server.AppConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.*
import java.io.File
import kotlin.reflect.KClass

private val test = flow {
    while (true) {
        delay(1000)
        emit(true)
    }
}

fun main() {
    runBlocking {
        launch {
            test.collect {
                println("first")
            }
        }
        launch {
            test.collect {
                delay(500)
                println("second")
            }
        }
    }
}

interface Log {
    fun d(message: String)
    fun e(error: Throwable)
}


class Logger private constructor(val tagName: String) : Log {

    companion object {
        fun newInstance(tag: String): Log = Logger(tag)
        fun newInstance(className: KClass<*>): Log = Logger(className.simpleName.toString())
        private val _instance = Logger("Logger")
        val instance get() = _instance
    }

    private val LogFilesLimit = 10
    private val logFolderName = "${AppConfig.PROJECT_ROOT}.logs".replace(".", File.separator)
    private var logFile: File? = null

    fun configureLog() {
        val logFolder = File(logFolderName)
        logFolder.createDirectory()
        val logfileName = "$logFolderName${File.separator}${date()}.txt"
        val newLogFile = File(logfileName)
        if (!newLogFile.exists()) {
            logFile?.appendText("\n : Continue : ${newLogFile.path}")
            logFile = newLogFile
            logFile?.createNewFile()
        }
    }

    fun limitLogFiles() {
        val logFolder = File(logFolderName)
        val logfiles = logFolder.walk().filter { it.isFile && it.name.contains(".txt") }.toList()
        val isCleanupRequired = logfiles.size > LogFilesLimit
        if (isCleanupRequired) {
            val sorted = logfiles.sortedByDescending { it.nameWithoutExtension.toLocalDate() }
            sorted.forEachIndexed { index, file ->
                if (index >= LogFilesLimit) {
                    file.delete()
                }
            }
        }
    }

    override fun d(message: String) {
        configureLog()
        logFile?.appendText("\n${Clock.System.now()} : Debug : $tagName $message")
    }

    override fun e(error: Throwable) {
        configureLog()
        logFile?.appendText("\n${Clock.System.now()} : Error : $tagName ${error.localizedMessage}")
        val traceBuilder = error.stackTrace.fold(StringBuilder()) { builder, trace ->
            builder.append(trace).append("\n")
        }
        logFile?.appendText("\n${Clock.System.now()} : Error : $traceBuilder")
    }



    private fun date(): String {
        val currentMoment: Instant = Clock.System.now()
        val datetimeInUtc: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.UTC)
        val date = datetimeInUtc.date
        return date.toString()
    }


}

private inline fun File.createDirectory() {
    if (!this.exists()) {
        this.mkdir()
    }
}


