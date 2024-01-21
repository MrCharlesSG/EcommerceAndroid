package hr.algebra.ecommerce.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class EcommerceWorker (
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        EcommerceFetcher(context).fetch()
        return Result.success()
    }


}