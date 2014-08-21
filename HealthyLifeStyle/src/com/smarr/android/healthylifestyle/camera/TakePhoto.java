package com.smarr.android.healthylifestyle.camera;

import java.io.File;
import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.smarr.android.healthylifestyle.utilities.http.HttpConnectionUtilities;
import com.smarr.android.healthylifestyle.utilities.shared_preferences.StoreAppInfo;

public class TakePhoto {

	private StoreAppInfo storage;

	private Context context;

	private File path;
	private File fileLeft, fileRight, fileFace, fileBelly, fileOther;
	private String folderName = "";
	private int day;
	private Bitmap myBitmap;

	public TakePhoto(Context context, String folder, String today) {
		this.setContext(context);
		this.storage = new StoreAppInfo(context);
		this.setFolderName(folder);
		
		this.setPath(new File(Environment.getExternalStorageDirectory()
				+ "/Healthy_LifeStyle/" + this.getFolderName()));
		this.setFileLeft(new File(path + "/left_side_image"+today+".jpg"));
		this.setFileRight(new File(path + "/right_side_image"+today+".jpg"));
		this.setFileFace(new File(path + "/face_image"+today+".jpg"));
		this.setFileBelly(new File(path + "/belly_image"+today+".jpg"));
		this.setFileOther(new File(path + "/other_image"+today+".jpg"));
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Intent takeLeftPhoto() {
		if (!(path.isDirectory())) {
			path.mkdir();
		}

		Uri outputFileLeftUri = Uri.fromFile(fileLeft);

		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileLeftUri);

		return cameraIntent;

	}

	public Intent takeRightPhoto() {
		if (!(path.isDirectory())) {
			path.mkdir();
		}

		Uri outputFileRightUri = Uri.fromFile(fileRight);

		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileRightUri);

		return cameraIntent;
	}

	public Intent takeFacePhoto() {
		if (!(path.isDirectory())) {
			path.mkdir();
		}

		Uri outputFileFaceUri = Uri.fromFile(fileFace);

		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileFaceUri);

		return cameraIntent;
	}

	public Intent takeBellyPhoto() {
		if (!(path.isDirectory())) {
			path.mkdir();
		}

		Uri outputFileBellyUri = Uri.fromFile(fileBelly);

		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileBellyUri);

		return cameraIntent;
	}

	public Intent takeOtherPhoto() {
		if (!(path.isDirectory())) {
			path.mkdir();
		}

		Uri outputFileOtherUri = Uri.fromFile(fileOther);

		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileOtherUri);

		return cameraIntent;

	}

	public Bitmap handleLeftPhoto() {
		if (this.getFileLeft().exists()) {
			if (myBitmap != null)
			   {
			     myBitmap.recycle();
			     myBitmap = null;
			     System.gc();
			   }
			// store file path for future use
			// photoTracker.addToLeftFile(this.getFileLeft().getAbsolutePath());
			// creates bitmap from file
			myBitmap = BitmapFactory.decodeFile(this.getFileLeft()
					.getAbsolutePath());
			// sets the image to display in the activity
			Matrix matrix = new Matrix();

			matrix.postRotate(90);

			myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),
					myBitmap.getHeight(), matrix, true);

			return myBitmap;
		}
		return null;
	}

	public Bitmap handleRightPhoto() {
		if (this.getFileRight().exists()) {
			if (myBitmap != null)
			   {
			     myBitmap.recycle();
			     myBitmap = null;
			     System.gc();
			   }
			// store file path for future use
			//photoTracker.addToRightFile(this.getFileRight().getAbsolutePath());
			// creates bitmap from file
			myBitmap = BitmapFactory.decodeFile(this.getFileRight()
					.getAbsolutePath());
			// sets the image to display in the activity
			Matrix matrix = new Matrix();

			matrix.postRotate(90);

			myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),
					myBitmap.getHeight(), matrix, true);

			return myBitmap;
		}
		return null;
	}

	public Bitmap handleFacePhoto() {
		if (this.getFileFace().exists()) {
			if (myBitmap != null)
			   {
			     myBitmap.recycle();
			     myBitmap = null;
			     System.gc();
			   }
			// store file path for future use
			//photoTracker.addToFaceFile(this.getFileFace().getAbsolutePath());
			// creates bitmap from file
			myBitmap = BitmapFactory.decodeFile(this.getFileFace()
					.getAbsolutePath());
			// sets the image to display in the activity
			Matrix matrix = new Matrix();

			matrix.postRotate(90);

			myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),
					myBitmap.getHeight(), matrix, true);

			return myBitmap;
		}
		return null;
	}

	public Bitmap handleBellyPhoto() {
		if (this.getFileBelly().exists()) {
			if (myBitmap != null)
			   {
			     myBitmap.recycle();
			     myBitmap = null;
			     System.gc();
			   }
			// store file path for future use
			//photoTracker.addToBellyFile(this.getFileBelly().getAbsolutePath());
			// creates bitmap from file
			myBitmap = BitmapFactory.decodeFile(this.getFileBelly()
					.getAbsolutePath());
			// sets the image to display in the activity
			Matrix matrix = new Matrix();

			matrix.postRotate(90);

			myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),
					myBitmap.getHeight(), matrix, true);

			return myBitmap;
		}
		return null;
	}

	public Bitmap handleOtherPhoto() {
		if (this.getFileOther().exists()) {
			if (myBitmap != null)
			   {
			     myBitmap.recycle();
			     myBitmap = null;
			     System.gc();
			   }
			// store file path for future use
			//photoTracker.addToOtherFile(this.getFileOther().getAbsolutePath());
			// creates bitmap from file
			myBitmap = BitmapFactory.decodeFile(this.getFileOther()
					.getAbsolutePath());
			// sets the image to display in the activity
			Matrix matrix = new Matrix();

			matrix.postRotate(90);

			myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),
					myBitmap.getHeight(), matrix, true);

			return myBitmap;
		}
		return null;
	}

	public void sendPhotos(String left_Image, String right_Image,
			String face_Image, String belly_Image, String other_Image,
			String url) {
		
		//create an array to store in shared preferences for image references
		
		
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
			Toast.makeText(this.getContext(),
					"User photos successfully uploaded", Toast.LENGTH_SHORT)
					.show();
		}
	}
	
	public boolean hasCamera(){
		boolean hasCamera = storage.getBoolean("hasCamera", false);
		if (!hasCamera) {
			PackageManager pm = this.getContext().getPackageManager();
			

			boolean frontCam = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
			boolean rearCam = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
			
			if(frontCam || rearCam){
				storage.putBoolean("hasCamera", hasCamera);
				return true;
			}else{
				storage.putBoolean("hasCamera", hasCamera);
				return false;
				
			}
		}
		return true;
	}

	public File getPath() {
		return path;
	}

	public void setPath(File path) {
		this.path = path;
	}

	public File getFileLeft() {
		return fileLeft;
	}

	public void setFileLeft(File fileLeft) {
		this.fileLeft = fileLeft;
	}

	public File getFileRight() {
		return fileRight;
	}

	public void setFileRight(File fileRight) {
		this.fileRight = fileRight;
	}

	public File getFileFace() {
		return fileFace;
	}

	public void setFileFace(File fileFace) {
		this.fileFace = fileFace;
	}

	public File getFileBelly() {
		return fileBelly;
	}

	public void setFileBelly(File fileBelly) {
		this.fileBelly = fileBelly;
	}

	public File getFileOther() {
		return fileOther;
	}

	public void setFileOther(File fileOther) {
		this.fileOther = fileOther;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
		this.storage.putInt("dayOfLastPhoto", this.day);
	}
}
