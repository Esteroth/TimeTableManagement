package jp.firstapp.ttm.model;


public class LecturMemoListData {
	String date;
	String memo;
	
	public LecturMemoListData(String date, String memo){
		this.date = date;
		this.memo = memo;
	}
	public String getDate(){
		return date;
	}
	
	public String getMemo(){
		return memo;
	}
}
