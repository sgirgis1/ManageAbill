import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

const val notificationID =1
const val channelID

class Notification : BroadcastReceiver()
{
    override fun onReceive(context: Context, intent: Intent) {
        var message = intent.getStringExtra("textExtra").toString()
        var title = intent.getStringExtra("titleExtra").toString()
        val notification :Notification = NotificationCompat.Builder(context, channelID).setSmallIcon(R.drawable.notification)
                .setContentText(message).setContentTitle(title).build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId, notification)
    }
}