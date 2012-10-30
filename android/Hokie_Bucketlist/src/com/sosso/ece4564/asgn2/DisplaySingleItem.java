package com.sosso.ece4564.asgn2;

import java.util.ArrayList;
import java.util.List;

import com.example.sossostats.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplaySingleItem extends Activity {

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
