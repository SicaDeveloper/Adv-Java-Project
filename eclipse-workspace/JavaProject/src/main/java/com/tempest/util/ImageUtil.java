package com.tempest.util;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.http.Part;

public class ImageUtil {

	public String getImageNameFromPart(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		String imageName = null;

		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				imageName = s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}

		if (imageName == null || imageName.isEmpty()) {
			imageName = "download.png";
		}

		return imageName;
	}


	public boolean uploadImage(Part part, String rootPath, String saveFolder) {
		String savePath = getSavePath(saveFolder);
		File fileSaveDir = new File(savePath);

		if (!fileSaveDir.exists()) {
			if (!fileSaveDir.mkdir()) {
				return false; // Failed to create the directory
			}
		}
		try {
			// Get the image name
			String imageName = getImageNameFromPart(part);
			String filePath = savePath + "/" + imageName;
			part.write(filePath);
			return true; 
		} catch (IOException e) {
			e.printStackTrace(); // Log the exception
			return false; // Upload failed
		}
	}
	
	public String getSavePath(String saveFolder) {
		return "C:/Users/rames/eclipse-workspace/JavaProject/src/main/webapp/resource/images/"+saveFolder+"/";
	}
}