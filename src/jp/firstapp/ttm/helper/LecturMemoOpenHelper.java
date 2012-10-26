package jp.firstapp.ttm.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LecturMemoOpenHelper extends SQLiteOpenHelper {
	public LecturMemoOpenHelper(Context con) {
		super(con, "lectur_memo", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE IF NOT EXISTS lectur_memo"
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "timetable_id INTEGER," + "date TEXT," + "memo TEXT);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
	}
}
