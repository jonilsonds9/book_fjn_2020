package com.jonilson.books.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonilson.books.R
import com.jonilson.books.http.BookHttp
import com.jonilson.books.model.Volume
import com.jonilson.books.ui.adapter.BookAdapter
import com.jonilson.books.ui.viewmodel.BookListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val viewModel: BookListViewModel by lazy {
        ViewModelProvider(this).get(BookListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is BookListViewModel.State.StateLoading -> {
                    progressLayout.visibility = View.VISIBLE
                }
                is BookListViewModel.State.StateLoaded -> {
                    progressLayout.visibility = View.GONE

                    val bookAdapter = BookAdapter(state.list, this@MainActivity::onVolumeClick)
                    rvBooks.layoutManager = LinearLayoutManager(this@MainActivity)
                    rvBooks.adapter = bookAdapter
                }
                is BookListViewModel.State.StateError -> {
                    progressLayout.visibility = View.GONE

                    if (!state.hasConsumed) {
                        state.hasConsumed = true
                        Toast.makeText(this@MainActivity,
                            R.string.erro_loading_books, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
        viewModel.loadBooks()
    }

    private fun onVolumeClick(volume: Volume) {
        val intencao = Intent(this, BookDetailActivity::class.java)
        intencao.putExtra("volume", volume)
        startActivity(intencao)
    }
}
