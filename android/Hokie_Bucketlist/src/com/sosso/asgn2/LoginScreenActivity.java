package com.sosso.asgn2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class LoginScreenActivity extends Activity {
	private String username;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		final EditText usernameEditText = (EditText) findViewById(R.id.username);
		Button fetchButton = (Button) findViewById(R.id.loginbutton);
		fetchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = usernameEditText.getText().toString();
				setUsername(username);
				loginAttempt(username);
			}
		});
	}

	private void setUsername(String username) {
		this.username = username;
	}

	public void loginAttempt(String username) {
		LoginTask task = new LoginTask(this, username);
		task.execute();
	}

	public void processLoginResult(boolean success) {
		if (success) {
			Intent i = new Intent(this, ActionScreenActivity.class);
			i.putExtra("username", this.username);
			startActivity(i);
		}

	}

}
