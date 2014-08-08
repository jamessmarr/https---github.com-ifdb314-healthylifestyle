package com.smarr.android.healthylifestyle.activity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.smarr.android.healthylifestyle.R;
import com.smarr.android.healthylifestyle.camera.TakePhoto;
import com.smarr.android.healthylifestyle.utilities.image.ImageConversion;
import com.smarr.android.healthylifestyle.utilities.shared_preferences.StoreAppInfo;

public class UserInfoUpdate extends Activity {

	private ViewFlipper flipper;
	private Button nextButton;
	private int current_weight, desired_weight, current_body_image_checked,
			desired_body_image_checked;

	private RadioButton current_image_1, current_image_2, current_image_3,
			current_image_4, current_image_5;

	private RadioButton desired_image_1, desired_image_2, desired_image_3,
			desired_image_4, desired_image_5;

	private EditText currentWeight, desiredWeight;

	private boolean currentWeightEntered, desiredWeightEntered, currentChecked, desiredChecked;

	private StoreAppInfo storage;
	
	private TakePhoto photo;
	
	private int dayCounter;

	private ImageView leftSideImage, rightSideImage, faceImage, bellyImage,
			otherImage;

	private TextView leftSideInfo, rightSideInfo, faceInfo, bellyInfo,
			otherInfo;

	private static final int CAMERA_RESULT_LEFT = 100;
	private static final int CAMERA_RESULT_RIGHT = 101;
	private static final int CAMERA_RESULT_FACE = 102;
	private static final int CAMERA_RESULT_BELLY = 103;
	private static final int CAMERA_RESULT_OTHER = 104;

	private String left_Image, right_Image, face_Image, belly_Image,
			other_Image;

