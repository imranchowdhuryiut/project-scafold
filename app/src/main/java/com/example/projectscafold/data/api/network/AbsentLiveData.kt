package com.example.projectscafold.data.api.network

import android.arch.lifecycle.LiveData
/**
 * Created by Sadman Sarar on 9/9/17.
 * Helper class for transmitting an empty LiveData - Pretty useful!
 */
class AbsentLiveData<T> private constructor() : LiveData<T>() {
    init {
        postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {

            return AbsentLiveData()
        }
    }
}
