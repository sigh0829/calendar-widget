package com.plusonelabs.calendar;

import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;

public class CalendarIntentUtil {

	private static final String KEY_DETAIL_VIEW = "DETAIL_VIEW";
	private static final String TIME = "time";
	private static final String END_TIME = "endTime";
	private static final String BEGIN_TIME = "beginTime";

	static Intent createOpenCalendarAtDayIntent(Context context, long goToTime) {
		Intent launchIntent = createOpenCalendarIntent();
		Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
		builder.appendPath(TIME);
		if (goToTime != 0) {
			launchIntent.putExtra(KEY_DETAIL_VIEW, true);
			ContentUris.appendId(builder, goToTime);
		}
		launchIntent.setData(builder.build());
		return launchIntent;
	}

	static PendingIntent createOpenCalendarEventPendingIntent(Context context) {
		Intent intent = createOpenCalendarIntent();
		return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	static Intent createOpenCalendarEventIntent(int eventId, long from, long to) {
		Intent intent = createOpenCalendarIntent();
		intent.setData(ContentUris.withAppendedId(Events.CONTENT_URI, eventId));
		intent.putExtra(BEGIN_TIME, from);
		intent.putExtra(END_TIME, to);
		return intent;
	}

	private static Intent createOpenCalendarIntent() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;
	}
}
