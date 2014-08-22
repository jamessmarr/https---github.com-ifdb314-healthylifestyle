package com.smarr.android.healthylifestyle.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.smarr.android.healthylifestyle.R;
import com.smarr.android.healthylifestyle.utilities.shared_preferences.StoreAppInfo;

@SuppressLint("InflateParams") public class Review extends Fragment {
	private View rootView;
	private StoreAppInfo storage;
	
	private LinearLayout leftGallery, rightGallery, faceGallery, bellyGallery, otherGallery;
	
	private int photoCounter;
	

	
	private String leftHold, rightHold, faceHold, bellyHold, otherHold;
	
	private Bitmap myBitmap;
	
	public Review() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_review, container, false);

		storage = new StoreAppInfo(getActivity());
		
		leftGallery = (LinearLayout)rootView.findViewById(R.id.leftGallery);
		rightGallery = (LinearLayout)rootView.findViewById(R.id.rightGallery);
		faceGallery = (LinearLayout)rootView.findViewById(R.id.faceGallery);
		bellyGallery = (LinearLayout)rootView.findViewById(R.id.bellyGallery);
		otherGallery = (LinearLayout)rootView.findViewById(R.id.otherGallery);
		
		photoCounter = storage.getInt("photoCounter", 0);
		
		loopThroughPhotos(inflater);
		

		return rootView;
	}


	
	public void loopThroughPhotos(LayoutInflater inflater){
		//loops through existing saved photos converts from string to bitmap then stores in arraylist
		for(int i=0;i<=photoCounter;i++){
			leftHold = storage.getString("leftSide"+i, "");
			rightHold = storage.getString("rightSide"+i, "");
			faceHold = storage.getString("faceImage"+i, "");
			bellyHold = storage.getString("bellyImage"+i, "");
			otherHold = storage.getString("otherImage"+i, "");
			
			leftPhoto(inflater);
			rightPhoto(inflater);
			facePhoto(inflater);
			bellyPhoto(inflater);
			otherPhoto(inflater);
			
		}
		
	}
	
	
	public void leftPhoto(LayoutInflater inflater){
		
		ImageView leftImageGallery = (ImageView)inflater.inflate(R.layout.image, null);
		
		myBitmap = BitmapFactory.decodeFile(leftHold);
		
		Matrix matrix = new Matrix();

		matrix.postRotate(90);

		myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),
				myBitmap.getHeight(), matrix, true);
		leftImageGallery.setImageBitmap(Bitmap.createScaledBitmap(myBitmap, 600, 800, false));
		leftGallery.addView(leftImageGallery);
	}
	
	public void rightPhoto(LayoutInflater inflater){
		
		ImageView rightImageGallery = (ImageView)inflater.inflate(R.layout.image, null);
		
		myBitmap = BitmapFactory.decodeFile(rightHold);
		
		Matrix matrix = new Matrix();

		matrix.postRotate(90);

		myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),
				myBitmap.getHeight(), matrix, true);
		rightImageGallery.setImageBitmap(Bitmap.createScaledBitmap(myBitmap, 600, 800, false));
		rightGallery.addView(rightImageGallery);
	}
	
	public void facePhoto(LayoutInflater inflater){
ImageView faceImageGallery = (ImageView)inflater.inflate(R.layout.image, null);
		
		myBitmap = BitmapFactory.decodeFile(faceHold);
		
		Matrix matrix = new Matrix();

		matrix.postRotate(90);

		myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),
				myBitmap.getHeight(), matrix, true);
		faceImageGallery.setImageBitmap(Bitmap.createScaledBitmap(myBitmap, 600, 800, false));
		faceGallery.addView(faceImageGallery);
		
	}
	
	public void bellyPhoto(LayoutInflater inflater){
ImageView bellyImageGallery = (ImageView)inflater.inflate(R.layout.image, null);
		
		myBitmap = BitmapFactory.decodeFile(bellyHold);
		
		Matrix matrix = new Matrix();

		matrix.postRotate(90);

		myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),
				myBitmap.getHeight(), matrix, true);
		bellyImageGallery.setImageBitmap(Bitmap.createScaledBitmap(myBitmap, 600, 800, false));
		bellyGallery.addView(bellyImageGallery);
		
	}
	
	public void otherPhoto(LayoutInflater inflater){
ImageView otherImageGallery = (ImageView)inflater.inflate(R.layout.image, null);
		
		myBitmap = BitmapFactory.decodeFile(otherHold);
		
		Matrix matrix = new Matrix();

		matrix.postRotate(90);

		myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),
				myBitmap.getHeight(), matrix, true);
		otherImageGallery.setImageBitmap(Bitmap.createScaledBitmap(myBitmap, 600, 800, false));
		otherGallery.addView(otherImageGallery);
		
	}
}
