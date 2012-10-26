package jp.firstapp.ttm;

import jp.firstapp.ttm.helper.TimeTableOpenHelper;
import jp.firstapp.ttm.model.TimeTable;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditLecturActivity extends Activity {

	TimeTable timeTable = new TimeTable();

	TimeTableOpenHelper timetableHelper = null;
	SQLiteDatabase db = null;
	SharedPreferences sPref;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_edit_lectur);

		Bundle intent = getIntent().getExtras();
		int position = intent.getInt("POSITION");

		sPref = PreferenceManager.getDefaultSharedPreferences(this);

		TextView textView_position = (TextView) findViewById(R.id.lecPosition);
		timeTable.init();
		timeTable.getGridPosition(position, sPref);
		textView_position.setText(timeTable.getLecPosition());

		Button saveBtn = (Button) findViewById(R.id.saveBtn);
		saveBtn.setTag("save");
		saveBtn.setOnClickListener(new ButtonClickListener());

		timetableHelper = new TimeTableOpenHelper(EditLecturActivity.this);
	}

	class ButtonClickListener implements OnClickListener {
		public void onClick(View v) {
			String tag = (String) v.getTag();
			String message = "";
			TextView label = (TextView) findViewById(R.id.editLecMessage);

			EditText inputLecName = (EditText) findViewById(R.id.lecturName);
			EditText inputDispName = (EditText) findViewById(R.id.dispName);
			EditText inputTeacherName = (EditText) findViewById(R.id.teacherName);
			EditText inputPlace = (EditText) findViewById(R.id.place);

			String lecName = inputLecName.getText().toString();
			String dispName = inputDispName.getText().toString();
			String teacherName = inputTeacherName.getText().toString();
			String place = inputPlace.getText().toString();

			String numWeek = ""; // 週を判別する番号 
			/* Mon=1, Tue=2, Wed=3, Thr=4, Fri=5, Sat=6, Sun=7 */
			int malWeekNumber = 0; // 一行あたりの要素数
			int gridPlace = 0; // すべてのGridに番号振り
			int weekPlace = 0; // numWeekを入れる
			int timePlace = 0; // 列内の番号

			if (timeTable.getWeek().equals("月")) {
				numWeek = "1";
			} else if (timeTable.getWeek().equals("火")) {
				numWeek = "2";
			} else if (timeTable.getWeek().equals("水")) {
				numWeek = "3";
			} else if (timeTable.getWeek().equals("木")) {
				numWeek = "4";
			} else if (timeTable.getWeek().equals("金")) {
				numWeek = "5";
			} else if (timeTable.getWeek().equals("土")) {
				numWeek = "6";
			} else if (timeTable.getWeek().equals("日")) {
				numWeek = "7";
			}

			if (timeTable.getAddSat() && timeTable.getAddSun()) {
				malWeekNumber = 8;
			} else if (timeTable.getAddSat() || timeTable.getAddSun()) {
				malWeekNumber = 7;
			} else {
				malWeekNumber = 6;
			}

			weekPlace = Integer.valueOf(numWeek).intValue();
			timePlace = Integer.valueOf(timeTable.getTime()).intValue()
					* malWeekNumber;
			gridPlace = weekPlace + timePlace;

			if (tag.equals("back")) {
				Intent intent = new Intent(EditLecturActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			} else if (tag.equals("save")) {
				db = timetableHelper.getWritableDatabase();
				if (lecName.length() == 0 || dispName.length() == 0
						|| teacherName.length() == 0 || place.length() == 0) {
					message += "データを入力してください\n";
					label.setText(message);
				} else {
					try {
						db.beginTransaction();

						ContentValues values = new ContentValues();
						values.put("week", timeTable.getWeek());
						values.put("time", timeTable.getTime());
						values.put("lecName", lecName);
						values.put("dispName", dispName);
						values.put("teacherName", teacherName);
						values.put("place", place);
						values.put("sortWeek", numWeek);
						values.put("gridPlace", gridPlace);

						db.insert("timetable", null, values);
						db.setTransactionSuccessful();
						db.endTransaction();

						message += "データ登録\n";

					} catch (Exception e) {
						message = "登録失敗\n";
						Log.e("ERROR", e.toString());
					}
					timetableHelper.close();
					db.close();

					label.setText(message);
					Intent intent = new Intent(EditLecturActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();
				}
			}
		}
	}
}