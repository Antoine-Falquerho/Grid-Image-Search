package com.example.gridimagesearch;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	private Spinner sColor;
	private Spinner sImageType;
	private Spinner sImageSize;
	private EditText etSiteFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
//		preferences		
		String sColorPref = getIntent().getStringExtra("imgcolor");
		String imgszPref = getIntent().getStringExtra("imgsz");
		String restrictPref = getIntent().getStringExtra("restrict");
		String imgtypePref = getIntent().getStringExtra("imgtype");
		
		
		sColor = (Spinner) findViewById(R.id.sColor);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterSColor = ArrayAdapter.createFromResource(this,
		        R.array.imgcolor_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterSColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		sColor.setAdapter(adapterSColor);
		sColor.setSelection(adapterSColor.getPosition(sColorPref));
		
		sImageType = (Spinner) findViewById(R.id.sImageType);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterSImageType = ArrayAdapter.createFromResource(this,
		        R.array.imgtype_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterSImageType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		sImageType.setAdapter(adapterSImageType);
		sImageType.setSelection(adapterSImageType.getPosition(imgtypePref));
		
		
		sImageSize = (Spinner) findViewById(R.id.sImageSize);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterSImageSize = ArrayAdapter.createFromResource(this,
		        R.array.imgtype_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterSImageType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		sImageSize.setAdapter(adapterSImageSize);
		sImageSize.setSelection(adapterSImageSize.getPosition(imgszPref));
		
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
		etSiteFilter.setText(restrictPref);
	}
	
	public void onSubmit(View v){
		Intent settings = new Intent();
		settings.putExtra("imgcolor", sColor.getSelectedItem().toString());
		settings.putExtra("imgtype", sImageType.getSelectedItem().toString());
		settings.putExtra("imgsz", sImageSize.getSelectedItem().toString());
		settings.putExtra("restrict", etSiteFilter.getText().toString());		
		setResult(RESULT_OK, settings);
		finish();
		
	}
	
	public void onCancel(View v ){
		Intent settings = new Intent();		
		finish();
	}
}
