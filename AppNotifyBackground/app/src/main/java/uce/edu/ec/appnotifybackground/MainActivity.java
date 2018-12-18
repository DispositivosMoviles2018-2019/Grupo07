package uce.edu.ec.appnotifybackground;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private RadioGroup  mOptionsGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOptionsGroup = (RadioGroup) findViewById(R.id.options_group);
    }

    public void onPostClick(View v) {
        final int noteId = mOptionsGroup.getCheckedRadioButtonId();
        final Notification note;
        switch (noteId) {
            case R.id.option_basic:
            case R.id.option_bigtext:
            case R.id.option_bigpicture:
            case R.id.option_inbox:
                note = buildStyledNotification(noteId);
                break;
            case R.id.option_private:
            case R.id.option_secret:
            case R.id.option_headsup:
                note = buildSecuredNotification(noteId);
                break;
            default:
                throw new IllegalArgumentException("Tipo desconocido");
        }
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(noteId, note);
    }

    private Notification buildStyledNotification(int type) {
        Intent launchIntent =
                new Intent(this, MainActivity.class);
        PendingIntent contentIntent =
                PendingIntent.getActivity(this, 0, launchIntent, 0);
// Create notification with the time it was fired
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                MainActivity.this);
        builder.setSmallIcon(R.drawable.ic_laucher)
                // builder.setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Notificación, algo pasó")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("Hemos terminado!")
                .setContentText("Haga clic aquí!")
                .setContentIntent(contentIntent);

        switch (type) {
            case R.id.option_basic:
//Return the simple notification
                return builder.build();

            case R.id.option_bigtext:
//Include two actions
                builder.addAction(android.R.drawable.ic_menu_call,
                        "Llamar", contentIntent);
                builder.addAction(android.R.drawable.ic_menu_recent_history,
                        "Historia", contentIntent);
//Use the BigTextStyle when expanded
                NotificationCompat.BigTextStyle textStyle =
                        new NotificationCompat.BigTextStyle(builder);
                textStyle.bigText(
                        "Aquí se muestra un texto adicional cuando la notificación está en modo expandido. "
                                + "¡Puedo incluir mucho más contenido en esta vista");
                return textStyle.build();

            case R.id.option_bigpicture:
//Add one additional action
                builder.addAction(android.R.drawable.ic_menu_compass,
                        "Ver Localización", contentIntent);
                //Use the BigPictureStyle when expanded
                NotificationCompat.BigPictureStyle pictureStyle =
                        new NotificationCompat.BigPictureStyle(builder);
                pictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(),
                        R.drawable.dog));
                return pictureStyle.build();

            case R.id.option_inbox:
//Use the InboxStyle when expanded
                NotificationCompat.InboxStyle inboxStyle =
                        new NotificationCompat.InboxStyle(builder);
                inboxStyle.setSummaryText("4 New Tasks");
                inboxStyle.addLine("Make Dinner");
                inboxStyle.addLine("Call Mom");
                inboxStyle.addLine("Call Wife First");
                inboxStyle.addLine("Pick up Kids");
                return inboxStyle.build();

            default:
                throw new IllegalArgumentException("Tipo Desconocido, Estilo no encontrado!");
        }
    }

    //These properties can be overridden by the user's notification settings
    private Notification buildSecuredNotification(int type) {
        Intent launchIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
        //Construir la notificación base
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_laucher)
                //.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Actualización del saldo de la cuenta")
                .setContentText("El saldo de su cuenta es -$250.00")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("El saldo de su cuenta es -$250.00; Cancele por favor "
                                + "o nos veremos obligados a emprender acciones legales!"))
                .setContentIntent(contentIntent);

        switch (type) {
            case R.id.option_private:
                //Proporcionar una versión única para pantallas de bloqueo seguro
                Notification publicNote = new Notification.Builder(this)
                        //NotificationCompat.Builder publicNote = new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_laucher)
                        //.setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Notificación de cuenta")
                        .setContentText("Ha llegado un mensaje importante.")
                        .setContentIntent(contentIntent)
                        .build();

                return builder.setPublicVersion(publicNote)
                        .build();

            case R.id.option_secret:
                //Ocultar la notificación de una pantalla de bloqueo segura por completo
                return builder.setVisibility(Notification.VISIBILITY_SECRET)
                        .build();

            case R.id.option_headsup:
                //Mostrar una notificación de aviso cuando se publique
                return builder.setDefaults(Notification.DEFAULT_SOUND)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .build();
            default:
                throw new IllegalArgumentException("Tipo Desconocido, Estilo no encontrado!");
        }
    }
}

