package com.example.hitapiproject.Utills

import android.os.Handler

class HandlerX(var runnable: Runnable? = null, delay: Long = 1000) {

    var mHandler: Handler? = null
    private var mRunnable: Runnable? = null

    init {
        this.mHandler = Handler()
        mRunnable = runnable
        mRunnable?.let {
            mHandler?.postDelayed(it, delay)
        }
    }

    fun destroyHandler() {
        mRunnable?.let {
            mHandler?.removeCallbacks(it)
        }
    }
    fun pauseHandler(){
        mRunnable?.let {

        }
    }

}