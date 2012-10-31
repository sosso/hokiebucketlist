package com.sosso.asgn2;

import java.net.URL;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class DisplaySingleItemActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.singleitem);

		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		String url = "http://10.0.2.2:5001/"
				+ getIntent().getStringExtra("imageurl");
		try {
			URL thumb_u = new URL(url);
			Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(),
					"src");
			imageView.setImageDrawable(thumb_d);
		} catch (Exception e) {
			// can't really do anything
		}
	}
}
