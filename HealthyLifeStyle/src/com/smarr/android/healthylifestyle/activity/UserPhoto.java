package com.smarr.android.healthylifestyle.activity;

import java.io.File;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.smarr.android.healthylifestyle.R;
import com.smarr.android.healthylifestyle.utilities.http.HttpConnectionUtilities;
import com.smarr.android.healthylifestyle.utilities.image.ImageConversion;
import com.smarr.android.healthylifestyle.utilities.shared_preferences.StoreAppInfo;

public class UserPhoto extends Activity {

	private ImageView leftSideImage, rightSideImage, faceImage, bellyImage,
			otherImage;

	private TextView leftSideInfo, rightSideInfo, faceInfo, bellyInfo,
			otherInfo;

	private ViewFlipper flipper;

	private Button nextActivity;

	private StoreAppInfo storage;

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
		setContentView(R.layout.activity_user_photo);

		storage = new StoreAppInfo(getApplicationContext());

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
			
			

			if (!hasCamera) {
				new AlertDialog.Builder(this)
						.setTitle("Camera Not Found")
						.setMessage("A Camera was not found on this device")
						.setNeutralButton(R.string.ok,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										nextActivity();
									}
								}).show();
			}

		}
		leftSideImage = (ImageView) findViewById(R.id.leftSideImageView);
		rightSideImage = (ImageView) findViewById(R.id.rightSideImageView);
		faceImage = (ImageView) findViewById(R.id.faceImageView);
		bellyImage = (ImageView) findViewById(R.id.bellyImageView);
		otherImage = (ImageView) findViewById(R.id.otherImageView);

		flipper = (ViewFlipper) findViewById(R.id.photoFlipper);

		nextActivity = (Button) findViewById(R.id.btnNext);
		nextActivity.setEnabled(false);

		nextActivity.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (flipper.getDisplayedChild() != flipper.getChildCount() - 1) {
					flipper.showNext();
					nextActivity.setEnabled(false);
				} else {
					nextActivity();

				}
			}
		});

	}

	private void nextActivity() {
		storage.putBoolean("needPhoto", false);
		sendPhotos();

		// sends user to the next activity in line
		Intent userStatus = new Intent(this, UserStatus.class);
		startActivity(userStatus);

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_RESULT_LEFT) {
			// converts the returned image into a bitmap
			if (fileLeft.exists()) {
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

				nextActivity.setEnabled(true);
			}
		}
		if (requestCode == CAMERA_RESULT_RIGHT) {
			if (fileRight.exists()) {
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

				nextActivity.setEnabled(true);
			}

		}
		if (requestCode == CAMERA_RESULT_FACE) {
			if (fileFace.exists()) {
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

				nextActivity.setEnabled(true);
			}

		}
		if (requestCode == CAMERA_RESULT_BELLY) {
			if (fileBelly.exists()) {
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

				nextActivity.setEnabled(true);
			}

		}
		if (requestCode == CAMERA_RESULT_OTHER) {
			if (fileOther.exists()) {
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

				nextActivity.setEnabled(true);
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
