package com.dicoding.auliarosyida.academy.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.*

/**
 * aplikasi Academy kali ini Anda menggunakan 4 Entity yang saling berkaitan satu sama lain.
 * Contohnya seperti entity di bawah,
 *
 * ModuleEntity memiliki keterkaitan dengan CourseEntity dan memiliki key yang sama yakni courseId.
 * Selain itu, ContentEntity juga terdapat dalam ModuleEntity.
 * */
@Entity(tableName = "moduleentities",
    primaryKeys = ["moduleId", "courseId"],
    foreignKeys = [ForeignKey(entity = CourseEntity::class,
        parentColumns = ["courseId"],
        childColumns = ["courseId"])],
    indices = [Index(value = ["moduleId"]),
        Index(value = ["courseId"])])
data class ModuleEntity(

    @NonNull
    @ColumnInfo(name = "moduleId")
    var moduleId: String,

    @NonNull
    @ColumnInfo(name = "courseId")
    var courseId: String,

    @NonNull
    @ColumnInfo(name = "title")
    var title: String,

    @NonNull
    @ColumnInfo(name = "position")
    var position: Int,

    @ColumnInfo(name = "read")
    var read: Boolean = false
){
    @Embedded
    var contentEntity: ContentEntity? = null
}
