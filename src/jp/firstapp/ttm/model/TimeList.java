package jp.firstapp.ttm.model;

import java.util.ArrayList;


public class TimeList {
	private ArrayList<TimeListItemData> timeListData = new ArrayList<TimeListItemData>();
	
	public ArrayList<TimeListItemData> getTimeListData(){
		return timeListData;
	}
	
	public void CreateTimeList(){
//		for(int i=0;i<5;i++){
//			timeListData.add(new TimeListItemData(i+"限","",""));
//		}
		
		timeListData.add(new TimeListItemData("1限", "09:30", "11:00"));
		timeListData.add(new TimeListItemData("2限", "11:10", "12:40"));
		timeListData.add(new TimeListItemData("3限", "13:30", "15:00"));
		timeListData.add(new TimeListItemData("4限", "15:10", "16:40"));
		timeListData.add(new TimeListItemData("5限", "16:50", "18:20"));
	}
}
