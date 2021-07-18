package com.weather

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.weather.util.Constants


/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget :
    AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }

    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        val appWidgetManager = AppWidgetManager.getInstance(context)
        val remoteViews = RemoteViews(context!!.packageName, R.layout.new_app_widget)
        val watchWidget = ComponentName(context, NewAppWidget::class.java)


        val currentTemp = intent?.getStringExtra(Constants.DEFAULTS.TEMP)
        remoteViews.setTextViewText(R.id.tv_current_weather,currentTemp)
        // Apply the changes
        appWidgetManager.updateAppWidget(watchWidget, remoteViews)

    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {

    val views = RemoteViews(context.packageName, R.layout.new_app_widget)
    appWidgetManager.updateAppWidget(appWidgetId, views)
}


