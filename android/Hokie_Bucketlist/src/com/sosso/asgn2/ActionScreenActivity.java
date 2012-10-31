package com.sosso.asgn2;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ActionScreenActivity extends Activity {
	private String username;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actionspanel);
		username = getIntent().getStringExtra("username");

		Button viewItemsButton = (Button) findViewById(R.id.viewitems);
		viewItemsButton.setOnClickListener(new OnClickListener() {
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
		Intent i = new Intent(this, CompletedItemsScreenActivity.class);
		i.putStringArrayListExtra("completedItems", completedItems);
		startActivity(i);
	}

}
