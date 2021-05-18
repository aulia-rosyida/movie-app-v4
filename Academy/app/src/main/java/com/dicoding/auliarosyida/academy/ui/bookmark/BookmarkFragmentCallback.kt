package com.dicoding.auliarosyida.academy.ui.bookmark

import com.dicoding.auliarosyida.academy.data.source.local.entity.CourseEntity

interface BookmarkFragmentCallback {
    fun onShareClick(course: CourseEntity)
}
