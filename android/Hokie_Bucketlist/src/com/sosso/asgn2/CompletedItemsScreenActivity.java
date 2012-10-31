package com.sosso.asgn2;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CompletedItemsScreenActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);

		ListView listView1 = (ListView) findViewById(R.id.listView1);

		final List<String> itemStrings = getIntent().getStringArrayListExtra("completedItems");
		Vector<String> itemDescriptions = new Vector<String>();
		for(String itemString : itemStrings){
			int descriptionIndex = itemString.indexOf("Description");
			String description = itemString.substring(descriptionIndex + 12, itemString.indexOf(";", descriptionIndex));
			itemDescriptions.add(description);
		}
		String[] stringArray = Arrays.copyOf(itemDescriptions.toArray(), itemDescriptions.size(), String[].class);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, stringArray);
		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				launchIntent(itemStrings.get(arg2));
			}

		});
		listView1.setAdapter(adapter);
	}

	private void launchIntent(String statString) {
		String[] imageData = statString.split(";")[0].split(":\t");
		if(imageData.length > 1){
			Intent i = new Intent(this, DisplaySingleItemActivity.class);
			i.putExtra("imageurl", imageData[1]);
			startActivity(i);
		}
		
	}

}
