package eu.mihailvelikov.scarsdale;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

public class DietDayActivity extends Activity {

	protected int mYear;
	protected int mMonth;
	protected int mDay;
	protected int mStartDay;
	protected int mStartMonth;
	protected int mStartYear;

	private SharedPreferences mPrefs;
	protected GregorianCalendar mStartDate;
	//protected GregorianCalendar mEndDate;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Calendar calendar = Calendar.getInstance();
		Intent intent = getIntent();

		mYear = intent.getIntExtra("selectedYear", calendar.get(Calendar.YEAR));
		mMonth = intent.getIntExtra("selectedMonth", calendar.get(Calendar.MONTH));
		mDay = intent.getIntExtra("selectedDay", calendar.get(Calendar.DATE));
		
		mStartDay = intent.getIntExtra("startDay", 1);
		mStartMonth = intent.getIntExtra("startMonth", 1);
		mStartYear = intent.getIntExtra("startYear", 1970);

		/*mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		if (mPrefs.contains("day") && mPrefs.contains("month")
				&& mPrefs.contains("year")) {
			mStartDay = mPrefs.getInt("day", 1);
			mStartMonth = mPrefs.getInt("month", 1);
			mStartYear = mPrefs.getInt("year", 1970);

		} else {
			Toast.makeText(this, R.string.missing_start_end_dates,
					Toast.LENGTH_LONG).show();
			finish();
		}*/
		mStartDate = new GregorianCalendar(mStartYear, mStartMonth, mStartDay);


		GregorianCalendar selectedDate = new GregorianCalendar(mYear, mMonth,
				mDay);
		// checks if there is something wrong with dates
		long diff = selectedDate.getTimeInMillis()
				- mStartDate.getTimeInMillis();
		long currentDietDay = 0;
		if (diff < 0) {
			Toast.makeText(this, R.string.problem_handling_date_differences,
					Toast.LENGTH_LONG).show();
			finish();
		} else {
			currentDietDay = daysBetween(mStartDate, selectedDate);
		}

		setContentView(R.layout.diet_day);

		TextView viewDinnerText = (TextView) findViewById(R.id.dinner_text);

		String[] dinnerArray = getResources().getStringArray(
				R.array.dinner_array);
		String[] lunchArray = getResources()
				.getStringArray(R.array.lunch_array);
		TextView viewLunchText = (TextView) findViewById(R.id.lunch_text);
		
		TextView viewTitle = (TextView) findViewById(R.id.title);
		String titleStr = getString(R.string.day);
		String title = String.format(titleStr, currentDietDay + 1);
		viewTitle.setText(title);
		// Days from 8 to 14 repeat first 7 days
		if (currentDietDay > 6) {
			currentDietDay -= 7;
		}

		try {
			// loads details for current days
			viewLunchText.setText(lunchArray[(int) currentDietDay]);
			viewDinnerText.setText(dinnerArray[(int) currentDietDay]);
		} catch (Exception e) {
			Toast.makeText(this, R.string.invalid_diet_day, Toast.LENGTH_LONG)
					.show();
			finish();
		}
	}

	public long daysBetween(GregorianCalendar startDate,
			GregorianCalendar endDate) {
		Calendar sDate = getDatePart(startDate);
		Calendar eDate = getDatePart(endDate);

		long daysBetween = 0;
		while (sDate.before(eDate)) {
			sDate.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}

	public static Calendar getDatePart(GregorianCalendar date) {
		Calendar cal = Calendar.getInstance(); // get calendar instance
		cal.setTimeInMillis(date.getTimeInMillis());
		cal.set(Calendar.HOUR_OF_DAY, 0); // set hour to midnight
		cal.set(Calendar.MINUTE, 0); // set minute in hour
		cal.set(Calendar.SECOND, 0); // set second in minute
		cal.set(Calendar.MILLISECOND, 0); // set millisecond in second

		return cal; // return the date part
	}
}
