package com.dicoding.auliarosyida.academy.di

import android.content.Context
import com.dicoding.auliarosyida.academy.data.source.AcademyRepository
import com.dicoding.auliarosyida.academy.data.source.local.LocalDataSource
import com.dicoding.auliarosyida.academy.data.source.local.room.AcademyDatabase
import com.dicoding.auliarosyida.academy.data.source.remote.RemoteDataSource
import com.dicoding.auliarosyida.academy.utils.AppExecutors
import com.dicoding.auliarosyida.academy.utils.JsonHelper

/**
 * kelas Injection, untuk meng-inject context ke dalam RemoteDataSource ketika ViewModel dipanggil.
 * Dengan object ini, Anda bisa memanggil AcademyRepository ketika menghubungkan ViewModel dengan Activity atau Fragment.
 * */
object Injection {

    fun provideRepository(context: Context): AcademyRepository {

        val database = AcademyDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.academyDao())
        val appExecutors = AppExecutors()

        return AcademyRepository.getInstance(remoteDataSource,localDataSource, appExecutors)
    }
}