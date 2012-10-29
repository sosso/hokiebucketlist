package org.eece261.vshare;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class VShare extends Activity {

	private EditText server_;
	private Button share_;
	private EditText username, item_id;

	/**
	 * This method is the entry point to the VShare code. The "onCreate" method
	 * is typically what you need to override to get your Activity started.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Android uses a special XML file to specify the
		// layout of the GUI. The iPhone uses a similar
		// mechanism. These lines build the gui using the
		// layout file res/layout/main.xml.
		LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

		// Once the gui is built, we need to grab the
		// various UI widgets that we will use to specify
		// the destination of the file.
		View layout = (View) li.inflate(R.layout.main, null);
		server_ = (EditText) layout.findViewById(R.id.server);
		username = (EditText) layout.findViewById(R.id.username);
		item_id = (EditText) layout.findViewById(R.id.item_id);
		
		
		
		
		
		/**
		 * Step 1:
		 * 
		 * Modify the layout main.xml to add GUI
		 * elements for capturing a user name and
		 * comment to associate with each shared
		 * picture.
		 * 
		 * Step 2:
		 * 
		 * You should add code here to get handles
		 * to your GUI elements for the comments
		 * and user name. You will also need to
		 * add member variables to this class to 
		 * hold those handles.
		 * 
		 * 
		 */
		
		
		
		

		// This is the button that the user will click
		// to submit the file.
		share_ = (Button) layout.findViewById(R.id.shareIt);

		// We add a click listener to invoke the share
		// machinery when the user is done.
		share_.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Temporarily disable the
				// share button.
				share_.setClickable(false);

				// Grab the destination network information
				// and invoke the sharing mechanism.
				String server = server_.getText().toString();
				String id = item_id.getText().toString();
				doShare(server, id);

				// Enable the share button again.
				share_.setClickable(true);

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
				/*if (stream != null && type != null) {
					Log.i("VShare", "Got a stream for an image...");

					// The content resolver allows us to grab the data
					// that the URI refers to. We can also use it to
					// read some metadata about the file.
					ContentResolver contentResolver = getContentResolver();

					// This call tries to guess what type of stream
					// we have been passed.
					String contentType = contentResolver.getType(stream);

					String name = null;
					int size = -1;
					// Now, we index into the metadata for the stream and
					// figure out what we are dealing with...size/name.
					Cursor metadataCursor = contentResolver.query(stream,
							new String[] { OpenableColumns.DISPLAY_NAME,
									OpenableColumns.SIZE }, null, null, null);
					if (metadataCursor != null) {
						try {
							if (metadataCursor.moveToFirst()) {
								name = metadataCursor.getString(0);
								size = metadataCursor.getInt(1);

							}
						} finally {
							metadataCursor.close();
						}
					}

					// If for some reason we couldn't get a name,
					// we just use the last path segment as the name.
					if (name == null)
						name = stream.getLastPathSegment();

					// Now, we try to resolve the URI to an actual InputStream
					// that we can read.
					InputStream in = contentResolver.openInputStream(stream);

					// Finally, we pipe the stream to the network.
					sendData(server, in, name);

				} else {
					Log.i("VShare", "Stream was null");
				}*/
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
//		byte[] data = IOUtils.toByteArray(in);
//		ByteArrayInputStream bin = new ByteArrayInputStream(data);
		
		HttpPost request = new HttpPost(url);
		
		// File uploads must use a multipart request. This
		// code creates the basic multipart request and 
		// and adds the file to it.
		MultipartEntity entity = new MultipartEntity();
//		entity.addPart("thefile", new InputStreamBody(bin,name));
		
		/**
		 * 
		 * Step 3:
		 * 
		 * Your code to add the comment and name parameters to
		 * the entity should go here. You can use the method
		 * addPart("your_param", new StringBody("your_value"))
		 * to add them.
		 */
		entity.addPart("username", new StringBody(username.getText().toString()));
		entity.addPart("item_id", new StringBody(item_id.getText().toString()));
		entity.addPart("file", new FileBody(f));
		
		
		
		/**
		 * 
		 * Step 5:
		 * 
		 * After you create your AsyncTask per the instructions
		 * below, you should be able to instantiate an instance
		 * of it here and execute it. The creation/execution
		 * code should look similar to what is commented out
		 * below.
		 */
		UploadTask t = new UploadTask(url, entity);
		t.execute();
	}
	
	/**
	 * 
	 * Step 4:
	 * 
	 * You should create an AsyncTask implementation called UploadTask. The
	 * task should take the MultipartEntity you create in sendData()
	 * and the url of the server as either constructor or method params.
	 * The task should then use these params to create an HttpClient and
	 * send the request to the server.
	 */


	
	
}