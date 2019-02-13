package com.example.projectscafold.data.api.network

/**
 * Created by Sadman Sarar on 10/15/18.
 */

interface AppExecutor{
    fun ioThread(f : () -> Unit)
    fun networkThread(f: () -> Unit)
    fun mainThread(f: () -> Unit)
}
