package com.smarr.android.healthylifestyle.activity;

import org.joda.time.DateTime;

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
import com.smarr.android.healthylifestyle.misc_class.DateFormatsAndInfo;
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

	private boolean currentWeightEntered, desiredWeightEntered, currentChecked,
			desiredChecked;

	private StoreAppInfo storage;

	private TakePhoto photo;
	private int weightCounter = 0;

	private ImageView leftSideImage, rightSideImage, faceImage, bellyImage,
			otherImage;

	private TextView leftSideInfo, rightSideInfo, faceInfo, bellyInfo,
			otherInfo;

	private Bitmap myBitmap;

	private static final int CAMERA_RESULT_LEFT = 100;
	private static final int CAMERA_RESULT_RIGHT = 101;
	private static final int CAMERA_RESULT_FACE = 102;
	private static final int CAMERA_RESULT_BELLY = 103;
	private static final int CAMERA_RESULT_OTHER = 104;

	private String left_Image, right_Image, face_Image, belly_Image,
			other_Image;

	private boolean hasCamera;

	private DateTime date;
	private DateTime nextSaturday,nextSaturdayPhoto, nextSaturdayWeight;
	private DateFormatsAndInfo dateInfo = new DateFormatsAndInfo();
	private String today;
	private String nextPhotoDay, nextWeightDay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info_update);

		storage = new StoreAppInfo(getApplicationContext());
		// sets new local date for user
		date = new DateTime();

		// gets the date in specified format
		today = dateInfo.getDateFormat(date);

		photo = new TakePhoto(this, "Day 1", today);
		hasCamera = photo.hasCamera();
		// sets the date of the next saturday for tracking future user
		// requirements
		nextSaturday = dateInfo.getNextSaturdayFirstUse(date);
		// adds 2 weeks to the closest saturday
		nextSaturdayPhoto = nextSaturday.plusDays(14);
		nextSaturdayWeight = nextSaturday.plus(7);
		// converts date to string for storage
		nextPhotoDay = dateInfo.getDateFormat(nextSaturdayPhoto);
		nextWeightDay = dateInfo.getDateFormat(nextSaturdayWeight);
		// stores a string value for the date
		storage.putString("firstPhoto", today);
		storage.putString("lastPhoto", today);
		storage.putString("nextPhotoDay", nextPhotoDay);
		storage.putString("nextWeightDay", nextWeightDay);
		storage.putString("lastFolderName", "Day 1");

		if (savedInstanceState == null) {
		}

		flipper = (ViewFlipper) findViewById(R.id.UserUpdateFlipper);
		// grabs initial user info
		currentWeight = (EditText) findViewById(R.id.currentWeight);
		currentWeight.addTextChangedListener(new TextWatcher() {

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
					int arg3) throws NumberFormatException {
				try {
					current_weight = Integer.parseInt(s.toString());
					currentWeightEntered = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				checkForNext();

			}
		});
		// grabs initial user info
		desiredWeight = (EditText) findViewById(R.id.desiredWeight);
		desiredWeight.addTextChangedListener(new TextWatcher() {

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
					int count) throws NumberFormatException {
				try {
					desired_weight = Integer.parseInt(s.toString());
					desiredWeightEntered = true;
				} catch (Exception e) {
					e.printStackTrace();
				}

				checkForNext();

			}
		});
		// grab radioButton elements from the UI
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
		// grab photo imageview elements from UI
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
				currentChecked = true;
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
				currentChecked = true;
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
				currentChecked = true;
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
				currentChecked = true;
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
				currentChecked = true;
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
				desiredChecked = true;
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
				desiredChecked = true;
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
				desiredChecked = true;
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
				desiredChecked = true;
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
				desiredChecked = true;
				checkForNext();
				desired_image_1.setChecked(false);
				desired_image_2.setChecked(false);
				desired_image_3.setChecked(false);
				desired_image_4.setChecked(false);
			}
		});
		// grab and control next button from UI
		nextButton = (Button) findViewById(R.id.next_button);
		nextButton.setEnabled(false);

		nextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (flipper.getDisplayedChild() != flipper.getChildCount() - 1) {

					if (flipper.getDisplayedChild() > 0 && !hasCamera) {
						storage.putBoolean("doneUpdate", true);
						storage.putInt("weightCounter", weightCounter);
						storage.putInt("current_Weight"+weightCounter, current_weight);
						storage.putInt("desired_Weight", desired_weight);
						storage.putInt("current_body_image",
								current_body_image_checked);
						storage.putInt("desired_body_image",
								desired_body_image_checked);
						Toast.makeText(getApplicationContext(),
								"Camera not detected", Toast.LENGTH_SHORT)
								.show();
						nextActivity();
					} else {
						flipper.showNext();
						nextButton.setEnabled(false);
					}
				} else {
					storage.putBoolean("doneUpdate", true);
					storage.putInt("weightCounter", weightCounter);
					storage.putInt("current_Weight"+weightCounter, current_weight);
					storage.putInt("desired_Weight", desired_weight);
					storage.putString("lastWeightUpdate", today);
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

	// controls individual photo buttons and photo intents
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

	// controls movement to next activity
	private void nextActivity() {

		Intent userStatus = new Intent(this, UserStatus.class);
		startActivity(userStatus);

	}

	// handles result from intent for result
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_RESULT_LEFT) {
			if (myBitmap != null) {
				myBitmap.recycle();
				myBitmap = null;
				System.gc();
			}
			// converts the returned image into a bitmap
			myBitmap = photo.handleLeftPhoto();

			leftSideImage.setImageBitmap(myBitmap);
			// encode the bitmap for database transmission
			left_Image = ImageConversion.encodeToBase64Image(myBitmap);

			leftSideInfo = (TextView) findViewById(R.id.leftSidePhotoInstructions);
			leftSideInfo.setVisibility(View.GONE);

			nextButton.setEnabled(true);

		}
		if (requestCode == CAMERA_RESULT_RIGHT) {
			if (myBitmap != null) {
				myBitmap.recycle();
				myBitmap = null;
				System.gc();
			}

			myBitmap = photo.handleRightPhoto();

			rightSideImage.setImageBitmap(myBitmap);
			// encode the bitmap for database transmission
			right_Image = ImageConversion.encodeToBase64Image(myBitmap);

			rightSideInfo = (TextView) findViewById(R.id.rightSidePhotoInstructions);
			rightSideInfo.setVisibility(View.GONE);

			nextButton.setEnabled(true);

		}
		if (requestCode == CAMERA_RESULT_FACE) {
			if (myBitmap != null) {
				myBitmap.recycle();
				myBitmap = null;
				System.gc();
			}
			myBitmap = photo.handleFacePhoto();

			faceImage.setImageBitmap(myBitmap);
			// encode the bitmap for database transmission
			face_Image = ImageConversion.encodeToBase64Image(myBitmap);

			faceInfo = (TextView) findViewById(R.id.facePhotoInstructions);
			faceInfo.setVisibility(View.GONE);

			nextButton.setEnabled(true);

		}
		if (requestCode == CAMERA_RESULT_BELLY) {
			if (myBitmap != null) {
				myBitmap.recycle();
				myBitmap = null;
				System.gc();
			}
			myBitmap = photo.handleBellyPhoto();

			bellyImage.setImageBitmap(myBitmap);
			// encode the bitmap for database transmission
			belly_Image = ImageConversion.encodeToBase64Image(myBitmap);

			bellyInfo = (TextView) findViewById(R.id.bellyPhotoInstructions);
			bellyInfo.setVisibility(View.GONE);

			nextButton.setEnabled(true);

		}
		if (requestCode == CAMERA_RESULT_OTHER) {
			if (myBitmap != null) {
				myBitmap.recycle();
				myBitmap = null;
				System.gc();
			}
			myBitmap = photo.handleOtherPhoto();

			otherImage.setImageBitmap(myBitmap);
			// encode the bitmap for database transmission
			other_Image = ImageConversion.encodeToBase64Image(myBitmap);

			otherInfo = (TextView) findViewById(R.id.otherPhotoInstructions);
			otherInfo.setVisibility(View.GONE);

			nextButton.setEnabled(true);

		}
	}

	// handles sending of photos to server
	public void sendPhotos() {
		photo.sendPhotos(left_Image, right_Image, face_Image, belly_Image,
				other_Image, null);
	}

	// handles validation of input to ensure all fields are filled out
	public void checkForNext() {
		if (currentWeightEntered) {
			currentWeight.setError(null);
		} else {
			currentWeight.setError("Required");
		}
		if (desiredWeightEntered) {
			desiredWeight.setError(null);
		} else {
			desiredWeight.setError("Required");
		}
		if (currentChecked) {
			current_image_5.setError(null);
		} else {
			current_image_5.setError("Required");
		}
		if (desiredChecked) {
			desired_image_5.setError(null);
		} else {
			desired_image_5.setError("Required");
		}
		if (currentWeightEntered && desiredWeightEntered && currentChecked
				&& desiredChecked) {
			nextButton.setEnabled(true);

		}
	}
}
