package com.example.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends Activity {
	private final int SETTINGS_CODE = 10;
	private EditText etQuery;
	private GridView gvResults;
	private Button btnSearch;	
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
//	PREF
	private String imgcolor;	
	private String imgsz;
	private String restrict;
	private String imgtype;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
			
		});
		
		gvResults.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
		    	searchImage(page);
		    }
	    });
		
		setPreferences();
		
	}
	
	private void setPreferences() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		imgcolor = pref.getString("imgcolor", "");
		imgsz = pref.getString("imgsz", "");
		restrict = pref.getString("restrict", "");
		imgtype = pref.getString("imgtype", "");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	} 
	
	public void onSettings(MenuItem mi){
		Intent iSettings = new Intent(MainActivity.this, SettingsActivity.class);
		iSettings.putExtra("imgcolor", imgcolor);		
		iSettings.putExtra("imgsz", imgsz);
		iSettings.putExtra("restrict", restrict);
		iSettings.putExtra("imgtype", imgtype);
		startActivityForResult(iSettings, SETTINGS_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == SETTINGS_CODE) {		 
	     SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
	     Editor edit = pref.edit();
	     imgcolor = data.getExtras().getString("imgcolor");	     
	     edit.putString("imgcolor", imgcolor );
	     edit.putString("imgsz", data.getExtras().getString("imgsz"));
	     edit.putString("restrict", data.getExtras().getString("restrict"));
	     edit.putString("imgtype", data.getExtras().getString("imgtype"));
	     edit.commit();	    
	     setPreferences();
	     searchImage(0);
	     Toast.makeText(this, "Settings Saved!", Toast.LENGTH_SHORT).show();
	  }
	} 
	
	public void setupViews(){
		etQuery = (EditText)findViewById(R.id.etQuery);
		gvResults = (GridView)findViewById(R.id.gvResults);
		btnSearch = (Button)findViewById(R.id.btnSearch);
	}
	
	public void onImageSearch(View v){
		searchImage(0);
	}
	
	public void searchImage(int page){
		String query = etQuery.getText().toString();
		if(page == 0){
			imageResults.clear();
		}
		AsyncHttpClient client = new AsyncHttpClient();
		// https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android
		client.get("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + Uri.encode(query) + "&rsz=8&start=" + (page * 8) + "&imgcolor=" + imgcolor + "&imgsz=" + imgsz + "&restrict=" + restrict + "&imgtype=" + imgtype, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject response){				
				try{
					JSONArray imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");					
					imageAdapter.addAll(ImageResult.fromJsonArray(imageJsonResults));					
				} catch(JSONException e){
					e.printStackTrace();
				}
			}			
		});
	}
}
