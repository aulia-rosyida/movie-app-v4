package com.dicoding.auliarosyida.academy.utils

import androidx.paging.PagedList

/** kelas baru yang berfungsi sebagai kelas pembantu PagedList. */
object PagedListUtil {

    fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = mock(PagedList::class.java) as PagedList<T>
        `when`(pagedList[anyInt()]).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        `when`(pagedList.size).thenReturn(list.size)

        return pagedList
    }
}