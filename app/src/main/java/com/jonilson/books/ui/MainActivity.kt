package com.jonilson.books.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonilson.books.R
import com.jonilson.books.http.BookHttp
import com.jonilson.books.model.Volume
import com.jonilson.books.ui.adapter.BookAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            progressLayout.visibility = View.VISIBLE
//            delay(2)
            val result = withContext(Dispatchers.IO) {
                BookHttp.searchBook("Dominando o Android")
            }
            progressLayout.visibility = View.GONE
            if (result?.items != null) {
                val bookAdapter = BookAdapter(result.items, this@MainActivity::onVolumeClick)
                rvBooks.layoutManager = LinearLayoutManager(this@MainActivity)
                rvBooks.adapter = bookAdapter
            } else {
                Toast.makeText(this@MainActivity,
                    R.string.erro_loading_books, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onVolumeClick(volume: Volume) {
        val intencao = Intent(this, BookDetailActivity::class.java)
        intencao.putExtra("volume", volume)
        startActivity(intencao)
    }
}
