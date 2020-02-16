package com.jonilson.books.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jonilson.books.R
import com.jonilson.books.model.Volume
import com.jonilson.books.repository.BookRepository
import com.jonilson.books.ui.viewmodel.BookDetailViewModel
import com.jonilson.books.ui.viewmodel.BookVmFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book_detail.*

class BookDetailActivity : AppCompatActivity() {

    private val viewModel: BookDetailViewModel by lazy {
        ViewModelProvider(
            this,
            BookVmFactory(
                BookRepository(this)
            )
        ).get(BookDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        val volume = intent.getParcelableExtra<Volume?>(EXTRA_BOOK)

        volume?.run {
            if (volume.volumeInfo.imageLinks?.smallThumbnail?.isNotEmpty() == true) {
                Picasso.get()
                    .load(volumeInfo.imageLinks?.smallThumbnail)
                    .into(imgCover)
            }
            txtTitle.text = volumeInfo.title
            txtAuthor.text = volumeInfo.authors?.joinToString() ?: "Nenhum"
            txtPages.text = volumeInfo.pageCount?.toString()
            txtPublishedDate.text = volumeInfo.publishedDate
            txtDescription.text = volumeInfo.description

            viewModel.onCreate(volume)
            viewModel.isFavote.observe(
                this@BookDetailActivity,
                Observer { isFavorite ->
                    if (isFavorite) {
                        fabFavorite.setImageResource(R.drawable.ic_delete)
                        fabFavorite.setOnClickListener {
                            viewModel.removeFromFavorites(volume)
                        }
                    } else {
                        fabFavorite.setImageResource(R.drawable.ic_add)
                        fabFavorite.setOnClickListener {
                            viewModel.saveToFavorite(volume)
                        }
                    }
                })
        }
    }

    companion object {
        private const val EXTRA_BOOK = "book"

        fun openWithVolume(context: Context, volume: Volume) {
            val intent = Intent(context, BookDetailActivity::class.java)
            intent.putExtra(EXTRA_BOOK, volume)
            context.startActivity(intent)
        }
    }
}