	private boolean hasCamera;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info_update);

		storage = new StoreAppInfo(getApplicationContext());
				
		dayCounter = 1;
		storage.putInt("dayCounter", dayCounter);
		
		photo = new TakePhoto(this, "Day "+dayCounter);		
		hasCamera = photo.hasCamera();

		if (savedInstanceState == null) {
		}

		flipper = (ViewFlipper) findViewById(R.id.UserUpdateFlipper);
		
		currentWeight = (EditText) findViewById(R.id.currentWeight);
		currentWeight.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2,
					int arg3) throws NumberFormatException{
				try {
					current_weight = Integer.parseInt(s.toString());
					currentWeightEntered = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				checkForNext();
				
			}});
		
		desiredWeight = (EditText) findViewById(R.id.desiredWeight);
		desiredWeight.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) throws NumberFormatException{
				try {
					desired_weight = Integer.parseInt(s.toString());
					desiredWeightEntered = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
		
				checkForNext();
				
			}});
		
		current_image_1 = (RadioButton) findViewById(R.id.currentImage1);
		current_image_2 = (RadioButton) findViewById(R.id.currentImage2);
		current_image_3 = (RadioButton) findViewById(R.id.currentImage3);
		current_image_4 = (RadioButton) findViewById(R.id.currentImage4);
		current_image_5 = (RadioButton) findViewById(R.id.currentImage5);

		desired_image_1 = (RadioButton) findViewById(R.id.desiredImage1);
		desired_image_2 = (RadioButton) findViewById(R.id.desiredImage2);
		desired_image_3 = (RadioButton) findViewById(R.id.desiredImage3);
		desired_image_4 = (RadioButton) findViewById(R.id.desiredImage4);
		desired_image_5 = (RadioButton) findViewById(R.id.desiredImage5);
		
		leftSideImage = (ImageView) findViewById(R.id.leftSideImageView);
		rightSideImage = (ImageView) findViewById(R.id.rightSideImageView);
		faceImage = (ImageView) findViewById(R.id.faceImageView);
		bellyImage = (ImageView) findViewById(R.id.bellyImageView);
		otherImage = (ImageView) findViewById(R.id.otherImageView);

		// controls radio button checks since they arent in a group
		current_image_1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				current_body_image_checked = 1;
				currentChecked=true;
				checkForNext();
				
				current_image_2.setChecked(false);
				current_image_3.setChecked(false);
				current_image_4.setChecked(false);
				current_image_5.setChecked(false);
			}
		});
		current_image_2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				current_body_image_checked = 2;
				currentChecked=true;
				checkForNext();
				current_image_1.setChecked(false);
				current_image_3.setChecked(false);
				current_image_4.setChecked(false);
				current_image_5.setChecked(false);
			}
		});

		current_image_3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				current_body_image_checked = 3;
				currentChecked=true;
				checkForNext();
				current_image_1.setChecked(false);
				current_image_2.setChecked(false);
				current_image_4.setChecked(false);
				current_image_5.setChecked(false);
			}
		});

		current_image_4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				current_body_image_checked = 4;
				currentChecked=true;
				checkForNext();
				current_image_1.setChecked(false);
				current_image_2.setChecked(false);
				current_image_3.setChecked(false);
				current_image_5.setChecked(false);
			}
		});
		current_image_5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				current_body_image_checked = 5;
				currentChecked=true;
				checkForNext();
				current_image_1.setChecked(false);
				current_image_2.setChecked(false);
				current_image_3.setChecked(false);
				current_image_4.setChecked(false);
			}
		});

		desired_image_1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				desired_body_image_checked = 1;
				desiredChecked=true;
				checkForNext();
				desired_image_2.setChecked(false);
				desired_image_3.setChecked(false);
				desired_image_4.setChecked(false);
				desired_image_5.setChecked(false);
			}
		});
		desired_image_2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				desired_body_image_checked = 2;
				desiredChecked=true;
				checkForNext();
				desired_image_1.setChecked(false);
				desired_image_3.setChecked(false);
				desired_image_4.setChecked(false);
				desired_image_5.setChecked(false);
			}
		});
		desired_image_3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				desired_body_image_checked = 3;
				desiredChecked=true;
				checkForNext();
				desired_image_1.setChecked(false);
				desired_image_2.setChecked(false);
				desired_image_4.setChecked(false);
				desired_image_5.setChecked(false);
			}
		});
		desired_image_4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				desired_body_image_checked = 4;
				desiredChecked=true;
				checkForNext();
				desired_image_1.setChecked(false);
				desired_image_2.setChecked(false);
				desired_image_3.setChecked(false);
				desired_image_5.setChecked(false);
			}
		});
		desired_image_5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				desired_body_image_checked = 5;
				desiredChecked=true;
				checkForNext();
				desired_image_1.setChecked(false);
				desired_image_2.setChecked(false);
				desired_image_3.setChecked(false);
				desired_image_4.setChecked(false);
			}
		});

		nextButton = (Button) findViewById(R.id.next_button);
		nextButton.setEnabled(false);

		nextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (flipper.getDisplayedChild() != flipper.getChildCount() - 1) {
					
					if (flipper.getDisplayedChild() > 0 && !hasCamera){
						storage.putInt("current_Weight", current_weight);
						storage.putInt("desired_Weight", desired_weight);
						storage.putInt("current_body_image",
								current_body_image_checked);
						storage.putInt("desired_body_image",
								desired_body_image_checked);
						Toast.makeText(getApplicationContext(), "Camera not detected", Toast.LENGTH_SHORT).show();
						nextActivity();
					}else{
						flipper.showNext();
					nextButton.setEnabled(false);
					}
				} else {

					storage.putInt("current_Weight", current_weight);
					storage.putInt("desired_Weight", desired_weight);
					storage.putInt("current_body_image",
							current_body_image_checked);
					storage.putInt("desired_body_image",
							desired_body_image_checked);
					sendPhotos();
					nextActivity();
				}
			}

		});

	}
	public void onTakeLeftPhotoClicked(View v) {
		
		startActivityForResult(photo.takeLeftPhoto(), CAMERA_RESULT_LEFT);

	}

	public void onTakeRightPhotoClicked(View v) {
		

		startActivityForResult(photo.takeRightPhoto(), CAMERA_RESULT_RIGHT);

	}

	public void onTakeFacePhotoClicked(View v) {
		

		startActivityForResult(photo.takeFacePhoto(), CAMERA_RESULT_FACE);

	}

	public void onTakeBellyPhotoClicked(View v) {
		

		startActivityForResult(photo.takeBellyPhoto(), CAMERA_RESULT_BELLY);

	}

	public void onTakeOtherPhotoClicked(View v) {

		startActivityForResult(photo.takeOtherPhoto(), CAMERA_RESULT_OTHER);

	}

	private void nextActivity() {

		
			Intent userStatus = new Intent(this, UserStatus.class);
			startActivity(userStatus);
		

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_RESULT_LEFT) {
			// converts the returned image into a bitmap
				Bitmap myBitmap = photo.handleLeftPhoto();

				leftSideImage.setImageBitmap(myBitmap);
				// encode the bitmap for database transmission
				left_Image = ImageConversion.encodeToBase64Image(myBitmap);

				leftSideInfo = (TextView) findViewById(R.id.leftSidePhotoInstructions);
				leftSideInfo.setVisibility(View.GONE);

				nextButton.setEnabled(true);
			
		}
		if (requestCode == CAMERA_RESULT_RIGHT) {
			
				
			Bitmap myBitmap = photo.handleRightPhoto();

				rightSideImage.setImageBitmap(myBitmap);
				// encode the bitmap for database transmission
				right_Image = ImageConversion.encodeToBase64Image(myBitmap);

				rightSideInfo = (TextView) findViewById(R.id.rightSidePhotoInstructions);
				rightSideInfo.setVisibility(View.GONE);

				nextButton.setEnabled(true);
			

		}
		if (requestCode == CAMERA_RESULT_FACE) {
				Bitmap myBitmap = photo.handleFacePhoto();

				faceImage.setImageBitmap(myBitmap);
				// encode the bitmap for database transmission
				face_Image = ImageConversion.encodeToBase64Image(myBitmap);

				faceInfo = (TextView) findViewById(R.id.facePhotoInstructions);
				faceInfo.setVisibility(View.GONE);

				nextButton.setEnabled(true);
			

		}
		if (requestCode == CAMERA_RESULT_BELLY) {
				Bitmap myBitmap = photo.handleBellyPhoto();

				bellyImage.setImageBitmap(myBitmap);
				// encode the bitmap for database transmission
				belly_Image = ImageConversion.encodeToBase64Image(myBitmap);

				bellyInfo = (TextView) findViewById(R.id.bellyPhotoInstructions);
				bellyInfo.setVisibility(View.GONE);

				nextButton.setEnabled(true);
			

		}
		if (requestCode == CAMERA_RESULT_OTHER) {
				Bitmap myBitmap = photo.handleOtherPhoto();

				otherImage.setImageBitmap(myBitmap);
				// encode the bitmap for database transmission
				other_Image = ImageConversion.encodeToBase64Image(myBitmap);

				otherInfo = (TextView) findViewById(R.id.otherPhotoInstructions);
				otherInfo.setVisibility(View.GONE);

				nextButton.setEnabled(true);
			

		}
	}

	public void sendPhotos() {
		photo.sendPhotos(left_Image, right_Image, face_Image, belly_Image, other_Image, null);
	}

	public void checkForNext(){
		if (currentWeightEntered){
			currentWeight.setError(null);
		}else{
			currentWeight.setError("Required");
		}
		if(desiredWeightEntered){
			desiredWeight.setError(null);
		}else{
			desiredWeight.setError("Required");
		}
		if(currentChecked){
			current_image_5.setError(null);
		}else{
			current_image_5.setError("Required");
		}
		if(desiredChecked){
			desired_image_5.setError(null);
		}else{
			desired_image_5.setError("Required");
		}
		if (currentWeightEntered && desiredWeightEntered && currentChecked && desiredChecked){
			nextButton.setEnabled(true);
			
		}
	}
}
