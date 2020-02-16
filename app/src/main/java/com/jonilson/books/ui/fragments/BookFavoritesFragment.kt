package com.jonilson.books.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonilson.books.R
import com.jonilson.books.model.Volume
import com.jonilson.books.repository.BookRepository
import com.jonilson.books.ui.BookDetailActivity
import com.jonilson.books.ui.adapter.BookAdapter
import com.jonilson.books.ui.viewmodel.BookFavoritesViewModel
import com.jonilson.books.ui.viewmodel.BookVmFactory
import kotlinx.android.synthetic.main.fragment_book_list.*

class BookFavoritesFragment: Fragment() {

    private val viewModel: BookFavoritesViewModel by lazy {
        ViewModelProvider(
            this,
            BookVmFactory(
                BookRepository(requireContext())
            )
        ).get(BookFavoritesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(
            R.layout.fragment_book_list,
            container,
            false
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.favoriteBooks.observe(
            viewLifecycleOwner,
            Observer { volumeList ->
                val bookAdapter = BookAdapter(
                    volumeList, this@BookFavoritesFragment::onVolumeClick
                )
                rvBooks.layoutManager = LinearLayoutManager(requireContext())
                rvBooks.adapter = bookAdapter
            }
        )
    }

    private fun onVolumeClick(volume: Volume) {
        BookDetailActivity.openWithVolume(requireContext(), volume)
    }
}