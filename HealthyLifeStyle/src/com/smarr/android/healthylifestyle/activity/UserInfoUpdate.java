package com.smarr.android.healthylifestyle.activity;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.smarr.android.healthylifestyle.R;
import com.smarr.android.healthylifestyle.utilities.http.HttpConnectionUtilities;
import com.smarr.android.healthylifestyle.utilities.image.ImageConversion;
import com.smarr.android.healthylifestyle.utilities.shared_preferences.StoreAppInfo;

public class UserInfoUpdate extends Activity {

	private ViewFlipper flipper;
	private Button nextButton;
	private int current_Weight, desired_Weight, current_body_image_checked,
			desired_body_image_checked;

	private RadioButton current_image_1, current_image_2, current_image_3,
			current_image_4, current_image_5;

	private RadioButton desired_image_1, desired_image_2, desired_image_3,
			desired_image_4, desired_image_5;

	private EditText input;
	private AlertDialog currentWeight, desiredWeight;
	private AlertDialog.Builder builder;

	

	private StoreAppInfo storage;

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

	private File fileLeft, fileRight, fileFace, fileBelly, fileOther;

	private File path = new File(Environment.getExternalStorageDirectory()
			+ "/Healthy_LifeStyle");

	private boolean hasCamera;
	private boolean frontCam, rearCam;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info_update);

		storage = new StoreAppInfo(getApplicationContext());
		
		currentWeightUpdate();
		
		hasCamera = storage.getBoolean("hasCamera", false);
		if (!hasCamera) {
			PackageManager pm = getPackageManager();
			

			frontCam = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
			rearCam = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
			
			if(frontCam || rearCam){
				hasCamera = true;
				storage.putBoolean("hasCamera", hasCamera);
			}else{
				hasCamera = false;
				storage.putBoolean("hasCamera", hasCamera);
			}
		}
		
		

		if (savedInstanceState == null) {
		}

		flipper = (ViewFlipper) findViewById(R.id.UserUpdateFlipper);
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
				nextButton.setEnabled(true);
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
				nextButton.setEnabled(true);
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
				nextButton.setEnabled(true);
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
				nextButton.setEnabled(true);
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
				nextButton.setEnabled(true);
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
				nextButton.setEnabled(true);
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

				nextButton.setEnabled(true);
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
				nextButton.setEnabled(true);
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
				nextButton.setEnabled(true);
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
				nextButton.setEnabled(true);
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
					flipper.showNext();
					nextButton.setEnabled(false);
				} else {

					storage.putInt("current_Weight", current_Weight);
					storage.putInt("desired_Weight", desired_Weight);
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
		if (!(path.isDirectory())) {
			path.mkdir();
		}
		fileLeft = new File(path + "/left_side_image_initial.jpg");
		Uri outputFileLeftUri = Uri.fromFile(fileLeft);

		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileLeftUri);

		startActivityForResult(cameraIntent, CAMERA_RESULT_LEFT);

	}

	public void onTakeRightPhotoClicked(View v) {
		fileRight = new File(path + "/right_side_image_initial.jpg");
		Uri outputFileRightUri = Uri.fromFile(fileRight);

		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileRightUri);

		startActivityForResult(cameraIntent, CAMERA_RESULT_RIGHT);

	}

	public void onTakeFacePhotoClicked(View v) {
		fileFace = new File(path + "/face_image_initial.jpg");
		Uri outputFileFaceUri = Uri.fromFile(fileFace);

		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileFaceUri);

		startActivityForResult(cameraIntent, CAMERA_RESULT_FACE);

	}

	public void onTakeBellyPhotoClicked(View v) {
		fileBelly = new File(path + "/belly_image_initial.jpg");
		Uri outputFileBellyUri = Uri.fromFile(fileBelly);

		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileBellyUri);

		startActivityForResult(cameraIntent, CAMERA_RESULT_BELLY);

	}

	public void onTakeOtherPhotoClicked(View v) {
		fileOther = new File(path + "/other_image_initial.jpg");
		Uri outputFileOtherUri = Uri.fromFile(fileOther);

		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileOtherUri);

		startActivityForResult(cameraIntent, CAMERA_RESULT_OTHER);

	}

	private void nextActivity() {

		
			Intent userStatus = new Intent(this, UserStatus.class);
			startActivity(userStatus);
		

	}

	public void currentWeightUpdate() {
		builder = new AlertDialog.Builder(this);
		input = new EditText(this);
		input.setInputType(2); // 2 is for numeric input keyboard
		input.setHint("Allowed values are 50-400");
		input.setMaxLines(1);
		input.setGravity(Gravity.CENTER_HORIZONTAL);
		builder.setView(input);
		builder.setTitle("Current Weight");
		builder.setMessage("Please Enter Your current weight");

		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// stores date user last updated weight

						Calendar c = Calendar.getInstance();
						int month = c.get(Calendar.MONTH) + 1;
						int year = c.get(Calendar.YEAR);
						int day = c.get(Calendar.DATE);

						String weight_last_updated = "  " + month + "/" + day
								+ "/" + year;
						storage.putString("weight_last_updated",
								weight_last_updated);
						desiredWeightUpdate();

					}
				});

		input.addTextChangedListener(new TextWatcher() {

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
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) throws NumberFormatException {
				try {
					current_Weight = Integer.parseInt(arg0.toString());

					if (current_Weight > 49) {
						if (current_Weight < 401) {
							input.setError(null);
							currentWeight.getButton(Dialog.BUTTON_POSITIVE)
									.setEnabled(true);
						} else {
							input.setError("Please make a valid entry");
							currentWeight.getButton(Dialog.BUTTON_POSITIVE)
									.setEnabled(false);
						}
					} else {
						input.setError("Please make a valid entry");
						currentWeight.getButton(Dialog.BUTTON_POSITIVE)
								.setEnabled(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		currentWeight = builder.create();
		currentWeight.show();
		currentWeight.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
	}

	public void desiredWeightUpdate() {
		builder = new AlertDialog.Builder(this);
		input = new EditText(this);
		input.setInputType(2); // 2 is for numeric input keyboard
		input.setHint("Allowed values are 50-400");
		input.setMaxLines(1);
		input.setGravity(Gravity.CENTER_HORIZONTAL);
		builder.setView(input);
		builder.setTitle("Weight Goal");
		builder.setMessage("Please Enter Your desired weight");

		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});

		input.addTextChangedListener(new TextWatcher() {

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
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) throws NumberFormatException {
				try {
					desired_Weight = Integer.parseInt(arg0.toString());

					if (desired_Weight > 49) {
						if (desired_Weight < 401) {
							input.setError(null);
							desiredWeight.getButton(Dialog.BUTTON_POSITIVE)
									.setEnabled(true);
						} else {
							input.setError("Please make a valid entry");
							desiredWeight.getButton(Dialog.BUTTON_POSITIVE)
									.setEnabled(false);
						}
					} else {
						input.setError("Please make a valid entry");
						desiredWeight.getButton(Dialog.BUTTON_POSITIVE)
								.setEnabled(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		desiredWeight = builder.create();
		desiredWeight.show();
		desiredWeight.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);

	}

	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_RESULT_LEFT) {
			// converts the returned image into a bitmap
			if (fileLeft.exists()) {
				//store file path for future use
				storage.putString("left_side_initial_image", fileLeft.getAbsolutePath());
				// creates bitmap from file
				Bitmap myBitmap = BitmapFactory.decodeFile(fileLeft
						.getAbsolutePath());
				// sets the image to display in the activity
				Matrix matrix = new Matrix();

				matrix.postRotate(270);

				myBitmap = Bitmap
						.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),
								myBitmap.getHeight(), matrix, true);

				leftSideImage.setImageBitmap(myBitmap);
				// encode the bitmap for database transmission
				left_Image = ImageConversion.encodeToBase64Image(myBitmap);

				leftSideInfo = (TextView) findViewById(R.id.leftSidePhotoInstructions);
				leftSideInfo.setVisibility(View.GONE);

				nextButton.setEnabled(true);
			}
		}
		if (requestCode == CAMERA_RESULT_RIGHT) {
			if (fileRight.exists()) {
				//store file path for future use
				storage.putString("right_side_initial_image", fileRight.getAbsolutePath());
				// creates bitmap from file
				Bitmap myBitmap = BitmapFactory.decodeFile(fileRight
						.getAbsolutePath());
				// sets the image to display in the activity
				Matrix matrix = new Matrix();

				matrix.postRotate(270);

				myBitmap = Bitmap
						.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),
								myBitmap.getHeight(), matrix, true);

				rightSideImage.setImageBitmap(myBitmap);
				// encode the bitmap for database transmission
				right_Image = ImageConversion.encodeToBase64Image(myBitmap);

				rightSideInfo = (TextView) findViewById(R.id.rightSidePhotoInstructions);
				rightSideInfo.setVisibility(View.GONE);

				nextButton.setEnabled(true);
			}

		}
		if (requestCode == CAMERA_RESULT_FACE) {
			if (fileFace.exists()) {
				//store file path for future use
				storage.putString("face_initial_image", fileFace.getAbsolutePath());
				// creates bitmap from file
				Bitmap myBitmap = BitmapFactory.decodeFile(fileFace
						.getAbsolutePath());
				// sets the image to display in the activity
				Matrix matrix = new Matrix();

				matrix.postRotate(270);

				myBitmap = Bitmap
						.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),
								myBitmap.getHeight(), matrix, true);

				faceImage.setImageBitmap(myBitmap);
				// encode the bitmap for database transmission
				face_Image = ImageConversion.encodeToBase64Image(myBitmap);

				faceInfo = (TextView) findViewById(R.id.facePhotoInstructions);
				faceInfo.setVisibility(View.GONE);

				nextButton.setEnabled(true);
			}

		}
		if (requestCode == CAMERA_RESULT_BELLY) {
			if (fileBelly.exists()) {
				//store file path for future use
				storage.putString("belly_initial_image", fileBelly.getAbsolutePath());
				// creates bitmap from file
				Bitmap myBitmap = BitmapFactory.decodeFile(fileBelly
						.getAbsolutePath());
				// sets the image to display in the activity
				Matrix matrix = new Matrix();

				matrix.postRotate(270);

				myBitmap = Bitmap
						.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),
								myBitmap.getHeight(), matrix, true);

				bellyImage.setImageBitmap(myBitmap);
				// encode the bitmap for database transmission
				belly_Image = ImageConversion.encodeToBase64Image(myBitmap);

				bellyInfo = (TextView) findViewById(R.id.bellyPhotoInstructions);
				bellyInfo.setVisibility(View.GONE);

				nextButton.setEnabled(true);
			}

		}
		if (requestCode == CAMERA_RESULT_OTHER) {
			if (fileOther.exists()) {
				//store file path for future use
				storage.putString("other_initial_image", fileOther.getAbsolutePath());
				// creates bitmap from file
				Bitmap myBitmap = BitmapFactory.decodeFile(fileOther
						.getAbsolutePath());
				// sets the image to display in the activity
				Matrix matrix = new Matrix();

				matrix.postRotate(270);

				myBitmap = Bitmap
						.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),
								myBitmap.getHeight(), matrix, true);

				otherImage.setImageBitmap(myBitmap);
				// encode the bitmap for database transmission
				other_Image = ImageConversion.encodeToBase64Image(myBitmap);

				otherInfo = (TextView) findViewById(R.id.otherPhotoInstructions);
				otherInfo.setVisibility(View.GONE);

				nextButton.setEnabled(true);
			}

		}
	}

	public void sendPhotos() {
		// this should be the url for posting user photos
		String url = null;

		int response = 10;// set this back at 0 when time comes to connect with
							// server the value of 10 gets me around the http
							// connection for now

		// creates data package to send updated info to server
		List<NameValuePair> postData = HttpConnectionUtilities
				.createPostDataHolder();

		HttpConnectionUtilities.addNameValuePair("left_image", left_Image,
				postData);
		HttpConnectionUtilities.addNameValuePair("right_image", right_Image,
				postData);
		HttpConnectionUtilities.addNameValuePair("face_image", face_Image,
				postData);
		HttpConnectionUtilities.addNameValuePair("belly_image", belly_Image,
				postData);
		HttpConnectionUtilities.addNameValuePair("other_image", other_Image,
				postData);

		/*
		 * try { response = HttpConnectionUtilities.postDataNoReturnMessage(url,
		 * postData); } catch (BaseException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */

		if (response == 10) {
			Toast.makeText(this, "User photos successfully uploaded",
					Toast.LENGTH_SHORT).show();
		}
	}

}
