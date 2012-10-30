package com.sosso.ece4564.asgn2;

import java.util.ArrayList;

import com.example.sossostats.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;

public class SossoStats extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final String username = getIntent().getStringExtra("username");
		Button fetchButton = (Button) findViewById(R.id.fetchbutton);
		fetchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getItems(username);
			}
		});

	}

	public void getItems(String username) {
		FetchItemsTask task = new FetchItemsTask(this, username);
		task.execute();
	}

	public void renderCompletedItems(ArrayList<String> completedItems) {
		Intent i = new Intent(this, ListActivity2.class);
		i.putStringArrayListExtra("completedItems", completedItems);
		startActivity(i);
	}

}
