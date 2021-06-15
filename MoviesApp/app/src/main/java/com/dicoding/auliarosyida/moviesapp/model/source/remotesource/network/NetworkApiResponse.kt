package com.dicoding.auliarosyida.moviesapp.model.source.remotesource.network

import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.StatusNetworkResponse

class NetworkApiResponse<T>(val status: StatusNetworkResponse, val body: T, val message: String?) {
    companion object {
        fun <T> success(body: T): NetworkApiResponse<T> = NetworkApiResponse(StatusNetworkResponse.SUCCESS, body, null)

        fun <T> error(msg: String, body: T): NetworkApiResponse<T> = NetworkApiResponse(
            StatusNetworkResponse.ERROR, body, msg)
    }
}