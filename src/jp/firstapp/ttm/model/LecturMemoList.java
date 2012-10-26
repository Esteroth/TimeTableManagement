package jp.firstapp.ttm.model;

import java.util.ArrayList;

import jp.firstapp.ttm.helper.LecturMemoOpenHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LecturMemoList {
	private ArrayList<LecturMemoListData> lecMemoListData = new ArrayList<LecturMemoListData>();

	public ArrayList<LecturMemoListData> getLecMemoListData() {
		return lecMemoListData;
	}
	
	public void addLecMemoListData(String saveDate,String memoContents){
		lecMemoListData.add(new LecturMemoListData(saveDate, memoContents));
	}

	public void CreateLecMemoList(LecturMemoOpenHelper helper,
			SQLiteDatabase db, Cursor cursor, int timetableID) {
		try{
			cursor.moveToFirst();
			do{
				if(timetableID == cursor.getInt(1)){
					lecMemoListData.add(new LecturMemoListData(cursor.getString(2), cursor.getString(3)));
				}
			}while(cursor.moveToNext());
		}catch (Exception e) {
			Log.e("ERROR",e.toString());
		}
		
		

	}
}
