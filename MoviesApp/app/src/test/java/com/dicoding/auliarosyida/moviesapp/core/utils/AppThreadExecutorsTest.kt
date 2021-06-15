package com.dicoding.auliarosyida.moviesapp.core.utils

import java.util.concurrent.Executor

class AppThreadExecutorsTest : Executor{
    override fun execute(command: Runnable) {
        command.run()
    }
}