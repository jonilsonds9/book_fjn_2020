package com.jonilson.books.http

import com.google.gson.Gson
import com.jonilson.books.model.SearchResult
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import java.util.concurrent.TimeUnit

object BookHttp {

    private const val API_KEY =
        "AIzaSyA6TFLQcT070BhjK7k2HxvyrQFny8GjMgo"
    private const val BOOK_JSON_URL =
        "https://www.googleapis.com/books/v1/volumes?q=%s&key=$API_KEY"

    private val client = OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.SECONDS)
        .connectTimeout( 10, TimeUnit.SECONDS )
        .build()

    fun searchBook(q: String): SearchResult? {
        val request = Request.Builder()
            .url(String.format(BOOK_JSON_URL, q))
            .build()
        try {
            val response = client.newCall(request).execute()
            val json = response.body?.string()
            return Gson().fromJson<SearchResult>(
                json, SearchResult::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}