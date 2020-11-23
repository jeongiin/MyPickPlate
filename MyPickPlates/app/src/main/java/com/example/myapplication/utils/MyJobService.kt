package com.example.myapplication.utils

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class MyJobService : JobService() {
    companion object {
        private val TAG = "MyJobService"
    }

    override
    fun onStartJob(params: JobParameters): Boolean {
        Log.d(TAG, "onStartJob: ${params.jobId}")
        return false
    }

    override
    fun onStopJob(params: JobParameters): Boolean {
        Log.d(TAG, "onStopJob: ${params.jobId}")
        return false
    }
}