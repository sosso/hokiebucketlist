package com.sosso.ece4564.asgn2;

import java.util.ArrayList;
import java.util.List;

import com.example.sossostats.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DisplayClubStatsActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_club_stats);
		TableLayout tableLayout = (TableLayout) findViewById(R.id.tab);
		// tableLayout.removeAllViews();
		List<String> dummyData = new ArrayList<String>();
		dummyData
				.add("128;115;243;86;129;12;14;26;0;660;19.4;0.00;0;0;0.000;0");
		dummyData
				.add("Hokagesama1;112;67;179;68;147;10;8;2;6;788;14.2;0.00;0;0;0.000;0");
		dummyData
				.add("Toolshed;48;57;105;35;178;9;4;411;3;387;12.4;4.00;4;15;0.789;0");
		dummyData.add("46;46;92;38;94;7;2;78;0;329;14.0;0.00;0;0;0.000;0");
		dummyData.add("27;62;89;65;69;4;0;17;9;286;9.4;5.00;5;6;0.545;0");
		dummyData.add("23;58;81;34;167;7;1;165;18;357;6.4;7.16;13;31;0.705;0");
		dummyData.add("15;36;51;29;35;5;0;36;12;202;7.4;5.00;5;21;0.808;0");
		dummyData.add("18;16;34;20;67;2;0;21;1;203;8.9;0.00;0;0;0.000;0");
		dummyData.add("14;14;28;13;59;3;0;21;4;105;13.3;0.00;0;0;0.000;0");
		int current = 0;
		for (String dataRow : dummyData) {
			TableRow tableRow = new TableRow(this);
			tableRow.setId(100 + current);
			final String[] cols = dataRow.split(";");
			for (int i = 0; i < 7; i++) {
				String col = cols[i];
				TextView columnsView = new TextView(this);
				columnsView.setId(current);
				columnsView.setTextColor(Color.BLACK);
				columnsView.setText(col);
				columnsView.setBackgroundColor(Color.YELLOW);
				tableRow.addView(columnsView);
			}
			current++;
			tableLayout.addView(tableRow);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_display_club_stats, menu);
		return true;
	}

}
