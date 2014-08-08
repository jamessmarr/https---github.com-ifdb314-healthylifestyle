package com.smarr.android.healthylifestyle.utilities.shared_preferences;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import android.os.Environment;

public class PhotoFileTracker {

	private List<String> leftImageArray, rightImageArray, faceImageArray,
			bellyImageArray, otherImageArray;

	private File path = new File(Environment.getExternalStorageDirectory()
			+ "/Healthy_LifeStyle/Misc");

	private File leftPhotos, rightPhotos, facePhotos, bellyPhotos, otherPhotos;

	private Scanner scanner;

	public void addToLeftFile(String filePath){
		leftPhotos = new File(path + "/leftPhotos.txt");
		checkPath();
		checkFile(leftPhotos);
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(leftPhotos);
			writer.append(filePath);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		

	}
	
		
	public List<String> getLeftImageArray(){
		return leftImageArray;
	}
	public void setLeftImageArray(List<String> list) {
		this.leftImageArray = list;
	}

	public List<String> getRightImageArray() {
		return rightImageArray;
	}

	public void setRightImageArray(List<String> rightImageArray) {
		this.rightImageArray = rightImageArray;
	}

	public List<String> getFaceImageArray() {
		return faceImageArray;
	}

	public void setFaceImageArray(List<String> faceImageArray) {
		this.faceImageArray = faceImageArray;
	}

	public List<String> getBellyImageArray() {
		return bellyImageArray;
	}

	public void setBellyImageArray(List<String> bellyImageArray) {
		this.bellyImageArray = bellyImageArray;
	}

	public List<String> getOtherImageArray() {
		return otherImageArray;
	}

	public void setOtherImageArray(List<String> otherImageArray) {
		this.otherImageArray = otherImageArray;
	}

	public void checkPath(){
		if(!path.exists()){
			path.mkdir();
		}
	}
	
	public void checkFile(File file){
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
