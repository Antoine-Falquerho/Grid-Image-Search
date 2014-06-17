package com.example.gridimagesearch;

import java.util.List;

import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> {
	
	public ImageResultArrayAdapter(Context context, List<ImageResult> images){
		super(context, R.layout.item_image_result, images);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ImageResult imageInfo = this.getItem(position);
        SmartImageView ivImageResult;
        TextView tvImageTitle;
        
        if (convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            convertView = inflator.inflate(R.layout.item_image_result, parent, false);
        }
        
        ivImageResult = (SmartImageView) convertView.findViewById(R.id.ivImageResult);
        tvImageTitle = (TextView) convertView.findViewById(R.id.tvImageTitle);
        
        ivImageResult.setImageUrl(imageInfo.getThumbUrl());
        tvImageTitle.setText(Html.fromHtml(imageInfo.getTitle()));
        
        return convertView;	
		
	}
	
	
}
