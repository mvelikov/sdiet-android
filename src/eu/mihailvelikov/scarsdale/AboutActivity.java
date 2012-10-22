package eu.mihailvelikov.scarsdale;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

public class AboutActivity extends SherlockActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
//		TextView textView = (TextView) findViewById(R.id.about_text);
//		textView.setMovementMethod(LinkMovementMethod.getInstance());
//		String str = getString(R.string.about_text);
//		textView.setText(Html.fromHtml(str));
	}
}
