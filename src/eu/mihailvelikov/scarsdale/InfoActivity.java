package eu.mihailvelikov.scarsdale;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;

public class InfoActivity extends SecondaryActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
	}
}
