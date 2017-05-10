package com.cy.pictool.action;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JToolTip;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.cy.pictool.MainWin;
import com.cy.pictool.bean.IPic;
import com.cy.pictool.bean.ImgBean;
import com.cy.pictool.component.PicPanel;
import com.cy.pictool.util.PropertiesConfig;
import com.cy.pictool.util.Util;

public final class PicAction {

	private static final PicAction instance = new PicAction();

//	private OV ov = new OV();
	
	private static String SIZE = "Size:XXKB";
//	public MainWin mainWin;
	public static int ORDER_BY = IPic.ORDER_BY_DATE;


	public static int PATTERN_BY = IPic.PATTERN_NONE;

	
	public static int MSG_QUEUE;

	private String delFrom;
	private String delTo;
	protected Point oldPos;
	protected Rectangle oldBounds;
	
	public static boolean ISMAC = false; 
	
	public int mouseMotion = 0; 
	
	private ImgBean imgBean = new ImgBean();

//	private Map<String, String> fileMap = new HashMap<String, String>();
	private String[] fileMap = new String[0];

	private PicAction() {
		
	}
	
	public String[] getFileMap() {
		return fileMap;
	}

	public void setFileMap(String[] fileMap) {
		this.fileMap = fileMap;
	}
	public ImgBean getImgBean() {
		return imgBean;
	}


	public String getDelTo() {
		return delTo;
	}

	public void setDelTo(String delTo) {
		this.delTo = delTo;
	}

	public String getDelFrom() {
		return delFrom;
	}

	public void setDelFrom(String delFrom) {
		this.delFrom = delFrom;
	}

	
//	public MainWin getMainWin() {
//		return mainWin;
//	}
//
//	public void setMainWin(MainWin mainWin) {
//		this.mainWin = mainWin;
//	}
  
	public static PicAction getInstance() {
		return instance;
	}

