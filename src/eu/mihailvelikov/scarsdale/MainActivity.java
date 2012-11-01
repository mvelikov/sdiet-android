package eu.mihailvelikov.scarsdale;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends SherlockFragmentActivity {

	static final int ID_DIALOG = 1;
	private static final int REQUEST_CODE = 2;
	private static SharedPreferences mPrefs;
	private static int mDay;
	private static int mMonth;
	private static int mYear;
	public static GregorianCalendar mStartDate;
	public static GregorianCalendar mEndDate;

	private final int MENU_RESET = 0;
	private final int MENU_INFO = 1;
	private final int MENU_ABOUT = 2;

	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, MENU_RESET, 0, R.string.reset)
				// dd(R.string.reset)
				.setIcon(R.drawable.trash_black)
				.setTitle(R.string.reset)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_WITH_TEXT
								| MenuItem.SHOW_AS_ACTION_IF_ROOM);

		menu.add(0, MENU_INFO, 0, R.string.info)
				.setIcon(R.drawable.info_plain)
				.setTitle(R.string.info)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_WITH_TEXT
								| MenuItem.SHOW_AS_ACTION_IF_ROOM);

		menu.add(0, MENU_ABOUT, 0, R.string.about)
				.setIcon(R.drawable.user)
				.setTitle(R.string.about)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_WITH_TEXT
								| MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_RESET:
			clearPreferences();
			return true;
		case MENU_INFO:
			startInfoActivity();
			return true;
		case MENU_ABOUT:
			startAboutActivity();
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	private void startAboutActivity() {
		Intent i = new Intent(this, AboutActivity.class);
		startActivity(i);
	}

	private void startInfoActivity() {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, InfoActivity.class);
		startActivity(i);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		/*if (firstLoad()) {
			showTips();
			Editor edit = mPrefs.edit();
			edit.putBoolean("secondVisit", true);
			edit.commit();
		}*/
		
		if (!readPreferences()) {
			Intent intent = new Intent(this, DatePickerActivity.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, REQUEST_CODE);
			// return;
		}

		mStartDate = new GregorianCalendar(mYear, mMonth, mDay);
		CalendarView.mStartDate = mStartDate;
		mEndDate = (GregorianCalendar) mStartDate.clone();
		mEndDate.add(Calendar.DAY_OF_MONTH, 14);
		CalendarAdapter.setStartDate(mStartDate);
		CalendarAdapter.setEndDate(mEndDate);

		// savedInstanceState.putInt("day", mDay);

		 android.support.v4.app.Fragment calendarView = new CalendarView();
		 // CalendarView.setStartDate(mStartDate);
		
		 android.support.v4.app.FragmentTransaction ft =
		 getSupportFragmentManager()
		 .beginTransaction();
		 ft.add(android.R.id.content, calendarView).commit();
/*		// request TEST ads to avoid being disabled for clicking your own ads
		AdRequest adRequest = new AdRequest();

		// create a Banner Ad
		adView = new AdView(this, AdSize.BANNER, MY_PUBLISHER_ID);
		// call the main layout from xml
		LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);

		// add the Banner Ad to our main layout
		mainLayout.addView(adView);

		// Initiate a request to load an ad in TEST mode. The test mode will
		// work only on emulators and your specific test device, the users will
		// get real ads.
		adView.loadAd(adRequest);*/
		// addListenerOnReset();
	}


	/*private boolean firstLoad() {
		//mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		if (mPrefs.contains("secondVisit")) {
			return false;
		}
		return true;
	}*/

	protected boolean readPreferences() {
		//mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		if (mDay > 0 && mMonth > 0 && mYear > 0) {
			return true;
		} else if (mPrefs.contains("day") && mPrefs.contains("month")
				&& mPrefs.contains("year")) {
			mDay = mPrefs.getInt("day", 1);
			mMonth = mPrefs.getInt("month", 1);
			mYear = mPrefs.getInt("year", 1970);
			return true;
		} else {
			return false;
		}
	}
	
	/*private void showTips() {
		new AlertDialog.Builder(this)
		.setIcon(R.drawable.ic_launcher)
		.setTitle(R.string.hello)
		.setMessage(R.string.tip)
		.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.cancel();
			}
		}).show();
		
	}*/


	protected void clearPreferences() {
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle(R.string.confirm_clear_preferences_title)
				.setMessage(R.string.new_diet_start)
				.setPositiveButton(R.string.yes,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int arg1) {
								Editor prefEditor = MainActivity.mPrefs.edit();
								prefEditor.clear();
								prefEditor.commit();
								mDay = mYear = mMonth = 0;
								Intent main = new Intent(MainActivity.this,
										MainActivity.class);
								main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(main);
								finish();
							}

						}).setNegativeButton(R.string.no, null).show();

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
			edit.commit();

			finish();
			startActivity(getIntent());
			
		} else {
			Toast.makeText(this, "No date was selected!", Toast.LENGTH_LONG)
					.show();
		}
	}

//	@Override
//	public void onDestroy() {
//		if (adView != null) {
//			adView.destroy();
//		}
//		super.onDestroy();
//	}
}
