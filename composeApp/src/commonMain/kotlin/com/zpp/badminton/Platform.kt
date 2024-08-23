package com.zpp.badminton

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform