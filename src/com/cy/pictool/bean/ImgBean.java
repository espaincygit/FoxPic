package com.cy.pictool.bean;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImgBean {
	public Image image;
	 
	public int imgWidth;
	public int imgHeight;
	public String imgSize;
	public String imgName;


//	public Color transWhite = new Color(0.238f, 0.238f, 0.238f, 0.2f);
//	public Color transCol = new Color(0.238f, 0.238f, 0.238f, 0.05f);
//	public Color transBlack = new Color(0f, 0f, 0f, 0.9f);

	public String imgPath;
	public String imgPath_OLD;
	

	public int imiW;
	public int imiH;

	public Image imageOrg;

	public Image imgInImage;

	public BufferedImage imgRefl;
	

	// added 2014/8/8
	public Image[] imgThumb;
//	public static int thumbIndex;


	public String getImgPath_OLD() {
		return imgPath_OLD;
	}

	public void setImgPath_OLD(String imgPath_OLD) {
		this.imgPath_OLD = imgPath_OLD;
	}
	// private long THREADHOLD =
	// Long.valueOf(PropertiesConfig.getConfig("THREADHOLD"));
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image[] getImgThumb() {
		return imgThumb;
	}

	public void setImgThumb(Image[] imgThumb) {
		this.clearThumb();
		this.imgThumb = imgThumb;
	}

	public BufferedImage getImgRefl() {
		return imgRefl;
	}

	public void setImgRefl(BufferedImage imgRefl) {
		this.imgRefl = imgRefl;
	}

	public int getImiW() {
		return imiW;
	}

	public void setImiW(int imiW) {
		this.imiW = imiW;
	}

	public int getImiH() {
		return imiH;
	}

	public void setImiH(int imiH) {
		this.imiH = imiH;
	}

	public Image getImgInImage() {
		return imgInImage;
	}

	public void setImgInImage(Image imgInImage) {
		this.imgInImage = imgInImage;
	}

	public Image getImageOrg() {
		return imageOrg;
	}

	public void setImageOrg(Image imageOrg) {
		this.imageOrg = imageOrg;
	}


	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getImgSize() {
		return imgSize;
	}

	public void setImgSize(String imgSize) {
		this.imgSize = imgSize;
	}

	public int getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	public int getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}
	
	public void clearThumb(){
		
		if (this.imgThumb != null){
			for (int i = 0 ; i < this.imgThumb.length; i++){
				
				this.imgThumb[i] = null;
			}
		}
		this.imgThumb = null;
	}
}
