package com.dev.ch8n.server.data.repositories

class GreetingRepository {
    fun getGreeting(name: String = ""): String {
        return "Hello $name!"
    }
}