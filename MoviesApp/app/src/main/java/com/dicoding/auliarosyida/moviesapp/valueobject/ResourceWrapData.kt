package com.dicoding.auliarosyida.moviesapp.valueobject

data class ResourceWrapData <T>(val status: IndicatorStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): ResourceWrapData<T> = ResourceWrapData(IndicatorStatus.SUCCESS, data, null)

        fun <T> error(msg: String?, data: T?): ResourceWrapData<T> = ResourceWrapData(IndicatorStatus.ERROR, data, msg)

        fun <T> loading(data: T?): ResourceWrapData<T> = ResourceWrapData(IndicatorStatus.LOADING, data, null)
    }
}