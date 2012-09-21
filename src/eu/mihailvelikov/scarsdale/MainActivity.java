package eu.mihailvelikov.scarsdale;

import java.util.Date;
import java.util.GregorianCalendar;

import eu.mihailvelikov.scarsdale.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CalendarView;
import android.widget.Toast;

public class MainActivity extends Activity {

	static final int ID_DIALOG = 1;
	private static final int REQUEST_CODE = 2;
	private int mDay;
	private int mMonth;
	private int mYear;
	private SharedPreferences mPrefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Intent intent = new Intent(this, DatePickerActivity.class);
		// CalendarView calendar = (CalendarView)
		// findViewById(R.id.calendarView1);
		// calendar.setMinDate(System.currentTimeMillis() - 1000);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		if (mPrefs.contains("day") && mPrefs.contains("month")
				&& mPrefs.contains("year")) {
			mDay = mPrefs.getInt("day", 1);
			mMonth = mPrefs.getInt("month", 1);
			mYear = mPrefs.getInt("year", 1970);
			setCalendar();
		} else {
			startActivityForResult(intent, REQUEST_CODE);

		}
	}

	private void setCalendar() {
		GregorianCalendar d = new GregorianCalendar(mYear, mMonth, mDay);

		CalendarView calendar = (CalendarView) findViewById(R.id.calendarView1);
		calendar.setDate(d.getTimeInMillis());
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
			setCalendar();
		} else {
			Toast.makeText(this, "No date was selected!", Toast.LENGTH_LONG)
					.show();
		}
	}
}
