package com.example.projectscafold.data.api.network


/**
 * Created by Sadman Sarar on 10/15/18.
 */
class IAppExecutor : AppExecutor {
    override fun ioThread(f: () -> Unit) {
        ioThread(f)
    }

    override fun networkThread(f: () -> Unit) {
        networkThread(f)
    }

    override fun mainThread(f: () -> Unit) {
        mainThread(f)
    }
}