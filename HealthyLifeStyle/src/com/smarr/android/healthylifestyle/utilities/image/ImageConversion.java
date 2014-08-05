package com.smarr.android.healthylifestyle.utilities.image;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

public class ImageConversion {


	private static final int DEFAULT_IMAGE_COMPRESSION_QUALITY=50; // 50%	
	private static final CompressFormat DEFAULT_IMAGE_FORMAT=Bitmap.CompressFormat.JPEG; // 50%	

	public static String encodeToBase64Image(Bitmap bm){
		return encodeToBase64Image(bm, DEFAULT_IMAGE_FORMAT,DEFAULT_IMAGE_COMPRESSION_QUALITY);
	}

	/**
	 * Use constants like Bitmap.CompressFormat.JPEG, Bitmap.CompressFormat.PNG
	 * @param bm
	 * @param cf
	 * @return
	 */
	public static String encodeToBase64Image(Bitmap bm, CompressFormat cf){
		return encodeToBase64Image(bm, cf, DEFAULT_IMAGE_COMPRESSION_QUALITY);
	}

	/**
	 * URL and File safe encoding
	 * @param bm
	 * @param cf constants like Bitmap.CompressFormat.JPEG, Bitmap.CompressFormat.PNG
	 * @param quality 0 -100% of original
	 * @return
	 */
	public static String encodeToBase64Image(Bitmap bm, CompressFormat cf, int quality){
		byte[] b = getCompressedBytesFromBitmap(bm,cf,quality); 
		String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
		return encodedImage;
	}




	public static Bitmap getImageFromBase64String(String imageUrl){
		if(imageUrl == null || imageUrl.equals("null")){
			return null;
		}
		byte[] bArray = Base64.decode(imageUrl.getBytes(), Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bArray, 0, bArray.length);
	}

	/**
	 * Rescale and image
	 * @param bm
	 * @param newHeight
	 * @param newWidth
	 * @return
	 */
	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);
		// RECREATE THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		return resizedBitmap;
	}


	public static byte[] getCompressedBytesFromBitmap(Bitmap bmp){
		return getCompressedBytesFromBitmap(bmp, DEFAULT_IMAGE_FORMAT, DEFAULT_IMAGE_COMPRESSION_QUALITY);

	}

	public static byte[] getCompressedBytesFromBitmap(Bitmap bmp, CompressFormat cf){
		return getCompressedBytesFromBitmap(bmp, cf, DEFAULT_IMAGE_COMPRESSION_QUALITY);
	}

	public static byte[] getCompressedBytesFromBitmap(Bitmap bmp, CompressFormat cf, int quality){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(cf, quality, stream);
		return stream.toByteArray();
	}


}