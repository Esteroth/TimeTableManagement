package jp.firstapp.ttm.adapter;

import java.util.ArrayList;

import jp.firstapp.ttm.R;
import jp.firstapp.ttm.helper.LecturMemoOpenHelper;
import jp.firstapp.ttm.model.LecturMemoListData;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class LecturMemoAdapter extends ArrayAdapter<LecturMemoListData> {
	LayoutInflater _layoutInflater;

	LecturMemoListData lecMemoData;
	TextView textView_date;
	TextView textView_memo;
	Button eraseMemoBtn;

	LecturMemoOpenHelper lecMemoHelper;
	SQLiteDatabase db;

	public LecturMemoAdapter(Context con, int textViewResource,
			ArrayList<LecturMemoListData> lecturMemoData) {
		super(con, textViewResource, lecturMemoData);
		_layoutInflater = (LayoutInflater) con
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		lecMemoData = (LecturMemoListData) getItem(position);

		if (null == convertView) {
			convertView = _layoutInflater.inflate(R.layout.memolist_item, null);
		}

		textView_date = (TextView) convertView.findViewById(R.id.saveDate);
		textView_memo = (TextView) convertView.findViewById(R.id.memoContents);
		
		textView_date.setTextSize(20);
		textView_memo.setTextSize(20);

		textView_date.setText(lecMemoData.getDate());
		textView_memo.setText(lecMemoData.getMemo());

		return convertView;
	}

}
