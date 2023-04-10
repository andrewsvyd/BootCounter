package com.svyd.bootcounter.presentation.feature.counter.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.svyd.bootcounter.data.repository.boot.model.BootEvent
import com.svyd.bootcounter.domain.common.ActionInteractor
import kotlinx.coroutines.*
import java.lang.System.currentTimeMillis

class SaveBootEventWorker(
    context: Context,
    params: WorkerParameters,
    private val saveEvent: ActionInteractor<BootEvent>
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {

            val timestamp = inputData.getLong(KEY_EVENT_TIMESTAMP, currentTimeMillis())
            val result = saveEvent(BootEvent(timestamp))

            return@withContext if (result.isLeft) {
                if (runAttemptCount < RETRY_LIMIT) Result.retry()
                else Result.failure()
            } else Result.success()
        }
    }

    companion object {
        const val RETRY_LIMIT = 3
        const val KEY_EVENT_TIMESTAMP = "event_timestamp"
    }
}