	public void getAllFiles(String absolutePath) {
 
		List<File> resultList = new ArrayList<File>();
 
		recurseFolder(absolutePath, resultList);
		 
		if (PicAction.ORDER_BY != IPic.ORDER_BY_NONE){
			resultList = sort(resultList);
		}
		 
//		File[] files = listToArray(resultList);
		  
		String[] filemap = new String[resultList.size()];
		
		
		int myindex = 0;
//		int i = 0;
		for (File files1 : resultList){
			
//			if (files1.isFile() && files1.getName().lastIndexOf(".") > 0 
//					&& PropertiesConfig.getConfig("FILE.EXT").indexOf(
//							files1.getName().toLowerCase().substring(
//									files1.getName().lastIndexOf("."))) != -1){
				filemap[myindex++] = files1.getPath();
//				filemap.put(String.valueOf(myindex++),  files1.getPath());
//			}
//			i++;
		}
		setFileMap(filemap);

		resultList.clear();
		resultList = null;
	}
	/*
	private File[] listToArray(List<File> resultList) {
		File[] files = new File[resultList.size()];
		int index = 0;
		for (File ff : resultList){
			
			if (ff.isFile()){
				files[index++] = ff;
				System.out.println(ff.getPath());
			}
		}
		return files;
	}
*/
	private void recurseFolder(String path, List<File> files) {


		File rFile = new File(path);

		if (!rFile.exists()) return;
 		File[] filessrc = rFile.listFiles();

		for (File subfile : filessrc) {

			if (subfile.isDirectory()) {

				recurseFolder(subfile.getPath(), files);
			} else {
				int lasindex = subfile.getName().lastIndexOf(".");
				if (lasindex > 0 
						&& PropertiesConfig.getConfig("FILE.EXT").indexOf(
								subfile.getName().toLowerCase().substring(
										lasindex)) != -1){
					files.add(subfile);
				}
			}
		} 
	}
	/*
	private List<File> recurseFolder(List<File> files) {

		File[] rtnList = null;
		 
		  
		for (File subFile : files){
			
			if (subFile.isDirectory()){
//				File subFolder = new File(subFile.getPath());
				
				List<File> tmpList = Arrays.asList(subFile.listFiles());
				files.addAll(tmpList); 
				recurseFolder(tmpList);

			} 
			
			
			 
			
		}
		 
		return null;
	}
*/
	private List<File> sort(List<File> list) {
		
//		List<File> list = new ArrayList<File>();
//		for (File f1 : files){
//			list.add(f1);
//		}
		Collections.sort(list, new Comparator<File>(){

			@Override
			public int compare(File arg0, File arg1) {
				
				File f1 = (File)arg0;
				File f2 = (File)arg1;
//				return f1..getName().compareTo(f2.getName());
				
				int rtn = 0;
				switch (PicAction.ORDER_BY){
				
				case IPic.ORDER_BY_DATE:

					long rtnL = f1.lastModified() - f2.lastModified();
					
					rtn = this.getOrderbyflag(rtnL);
					
					break;
				case IPic.ORDER_BY_NAME:
					rtn = compareDate(f1.getName(), f2.getName());
					
					break;
				case IPic.ORDER_BY_SIZE:
					
					try {
						FileInputStream fis1 = new FileInputStream(f1);
						
						long s1 = fis1.available();
						fis1.close();
						FileInputStream fis2 = new FileInputStream(f2);
						
						long s2 = fis2.available();
						fis2.close();
						rtnL = s2 - s1;
						rtn = this.getOrderbyflag(rtnL);
						/*
						if (rtnL == 0) {
							rtn = 0;//compareDate(f1.getName(), f2.getName());
						} else if (rtnL > 0){
							rtn = -1;
						} else if (rtnL < 0){
							rtn = 1;
						}
						*/
					} catch (FileNotFoundException e) {
						
						e.printStackTrace();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
					break;
				case IPic.ORDER_BY_NONE: 
					break;
				}
				
				return rtn;
			}

			private int getOrderbyflag(long rtn) {
				if (rtn == 0) {
					rtn = 0;//compareDate(f1.getName(), f2.getName());
				} else if (rtn > 0){
					rtn = -1;
				} else if (rtn < 0){
					rtn = 1;
				}
				return (int)rtn;
			}

			private int compareDate(String f1, String f2) {
				
				return f1.compareTo(f2);
			}
			
		}); 
//		files = new File[list.size()];
//		for (int i = 0 , ind = list.size();  i < ind; i++){
//			files[i] = list.get(i);
//		}
		return list;//(File[])list.toArray();
		
	}

	public String existF(String file, PicPanel picPanel) {
 
		File fl = new File(file);
		String size = null;
		if (fl.exists()){
			long tmpsize = fl.length() / 1000;
			size = SIZE.replace("XX", String.valueOf(tmpsize));
//			picPanel.setImgSize(tmpsize);
		}  
		return size;//(!new File(file).exists()) ? false : true;
	}

	public void setWinStyle() { 
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) { 
			e.printStackTrace();
		} catch (InstantiationException e) { 
			e.printStackTrace();
		} catch (IllegalAccessException e) { 
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) { 
			e.printStackTrace();
		}
 	}

	public String getcurrfile(int currIndex) {
 		return getFileMap()[currIndex];
	}

	public int getRandomfile(int currIndex) {
		
//		String file = this.getcurrfile(currIndex);
		
//		Map<String, String> map = getFileMap();
//		
//		Iterator<String> iterator = map.keySet().iterator();
//		while(iterator.hasNext()) { 
//			String key = iterator.next();
//
//			if (map.get(key).equals(file)){
//				
//				if (iterator.hasNext()) {
//					index = Integer.valueOf(iterator.next());
//				} 
//				break;
//			}
//		} 
		int index = (int)(Math.random() * this.getFileMap().length);
		
		return index;
	}

