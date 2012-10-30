package com.sosso.ece4564.asgn2;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class UploadTask extends AsyncTask<Void, Void, Void> {

	private String url;
	private MultipartEntity entity;

	public UploadTask(String url, MultipartEntity entity) {
		this.url = url;
		this.entity = entity;
	}

	@Override
	protected Void doInBackground(Void... params) {
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(this.url);
		request.setEntity(this.entity);
		try {
			HttpResponse response = client.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
