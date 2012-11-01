package com.sosso.asgn2;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class VShare extends Activity {

	private Button share;
	private EditText username, item_id;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

		View layout = (View) li.inflate(R.layout.main, null);
		username = (EditText) layout.findViewById(R.id.username);
		item_id = (EditText) layout.findViewById(R.id.item_id);
		
		// This is the button that the user will click
		// to submit the file.
		share = (Button) layout.findViewById(R.id.shareIt);

		// We add a click listener to invoke the share
		// machinery when the user is done.
		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Temporarily disable the
				// share button.
				share.setClickable(false);

				// Grab the destination network information
				// and invoke the sharing mechanism.
				String url = getString(R.string.BASE_URL) + "/completeitem";
				String id = item_id.getText().toString();
				doShare(url, id);

				// Enable the share button again.
				share.setClickable(true);

				// Close the Activity
				VShare.this.finish();
			}
		});

		// Set the main view for the Activity
		setContentView(layout);
	}

	/**
	 * If the Activity was called via a SEND intent with an associated stream,
	 * this method resolves that stream and pipes the data across the network to
	 * the VShare server.
	 * 
	 * @param server
	 */
	public void doShare(String server, String id) {
		try {

			// First, we need to figure out who
			// called us.
			Intent intent = getIntent();

			// Now, we make sure that someone passed us
			// a stream to send to the server.
			if (Intent.ACTION_SEND.equals(intent.getAction())
					&& intent.hasExtra(Intent.EXTRA_STREAM)) {

				// Grab the stream for the image, video, or
				// other shareable object...
				String type = intent.getType();
				Uri stream = (Uri) intent
						.getParcelableExtra(Intent.EXTRA_STREAM);
				File f = new File(getRealPathFromURI(stream));
				sendData(server, f, id);
			} else {
				Log.i("VShare", "Invoked outside of send....");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//https://gist.github.com/1138617
	public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( contentUri,
                        proj, // Which columns to return
                        null, // WHERE clause; which rows to return (all rows)
                        null, // WHERE clause selection arguments (none)
                        null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
}
	
	/**
	 * You must modify this method to add the parameters for the
	 * user name and comment to the POST. 
	 * 
	 * @param server
	 *            - the url of remote server
	 * @param in
	 *            - the input stream to read
	 */
	public void sendData(String url, File f, String name)
			throws IOException {
		
		// Since we are going to asynchronously send the data to
		// the server, we have to create a complete copy of the
		// input stream. Otherwise, the stream may be gone when
		// we try to use it.
		HttpPost request = new HttpPost(url);
		
		// File uploads must use a multipart request. This
		// code creates the basic multipart request and 
		// and adds the file to it.
		MultipartEntity entity = new MultipartEntity();
		entity.addPart("username", new StringBody(username.getText().toString()));
		entity.addPart("item_id", new StringBody(item_id.getText().toString()));
		entity.addPart("file", new FileBody(f, "image/jpeg"));
		
		UploadTask t = new UploadTask(url, entity);
		t.execute();
	}
}