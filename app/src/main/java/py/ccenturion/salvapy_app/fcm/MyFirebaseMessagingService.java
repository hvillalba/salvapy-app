package py.ccenturion.salvapy_app.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import py.ccenturion.salvapy_app.HomeActivity;
import py.ccenturion.salvapy_app.MainActivity;
import py.ccenturion.salvapy_app.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private String message;
    private String title;
    private String nombre;
    private String sexo;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            /* Check if data needs to be processed by long running job */
            if (remoteMessage.getData().size() > 0) {
                Log.e(TAG, "Message data payload: : " + remoteMessage.getFrom());
                String action = "";

                // Obtenemos el action de la notification
                Map<String, String> receivedMap = remoteMessage.getData();
                if (receivedMap != null) {
                    try {
                        action = receivedMap.get("action");
                            title = receivedMap.get("text");
                            message = receivedMap.get("title");
                            //sexo = receivedMap.get("sexo");
;                    } catch (Exception e) {
                        Log.e(TAG, "Error:" + e.getMessage());
                    }
                }
            }
            if ( true) {
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                //handleNow();
            }
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " +
                    remoteMessage.getNotification().getBody());
        }
//        if (title.equalsIgnoreCase("usuarios")){
//            updateUser();
//            return;
//        }
//        insertarAlerta();
        sendNotification(message);
    }

//    private void insertarAlerta(){
//        Data inputData = new Data.Builder()
//                .putString("text", message)
//                .putString("title", title)
//                .putString("sexo", sexo)
//                .build();
//        OneTimeWorkRequest uploadWork = new OneTimeWorkRequest.Builder(InsertarAlertaWork.class)
//                .setInputData(inputData).build();
//        WorkManager.getInstance().enqueue(uploadWork);
//    }
//    private void updateUser(){
//        OneTimeWorkRequest uploadWork = new OneTimeWorkRequest.Builder(UpdateUserWork.class)
//               .build();
//        WorkManager.getInstance().enqueue(uploadWork);
//    }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("fcmToken", s);
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(false)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(1, notificationBuilder.build());
    }
}
