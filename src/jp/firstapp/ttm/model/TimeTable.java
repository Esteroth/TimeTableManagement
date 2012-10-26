package jp.firstapp.ttm.model;

import java.util.ArrayList;

import jp.firstapp.ttm.helper.TimeTableOpenHelper;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TimeTable {
	
	public static final int ADD_ONE_WEEK = 7;
	public static final int ADD_TWO_WEEK = 8;
	public static final int ADD_NO_WEEK = 6;

	private int gridWidth; // 時間割表：横の長さ
	private int gridHeight; // 時間割表：縦の長さ
	private int timeData; // Gridの数字を入れる場所
	private int count; // 数字を入れるタイミング
	private int hour = 1; // 何限目かを代入する数字
	private Boolean moveCursor = true; // カーソルの判定
	private Boolean addSat = false; // 土曜日追加
	private Boolean addSun = false; // 日曜日追加
	private int gridLength; // 時間割表の長さ

	private int i;

	private ArrayList<GridItemData> timeTableData = new ArrayList<GridItemData>();

	private String lecPosition;
	private String week;
	private String time;

	public void init() {
		gridWidth = 0;
		gridHeight = 0;
		count = 0;
		hour = 1;
		moveCursor = true;
		addSat = false;
		addSun = false;
		gridLength = 0;
		i = 0;
		timeTableData.clear();
		lecPosition = "";
		week = "";
		time = "";
	}

	public int getGridWidth() {
		return gridWidth;
	}

	public Boolean getAddSat() {
		return addSat;
	}

	public Boolean getAddSun() {
		return addSun;
	}

	public String getLecPosition() {
		return lecPosition;
	}

	public String getWeek() {
		return week;
	}

	public String getTime() {
		return time;
	}

	public ArrayList<GridItemData> getTimeTableData() {
		return timeTableData;
	}

	public void CreateTimeTable(TimeTableOpenHelper helper, SQLiteDatabase db,
			Cursor cursor, SharedPreferences sPref) {
		try {

			gridHeight = Integer
					.parseInt(sPref.getString("addTimeLength", "6"));
			addSat = sPref.getBoolean("addSat", false);
			addSun = sPref.getBoolean("addSun", false);

			timeTableData.add(new GridItemData("", ""));
			timeTableData.add(new GridItemData("月", ""));
			timeTableData.add(new GridItemData("火", ""));
			timeTableData.add(new GridItemData("水", ""));
			timeTableData.add(new GridItemData("木", ""));
			timeTableData.add(new GridItemData("金", ""));

			if (addSat && !addSun) {
				timeTableData.add(new GridItemData("土", ""));
				gridWidth = ADD_ONE_WEEK;
			} else if (!addSat && addSun) {
				timeTableData.add(new GridItemData("日", ""));
				gridWidth = ADD_ONE_WEEK;
			} else if (addSat && addSun) {
				timeTableData.add(new GridItemData("土", ""));
				timeTableData.add(new GridItemData("日", ""));
				gridWidth = ADD_TWO_WEEK;
			} else {
				gridWidth = ADD_NO_WEEK;
			}

			i = gridWidth;
			timeData = gridWidth;
			count = gridWidth;
			gridLength = gridWidth * gridHeight;

			cursor.moveToFirst();
			for (; i < gridLength; i++) {
				if (count == timeData) {
					timeTableData
							.add(new GridItemData(String.valueOf(hour), ""));
					hour++;
					timeData = timeData + gridWidth;
				} else if (moveCursor) {
					if (count == cursor.getInt(7)) {
						timeTableData.add(new GridItemData(cursor.getString(4),
								cursor.getString(6)));
						if (!cursor.moveToNext()) {
							moveCursor = false;
						}
					} else {
						timeTableData.add(new GridItemData("", ""));
					}
				} else {
					timeTableData.add(new GridItemData("", ""));
				}
				count++;
			}
		} catch (Exception e) {
			i = count;
			for (; i < gridLength; i++) {
				if (i == timeData) {
					timeTableData
							.add(new GridItemData(String.valueOf(hour), ""));
					hour++;
					timeData = timeData + gridWidth;
				} else {
					timeTableData.add(new GridItemData("", ""));
				}
			}
			Log.e("ERROR", e.toString());
		}
	}

	public void getGridPosition(int position, SharedPreferences sPref) {

		gridWidth = 0;
		count = 0;
		gridLength = 0;

		addSat = sPref.getBoolean("addSat", false);
		addSun = sPref.getBoolean("addSun", false);
		gridLength = Integer.parseInt(sPref.getString("addTimeLength", "6"));

		if (addSat && addSun) {
			gridWidth = ADD_TWO_WEEK;
		} else if (addSat || addSun) {
			gridWidth = ADD_ONE_WEEK;
		} else {
			gridWidth = ADD_NO_WEEK;
		}

		for (int i = 1; count < gridLength; i += gridWidth) {
			if (position == i) {
				lecPosition = "月曜日 " + count + "限";
				week = "月";
				time = String.valueOf(count);
			}
			count++;
		}
		count = 0;
		for (int i = 2; count < gridLength; i += gridWidth) {
			if (position == i) {
				lecPosition = "火曜日 " + count + "限";
				week = "火";
				time = String.valueOf(count);
			}
			count++;
		}
		count = 0;
		for (int i = 3; count < gridLength; i += gridWidth) {
			if (position == i) {
				lecPosition = "水曜日 " + count + "限";
				week = "水";
				time = String.valueOf(count);
			}
			count++;
		}
		count = 0;
		for (int i = 4; count < gridLength; i += gridWidth) {
			if (position == i) {
				lecPosition = "木曜日 " + count + "限";
				week = "木";
				time = String.valueOf(count);
			}
			count++;
		}
		count = 0;
		for (int i = 5; count < gridLength; i += gridWidth) {
			if (position == i) {
				lecPosition = "金曜日 " + count + "限";
				week = "金";
				time = String.valueOf(count);
			}
			count++;
		}
		count = 0;

		if (addSat && addSun) {
			for (int i = 6; count < gridLength; i += gridWidth) {
				if (position == i) {
					lecPosition = "土曜日 " + count + "限";
					week = "土";
					time = String.valueOf(count);
				}
				count++;
			}
			count = 0;
			for (int i = 7; count < gridLength; i += gridWidth) {
				if (position == i) {
					lecPosition = "日曜日 " + count + "限";
					week = "日";
					time = String.valueOf(count);
				}
				count++;
			}
			count = 0;
		} else if (addSat) {
			for (int i = 6; count < gridLength; i += gridWidth) {
				if (position == i) {
					lecPosition = "土曜日 " + count + "限";
					week = "土";
					time = String.valueOf(count);
				}
				count++;
			}
			count = 0;
		} else if (addSun) {
			for (int i = 6; count < gridLength; i += gridWidth) {
				if (position == i) {
					lecPosition = "日曜日 " + count + "限";
					week = "日";
					time = String.valueOf(count);
				}
				count++;
			}
			count = 0;
		}
	}
}
