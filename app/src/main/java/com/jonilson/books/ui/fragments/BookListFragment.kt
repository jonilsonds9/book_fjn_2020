package com.jonilson.books.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonilson.books.R
import com.jonilson.books.model.Volume
import com.jonilson.books.ui.BookDetailActivity
import com.jonilson.books.ui.adapter.BookAdapter
import com.jonilson.books.ui.viewmodel.BookListViewModel
import kotlinx.android.synthetic.main.fragment_book_list.*

class BookListFragment: Fragment() {

    private val viewModel: BookListViewModel by lazy {
        ViewModelProvider(this).get(BookListViewModel::class.java)
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

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is BookListViewModel.State.StateLoading -> {
                    progressLayout.visibility = View.VISIBLE
                }
                is BookListViewModel.State.StateLoaded -> {
                    progressLayout.visibility = View.GONE

                    val bookAdapter = BookAdapter(
                        state.list, this@BookListFragment::onVolumeClick
                    )
                    rvBooks.layoutManager = LinearLayoutManager(requireContext())
                    rvBooks.adapter = bookAdapter
                }
                is BookListViewModel.State.StateError -> {
                    progressLayout.visibility = View.GONE

                    if (!state.hasConsumed) {
                        state.hasConsumed = true
                        Toast.makeText(requireContext(),
                            R.string.erro_loading_books, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
        viewModel.loadBooks()
    }

    private fun onVolumeClick(volume: Volume) {
        BookDetailActivity.openWithVolume(requireContext(), volume)
    }
}