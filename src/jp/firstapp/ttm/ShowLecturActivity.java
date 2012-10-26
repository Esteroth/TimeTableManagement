package jp.firstapp.ttm;

import java.util.Calendar;

import jp.firstapp.ttm.adapter.LecturMemoAdapter;
import jp.firstapp.ttm.helper.LecturMemoOpenHelper;
import jp.firstapp.ttm.helper.TimeTableOpenHelper;
import jp.firstapp.ttm.model.LecturMemoList;
import jp.firstapp.ttm.model.TimeTable;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ShowLecturActivity extends Activity {

	TimeTable timeTable = new TimeTable();
	LecturMemoList lecMemoList = new LecturMemoList();
	LecturMemoAdapter lecMemoAdapter;

	Calendar calendar = Calendar.getInstance();
	String limitDate;
	int mYear, mMonth, mDay;

	TimeTableOpenHelper timetableHelper = null;
	LecturMemoOpenHelper lecMemoHelper = null;
	SQLiteDatabase db = null;
	SharedPreferences sPref;

	String lecName = "";
	String teacherName = "";
	String place = "";
	int timetableID = 0;

	TextView textView_lecPosition;
	TextView textView_lecName;
	TextView textView_teacherName;
	TextView textView_place;
	TextView textView_limitDate;
	Button lecEraseBtn;
	Button memoSaveBtn;
	Button dateSetBtn;
	ListView memoList;
	
	DatePickerDialog datePickerDialog;
	DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear + 1;
			mDay = dayOfMonth;
			limitDate = mYear + "/" + mMonth + "/" + mDay;
			
			textView_limitDate.setText(limitDate);
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_show_lectur);

		Bundle intent = getIntent().getExtras();
		int position = intent.getInt("POSITION");

		textView_lecPosition = (TextView) findViewById(R.id.lecPosition);
		textView_lecName = (TextView) findViewById(R.id.dbLecName);
		textView_teacherName = (TextView) findViewById(R.id.dbTeacherName);
		textView_place = (TextView) findViewById(R.id.dbPlace);
		textView_limitDate = (TextView) findViewById(R.id.showTimeLimit);

		sPref = PreferenceManager.getDefaultSharedPreferences(this);
		
		mYear = calendar.get(Calendar.YEAR);
		mMonth = calendar.get(Calendar.MONTH) + 1;
		mDay = calendar.get(Calendar.DATE);
		limitDate = mYear + "/" + mMonth + "/" + mDay;
		
		textView_limitDate.setText(limitDate);

		timeTable.init();
		timeTable.getGridPosition(position, sPref);
		textView_lecPosition.setText(timeTable.getLecPosition());

		try {

			timetableHelper = new TimeTableOpenHelper(ShowLecturActivity.this);
			db = timetableHelper.getReadableDatabase();
			String[] columns = { "_id", "week", "time", "lecName", "dispName",
					"teacherName", "place" };
			Cursor cursor = db.query("timetable", columns, null, null, null,
					null, null);
			cursor.moveToFirst();

			do {
				if (timeTable.getWeek().equals(cursor.getString(1))) {
					if (timeTable.getTime().equals(cursor.getString(2))) {

						lecName = cursor.getString(3);
						teacherName = cursor.getString(5);
						place = cursor.getString(6);
						timetableID = cursor.getInt(0);
					
					}
				}
			} while (cursor.moveToNext());

			textView_lecName.setText(lecName);
			textView_teacherName.setText(teacherName);
			textView_place.setText(place);
			cursor.close();
			
		} catch (Exception e) {
			Log.e("ERROR", e.toString());
		}
		timetableHelper.close();
		db.close();

		try {
			
			lecMemoHelper = new LecturMemoOpenHelper(this);
			db = lecMemoHelper.getReadableDatabase();
			String[] columns = { "_id", "timetable_id", "date", "memo" };
			Cursor cursor = db.query("lectur_memo", columns, null, null, null,
					null, null, null);
			lecMemoList.CreateLecMemoList(lecMemoHelper, db, cursor,
					timetableID);
			cursor.close();
			
		} catch (Exception e) {
			Log.e("ERROR", e.toString());
		}
		lecMemoHelper.close();
		db.close();

		lecMemoAdapter = new LecturMemoAdapter(getApplicationContext(),
				R.layout.layout_show_lecmemo, lecMemoList.getLecMemoListData());

		memoList = (ListView) findViewById(R.id.memoList);
		memoList.setAdapter(lecMemoAdapter);
		memoList.setOnItemClickListener(new ListItemClickListener());

		lecEraseBtn = (Button) findViewById(R.id.eraseBtn);
		lecEraseBtn.setTag("lecErase");
		lecEraseBtn.setOnClickListener(new ButtonClickListener());

		memoSaveBtn = (Button) findViewById(R.id.memoSaveBtn);
		memoSaveBtn.setTag("memoSave");
		memoSaveBtn.setOnClickListener(new ButtonClickListener());

		dateSetBtn = (Button) findViewById(R.id.dateDialogBtn);
		dateSetBtn.setTag("dateSet");
		dateSetBtn.setOnClickListener(new ButtonClickListener());

		// Button memoEraseBtn = (Button) findViewById(R.id.memoEraseBtn);
		// memoEraseBtn.setTag("memoErase");
		// memoEraseBtn.setOnClickListener(new ButtonClickListener());
		//
		lecMemoHelper = new LecturMemoOpenHelper(ShowLecturActivity.this);
	}

	class ButtonClickListener implements OnClickListener {
		public void onClick(View v) {
			String tag = (String) v.getTag();
			EditText inputLecturMemo = (EditText) findViewById(R.id.showLecMemo);
			String lecturMemoContents = inputLecturMemo.getText().toString();
			String message = "";
			TextView label = (TextView) findViewById(R.id.showLecMessage);

			if (tag.equals("lecErase")) {
				new AlertDialog.Builder(ShowLecturActivity.this)
						.setMessage("本当に削除しますか？")
						.setCancelable(true)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

										timetableHelper = new TimeTableOpenHelper(
												ShowLecturActivity.this);
										db = timetableHelper
												.getReadableDatabase();
										db.delete("timeTable", "_id ="
												+ timetableID, null);
										timetableHelper.close();
										db.close();

										lecMemoHelper = new LecturMemoOpenHelper(
												ShowLecturActivity.this);
										db = lecMemoHelper
												.getReadableDatabase();
										db.delete("lectur_memo",
												"timetable_id =" + timetableID,
												null);
										lecMemoHelper.close();
										db.close();

										Intent intent = new Intent(
												ShowLecturActivity.this,
												MainActivity.class);
										startActivity(intent);
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
									}
								}).show();
			} else if (tag.equals("memoSave")) {
				db = lecMemoHelper.getWritableDatabase();

				if (lecturMemoContents.length() != 0) {
					try {
						db.beginTransaction();

						ContentValues values = new ContentValues();
						values.put("timetable_id", timetableID);
						values.put("date", limitDate);
						values.put("memo", lecturMemoContents);

						db.insert("lectur_memo", null, values);
						db.setTransactionSuccessful();
						db.endTransaction();

						message += "データ登録\n";

						inputLecturMemo.getEditableText().clear();

						lecMemoList.addLecMemoListData(limitDate,
								lecturMemoContents);
						lecMemoAdapter.notifyDataSetChanged();

					} catch (Exception e) {
						message += "登録失敗\n";
						Log.e("ERROR", e.toString());
					}
					label.setText(message);

				} else {
					message += "メモ内容を入力";
					label.setText(message);
				}
				lecMemoHelper.close();
				db.close();
			} else if (tag.equals("dateSet")) {
				datePickerDialog = new DatePickerDialog(ShowLecturActivity.this, dateSetListener, mYear, mMonth - 1, mDay);
				datePickerDialog.show();
			}
		}
	}

	class ListItemClickListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

		}

	}
}