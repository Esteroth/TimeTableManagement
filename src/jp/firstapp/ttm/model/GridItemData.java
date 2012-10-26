package jp.firstapp.ttm.model;

public class GridItemData {
	private String lecturName;
	private String place;

	public GridItemData(String lecturName, String place) {
		this.lecturName = lecturName;
		this.place = place;
	}

	public String getLecturName() {
		return lecturName;
	}

	public String getPlace() {
		return place;
	}
}