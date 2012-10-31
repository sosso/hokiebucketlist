package com.sosso.asgn2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplayPlayerStatsActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statlistview);

		ListView statListView = (ListView) findViewById(R.id.statListView);
		String statString = getIntent().getStringExtra("statString").replace("_", " ");
		String[] splitStats = statString.split(";");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, splitStats);
		statListView.setAdapter(adapter);
	}
}
