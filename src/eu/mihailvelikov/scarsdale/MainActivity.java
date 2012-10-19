package eu.mihailvelikov.scarsdale;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import eu.mihailvelikov.scarsdale.R;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	static final int ID_DIALOG = 1;
	private static final int REQUEST_CODE = 2;
	public static SharedPreferences mPrefs;
	public int mDay;
	private int mMonth;
	private int mYear;
	public static GregorianCalendar mStartDate;
	public static GregorianCalendar mEndDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		if (mPrefs.contains("day") && mPrefs.contains("month")
				&& mPrefs.contains("year")) {
			mDay = mPrefs.getInt("day", 1);
			mMonth = mPrefs.getInt("month", 1);
			mYear = mPrefs.getInt("year", 1970);

		} else {
			Intent intent = new Intent(this, DatePickerActivity.class);
			startActivityForResult(intent, REQUEST_CODE);

		}
		mStartDate = new GregorianCalendar(mYear, mMonth, mDay);

		mEndDate = (GregorianCalendar) mStartDate.clone();
		mEndDate.add(Calendar.DAY_OF_MONTH, 14);
		CalendarAdapter.setStartDate(mStartDate);
		CalendarAdapter.setEndDate(mEndDate);

		// savedInstanceState.putInt("day", mDay);
		setContentView(R.layout.main);
		android.app.Fragment calendarView = new CalendarView();
		// CalendarView.setStartDate(mStartDate);

		

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.add(android.R.id.content, calendarView).commit();
		//addListenerOnReset();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			Bundle extras = data.getExtras();

			mYear = extras.getInt("year");
			mMonth = extras.getInt("month");
			mDay = extras.getInt("day");
			Editor edit = mPrefs.edit();
			edit.putInt("year", mYear);
			edit.putInt("month", mMonth);
			edit.putInt("day", mDay);
			edit.apply();

			finish();
			startActivity(getIntent());
			/*
			 * ViewGroup vg = (ViewGroup) findViewById(R.layout.main);
			 * vg.invalidate();
			 */

		} else {
			Toast.makeText(this, "No date was selected!", Toast.LENGTH_LONG)
					.show();
		}
	}
	public static void addListenerOnReset(Button resetButton) {

		resetButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPrefs.edit().clear();
				finish();
				startActivity(getIntent());
			}
		});
	}

}
