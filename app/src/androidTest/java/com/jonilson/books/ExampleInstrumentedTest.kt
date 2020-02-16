package com.jonilson.books

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jonilson.books.repository.AppDatabase
import com.jonilson.books.repository.Book
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val db = AppDatabase.getDatabase(appContext)
        val dao = db.getBookDao()

        runBlocking {
            val bookUnderTest = Book("MEUID", "http://selflink",
                "Book 1", "Desc", listOf("Eu", "tu"),
                "Novatec", "2019-01-01", 1000,
                "http://small", "http://thumb"
            )
            val rowId = dao.save(bookUnderTest)
            Assert.assertTrue(rowId > -1)

            val books = dao.allFavorites().first()
            books.forEach { book ->
                Assert.assertTrue(book.title == "Book 1")
            }

            val isFavorite = dao.isFavorite("MEUID")
            Assert.assertTrue(isFavorite == 1)

            val result = dao.delete(bookUnderTest)
            Assert.assertTrue(result == 1)

//            dao.save(bookUnderTest)
//            Log.d("JSS", "Inserted")
//            val books = dao.allFavorites().first()
//            books.forEach { book ->
//                Log.d("JSS", book.title)
//            }
//            val isFavorite = dao.isFavorite("MEUID")
//            Log.d("JSS", "isFavorite: $isFavorite")
//            val result = dao.delete(bookUnderTest)
//            Log.d("JSS", "Rows affected: $result")
        }
    }
}
