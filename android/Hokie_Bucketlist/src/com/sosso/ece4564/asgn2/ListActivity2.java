package com.sosso.ece4564.asgn2;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.example.sossostats.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity2 extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);

		ListView listView1 = (ListView) findViewById(R.id.listView1);

		final List<String> dummyData = getIntent().getStringArrayListExtra("clubData");
		Vector<String> usernames = new Vector<String>();
		for(String playerString : dummyData){
			int memberIndex = playerString.indexOf("member_name");
			String member_name = playerString.substring(memberIndex, playerString.indexOf(";", memberIndex));
			usernames.add(member_name);
		}
		String[] stringArray = Arrays.copyOf(usernames.toArray(), usernames.size(), String[].class);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, stringArray);
		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				launchIntent(dummyData.get(arg2));
			}

		});
		listView1.setAdapter(adapter);
	}

	private void launchIntent(String statString) {
		Intent i = new Intent(this, DisplayPlayerStatsActivity.class);
		i.putExtra("statString", statString);
		startActivity(i);
	}

}
