package com.jonilson.books.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jonilson.books.R
import com.jonilson.books.model.Volume
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book_detail.*

class BookDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        val volume = intent.getParcelableExtra<Volume?>("volume")

        volume?.run {
            Picasso.get()
                .load(volumeInfo.imageLinks?.smallThumbnail)
                .into(imgCover)
            txtTitle.text = volumeInfo.title
            txtAuthor.text = volumeInfo.authors?.joinToString() ?: "Nenhum"
            txtPages.text = volumeInfo.pageCount?.toString()
            txtDescription.text = volumeInfo.description
        }

//        if (volume != null) {
//            Picasso.get()
//                .load(volume.volumeInfo.imageLinks?.smallThumbnail)
//                .into(imgCover)
//            txtTitle.text = volume.volumeInfo.title
//            txtAuthor.text = volume.volumeInfo.authors?.joinToString() ?: "Nenhum"
//            txtPages.text = volume.volumeInfo.pageCount?.toString()
//            txtDescription.text = volume.volumeInfo.description
//            txtPublishedDate.text = volume.volumeInfo.publishedDate
//        } else {
//            // TODO erro
//        }
    }
}
