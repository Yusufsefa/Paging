package com.yyusufsefa.scorpcase.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yyusufsefa.scorpcase.data.local.DataSource
import com.yyusufsefa.scorpcase.data.local.Person
import com.yyusufsefa.scorpcase.data.pagingSource.UserPagingSource
import com.yyusufsefa.scorpcase.util.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonRepository @Inject constructor(private val dataSource: DataSource) {

    fun getPersons(): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE
            ),
            pagingSourceFactory = { UserPagingSource(dataSource) }
        ).flow
    }
}