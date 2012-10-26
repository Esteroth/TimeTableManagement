package jp.firstapp.ttm;

import jp.firstapp.ttm.adapter.TimeListAdapter;
import jp.firstapp.ttm.model.TimeList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class EditTimeActivity extends Activity {
	TimeList timeList = new TimeList();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_edit_time);

		timeList.CreateTimeList();

		TimeListAdapter timeListAdapter = new TimeListAdapter(
				getApplicationContext(), R.layout.timelist_item,
				timeList.getTimeListData());

		ListView listView = (ListView) findViewById(R.id.timeList);
		listView.setAdapter(timeListAdapter);
		
	}

	class ButtonClickListener implements OnClickListener {
		public void onClick(View v) {
			String tag = (String) v.getTag();

			if (tag.equals("back")) {
				Intent intent = new Intent(EditTimeActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}
}
