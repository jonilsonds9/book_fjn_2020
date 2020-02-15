package com.jonilson.books

import com.jonilson.books.http.BookHttp
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun searchIsComplete() {
        val searchResult = BookHttp.searchBook("Dominando o Android")
        searchResult?.items?.forEach { volume ->
            println(volume.volumeInfo.title)
        }
    }
}
