package com.dicoding.auliarosyida.academy.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dicoding.auliarosyida.academy.data.source.local.entity.CourseEntity
import com.dicoding.auliarosyida.academy.data.source.local.entity.CourseWithModule
import com.dicoding.auliarosyida.academy.data.source.local.entity.ModuleEntity
import com.dicoding.auliarosyida.academy.vo.Resource

/**
 *  interface yang nantinya akan digunakan untuk menggabungkan 2 repository
 * */
interface AcademyDataSource {

    fun getAllCourses(): LiveData<Resource<PagedList<CourseEntity>>>

    fun getBookmarkedCourses(): LiveData<PagedList<CourseEntity>>

    fun getCourseWithModules(courseId: String): LiveData<Resource<CourseWithModule>>

    fun getAllModulesByCourse(courseId: String): LiveData<Resource<List<ModuleEntity>>>

    fun getContent(moduleId: String): LiveData<Resource<ModuleEntity>>

    //fungsi untuk menambahkan course ke daftar bookmark
    fun setCourseBookmark(course: CourseEntity, state: Boolean)

    //untuk memperlihatkan module mana yang terakhir dibaca.
    fun setReadModule(module: ModuleEntity)

}