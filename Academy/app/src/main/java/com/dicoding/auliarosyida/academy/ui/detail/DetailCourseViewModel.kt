package com.dicoding.auliarosyida.academy.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.auliarosyida.academy.data.source.local.entity.CourseEntity
import com.dicoding.auliarosyida.academy.data.source.local.entity.ModuleEntity
import com.dicoding.auliarosyida.academy.data.source.AcademyRepository

/**
 *  kode pada kelas tersebut untuk menetapkan atau mendapatkan courseId, mendapatkan list module dan mendapatkan CourseEntity.
 * */
class DetailCourseViewModel(private val academyRepository: AcademyRepository): ViewModel() {
    private lateinit var courseId: String

    fun setSelectedCourse(courseId: String) {
        this.courseId = courseId
    }

    fun getCourse(): LiveData<CourseEntity> = academyRepository.getCourseWithModules(courseId)

    fun getModules(): LiveData<List<ModuleEntity>> = academyRepository.getAllModulesByCourse(courseId)
}