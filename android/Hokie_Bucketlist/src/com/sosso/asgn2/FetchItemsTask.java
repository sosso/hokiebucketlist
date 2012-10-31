package com.sosso.asgn2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class FetchItemsTask extends AsyncTask<Void, Void, ArrayList<String>> {
	private ProgressDialog dialog;
	private ActionScreenActivity context;
	private String username;

	public FetchItemsTask(ActionScreenActivity actionScreenActivity, String username) {
		this.context = actionScreenActivity;
		this.username = username;
	}

	@Override
	protected void onPreExecute() {
		try {
			this.dialog = ProgressDialog.show(context, "",
					"now downloading items", true);
		} catch (final Throwable th) {
			// TODO
		}
	}

	protected ArrayList<String> doInBackground(Void... passing) {
		BufferedReader in;
		ArrayList<String> itemStrings = new ArrayList<String>();
		try {
			HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://10.0.2.2:5001/viewitems?username=" + username));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line;
			
			while ((line = in.readLine()) != null) {
				JSONArray items = new JSONArray(line);
				for(int i = 0; i < items.length(); i++){
					JSONObject item = (JSONObject) items.get(i);
					Iterator<?> itemKeys = item.keys();
					String itemString = "";
			        while( itemKeys.hasNext() ){
			        	String key = (String)itemKeys.next();
			        	String value = (String) item.getString(key);
			            itemString += key + ":\t" + value + ";";
			        }
			        itemStrings.add(itemString);
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return itemStrings;
	}

	protected void onPostExecute(ArrayList<String> result) {
		dialog.dismiss();
		this.context.renderCompletedItems(result);
	}
}