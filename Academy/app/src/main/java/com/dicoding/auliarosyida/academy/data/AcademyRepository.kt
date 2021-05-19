package com.dicoding.auliarosyida.academy.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.auliarosyida.academy.data.source.local.LocalDataSource
import com.dicoding.auliarosyida.academy.data.source.local.entity.CourseEntity
import com.dicoding.auliarosyida.academy.data.source.local.entity.CourseWithModule
import com.dicoding.auliarosyida.academy.data.source.local.entity.ModuleEntity
import com.dicoding.auliarosyida.academy.data.source.remote.ApiResponse
import com.dicoding.auliarosyida.academy.data.source.remote.RemoteDataSource
import com.dicoding.auliarosyida.academy.data.source.remote.response.ContentResponse
import com.dicoding.auliarosyida.academy.data.source.remote.response.CourseResponse
import com.dicoding.auliarosyida.academy.data.source.remote.response.ModuleResponse
import com.dicoding.auliarosyida.academy.utils.AppExecutors
import com.dicoding.auliarosyida.academy.vo.Resource

/**
 * kelas untuk menghubungkan RemoteDataSource.
 * AcademyRepository sebagai filter antara remote dan local.
 * */
class AcademyRepository private constructor(
    private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : AcademyDataSource {

    companion object {
        @Volatile
        private var instance: AcademyRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): AcademyRepository =
            instance ?: synchronized(this) {
                AcademyRepository(remoteData, localData, appExecutors).apply { instance = this }
            }
    }

    /**
     * metode getAllCourses melakukan perubahan data array dari CourseResponse menjadi CourseEntity.
     * Ini dilakukan agar apa yang ada di View tidak banyak berubah.
     *
     * Sehingga di dalam AcademyViewModel bisa langsung memanggil getAllCourses()
     * */
    override fun getAllCourses(): LiveData<Resource<PagedList<CourseEntity>>> {

        return object : NetworkBoundResource<PagedList<CourseEntity>, List<CourseResponse>>(appExecutors) {

            public override fun loadFromDB(): LiveData<PagedList<CourseEntity>>  {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllCourses(), config).build()
            }

            override fun shouldFetch(data: PagedList<CourseEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<CourseResponse>>> =
                remoteDataSource.getAllCourses()

            public override fun saveCallResult(data: List<CourseResponse>) {
                val courseList = ArrayList<CourseEntity>()
                for (response in data) {
                    val course = CourseEntity(response.id,
                        response.title,
                        response.description,
                        response.date,
                        false,
                        response.imagePath)
                    courseList.add(course)
                }

                localDataSource.insertCourses(courseList)
            }
        }.asLiveData()
    }

    override fun setCourseBookmark(course: CourseEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setCourseBookmark(course, state) }

    override fun setReadModule(module: ModuleEntity) =
        appExecutors.diskIO().execute { localDataSource.setReadModule(module) }

    /**
     * , dari DataSource.Factory bisa di ubah menjadi LiveData<PagedList<CourseEntity> dengan LivePagedListBuilder
     * */
    override fun getBookmarkedCourses(): LiveData<PagedList<CourseEntity>>  {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4) //ketika data akan di-load, bisa Anda kustom seperti kode di atas akan di-load sebanyak 4 item sekali.
            .build()
        return LivePagedListBuilder(localDataSource.getBookmarkedCourses(), config).build()
    }

    override fun getCourseWithModules(courseId: String): LiveData<Resource<CourseWithModule>> {

        return object : NetworkBoundResource<CourseWithModule, List<ModuleResponse>>(appExecutors) {

            override fun loadFromDB(): LiveData<CourseWithModule> =
                localDataSource.getCourseWithModules(courseId)

            override fun shouldFetch(data: CourseWithModule?): Boolean =
                data?.mModules == null || data.mModules.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ModuleResponse>>> =
                remoteDataSource.getModules(courseId)

            override fun saveCallResult(data: List<ModuleResponse>) {
                val moduleList = ArrayList<ModuleEntity>()
                for (response in data) {
                    val course = ModuleEntity(response.moduleId,
                        response.courseId,
                        response.title,
                        response.position,
                        false)
                    moduleList.add(course)
                }
                localDataSource.insertModules(moduleList)
            }
        }.asLiveData()
    }

    override fun getAllModulesByCourse(courseId: String): LiveData<Resource<List<ModuleEntity>>> {

        return object : NetworkBoundResource<List<ModuleEntity>, List<ModuleResponse>>(appExecutors) {

            override fun loadFromDB(): LiveData<List<ModuleEntity>> =
                localDataSource.getAllModulesByCourse(courseId)

            override fun shouldFetch(data: List<ModuleEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ModuleResponse>>> =
                remoteDataSource.getModules(courseId)

            override fun saveCallResult(data: List<ModuleResponse>) {
                val moduleList = ArrayList<ModuleEntity>()
                for (response in data) {
                    val course = ModuleEntity(response.moduleId,
                        response.courseId,
                        response.title,
                        response.position,
                        false)

                    moduleList.add(course)
                }

                localDataSource.insertModules(moduleList)
            }
        }.asLiveData()
    }

    override fun getContent(moduleId: String): LiveData<Resource<ModuleEntity>> {

        return object : NetworkBoundResource<ModuleEntity, ContentResponse>(appExecutors) {

            //digunakan untuk membaca getAllCourses dari LocalDataSource
            override fun loadFromDB(): LiveData<ModuleEntity> =
                localDataSource.getModuleWithContent(moduleId)

            /**
             * Pada bagian shoudFetch ini dilakukan pengecekan apakah ada datanya atau tidak.
             * Jika balikan dari pengecekan itu true maka akan memanggil fungsi createCall()
             * */
            override fun shouldFetch(data: ModuleEntity?): Boolean =
                data?.contentEntity == null

            override fun createCall(): LiveData<ApiResponse<ContentResponse>> =
                remoteDataSource.getContent(moduleId)

            /**
             * Karena data dari LocalDataSource null atau empty, maka akan dilakukan pengambilan data dari RemoteDataSource.
             * Dan selanjutnya akan dilakukan proses inserting
             *
             * Metode saveCallResult akan terpanggil dan akan melakukan proses insert data yang berasal dari RemoteDataSource.
             * Kemudian hasilnya dari NetworkBoundResource dibungkus dengan kelas Resource,
             *
             * sehingga di bagian Activity atau Fragment akan dapat dipanggil dengan viewModel.getCourses().observe(...
             * */
            override fun saveCallResult(data: ContentResponse) =
                localDataSource.updateContent(data.content.toString(), moduleId)

        }.asLiveData()
    }
}