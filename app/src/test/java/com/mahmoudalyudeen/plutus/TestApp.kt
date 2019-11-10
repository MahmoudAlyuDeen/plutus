package com.mahmoudalyudeen.plutus

/** Bypassing our custom [App] to prevent dependency injection while running unit tests */
@Suppress("unused")
class TestApp : App() {

    override fun onCreate() = Unit

}