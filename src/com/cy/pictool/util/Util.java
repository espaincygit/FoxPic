package com.cy.pictool.util;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

public class Util {
	
	

	public static final int ListEnd = 1;
	public static final int NoneFile = 2;

	public static String getNameByDate(String desName) {
		// TODO Auto-generated method stub

		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String appfix = "_" + df.format(date);
		
		int extindex = desName.lastIndexOf(".");
		String ext = desName.substring(extindex); 
		
		return desName.replaceAll(ext, appfix + ext);
		 
	}
	
	public static String getName(String filepath){
		if (filepath == null) return "";
		return filepath.substring(filepath
				.lastIndexOf(File.separator) + 1);
	}

	public static Image imageIOread(String path){
		
		Image imageOrg = null;
		try {
			File file = new File(path);
			if (!file.exists()) return imageOrg;
			FileInputStream fis = new FileInputStream(file);
			imageOrg = ImageIO.read(fis);
			fis.close(); 
		} catch (Exception e) { 
//			System.out.println(path);
//			e.printStackTrace();
		}
		return imageOrg;
	}
	
	public static int getThumbIndex(int curindex){
		return curindex % 10;
	}
	public static void openFileByFinder(String[] cmd ) {
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static int getInterval(String text) {
		
		return (int)(Double.valueOf(text) * 1000);
	}

}
