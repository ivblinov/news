package com.example.news.domain

import android.content.Context
import android.content.Intent
import com.example.news.ui.headlines_screen.HeadlinesFragment
import com.example.news.ui.main_screen.MainScreenActivity
import com.example.news.ui.news_screen.ArticleParcel
import com.example.news.ui.news_screen.NewsScreenFragment
import com.example.news.ui.saved_screen.SavedFragment
import com.example.news.ui.sources_screen.SourcesFragment
import com.example.news.ui.sources_screen.only_one_source.OneSourceFragment
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun mainScreen(context: Context) = ActivityScreen { Intent(context, MainScreenActivity::class.java) }

    fun headlinesFragment(): FragmentScreen = FragmentScreen { HeadlinesFragment.newInstance() }

    fun savedFragment(): FragmentScreen = FragmentScreen { SavedFragment.newInstance() }

    fun sourcesFragment(): FragmentScreen = FragmentScreen { SourcesFragment.newInstance() }

    fun newsScreenFragment(param: ArticleParcel?): FragmentScreen = FragmentScreen { NewsScreenFragment.newInstance(param) }

    fun oneSourceFragment(idSource: String): FragmentScreen = FragmentScreen { OneSourceFragment.newInstance(idSource) }
}