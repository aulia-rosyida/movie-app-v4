package com.dicoding.auliarosyida.moviesapp.core.data.api

sealed class RetrofitApiStatus<out T> {
    data class Success<out T>(val data: T) : RetrofitApiStatus<T>()
    data class Failed(val error: String) : RetrofitApiStatus<Nothing>()
    object Empty : RetrofitApiStatus<Nothing>()
}