package com.example.newsapp.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.BuildConfig
import com.example.newsapp.data.remote.NewsApi
import com.example.newsapp.data.remote.dto.Article

class SearchNewsPagingSource(
    private val api: NewsApi,
    private val searchQuery: String,
    private val sources: String
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPage ->
            val page = state.closestPageToPosition(anchorPage)
            page?.nextKey?.minus(1) ?: page?.prevKey?.plus(1)
        }
    }

    private var totalNewsCount = 0
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val newsResponse = api.searchNews(
                searchQuery = searchQuery,
                sources = sources,
                page = page,
                BuildConfig.API_KEY,
            )
            totalNewsCount += newsResponse.articles?.size ?: 0
            val articles =
                newsResponse.articles?.distinctBy { it.title } ?: emptyList()//Remove duplicates
            LoadResult.Page(
                data = articles,
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        }
    }
}