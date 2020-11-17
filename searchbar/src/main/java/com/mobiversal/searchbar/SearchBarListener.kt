package com.mobiversal.searchbar

/**
 * Created by Csaba on 8/9/2019.
 */
interface SearchBarListener {

    fun onStartSearch(text: String)

    fun onTextChanged(text: String) {

    }

    fun onTextCleared()
}