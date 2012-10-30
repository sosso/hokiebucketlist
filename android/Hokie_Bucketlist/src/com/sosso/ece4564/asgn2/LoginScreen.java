package com.sosso.ece4564.asgn2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.sossostats.R;

public class LoginScreen extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		final EditText usernameEditText = (EditText) findViewById(R.id.username);
		Button fetchButton = (Button) findViewById(R.id.fetchbutton);
		fetchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = usernameEditText.getText().toString();
				launchIntent(username);
			}
		});

	}

	public void launchIntent(String username) {
		LoginTask task = new LoginTask(this, username);
		task.execute();
	}

	public void renderClubRoster(String username) {
		Intent i = new Intent(this, ListActivity2.class);
		i.putExtra("username", username);
		startActivity(i);
	}

}
