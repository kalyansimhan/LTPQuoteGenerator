package com.apv.localteaparty;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.apache.http.util.ByteArrayBuffer;

import com.apv.localteaparty.R;

import android.content.Context;

public class CustomHttpClient {

	private static final String serviceUrl = "http://yuvi.in/autopeople/ltp/data";

	public static String fetchResponse(Context context) {
		String appendUrl = getDataLocation();
		System.out.println(appendUrl);
		String callResult = "";
		try {
			HttpURLConnection http = null;
			URL url = new URL(serviceUrl + appendUrl);
			http = (HttpURLConnection) url.openConnection();
			http.addRequestProperty("Accept-Encoding",
					"application/octet-stream");

			InputStream inputStream = http.getInputStream();
			BufferedInputStream stream = new BufferedInputStream(inputStream);
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = stream.read()) != -1) {
				baf.append((byte) current);
			}
			callResult = new String(baf.toByteArray());
		} catch (Exception e) {
			return context.getResources().getString(R.string.bio);
		}
		return callResult;
	}

	// Echoose me for the really ugly logic.
	private static String getDataLocation() {
		Random random = new Random();
		int dirNo = random.nextInt(999);

		// bharatha maathave.. so sorry
		int fileNo = random.nextInt(99);
		if (dirNo <= 9)
			fileNo = fileNo * 100;
		else if (dirNo >= 10 && dirNo <= 99)
			fileNo = fileNo * 10;
		return "/" + dirNo + "/" + fileNo + "" + dirNo;
	}
}