	public void setFileOrder(int flg, MainWin mainWin) {
		
		if (PicAction.ORDER_BY == flg){
			flg = IPic.ORDER_BY_NONE;
			
		} 
		
		PicAction.ORDER_BY = flg;
		
		
		mainWin.chckbxmntmOrderByDate.setSelected(false);
		mainWin.chckbxmntmOrderByName.setSelected(false);
		 
		mainWin.chckbxmntmOrderByNone.setSelected(false);
		mainWin.chckbxmntmOderBySize.setSelected(false);
		switch (flg){
		
			case IPic.ORDER_BY_DATE:

				mainWin.chckbxmntmOrderByDate.setSelected(true);				 
				break;
			case IPic.ORDER_BY_NAME:
				mainWin.chckbxmntmOrderByName.setSelected(true);
				break;
			case IPic.ORDER_BY_SIZE:
				mainWin.chckbxmntmOderBySize.setSelected(true);
				break;
			case IPic.ORDER_BY_NONE:
				mainWin.chckbxmntmOrderByNone.setSelected(true);
				break;
		}
		
		this.getAllFiles(mainWin.frmFoxpic.getTitle());
		
	}
	
//	public OV getOv() {
//		return ov;
//	}
//
//	public void setOv(OV ov) {
//		this.ov = ov;
//	}

	public boolean doRecycle(int currIndex) {
		
		boolean ismoved = false;
		String desFolder = PropertiesConfig.getConfig("FOLDER.RECYC");
		
		File desF = new File(desFolder);
		 
		String filepath = getcurrfile(currIndex);
  
		File src = new File(filepath);

		if (src.exists()){
			String desStr = desF.getPath() + File.separator + src.getName();
			File desFi = new File(desStr);

			if (desFi.exists()){
				 
				String desName = Util.getNameByDate(desFi.getName()); 
				desStr = desF.getPath() + 
 						File.separator + desName;
			} else {
			
				ismoved = copyFile(filepath, desStr);
				boolean isdel = src.exists() ? src.delete() : false;
 
				ismoved = (ismoved && isdel) ? true : false;
				
			}
			setRestoreInfo(filepath, desStr);
//			String delemsg = ismoved ? "Moved!" : "Failed!";
			 
//			mainWin.drawDelMsg(delemsg);

//			mainWin.doNext();
		} else {
			ismoved = false;
			setRestoreInfo(null, null);
//			mainWin.drawDelMsg("Src Not Exist!");
		}
		return ismoved;
	}

	private void setRestoreInfo(String filepath, String desStr) {
		this.delFrom = filepath;
		this.delTo = desStr;
	}

	public void saveIndex(int currIndex, String title, PicPanel picpanel) {

		String filepath = getcurrfile(currIndex);
		List<String> list = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(PropertiesConfig.CONFIGFILE))));
			String tempStr; 
			while ((tempStr = br.readLine()) != null) {
  
				if (tempStr.indexOf(PropertiesConfig.folderkey) != -1 
						|| tempStr.indexOf(PropertiesConfig.indexkey) != -1){
					  
				} else {
					list.add(tempStr);
				}
			} 
			if (filepath != null){
				tempStr = PropertiesConfig.folderkey
						+ "=" + title;
//						+ filepath.substring(0, filepath.lastIndexOf(File.separator))
//						.replaceAll("\\" + File.separator, 
//						"\\" + File.separator + File.separator);
				list.add(tempStr);
				tempStr = PropertiesConfig.indexkey
						+ "="
						+ String.valueOf(currIndex);
				list.add(tempStr);
			}
			br.close();

			writeConfig(PropertiesConfig.CONFIGFILE, list);
