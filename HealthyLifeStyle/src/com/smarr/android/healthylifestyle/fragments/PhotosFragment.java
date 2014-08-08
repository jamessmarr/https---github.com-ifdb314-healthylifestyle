package com.smarr.android.healthylifestyle.fragments;

import java.util.Calendar;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.smarr.android.healthylifestyle.R;
import com.smarr.android.healthylifestyle.camera.TakePhoto;
import com.smarr.android.healthylifestyle.utilities.image.ImageConversion;
import com.smarr.android.healthylifestyle.utilities.shared_preferences.StoreAppInfo;

public class PhotosFragment extends Fragment {

	private View rootView;

	private boolean hasCamera;

	private int dayCounter;

	private int dayOfLastPhoto;

	private int difference;

	private StoreAppInfo storage;

	private String dayLabel;

	private TakePhoto photo;

	private Button nextButton, leftPhoto, rightPhoto, facePhoto, bellyPhoto,
			otherPhoto;

	private ViewFlipper flipper;

	private ImageView leftSideImage, rightSideImage, faceImage, bellyImage,
			otherImage;

	private String left_Image, right_Image, face_Image, belly_Image,
			other_Image;

	private TextView leftSideInfo, rightSideInfo, faceInfo, bellyInfo,
			otherInfo;

	private static final int CAMERA_RESULT_LEFT = 100;
	private static final int CAMERA_RESULT_RIGHT = 101;
	private static final int CAMERA_RESULT_FACE = 102;
	private static final int CAMERA_RESULT_BELLY = 103;
	private static final int CAMERA_RESULT_OTHER = 104;

