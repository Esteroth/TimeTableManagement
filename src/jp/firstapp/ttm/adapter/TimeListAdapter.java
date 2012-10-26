package jp.firstapp.ttm.adapter;

import java.util.ArrayList;

import jp.firstapp.ttm.R;
import jp.firstapp.ttm.model.TimeListItemData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TimeListAdapter extends ArrayAdapter<TimeListItemData> {
	LayoutInflater _layoutInflater;
	
	TextView textView_1;
	TextView textView_2;
	TextView textView_3;

	public TimeListAdapter(Context con, int textViewResource,
			ArrayList<TimeListItemData> timeListData) {
		super(con, textViewResource, timeListData);
		_layoutInflater = (LayoutInflater) con
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TimeListItemData listItem = (TimeListItemData) getItem(position);

		if (null == convertView) {
			convertView = _layoutInflater.inflate(R.layout.timelist_item, null);
		}

		textView_1 = (TextView) convertView.findViewById(R.id.dispTime);
		textView_2 = (TextView) convertView.findViewById(R.id.startTime);
		textView_3 = (TextView) convertView.findViewById(R.id.endTime);

		textView_1.setText(listItem.getTime());
		textView_2.setText(listItem.getStartTime());
		textView_3.setText(listItem.getEndTime());
		return convertView;
	}
}
