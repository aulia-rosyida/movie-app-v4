package com.dicoding.auliarosyida.academy.ui.academy

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.auliarosyida.academy.data.source.local.entity.CourseEntity
import com.dicoding.auliarosyida.academy.data.AcademyRepository
import com.dicoding.auliarosyida.academy.vo.Resource

class AcademyViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    fun getCourses(): LiveData<Resource<PagedList<CourseEntity>>> = academyRepository.getAllCourses()
}