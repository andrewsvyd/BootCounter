package com.svyd.bootcounter.presentation.feature.notification.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.svyd.bootcounter.common.exception.Failure
import com.svyd.bootcounter.common.functional.Either
import com.svyd.bootcounter.common.mapper.TypeMapper
import com.svyd.bootcounter.data.repository.boot.model.BootEvent
import com.svyd.bootcounter.domain.common.RetrievingInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationWorker(
    context: Context,
    params: WorkerParameters,
    private val lastEvents: RetrievingInteractor<Pair<BootEvent, BootEvent>>,
    private val mapper: TypeMapper<Pair<BootEvent, BootEvent>, String>
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val result = lastEvents(mapper)

            return@withContext if (result.isLeft) {
                if (runAttemptCount < RETRY_LIMIT) Result.retry()
                else Result.failure()
            } else {
                launch(Dispatchers.Main) { showNotification(result) }
                Result.success()
            }
        }
    }

    private fun showNotification(lastEvents: Either<Failure, String>) {
        TODO("Not yet implemented")
    }

    companion object {
        const val RETRY_LIMIT = 3
    }
}