package jp.firstapp.ttm;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Util {
	private static final float LOW_DPI_STATUS_BAR_HEIGHT = 19f;
	private static final float MEDIUM_DPI_STATUS_BAR_HEIGHT = 25f;
	private static final float HIGH_DPI_STATUS_BAR_HEIGHT = 38f;
       //ステータスバーの高さを取得する
	public static final float getStatusBarHeight(Context context) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
		float statusBarHeight;
		switch (displayMetrics.densityDpi) {
		case DisplayMetrics.DENSITY_HIGH:
			statusBarHeight = HIGH_DPI_STATUS_BAR_HEIGHT;
			break;
		case DisplayMetrics.DENSITY_MEDIUM:
			statusBarHeight = MEDIUM_DPI_STATUS_BAR_HEIGHT;
			break;
		case DisplayMetrics.DENSITY_LOW:
			statusBarHeight = LOW_DPI_STATUS_BAR_HEIGHT;
			break;
		default:
			statusBarHeight = MEDIUM_DPI_STATUS_BAR_HEIGHT;
		}
		return statusBarHeight;
	}
}