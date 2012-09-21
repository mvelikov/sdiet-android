package eu.mihailvelikov.scarsdale;


import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class DatePickerFragment extends DialogFragment {


	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);
		DatePickerDialog dpd = new DatePickerDialog(getActivity(), (DatePickerActivity) getActivity(), year, month, day);
		dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
		return dpd;
	}


}
