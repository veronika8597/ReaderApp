package com.example.myreaderapp.network

import com.example.myreaderapp.model.Book
import com.example.myreaderapp.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton


// Data access object - responsible for creating the infrastructure
// that will connect to our retrofit and get information - in our cas - get books.
@Singleton
interface BooksApi {

    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): Book

    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId")bookId: String): Item
}