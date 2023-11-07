package com.example.news.domain

import android.app.Application
import androidx.room.Room
import com.example.news.data.room.AppDataBase
import com.github.terrakok.cicerone.Cicerone

class App : Application() {

    lateinit var db: AppDataBase

    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()
        instance = this

        db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "news.db"
        ).build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}