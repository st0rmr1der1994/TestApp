package com.my.test.testapp.ui.detail.util

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.support.v4.app.NotificationCompat
import com.my.test.testapp.R
import com.my.test.testapp.utils.NOTIFICATION_CHANNEL_ID
import com.my.test.testapp.utils.NOTIFICATION_ID

class NotificationProgressViewImpl(
        context: Context
) : NotificationProgressView {

    private val notificationManager: NotificationManager
            = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val progressNotification: Notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setProgress(0, 0, true)
            .setContentTitle(context.getString(R.string.notification_download_title))
            .build()

    override fun show() = notificationManager.notify(NOTIFICATION_ID, progressNotification)

    override fun hide() = notificationManager.cancel(NOTIFICATION_ID)

}
