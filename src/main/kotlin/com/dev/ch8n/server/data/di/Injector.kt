package com.dev.ch8n.server.data.di

import com.dev.ch8n.server.data.repositories.GreetingRepository

object Injector {
    val greetingRepository by lazy { GreetingRepository() }
}