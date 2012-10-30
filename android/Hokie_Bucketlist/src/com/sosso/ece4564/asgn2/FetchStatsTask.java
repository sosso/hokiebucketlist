package com.sosso.ece4564.asgn2;

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

public class FetchStatsTask extends AsyncTask<Void, Void, ArrayList<String>> {
	private ProgressDialog dialog;
	private SossoStats context;
	private String radioButtonSelected;

	public FetchStatsTask(SossoStats context, String radioButtonSelected) {
		this.context = context;
		this.radioButtonSelected = radioButtonSelected;
	}

	@Override
	protected void onPreExecute() {
		try {
			this.dialog = ProgressDialog.show(context, "",
					"now downloading stats", true);
		} catch (final Throwable th) {
			// TODO
		}
	}

	protected ArrayList<String> doInBackground(Void... passing) {
		ArrayList<String> listItems = new ArrayList<String>();
		BufferedReader in;
		ArrayList<String> playerStrings = new ArrayList<String>();
		try {
			HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
//            request.setURI(new URI("https://dl.dropbox.com/u/6996716/mock.json"));
            request.setURI(new URI("http://mysterious-mesa-9599.herokuapp.com/showstats?club=" + radioButtonSelected));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line;
			
			while ((line = in.readLine()) != null) {
				JSONArray players = new JSONArray(line);
				for(int i = 0; i < players.length(); i++){
					JSONObject player = (JSONObject) players.get(i);
					Iterator<?> playerStats = player.keys();
					String playerString = "";
			        while( playerStats.hasNext() ){
			        	String key = (String)playerStats.next();
			            if(key.equals("py/object")){
			            	continue;
			            }
			        	String value = (String) player.getString(key);
			            playerString += key + ":\t" + value + ";";
			        }
			        playerStrings.add(playerString);
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
		return playerStrings;
	}

	protected void onPostExecute(ArrayList<String> result) {
		dialog.dismiss();
		this.context.renderClubRoster(result);
	}
}