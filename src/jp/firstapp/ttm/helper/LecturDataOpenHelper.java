package jp.firstapp.ttm.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LecturDataOpenHelper extends SQLiteOpenHelper {
	public LecturDataOpenHelper(Context con) {
		super(con, "lectur_data", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE IF NOT EXISTS timetable"
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "timetable_ID INTEGER," + "lecTime INTEGER,"
				+ "attendance INTEGER);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
	}
}
