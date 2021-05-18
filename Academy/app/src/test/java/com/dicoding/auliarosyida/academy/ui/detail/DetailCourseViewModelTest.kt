package com.dicoding.auliarosyida.academy.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.auliarosyida.academy.data.source.local.entity.CourseEntity
import com.dicoding.auliarosyida.academy.data.source.local.entity.ModuleEntity
import com.dicoding.auliarosyida.academy.data.AcademyRepository
import com.dicoding.auliarosyida.academy.data.source.local.entity.CourseWithModule
import com.dicoding.auliarosyida.academy.utils.DataDummy
import com.dicoding.auliarosyida.academy.vo.Resource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Memuat Course:
- Memanipulasi data ketika pemanggilan data course di kelas repository.
- Memastikan metode di kelas repository terpanggil.
- Melakukan pengecekan data course apakah null atau tidak.
- Membandingkan data course sudah sesuai dengan yang diharapkan atau tidak.

* Memuat Modules:
- Memanipulasi data ketika pemanggilan data module di kelas repository.
- Memastikan metode di kelas repository terpanggil.
- Melakukan pengecekan data module apakah null atau tidak.
- Melakukan pengecekan jumlah data module apakah sudah sesuai atau belum.
 * */

@RunWith(MockitoJUnitRunner::class)
class DetailCourseViewModelTest {
    private lateinit var viewModel: DetailCourseViewModel
    private val dummyCourse = DataDummy.generateDummyCourses()[0]
    private val courseId = dummyCourse.courseId
    private val dummyModules = DataDummy.generateDummyModules(courseId)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var academyRepository: AcademyRepository

    @Mock
    private lateinit var observer: Observer<Resource<CourseWithModule>>

    @Before
    fun setUp() {
        viewModel = DetailCourseViewModel(academyRepository)
        viewModel.setSelectedCourse(courseId)
    }

    @Test
    fun getCourseWithModule() {
        val dummyCourseWithModule = Resource.success(DataDummy.generateDummyCourseWithModules(dummyCourse, true))
        val course = MutableLiveData<Resource<CourseWithModule>>()
        course.value = dummyCourseWithModule
        `when`(academyRepository.getCourseWithModules(courseId)).thenReturn(course)
        viewModel.courseModule.observeForever(observer)
        verify(observer).onChanged(dummyCourseWithModule)
    }
}