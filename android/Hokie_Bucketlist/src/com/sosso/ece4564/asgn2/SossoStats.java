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

		final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		Button fetchButton = (Button) findViewById(R.id.fetchbutton);
		fetchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int checkedRadioButton = radioGroup.getCheckedRadioButtonId();
				String radioButtonSelected = "";
				switch (checkedRadioButton) {
				case R.id.hrp:
					radioButtonSelected = "hrp";
					break;
				case R.id.rdt:
					radioButtonSelected = "rdt";
					break;
				case R.id.pgf:
					radioButtonSelected = "pgf";
					break;
				}
				launchIntent(radioButtonSelected);
			}
		});

	}

	public void launchIntent(String radioButtonSelected) {
		FetchStatsTask task = new FetchStatsTask(this, radioButtonSelected);
		task.execute();
	}

	public void renderClubRoster(ArrayList<String> clubData) {
		Intent i = new Intent(this, ListActivity2.class);
		i.putStringArrayListExtra("clubData", clubData);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
