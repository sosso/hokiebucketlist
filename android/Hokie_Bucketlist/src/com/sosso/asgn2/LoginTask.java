package com.sosso.asgn2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class LoginTask extends AsyncTask<Void, Void, String> {
	private ProgressDialog dialog;
	private LoginScreenActivity context;
	private String username, baseurl;

	public LoginTask(LoginScreenActivity loginScreen, String username, String baseurl) {
		this.context = loginScreen;
		this.username = username;
		this.baseurl = baseurl;
	}

	@Override
	protected void onPreExecute() {
		try {
			this.dialog = ProgressDialog.show(context, "", "Logging in. . .",
					true);
		} catch (final Throwable th) {
		}
	}

	protected String doInBackground(Void... passing) {
		BufferedReader in;
		String result = "";
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			String url = this.baseurl + "/createuser?username="
					+ username;
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			String line;
			
			while ((line = in.readLine()) != null) {
				result += line;
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return result;
	}

	protected void onPostExecute(String result) {
		dialog.dismiss();
		if(result.equals("User creation successful!")){
			this.context.processLoginResult(true);
		}else{
			this.context.processLoginResult(false);
		}
	}
}