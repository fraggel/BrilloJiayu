package es.jiayu.brillojiayu;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    static double brilloMin=0.25;
    static double brilloMax=1.0;
    static boolean pulsado=false;
    static ImageButton imageButton;

    private static final String SYNC_CLICKED    = "BrilloJiayu";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
        RemoteViews remoteViews;
        ComponentName watchWidget;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        watchWidget = new ComponentName(context, NewAppWidget.class);

        remoteViews.setOnClickPendingIntent(R.id.imageButton, getPendingSelfIntent(context, SYNC_CLICKED));
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);
        try {
            if (SYNC_CLICKED.equals(intent.getAction())) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

                RemoteViews remoteViews;
                ComponentName watchWidget;

                remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
                watchWidget = new ComponentName(context, NewAppWidget.class);

                Intent intent2 = new Intent(context, DummyBrightnessActivity.class);
                //int brillo=Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
                int brillo_actual=Settings.System.getInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS);

                if(brillo_actual<170){
                    int brightnessInt = (int)(brilloMax*255);

                    if(brightnessInt<1) {brightnessInt=1;}


                    Settings.System.putInt(context.getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightnessInt);

                    intent2 = new Intent(context, DummyBrightnessActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent2.putExtra("brightness value", brilloMax);

                }else{
                    int brightnessInt = (int)(brilloMin*255);

                    if(brightnessInt<1) {brightnessInt=1;}

                    Settings.System.putInt(context.getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightnessInt);

                    intent2 = new Intent(context, DummyBrightnessActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent2.putExtra("brightness value", brilloMin);
                }
                remoteViews.setOnClickPendingIntent(R.id.imageButton, getPendingSelfIntent(context, SYNC_CLICKED));
                appWidgetManager.updateAppWidget(watchWidget, remoteViews);

            }
        }catch(Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }


	public void updateAppWidget(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId) {

		// Construct the RemoteViews object
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.new_app_widget);

       // Instruct the widget manager to update the widget
        views.setOnClickPendingIntent(R.id.imageButton, getPendingSelfIntent(context, SYNC_CLICKED));
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}
}
