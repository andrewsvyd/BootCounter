package com.svyd.bootcounter.presentation.feature.counter.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.*
import com.svyd.bootcounter.presentation.feature.counter.worker.SaveBootEventWorker
import com.svyd.bootcounter.presentation.feature.counter.worker.SaveBootEventWorker.Companion.KEY_EVENT_TIMESTAMP
import java.lang.System.currentTimeMillis
import java.util.concurrent.TimeUnit

class BootEventReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { saveEvent(it) }
    }

    private fun saveEvent(context: Context) {
        val saveRequest = OneTimeWorkRequestBuilder<SaveBootEventWorker>()
            .setInputData(workDataOf(KEY_EVENT_TIMESTAMP  to currentTimeMillis()))
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        WorkManager
            .getInstance(context)
            .enqueue(saveRequest)
    }

}