//			writeConfig(PropertiesConfig.getConfig("CONFIG.ORG")  + File.separator + "setting.config", list);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		this.showMsg("Config Saved", picpanel);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private void writeConfig(String filePath, List<String> list) throws IOException {
		File file = new File(filePath);// "C:\\temp\\before\\diffresult.csv");
		OutputStream outputstream = new FileOutputStream(file, false);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				outputstream));
		for (String line : list){
			bw.append(line);
			bw.append("\r\n");
		}
		bw.flush();
		bw.close();
	}

	public boolean saveBmp(int currIndex) {
		boolean isok = false;
		try {
			String filepath = getcurrfile(currIndex);

			BufferedImage imageOrg = ImageIO.read(new FileInputStream(filepath));
			
			filepath = filepath.substring(0, filepath.lastIndexOf(File.separator))
//					+ File.separator
					+ Util.getNameByDate(filepath.substring(filepath.lastIndexOf(File.separator)));
			filepath = filepath.substring(0, filepath.lastIndexOf(".")) + ".bmp";
			File bmp = new File(filepath);
			
			ImageIO.write(imageOrg, "bmp", bmp);
			
			isok = true;
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return isok;
	}
	// added 2014/8/8
	public void setThumbImg(int currIndex, PicPanel panel, int offset) {
		this.showMsg("Loading...", panel);

		if (PicAction.PATTERN_BY != IPic.PATTERN_THUMB) return;
		int imgLength = 10;
		
		if ((currIndex % imgLength == 0 && offset == 1)
				|| (currIndex != 0 && currIndex % imgLength == 9 && offset == -1)){
//			this.showMsg("Loading...", panel);
			int[] pos = panel.getMsgPosition(93, panel);
			Graphics gra = panel.getGraphics();                                   // progress bar
			gra.setColor(IPic.transVBar);                                            // progress bar
			int x = pos[0] + 2;
			int y = pos[1] + 8 + (MSG_QUEUE - 1) * PicPanel.MSG_HEIGHT;
			gra.fillRect(x, y, 90, 2);                                // progress bar
			gra.setColor(IPic.COLOR_MAIN);       
			Image[] imagetmp = new Image[imgLength];
			int thumbindex = currIndex;
			int mapLength = getFileMap().length;
			for (int i = 0; i < imgLength ; i++){
				int tmpindx = thumbindex + (i * offset);
				if (tmpindx >= 0 && (tmpindx) < mapLength) {
					String filepath = getcurrfile(tmpindx);
				 
					File fpic = new File(filepath);
					if (!fpic.exists()) continue; 

					int width = (i + 1) * 9;
					gra.fillRect(x, y, width, 3);                         // progress bar
					  
					if (offset == 1) {
						imagetmp[i] = Util.imageIOread(filepath);
//						PicPanel.thumbIndex = -1;
					} else {
						imagetmp[imgLength + i * offset  - 1] = Util.imageIOread(filepath);
//						PicPanel.thumbIndex = 10;
					}
				 
				}
				
			}
			gra.dispose();                                                        // progress bar
			this.imgBean.setImgThumb(imagetmp);
		}
//		PicPanel.thumbIndex += offset;
//		if (PicPanel.thumbIndex >= imgLength)
//			PicPanel.thumbIndex = 9;
//		if (PicPanel.thumbIndex < 0) 
//			PicPanel.thumbIndex = 0;
//		System.out.println(PicPanel.thumbIndex);
	}


	public boolean copyFile(String oldPath, String newPath) {
	 		
 		boolean isok = false;
 		try {
// 			int bytesum = 0;
 			int byteread = 0;
 			File oldfile = new File(oldPath);
 			if (oldfile.exists()) {
 				InputStream inStream = new FileInputStream(oldPath);
 				FileOutputStream fs = new FileOutputStream(newPath);
 				byte[] buffer = new byte[2048];
 				
 				while ((byteread = inStream.read(buffer)) != -1) {
// 					bytesum += byteread;
 					// System.out.println(bytesum);
 					fs.write(buffer, 0, byteread);
 				}
 				inStream.close();
 				fs.close();
 				isok = true;
 			}
 		} catch (Exception e) {
 			e.printStackTrace();
 			isok = false;
 		}
 		return isok;
 	}

	public String delRecycle() {

		File file = new File(PropertiesConfig.getConfig("FOLDER.RECYC"));
		
		File[] list = file.listFiles();
		
		for (File f : list){
			if (f.exists()) {
				f.delete();
				
			}
		}
		return "All files deleted";
//		mainWin.drawDelMsg("All files in " + file + " Deleted");
	}
	
	public void showMsg(String delemsg, PicPanel picPanel) {

		Graphics gs = picPanel.getGraphics();
		picPanel.drawMsg(delemsg, gs, picPanel);
		gs.dispose();
	}
	 


	public boolean restoreFile(int currIndex) {

		boolean rtn = false;
		
		if (this.delFrom != null) {
			 
			rtn = this.copyFile(this.delTo, this.delFrom);
			
			File file = new File(this.delTo);
			
			file.delete();
			
		}
		this.setRestoreInfo(null, null); 
		return rtn;
	}


	public void clearThumbByIndex(PicPanel picPanel, int currIndex) {
		
		if (this.imgBean.getImgThumb() != null){

			this.imgBean.getImgThumb()[Util.getThumbIndex(currIndex)] = null;
		}
	}

	public int getNextByIndex(int currIndex, int offset) {
		int tmpindex = currIndex;
		tmpindex = (tmpindex - tmpindex % offset) + offset - 1;
			
		int maxindex = getFileMap().length - 1;
		
		if (tmpindex > maxindex){
			return currIndex;
		} else if (tmpindex < 0){
			return -1;
		} else {
			return tmpindex;
		}
	}

//	public void restoreThumbByIndex(PicPanel picPanel, int currIndex) {
//		if (picPanel.getImgThumb() != null){
//
//			String filepath = getcurrfile(currIndex, getOv());
//			picPanel.getImgThumb()[Util.getThumbIndex(currIndex)] = Util.imageIOread(filepath);
//		}
//	}
	
	public boolean setPanelInfo(float panelwidth, float panelheight, int currIndex) {
		
		try {
			ImgBean _bean = getImgBean();  //System.out.println("setPanelInfo     " +currIndex + "   " +_bean.imgPath);
			if (_bean.imgPath == null) return false;
			
			_bean.imgName = Util.getName(_bean.imgPath);
//			String path = this.imgPath; 
			 
			boolean isreload = isSameImgPath(_bean.imgPath, _bean.imgPath_OLD) ? false : true; 
//			System.out.println("isreload     " + isreload);
			Image imageOrg = isreload == true ? getImageFromThumb(_bean.imgPath, currIndex) : _bean.imageOrg;
		 
			if (imageOrg == null) {
				if (PicAction.PATTERN_BY == IPic.PATTERN_PIP){
					_bean.imgInImage = null;
				} else {
					_bean.image = null;
					_bean.imgRefl = null;
					_bean.imageOrg = null;
				}
				
				_bean.imgPath_OLD = _bean.imgPath;
				return false;
			}

			float picwidth = imageOrg.getWidth(null);
			float picheight = imageOrg.getHeight(null);//this);

//			float panelwidth = this.getSize().width;
//			float panelheight = this.getSize().height;

//			if (MainWin.RUN_STATE && PicAction.PATTERN_BY == PicAction.PATTERN_PIP){
			if (PicAction.PATTERN_BY == IPic.PATTERN_PIP){
				if ((picwidth > picheight && panelwidth > panelheight)
						|| (picwidth < picheight && panelwidth < panelheight)) {
					
					// ok
					_bean.imgInImage = null;
				} else {
					int imiW = 0;
					if (picwidth < picheight && panelwidth > panelheight) {
						imiW = (int) (panelwidth / 4) - 20;
					} else {
						imiW = (int) (panelwidth / 2) - 20;
					}
					int imiH = (int) (imiW * picheight / picwidth);

					_bean.setImiH(imiH);
					_bean.setImiW(imiW);
//					this.imgInImage = imageOrg;
					_bean.imgInImage = getScaledImage(imageOrg, imiW, imiH);
//					imageOrg.getScaledInstance((int) imiW,
//							(int) imiH, Image.SCALE_AREA_AVERAGING);
					imageOrg = null;
					_bean.imgPath_OLD = _bean.imgPath;
//					return false;
					return true;
				}
			} else {
				_bean.imgInImage = null;
			}
			float finalwidth = 0;
			float finalheight = 0;

			if (picwidth < panelwidth && picheight < panelheight) {
				finalwidth = picwidth;
				finalheight = picheight;
				_bean.image = imageOrg;
			} else {
				float panelwidth2 = panelheight * picwidth / picheight;
				if (panelwidth2 > panelwidth) {
					finalwidth = panelwidth;
					finalheight = panelwidth * picheight / picwidth;
				} else {
					finalwidth = panelwidth2;
					finalheight = panelwidth2 * picheight / picwidth;
				}
//				this.image = imageOrg;
				_bean.image = this.getScaledImage(imageOrg, (int)finalwidth, (int)finalheight);
//				imageOrg.getScaledInstance((int) finalwidth,
//						(int) finalheight, Image.SCALE_AREA_AVERAGING);
				  
			}
			_bean.imgRefl = (BufferedImage)imageOrg;//getReflect((BufferedImage)imageOrg);
			_bean.setImgWidth((int) finalwidth);
			_bean.setImgHeight((int) finalheight);
//			setImgWidth(this.image.getWidth(null));
//			setImgHeight(this.image.getHeight(null));
			_bean.imgPath_OLD = _bean.imgPath;
//			this.imgPath = path;
			_bean.imageOrg = imageOrg;
			imageOrg = null; 
		} catch (Exception e) {
			
		}
		return true;
	}
	private Image getScaledImage(Image image, int width, int height) {
//		System.out.println("mouseMotion " + mouseMotion);
		if (mouseMotion == 0){
			return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} else {
			return image;
		}
	}
	
	private Image getImageFromThumb(String path, int currIndex) {
		 
		Image tmpImg = null;
		ImgBean _bean = this.getImgBean();
		if (PicAction.PATTERN_BY == IPic.PATTERN_THUMB){
			if (_bean.imgThumb == null){
				tmpImg = Util.imageIOread(path);
			} else {
				tmpImg = _bean.imgThumb[Util.getThumbIndex(currIndex)];
			}
		} else {
			tmpImg = Util.imageIOread(path);
		}
		return tmpImg;
	}


	private boolean isSameImgPath(String imgPath, String imgPath_OLD) {
		
		if (imgPath_OLD == null) return false;
		if (imgPath.equals(imgPath_OLD)) {
			return true;
		} else {
			return false;
		}
		
	}


	public void setBounds(MainWin mainWin) {

		int winWidth = mainWin.picPanel.getWidth();
		int winHeight = mainWin.picPanel.getHeight();

		int imgWidth = this.imgBean.getImgWidth();
		int imgHeight = this.imgBean.getImgHeight();
		
		Rectangle bounds = mainWin.frmFoxpic.getBounds();
		
		int resWidth = 0;
		int resHeight = 0;
		
		if (winWidth > imgWidth && winHeight > imgHeight) return;
		
		if (winWidth > imgWidth){
			resWidth = winWidth - imgWidth;
		} else if (winHeight > imgHeight){
			
			resHeight = winHeight - imgHeight;
		}

//		System.out.println("mouseDragged  " + (bounds.width - resWidth) + "   " + (bounds.height - resHeight));	
		mainWin.frmFoxpic.setBounds(bounds.x, bounds.y, bounds.width - resWidth, bounds.height - resHeight);
	}

	public void setListenser(PicPanel picPanel) {

		picPanel.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent arg0) {
//				System.out.println("mouseMoved");
				
				PicPanel picPanel = MainWin.getInstance().picPanel;
				int frameWidth = picPanel .getWidth();
				int frameHeight = picPanel.getHeight();
				if ((frameWidth - arg0.getX() < IPic.moveArea && frameHeight - arg0.getY() < IPic.moveArea)
						|| (frameWidth - arg0.getX() < IPic.moveArea && arg0.getY() < IPic.moveArea)){
					MainWin.getInstance().frmFoxpic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					MainWin.getInstance().frmFoxpic.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
//				System.out.println("mvoed  " + arg0.getY() + "  " + (frameHeight - panel_2.getHeight()));
			}

			@Override
			public void mouseDragged(MouseEvent e) {
//				if (e.getY() < 0) return;
				JFrame frmFoxpic = MainWin.getInstance().frmFoxpic;
				Rectangle bounds = frmFoxpic.getBounds();
				Point loc = frmFoxpic.getLocation();
				int width = (int)oldBounds.getWidth() + e.getX() - oldPos.x; 
				int height = 0;
				switch (mouseMotion){

				case IPic.ISENLARGE_DOWN :
//					System.out.println(loc.x +"     "+ IPic.demen.width);		
//					System.out.println("mouseDragged  " + bounds.x + "   " + bounds.y);			
					height = (int)oldBounds.getHeight() + e.getY() - oldPos.y; 
					if (loc.x < IPic.demen.width && height > IPic.demen.height - IPic.WIN_Y) height = IPic.demen.height - IPic.WIN_Y; 
					bounds.setSize(width, height);
					frmFoxpic.setBounds(bounds);
					break;
				case IPic.ISENLARGE_UP :
//System.out.println(loc.x);
					int offset = e.getY() - oldPos.y;
					int y = loc.y + offset; 
					
					height = (int)bounds.getHeight() - offset; 
//					if (loc.x < IPic.demen.width && height > IPic.demen.height - IPic.WIN_Y) 
//						height = IPic.demen.height - IPic.WIN_Y; 
					if (loc.x < IPic.demen.width && y < IPic.WIN_Y) {

						bounds.setSize(width, height - IPic.WIN_Y + y);
						y = IPic.WIN_Y;

					} else {

						bounds.setSize(width, height);	
					}
					bounds.setLocation(loc.x, y);
					
					frmFoxpic.setBounds(bounds);
					break;
				case IPic.ISMOVE :
					
					int x = loc.x + e.getX() - oldPos.x;
					int axy = loc.y + e.getY() - oldPos.y;
	
					if (loc.x < IPic.demen.width && axy < IPic.WIN_Y) {
						axy = IPic.WIN_Y;
					} else if (axy < 0){
						axy = 0;
					}
					if (x < 0) x = 0;
					frmFoxpic.setLocation(x, axy);
					break;
				}
				 
			}
		});
		picPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (((MouseEvent) arg0).getClickCount() == 3) {

					MainWin.getInstance().frmFoxpic.setExtendedState(Frame.ICONIFIED);
				}
			}

			@Override
			public void mousePressed(MouseEvent arg0) {

				JFrame frmFoxpic = MainWin.getInstance().frmFoxpic;
				PicPanel picPanel = MainWin.getInstance().picPanel;
				oldPos = arg0.getPoint();
				
				oldBounds = frmFoxpic.getBounds();
				
				int frameWidth = picPanel.getWidth();
				int frameHeight = picPanel.getHeight();
				
//				if (frameWidth - oldPos.x > 15 && frameHeight - oldPos.y > 15){
				if (frameWidth - arg0.getX() < IPic.moveArea && frameHeight - arg0.getY() < IPic.moveArea) {
					mouseMotion = IPic.ISENLARGE_DOWN;
				} else if (frameWidth - arg0.getX() < IPic.moveArea && arg0.getY() < IPic.moveArea) {
					mouseMotion = IPic.ISENLARGE_UP;
//					MainWin.RESIZED = true;
				} else {
					mouseMotion = IPic.ISMOVE;
					frmFoxpic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
//				System.out.println("mouseMotion   " + mouseMotion);
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
//				MainWin.RESIZED = false;
				mouseMotion = 0;
				MainWin.getInstance().frmFoxpic.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				MainWin.getInstance().frmFoxpic.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				
			}
		});
	}


	public void setToolTip(JToolTip tip) {

        tip.setBackground(IPic.transVBar);
        tip.setForeground(Color.DARK_GRAY);
	}


	public boolean isMac() {
		Properties prop = System.getProperties();

		String os = prop.getProperty("os.name");
		return !os.toLowerCase().startsWith("win"); 
	}



	public void openByFinder() {
		String[] cmd = IPic.CMD_OPEN;
		
		cmd[2] = "open " + this.getImgBean().getImgPath();
		
		Util.openFileByFinder(cmd);
	}
}
