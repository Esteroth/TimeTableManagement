
package jp.firstapp.ttm.model;


public class TimeListItemData {
	private String dispTime;
	private String startTime;
	private String endTime;

	public TimeListItemData(String dispTime, String startTime, String endTime) {
		this.dispTime = dispTime;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getTime() {
		return dispTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String toString() {
		return startTime + endTime;
	}
}
