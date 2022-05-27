package com.yyusufsefa.scorpcase.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yyusufsefa.scorpcase.data.local.DataSource
import com.yyusufsefa.scorpcase.data.local.FetchResponse
import com.yyusufsefa.scorpcase.data.local.Person
import com.yyusufsefa.scorpcase.util.Constants
import com.yyusufsefa.scorpcase.util.Resource
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class UserPagingSource(private val source: DataSource) : PagingSource<Int, Person>() {

    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        val page = params.key ?: Constants.INITIAL_LOAD_SIZE
        return try {
            when (val response = getPersonFromDataSource(page)) {
                is Resource.Error -> {
                    LoadResult.Error(Exception(response.message))
                }
                is Resource.Success -> {
                    LoadResult.Page(
                        data = response.data.people,
                        prevKey = if (page == Constants.INITIAL_LOAD_SIZE) null else page - 1,
                        nextKey = response.data.next?.toInt()
                    )
                }
            }

        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private suspend fun getPersonFromDataSource(page: Int): Resource<FetchResponse> {
        return suspendCoroutine<Resource<FetchResponse>> { continuation ->
            source.fetch(
                next = page.toString(),
                completionHandler = { fetchResponse, fetchError ->
                    fetchResponse?.let {
                        continuation.resume(
                            Resource.Success(
                                data = it
                            )
                        )
                    } ?: fetchError?.let {
                        continuation.resume(Resource.Error(it.errorDescription))
                    }
                }
            )
        }
    }

}