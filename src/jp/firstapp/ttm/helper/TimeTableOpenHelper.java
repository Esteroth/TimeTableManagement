package jp.firstapp.ttm.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TimeTableOpenHelper extends SQLiteOpenHelper {
	public TimeTableOpenHelper(Context con) {
		super(con, "timetable", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE IF NOT EXISTS timetable"
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + "week TEXT,"
				+ "time TEXT," + "lecName TEXT," + "dispName TEXT,"
				+ "teacherName TEXT," + "place TEXT," + "sortWeek TEXT,"
				+ "gridPlace INTEGER);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
	}
}
