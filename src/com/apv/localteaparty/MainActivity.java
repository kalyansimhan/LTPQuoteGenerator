package com.apv.localteaparty;

import android.content.Context;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.apv.localteaparty.R;

public class MainActivity extends SherlockActivity {

	String theRealQuote = "";
	String sideDish = "( via @localteaparty )"; // theivaammeeee
	TextView theRealQuoteTextView;
	QuoteDownloaderTask quoteDownloaderTask;
	ShareActionProvider actionProvider;
	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.generate:
			if (quoteDownloaderTask != null) {
				quoteDownloaderTask.cancel(true);
			}
			quoteDownloaderTask = new QuoteDownloaderTask();
			quoteDownloaderTask.execute();
			break;
		case R.id.tweet:
			Intent intent = new Intent(Intent.ACTION_SEND);
			if ("".equals(theRealQuote))
				theRealQuote = getResources().getString(R.string.bio);
			intent.putExtra(Intent.EXTRA_TEXT, theRealQuote + " " + sideDish);
			intent.setType("text/plain");
			startActivity(intent);
			break;
		}
		return true;
	}

	private class QuoteDownloaderTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			setSupportProgressBarVisibility(true);
			return CustomHttpClient.fetchResponse(context);
		}

		@Override
		protected void onPostExecute(String result) {
			theRealQuote = result;
			if (theRealQuote.length() >= 345)
				theRealQuote = getResources().getString(R.string.bio);
			theRealQuoteTextView = (TextView) findViewById(R.id.theRealQuote);
			theRealQuoteTextView.setText(theRealQuote);
			setSupportProgressBarVisibility(false);
		}
	}
}
