package com.dicoding.auliarosyida.academy.data

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Coursewithmodule adalah obyek baru yang menggabungkan CoursesEntity dengan ModulesEntity.
 * Ini bisa tercipta dengan memanfaatkan anotasi @Embedded dan @Relation.
 * */
data class CourseWithModule(

    @Embedded
    var mCourse: CourseEntity,

    @Relation(parentColumn = "courseId", entityColumn = "courseId")
    var mModules: List<ModuleEntity>
)
