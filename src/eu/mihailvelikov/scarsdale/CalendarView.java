package eu.mihailvelikov.scarsdale;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.actionbarsherlock.view.MenuItem;

import eu.mihailvelikov.scarsdale.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class CalendarView extends android.support.v4.app.Fragment {
	// protected OnDateSelectedListener mListener;
	protected final Calendar calendar;
	private final Locale locale;
	private ViewSwitcher calendarSwitcher;
	private TextView currentMonth;
	private CalendarAdapter calendarAdapter;
	// private ViewGroup mainLayout;
	private RelativeLayout calendarLayout;

	// private Button resetButton;

	public CalendarView() {
		calendar = Calendar.getInstance();
		locale = Locale.getDefault();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// mainLayout = (ViewGroup)
		// int date = getActivity()
		calendarLayout = (RelativeLayout) inflater.inflate(R.layout.calendar,
				null);
		final GridView calendarDayGrid = (GridView) calendarLayout
				.findViewById(R.id.calendar_days_grid);
		/*
		 * final GestureDetector swipeDetector = new GestureDetector(
		 * getActivity(), new SwipeGesture(getActivity()));
		 */
		final GridView calendarGrid = (GridView) calendarLayout
				.findViewById(R.id.calendar_grid);
		calendarSwitcher = (ViewSwitcher) calendarLayout
				.findViewById(R.id.calendar_switcher);
		currentMonth = (TextView) calendarLayout
				.findViewById(R.id.current_month);

		calendarAdapter = new CalendarAdapter(getActivity(), calendar);
		updateCurrentMonth();

		final TextView nextMonth = (TextView) calendarLayout
				.findViewById(R.id.next_month);
		nextMonth.setOnClickListener(new NextMonthClickListener());
		final TextView prevMonth = (TextView) calendarLayout
				.findViewById(R.id.previous_month);
		prevMonth.setOnClickListener(new PreviousMonthClickListener());
		calendarGrid.setOnItemClickListener(new DayItemClickListener());

		calendarGrid.setAdapter(calendarAdapter);
		/*
		 * calendarGrid.setOnTouchListener(new OnTouchListener() {
		 * 
		 * @Override public boolean onTouch(View v, MotionEvent event) { return
		 * swipeDetector.onTouchEvent(event); } });
		 */
		calendarDayGrid.setAdapter(new ArrayAdapter<String>(getActivity(),
				R.layout.day_item, getResources().getStringArray(
						R.array.days_array)));
		return calendarLayout;
	}

	protected void updateCurrentMonth() {
		calendarAdapter.refreshDays();
		currentMonth.setText(String.format(locale, "%tB", calendar) + " "
				+ calendar.get(Calendar.YEAR));
	}

	private final class DayItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			final ProgressDialog progressBar = new ProgressDialog(
					view.getContext());
			progressBar.setCancelable(false);
			progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressBar.show();
			final TextView dayView = (TextView) view.findViewById(R.id.date);
			boolean isDayClickable = dayView.isClickable();

			final CharSequence text = dayView.getText();
			if (text != null && !"".equals(text)) {
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = Integer.valueOf(String.valueOf(text));
				calendarAdapter.setSelected(year, month, day);

				if (!isDayClickable) {
					Log.i("isDietString", "Diet");
					Intent i = new Intent(getActivity(), DietDayActivity.class);

					i.putExtra("selectedYear", year);
					i.putExtra("selectedMonth", month);
					i.putExtra("selectedDay", day);
					i.putExtra("startYear",
							MainActivity.mStartDate.get(Calendar.YEAR));
					i.putExtra("startMonth",
							MainActivity.mStartDate.get(Calendar.MONTH));
					i.putExtra("startDay",
							MainActivity.mStartDate.get(Calendar.DATE));
					// i.putExtra("end", MainActivity.mEndDate);
					// i.putExtra("start");

					getActivity().startActivity(i);
					getActivity().overridePendingTransition(
							R.anim.in_from_right, R.anim.out_to_right);
				}
			}
			progressBar.dismiss();
		}

	}

	protected final void onNextMonth() {
		calendarSwitcher.setInAnimation(getActivity(), R.anim.in_from_right);
		calendarSwitcher.setOutAnimation(getActivity(), R.anim.out_to_left);
		calendarSwitcher.showNext();
		if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
			calendar.set((calendar.get(Calendar.YEAR) + 1), Calendar.JANUARY, 1);
		} else {
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
		}
		updateCurrentMonth();
	}

	protected final void onPreviousMonth() {
		calendarSwitcher.setInAnimation(getActivity(), R.anim.in_from_left);
		calendarSwitcher.setOutAnimation(getActivity(), R.anim.out_to_right);
		calendarSwitcher.showPrevious();
		if (calendar.get(Calendar.MONTH) == Calendar.JANUARY) {
			calendar.set((calendar.get(Calendar.YEAR) - 1), Calendar.DECEMBER,
					1);
		} else {
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
		}
		updateCurrentMonth();
	}

	private final class NextMonthClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			onNextMonth();
		}
	}

	private final class PreviousMonthClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			onPreviousMonth();
		}
	}

	

	// protected void addListenerOnReset() {
	//
	// resetButton.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// //MainActivity.mPrefs =
	// PreferenceManager.getDefaultSharedPreferences(this);
	// Editor prefEditor = MainActivity.mPrefs.edit();
	// prefEditor.clear();
	// prefEditor.apply();
	// //ViewGroup vg = (ViewGroup) findViewById(R.layout.main);
	// ((ViewGroup) v).invalidate();
	//
	// }
	// });
	// }

	/*
	 * private final class SwipeGesture extends SimpleOnGestureListener {
	 * private final int swipeMinDistance; private final int
	 * swipeThresholdVelocity;
	 * 
	 * public SwipeGesture(Context context) { final ViewConfiguration viewConfig
	 * = ViewConfiguration.get(context); swipeMinDistance =
	 * viewConfig.getScaledTouchSlop(); swipeThresholdVelocity =
	 * viewConfig.getScaledMinimumFlingVelocity(); }
	 * 
	 * @Override public boolean onFling(MotionEvent e1, MotionEvent e2, float
	 * velocityX, float velocityY) { if (e1.getX() - e2.getX() >
	 * swipeMinDistance && Math.abs(velocityX) > swipeThresholdVelocity) {
	 * onNextMonth(); } else if (e2.getX() - e1.getX() > swipeMinDistance &&
	 * Math.abs(velocityX) > swipeThresholdVelocity) { onPreviousMonth(); }
	 * return false; } }
	 */
}
