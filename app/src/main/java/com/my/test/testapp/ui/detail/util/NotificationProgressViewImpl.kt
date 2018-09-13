package com.my.test.testapp.ui.detail.util

import android.app.NotificationManager
import android.content.Context
import android.support.v4.app.NotificationCompat
import com.my.test.testapp.R
import com.my.test.testapp.service.network.util.DownloadProgressHolder
import com.my.test.testapp.utils.NOTIFICATION_CHANNEL_ID
import com.my.test.testapp.utils.NOTIFICATION_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NotificationProgressViewImpl(
        context: Context,
        private val progressHolder: DownloadProgressHolder
) : NotificationProgressView {

    private val notificationManager: NotificationManager
            = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(context.getString(R.string.notification_download_title))

    override fun onAttach() {
        //maybe not needed
    }

    override fun show() {
        notificationBuilder.setProgress(100, 0, false)
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        val progressDisposable = progressHolder.progressObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { progress(it) }
        compositeDisposable.add(progressDisposable)
    }

    override fun hide() {
        notificationManager.cancel(NOTIFICATION_ID)
        compositeDisposable.clear()
    }

    private fun progress(progress: Int) {
            notificationBuilder.setProgress(100, progress, false)
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        }

    override fun onDetach() {
            if (!compositeDisposable.isDisposed) {
                compositeDisposable.dispose()
            }
        }
}
