package com.dicoding.auliarosyida.academy.data.source.remote

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.auliarosyida.academy.data.source.remote.response.ContentResponse
import com.dicoding.auliarosyida.academy.data.source.remote.response.CourseResponse
import com.dicoding.auliarosyida.academy.data.source.remote.response.ModuleResponse
import com.dicoding.auliarosyida.academy.utils.EspressoIdlingResource
import com.dicoding.auliarosyida.academy.utils.JsonHelper

/**
 *  kelas repository untuk remote
 *
 *  bagaimana RemoteDataSource di panggil:
 *  Ketika Anda memanggil RemoteDataSource, kelas tersebut membutuhkan masukan Context untuk inisialisasi JsonHelper.
 *  Context ini digunakan untuk mengambil data dari asset.
 *  Jadi Anda harus membuat kelas Injection, untuk meng-inject context ke dalam RemoteDataSource ketika ViewModel dipanggil.
 * */
class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {

    /**
     * Handler dari package android.os ketika pengambilan data.
     * Handler berfungsi untuk memberikan waktu delay sesuai dengan kebutuhan.
     *
     *  Handler untuk delay, seperti yang dilakukan di sini merupakah hal yang tidak disarankan.
     *  Karena aplikasi yang kita buat hanyalah simulasi, di mana data yang diperoleh disimulasikan berasal dari server atau API.
     *  Maka dari itu, penggunaan Handler pada aplikasi Academy digunakan untuk mensimulasikan proses asynchonous yang terjadi.
     * */
    private val handler = Handler(Looper.getMainLooper())

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        /**
         *  method getInstance yang berfungsi untuk membuat kelas RemoteDataSource sebagai Singleton.
         *  Fungsinya yaitu supaya tercipta satu instance saja di dalam JVM.
         * */
        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource(helper).apply { instance = this }
            }
    }

    //semua data yang diambil dari Repository sampai ke ViewModel akan dibungkus dengan LiveData
    fun getAllCourses(): LiveData<ApiResponse<List<CourseResponse>>> {
        EspressoIdlingResource.increment()
        val resultCourse = MutableLiveData<ApiResponse<List<CourseResponse>>>()

        handler.postDelayed({
            resultCourse.value = ApiResponse.success(jsonHelper.loadCourses())
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)

        return resultCourse
    }

    fun getModules(courseId: String): LiveData<ApiResponse<List<ModuleResponse>>> {
        EspressoIdlingResource.increment()
        val resultModules = MutableLiveData<ApiResponse<List<ModuleResponse>>>()

        handler.postDelayed({
            resultModules.value = ApiResponse.success(jsonHelper.loadModule(courseId))
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)

        return resultModules
    }

    fun getContent(moduleId: String): LiveData<ApiResponse<ContentResponse>> {
        EspressoIdlingResource.increment()
        val resultContent = MutableLiveData<ApiResponse<ContentResponse>>()

        handler.postDelayed({
            resultContent.value = ApiResponse.success(jsonHelper.loadContent(moduleId))
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)

        return resultContent
    }

    interface LoadCoursesCallback {
        fun onAllCoursesReceived(courseResponses: List<CourseResponse>)
    }
    interface LoadModulesCallback {
        fun onAllModulesReceived(moduleResponses: List<ModuleResponse>)
    }
    interface LoadContentCallback {
        fun onContentReceived(contentResponse: ContentResponse)
    }

}