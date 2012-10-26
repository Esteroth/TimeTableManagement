package jp.firstapp.ttm.adapter;

import java.util.ArrayList;

import jp.firstapp.ttm.MainActivity;
import jp.firstapp.ttm.R;
import jp.firstapp.ttm.model.GridItemData;
import jp.firstapp.ttm.model.TimeTable;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TimeTableAdapter extends ArrayAdapter<GridItemData> {

	public static final int DEFAULT_GRID_HEIGHT = 6;
	public static final int DEFAULT_GRID_WIDTH = 6;

	private LayoutInflater _layoutInflater;
	private WindowManager _windowManager;
	int gridHeight = DEFAULT_GRID_HEIGHT;
	int gridWidth = DEFAULT_GRID_WIDTH;
	int itemHeight;
	int itemWidth;
	int placeHeight;
	int lecNameHeight;
	float textSize;
	Boolean addSat, addSun;

	TextView textView_name;
	TextView textView_place;

	public TimeTableAdapter(Context con, int textViewResource,
			ArrayList<GridItemData> timeTableData, SharedPreferences sPref) {
		super(con, textViewResource, timeTableData);
		_layoutInflater = (LayoutInflater) con
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		_windowManager = (WindowManager) con
				.getSystemService(Context.WINDOW_SERVICE);
		gridHeight = Integer.parseInt(sPref.getString("addTimeLength", "6"));

		addSat = sPref.getBoolean("addSat", false);
		addSun = sPref.getBoolean("addSun", false);
		
		if (addSat && addSun) {
			gridWidth = TimeTable.ADD_TWO_WEEK;
		} else if (addSat || addSun) {
			gridWidth = TimeTable.ADD_ONE_WEEK;
		} else {
			gridWidth = TimeTable.ADD_NO_WEEK;
		}
	}

	@SuppressLint("FloatMath")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GridItemData gridItem = (GridItemData) getItem(position);

		Display dispSize = _windowManager.getDefaultDisplay();
		int dispHeight = dispSize.getHeight();
		int dispWidth = dispSize.getWidth();

		float timeTableHeight = dispHeight - MainActivity.statusBarHeight;

		if (null == convertView) {
			convertView = _layoutInflater
					.inflate(R.layout.timetable_item, null);
		}

		itemHeight = (int) (Math.floor(timeTableHeight) / gridHeight);
		itemWidth = dispWidth / gridWidth;

		if (itemHeight >= itemWidth) {
			textSize = itemWidth;
		} else {
			textSize = itemHeight;
		}

		placeHeight = (int) (Math.ceil(textSize * 1 / 5)) + 5;
		lecNameHeight = itemHeight - placeHeight;

		textView_name = (TextView) convertView.findViewById(R.id.itemDispName);

		textView_place = (TextView) convertView
				.findViewById(R.id.itemDispPlace);

		textView_name.setTextSize(textSize * 2 / 9);
		textView_place.setTextSize(textSize * 2 / 13);

		textView_name.setHeight(lecNameHeight);
		textView_place.setHeight(placeHeight);

		textView_name.setText(gridItem.getLecturName());
		textView_place.setText(gridItem.getPlace());

		return convertView;
	}
}
