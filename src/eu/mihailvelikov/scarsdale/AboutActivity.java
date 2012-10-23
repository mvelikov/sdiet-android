package eu.mihailvelikov.scarsdale;

import android.os.Bundle;

public class AboutActivity extends SecondaryActivity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		// TextView textView = (TextView) findViewById(R.id.about_text);
		// textView.setMovementMethod(LinkMovementMethod.getInstance());
		// String str = getString(R.string.about_text);
		// textView.setText(Html.fromHtml(str));
	}
}
