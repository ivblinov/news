package com.example.news.ui.main_screen

sealed class StateIconSave {
    object Saved : StateIconSave()
    object NotSaved : StateIconSave()
}
