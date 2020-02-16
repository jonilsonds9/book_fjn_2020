package com.jonilson.books.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.jonilson.books.R
import com.jonilson.books.ui.adapter.BookPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager.adapter = BookPagerAdapter(this)
        TabLayoutMediator(tabs, pager) { tab, position ->
            tab.setText(
                if (position == 0) {
                    R.string.tab_books
                } else {
                    R.string.tab_favorites
                }
            )
        }.attach()
    }
}