	public PhotosFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_photos, container, false);

		storage = new StoreAppInfo(getActivity());

		dayOfLastPhoto = storage.getInt("dayOfLastPhoto", 0);

		dayCounter = storage.getInt("dayCounter", 0);

		difference = Calendar.DAY_OF_YEAR - dayOfLastPhoto;
		if (difference < 0){
			
			difference = (Calendar.DAY_OF_YEAR +365) - dayOfLastPhoto;
		}
		
		if (difference == 0 || difference <7){
			dayLabel = "Day "+dayCounter;
		}else if(difference >=7){
			dayCounter = difference + dayCounter;
			dayLabel = "Day"+dayCounter;
			storage.putInt("dayCounter", dayCounter);
		}
		
		
		photo = new TakePhoto(getActivity(), dayLabel);
		hasCamera = photo.hasCamera();
		if (!hasCamera) {
			Toast.makeText(getActivity(), "No camera detected on this device",
					Toast.LENGTH_SHORT).show();
			getFragmentManager().popBackStackImmediate();

		}

		flipper = (ViewFlipper) rootView
				.findViewById(R.id.photoFragmentFlipper);

		leftSideImage = (ImageView) rootView.findViewById(R.id.leftSideImage);
		rightSideImage = (ImageView) rootView.findViewById(R.id.rightSideImage);
		faceImage = (ImageView) rootView.findViewById(R.id.faceImage);
		bellyImage = (ImageView) rootView.findViewById(R.id.bellyImage);
		otherImage = (ImageView) rootView.findViewById(R.id.otherImage);

		nextButton = (Button) rootView.findViewById(R.id.nextBTN);
		nextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (flipper.getDisplayedChild() != flipper.getChildCount() - 1) {
					flipper.showNext();
					nextButton.setEnabled(false);
				} else {

					photo.sendPhotos(left_Image, right_Image, face_Image,
							belly_Image, other_Image, null);

					Toast.makeText(getActivity(), "Photos Updated",
							Toast.LENGTH_SHORT).show();
					getFragmentManager().popBackStackImmediate();
				}

			}
		});

		leftPhoto = (Button) rootView.findViewById(R.id.leftPhotoButton);
		rightPhoto = (Button) rootView.findViewById(R.id.rightPhotoButton);
		facePhoto = (Button) rootView.findViewById(R.id.facePhotoButton);
		bellyPhoto = (Button) rootView.findViewById(R.id.bellyPhotoButton);
		otherPhoto = (Button) rootView.findViewById(R.id.otherPhotoButton);

		leftPhoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onTakeLeftPhotoClicked();

			}
		});

		rightPhoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onTakeRightPhotoClicked();

			}
		});

		facePhoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onTakeFacePhotoClicked();

			}
		});

		bellyPhoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onTakeBellyPhotoClicked();

			}
		});

		otherPhoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onTakeOtherPhotoClicked();

			}
		});

		return rootView;
	}

	public void onTakeLeftPhotoClicked() {

		startActivityForResult(photo.takeLeftPhoto(), CAMERA_RESULT_LEFT);

	}

	public void onTakeRightPhotoClicked() {

		startActivityForResult(photo.takeRightPhoto(), CAMERA_RESULT_RIGHT);

	}

	public void onTakeFacePhotoClicked() {

		startActivityForResult(photo.takeFacePhoto(), CAMERA_RESULT_FACE);

	}

	public void onTakeBellyPhotoClicked() {

		startActivityForResult(photo.takeBellyPhoto(), CAMERA_RESULT_BELLY);

	}

	public void onTakeOtherPhotoClicked() {

		startActivityForResult(photo.takeOtherPhoto(), CAMERA_RESULT_OTHER);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_RESULT_LEFT) {
			// converts the returned image into a bitmap
			Bitmap myBitmap = photo.handleLeftPhoto();

			leftSideImage.setImageBitmap(myBitmap);
			// encode the bitmap for database transmission
			left_Image = ImageConversion.encodeToBase64Image(myBitmap);

			leftSideInfo = (TextView) rootView.findViewById(R.id.leftSidePhoto);
			leftSideInfo.setVisibility(View.GONE);

			nextButton.setEnabled(true);

		}
		if (requestCode == CAMERA_RESULT_RIGHT) {

			Bitmap myBitmap = photo.handleRightPhoto();

			rightSideImage.setImageBitmap(myBitmap);
			// encode the bitmap for database transmission
			right_Image = ImageConversion.encodeToBase64Image(myBitmap);

			rightSideInfo = (TextView) rootView
					.findViewById(R.id.rightSidePhoto);
			rightSideInfo.setVisibility(View.GONE);

			nextButton.setEnabled(true);

		}
		if (requestCode == CAMERA_RESULT_FACE) {
			Bitmap myBitmap = photo.handleFacePhoto();

			faceImage.setImageBitmap(myBitmap);
			// encode the bitmap for database transmission
			face_Image = ImageConversion.encodeToBase64Image(myBitmap);

			faceInfo = (TextView) rootView.findViewById(R.id.facePhoto);
			faceInfo.setVisibility(View.GONE);

			nextButton.setEnabled(true);

		}
		if (requestCode == CAMERA_RESULT_BELLY) {
			Bitmap myBitmap = photo.handleBellyPhoto();

			bellyImage.setImageBitmap(myBitmap);
			// encode the bitmap for database transmission
			belly_Image = ImageConversion.encodeToBase64Image(myBitmap);

			bellyInfo = (TextView) rootView.findViewById(R.id.bellyPhoto);
			bellyInfo.setVisibility(View.GONE);

			nextButton.setEnabled(true);

		}
		if (requestCode == CAMERA_RESULT_OTHER) {
			Bitmap myBitmap = photo.handleOtherPhoto();

			otherImage.setImageBitmap(myBitmap);
			// encode the bitmap for database transmission
			other_Image = ImageConversion.encodeToBase64Image(myBitmap);

			otherInfo = (TextView) rootView.findViewById(R.id.otherPhoto);
			otherInfo.setVisibility(View.GONE);

			nextButton.setEnabled(true);

		}
	}

}
