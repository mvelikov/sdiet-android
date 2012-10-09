package eu.mihailvelikov.scarsdale;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class DietDayActivity extends Activity {
	protected int mYear;
	protected int mMonth;
	protected int mDay;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Calendar calendar = Calendar.getInstance();
		Intent intent = getIntent();
		mYear = intent.getIntExtra("year", calendar.get(Calendar.YEAR));
		mMonth = intent.getIntExtra("month", calendar.get(Calendar.MONTH));
		mDay = intent.getIntExtra("day", calendar.get(Calendar.DATE));
		setContentView(R.layout.diet_day);
	}
}
