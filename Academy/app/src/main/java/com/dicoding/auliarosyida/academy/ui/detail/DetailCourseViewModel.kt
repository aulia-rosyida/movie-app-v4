package com.dicoding.auliarosyida.academy.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dicoding.auliarosyida.academy.data.source.local.entity.CourseEntity
import com.dicoding.auliarosyida.academy.data.source.local.entity.ModuleEntity
import com.dicoding.auliarosyida.academy.data.AcademyRepository
import com.dicoding.auliarosyida.academy.data.source.local.entity.CourseWithModule
import com.dicoding.auliarosyida.academy.vo.Resource

/**
 *  kode pada kelas tersebut untuk menetapkan atau mendapatkan courseId, mendapatkan list module dan mendapatkan CourseEntity.
 * */
class DetailCourseViewModel(private val academyRepository: AcademyRepository): ViewModel() {
    val courseId = MutableLiveData<String>()

    fun setSelectedCourse(courseId: String) {
        this.courseId.value = courseId
    }

    /**
     * Metode  Transformations.switchMap digunakan untuk mengambil data setiap kali courseId-nya berubah.
     *
     * Penggunaan fungsi switchMap mirip dengan map,
     * bedanya perubahan dari sebuah LiveData yang diamati akan memicu pemanggilan LiveData lain
     * */
    var courseModule: LiveData<Resource<CourseWithModule>> = Transformations.switchMap(courseId) { mCourseId ->
        academyRepository.getCourseWithModules(mCourseId)
    }

    fun setBookmark() {
        val moduleResource = courseModule.value
        if (moduleResource != null) {
            val courseWithModule = moduleResource.data
            if (courseWithModule != null) {
                val courseEntity = courseWithModule.mCourse
                val newState = !courseEntity.bookmarked
                academyRepository.setCourseBookmark(courseEntity, newState)
            }
        }
    }
}