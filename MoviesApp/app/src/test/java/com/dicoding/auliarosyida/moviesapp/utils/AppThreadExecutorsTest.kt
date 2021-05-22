package com.dicoding.auliarosyida.moviesapp.utils

import java.util.concurrent.Executor

class AppThreadExecutorsTest : Executor{
    override fun execute(command: Runnable) {
        command.run()
    }
}