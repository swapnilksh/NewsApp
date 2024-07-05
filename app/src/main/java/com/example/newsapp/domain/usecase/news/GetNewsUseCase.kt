package com.example.newsapp.domain.usecase.news

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.newsapp.common.Result
import com.example.newsapp.data.remote.dto.Article
import com.example.newsapp.domain.repository.news.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
   /* operator fun invoke(sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.getNews(sources)
    }*/

   /* operator fun invoke(sources: List<String>) : Flow<Result<PagingData<Article>>> = flow{
       try {
           emit(Result.Loading<List<Article>>())
           val articles = newsRepository.getNews(sources)

           emit(Result.Success(articles))

       } catch (e: HttpException) {
           emit(Result.Error<String>(e.localizedMessage ?: "An unexpected error occured"))
       } catch (e: IOException) {
           emit(Result.Error<List<Article>>("Couldn't reach server. Check your internet connection."))
       }
   }*/



       operator fun invoke(sources: List<String>): Flow<Result<PagingData<Article>>> = flow {
       try {
           emit(Result.Loading<PagingData<Article>>())
          /*  newsRepository.getNews(sources).collect { article ->
               emit(Result.Success(article))
           }*/ // Get the first emitted value
           val pagingData = newsRepository.getNews(sources)
           emitAll(pagingData.map {
               Result.Success(it)
           })


       } catch (e: HttpException) {
           emit(Result.Error<PagingData<Article>>(e.localizedMessage ?: "An unexpected error occurred"))
       } catch (e: IOException) {
           emit(Result.Error<PagingData<Article>>("Couldn't reach server. Check your internet connection."))
       }
   }
}