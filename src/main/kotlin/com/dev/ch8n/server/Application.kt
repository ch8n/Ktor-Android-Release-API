package com.dev.ch8n.server

import com.dev.ch8n.server.middleware.registerMiddleware
import com.dev.ch8n.server.routes.registerRouters
import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*


fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    registerMiddleware()
    registerRouters()
}



