package jp.firstapp.ttm;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SetUpFunctionActivity extends PreferenceActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.layout_pref);
	}
}
