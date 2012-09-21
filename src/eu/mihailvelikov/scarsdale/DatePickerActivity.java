package eu.mihailvelikov.scarsdale;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.widget.DatePicker;

public class DatePickerActivity extends FragmentActivity implements
		DatePickerDialog.OnDateSetListener {

	private int mDateSelected;
	private int mYearSelected;
	private int mMonthSelected;
	private int mDaySelected;
	DatePickerFragment mDatePicker;
	private static final String DATEPICKER_FORMAT = "W";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mDatePicker = new DatePickerFragment();
		mDatePicker.show(getSupportFragmentManager(), "datePicker");
	}
	
	public void finish() {
		Intent data = new Intent();

		data.putExtra("year", mYearSelected);
		data.putExtra("month", mMonthSelected);
		data.putExtra("day", mDaySelected);
		setResult(RESULT_OK, data);
		super.finish();
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		String dateStr = year + "/" + month + "/" + day;

		mYearSelected = year;
		mMonthSelected = month;
		mDaySelected = day;

		finish();
	}
}
