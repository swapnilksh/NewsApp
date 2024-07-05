package com.example.newsapp.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.BuildConfig
import com.example.newsapp.data.remote.NewsApi
import com.example.newsapp.data.remote.dto.Article

class NewsPagingSource(
    private val newsApi: NewsApi,
    private val source: String
) : PagingSource<Int, Article>() {
    private var totalNewsCount = 0
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            // Start refresh at page 1 if undefined.
            val page = params.key ?: 1
            val response = newsApi.getNews(source, page, BuildConfig.API_KEY)
            totalNewsCount += response.articles?.size ?: 0
            LoadResult.Page(
                data = response.articles?.distinctBy { it.title }
                    ?: emptyList(),// Just to remove duplicate since response contains duplicate data,
                prevKey = null, // Only paging forward.
                nextKey = if (totalNewsCount == response.totalResults) null else page + 1,
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
            // Handle errors in this block and return LoadResult.Error for
            // expected errors (such as a network failure).

        }

    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}