package jp.firstapp.ttm;

import jp.firstapp.ttm.adapter.TimeTableAdapter;
import jp.firstapp.ttm.helper.TimeTableOpenHelper;
import jp.firstapp.ttm.model.GridItemData;
import jp.firstapp.ttm.model.TimeTable;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TimeTable timeTable = new TimeTable();

	public static float statusBarHeight;

	SharedPreferences sPref;
	Context context;
	TimeTableOpenHelper helper = null;
	SQLiteDatabase db = null;

	GridView gridView;

	int setId;

	/******  ******/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	@Override
	protected void onResume() {
		super.onResume();

		statusBarHeight = Util.getStatusBarHeight(this);

		// Rect rect = new Rect();
		// Window window = this.getWindow();
		// window.getDecorView().getWindowVisibleDisplayFrame(rect);
		// statusBarHeight = rect.top;

		setContentView(R.layout.layout_main);
		/****** 時間割表作成 ******/
		try {
			sPref = PreferenceManager.getDefaultSharedPreferences(this);
			helper = new TimeTableOpenHelper(this);
			db = helper.getReadableDatabase();
			String[] columns = { "_id", "week", "time", "lecName", "dispName",
					"teacherName", "place", "gridPlace" };
			Cursor cursor = db.query("timetable", columns, null, null, null,
					null, "time,sortWeek", null);
			timeTable.init();
			timeTable.CreateTimeTable(helper, db, cursor, sPref);
			cursor.close();
			helper.close();
			db.close();
		} catch (Exception e) {
			Log.e("ERROR", e.toString());

		}
		/****** 時間割表作成 ******/

		TimeTableAdapter timeTableAdapter = new TimeTableAdapter(
				getApplicationContext(), R.layout.timetable_item,
				timeTable.getTimeTableData(), sPref);

		gridView = (GridView) findViewById(R.id.timeTable);

		gridView.setNumColumns(timeTable.getGridWidth());
		gridView.setAdapter(timeTableAdapter);
		gridView.setOnItemClickListener(new GridOnItemClickListener());
	}

	class GridOnItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (position >= timeTable.getGridWidth()
					&& position % timeTable.getGridWidth() != 0) {
				GridItemData item = (GridItemData) parent
						.getItemAtPosition(position);

				if (!item.getLecturName().equals("")) {
					Intent intent = new Intent(MainActivity.this,
							ShowLecturActivity.class);
					intent.putExtra("POSITION", position);
					startActivity(intent);

				} else if (item.getLecturName().equals("")) {
					Intent intent = new Intent(MainActivity.this,
							EditLecturActivity.class);
					intent.putExtra("POSITION", position);
					startActivity(intent);

				} else {
					Toast.makeText(MainActivity.this, "値のエラー",
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater editLecMenu = getMenuInflater();
		editLecMenu.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {

		case R.id.editTimeMenu:
			Intent editTimeIntent = new Intent(MainActivity.this,
					EditTimeActivity.class);
			startActivity(editTimeIntent);
			finish();

			return true;

		case R.id.setupFuncMenu:
			Intent setupFuncIntent = new Intent(MainActivity.this,
					SetUpFunctionActivity.class);
			startActivity(setupFuncIntent);

			return true;
		}
		return false;
	}
}
