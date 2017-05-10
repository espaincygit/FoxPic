package com.cy.pictool.action;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.cy.pictool.MainWin;
import com.cy.pictool.bean.IPic;
import com.cy.pictool.bean.ImgBean;
import com.cy.pictool.component.PicPanel;
import com.cy.pictool.resource.icon.PicIcon;
import com.cy.pictool.util.PropertiesConfig;
import com.cy.pictool.util.Util;

public final class FrameAction {

	private static final FrameAction instance = new FrameAction();

	private final PicAction picAction = PicAction.getInstance();


	public int currIndex = -1;
	public static boolean RUN_STATE;
	public Timer timer;

	private final BigDecimal add = new BigDecimal("0.1");
	private final BigDecimal addTen = new BigDecimal("1");
	private FrameAction() {

	}

	public static FrameAction getInstance() {
		return instance;
	}
	public PicAction getPicAction() {
		return picAction;
	}
	public void addlabelNext100Listener(final JLabel labelNext100, final PicPanel picPanel) {

		labelNext100.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
	  
				currIndex = picAction.getNextByIndex(currIndex, 100);

				doNext(picPanel); 
			}

			@Override
	        public void mouseEntered(MouseEvent e) {
				labelNext100.setForeground(IPic.COLOR_MAIN);
	        }

	        @Override
	        public void mouseExited(MouseEvent e) {
	        	labelNext100.setForeground(Color.WHITE);
	        }
		});
	}

	public void miniAndStop(MainWin mainwin) {

//		if (timer != null) {
			stopSlider(mainwin);
//		}

//		picPanel.setImage(null);
		this.picAction.getImgBean().setImage(null);
		mainwin.frmFoxpic.repaint();
		mainwin.frmFoxpic.setExtendedState(Frame.ICONIFIED);
	}

	public void doNext10(int offset, PicPanel picPanel) {

		currIndex = picAction.getNextByIndex(currIndex, 10 * offset);
		this.doNext(picPanel);
	}


	public void doRestoreFile(PicPanel picPanel) {

		boolean rtn = this.picAction.restoreFile(currIndex);
		
		if (rtn == true)
			picAction.showMsg("Restored", picPanel); 
			
		else
			picAction.showMsg("Restore failed", picPanel);
	}


	public void doRecycle(PicPanel picPanel) {

		boolean isok = picAction.doRecycle(currIndex);
		
		if (isok){
			picAction.showMsg("Moved!", picPanel);
			
			if (PicAction.PATTERN_BY == IPic.PATTERN_THUMB) { 
				picAction.clearThumbByIndex(picPanel, currIndex);
			}
//			this.picPanel.setImageOrg(null);
//			this.picPanel.setImgInImage(null);
			doNext(picPanel);
		} else {
			picAction.showMsg("Failed!", picPanel);
		}
	}


	public int doNext(PicPanel picPanel) {

		
		int maxindex = picAction.getFileMap().length - 1;
		currIndex = (currIndex >= maxindex) ? maxindex
				: currIndex + 1;
		// added 2014/8/8
		picAction.setThumbImg(currIndex, picPanel, 1);
	
		int rtn = setCompValue(currIndex);
		if (RUN_STATE && currIndex == maxindex){
//			this.stopSlider();
			return Util.ListEnd;
		} else {
			return rtn;
 //		return shouldStop;
		}
	}

	public void doRandom(PicPanel picPanel) {
		if (-1 < currIndex && picAction.getFileMap().length > currIndex) {
			currIndex = picAction.getRandomfile(currIndex);
			
			if (PicAction.PATTERN_BY == IPic.PATTERN_THUMB){
				
				currIndex = currIndex - currIndex % 10;
				picAction.setThumbImg(currIndex, picPanel, 1);
			}

			setCompValue(currIndex);
		}
	}

	public void doPre(PicPanel picPanel) {

		currIndex = (currIndex - 1 < 0) ? currIndex : currIndex - 1;

		// added 2014/8/8
		picAction.setThumbImg(currIndex, picPanel, -1);

		setCompValue(currIndex);

	}

	public int setCompValue(int currIndex) {
		MainWin mainwin = MainWin.getInstance();
		int rtn = 0;
		mainwin.lblNo.setText(" No." + (currIndex + 1) + "/"
				+ picAction.getFileMap().length);

		String filepath = picAction.getcurrfile(currIndex);
		if (filepath == null){
			picAction.getImgBean().setImgSize("Size:0KB"); 
			picAction.getImgBean().setImgPath(null);
			picAction.getImgBean().setImgPath_OLD(null);
			mainwin.frmFoxpic.repaint();
			return Util.NoneFile;
		}
		String size = picAction.existF(filepath, mainwin.picPanel);
		if (size == null) {
			picAction.getImgBean().setImgSize("Size:0KB"); 
			rtn = Util.NoneFile;
		} else {
			picAction.getImgBean().setImgSize(size); 
			
		}
		picAction.getImgBean().setImgPath(filepath); 
		picAction.setPanelInfo(mainwin.picPanel.getWidth(), mainwin.picPanel.getHeight(), this.currIndex);
		mainwin.panel_2.setToolTipText(filepath);
//		PicPanel.ISPAINT = 1;
		mainwin.frmFoxpic.repaint();
		return rtn;
	}

	public void stopSlider(MainWin mainwin) {

		RUN_STATE = false;
		mainwin.btnAuto.setText("Auto");
//		PicPanel.ISPAINT = 0;
		if (timer != null){ 
 
			timer.cancel();
			timer = null;
		}
//		this.picPanel.setImgInImage(null);
	}

	public int doDel(MainWin mainwin) {

		String filepath = picAction.getcurrfile(currIndex);

		String size = picAction.existF(filepath, mainwin.picPanel);
		if (size != null) {

			int rs = JOptionPane.showConfirmDialog(mainwin.frmFoxpic,
					"Are you sure to delete this file? \r\n" + filepath,
					"Delete Dialog", JOptionPane.YES_NO_OPTION);

			if (rs != 0) return rs;

			File dd = new File(filepath);
			boolean isok = dd.delete();
			String delemsg = isok ? "Deleted!" : "Failed!";

			picAction.showMsg(delemsg, mainwin.picPanel);
			// frmFoxpic.setTitle(frmFoxpic.getTitle() + " deleted!");
		}
		return 0;
	}

	public void setPath(String absolutePath, int indt) {

		MainWin mainwin = MainWin.getInstance();
		mainwin.frmFoxpic.setTitle(absolutePath);

		this.picAction.showMsg("Init...", mainwin.picPanel);
		picAction.getAllFiles(absolutePath);
		currIndex = indt;
//		picPanel.requestFocus();
		this.picAction.getImgBean().clearThumb();
		this.picAction.getImgBean().setImgInImage(null);
		doNext(mainwin.picPanel);
		mainwin.btnAuto.setEnabled(true);
		mainwin.btnNext.setEnabled(true);
		mainwin.btnDelete.setEnabled(true);

		mainwin.btnNext.requestFocus();
	}

	public void doPick(MainWin mainwin) {
		
		int result = mainwin.jfchooser.showOpenDialog(mainwin.frmFoxpic);
		File file = null;
		if (JFileChooser.APPROVE_OPTION == result) {
			file = mainwin.jfchooser.getSelectedFile();
			if (!file.isDirectory()) {
				JOptionPane.showMessageDialog(null, "Folder not existed!");
				return;
			}

			setPath(file.getAbsolutePath(), -1);
		}
	}


	public void doAuto(MainWin mw) {

		String val = mw.textInterval.getText().trim();
		
		if (val.equals("") || val.equals("0")){
			mw.textInterval.setText("1");
		}
		if (!RUN_STATE) {
			timer = null;
			timer = new Timer();

			timer.schedule(new PicSlider(), 10, Integer.MAX_VALUE);
			RUN_STATE = true;
			mw.btnAuto.setText("Stop");
		} else {
			stopSlider(mw);
		}  
	}

	public void doContinue(PicPanel picPanel) {
		
		String path = PropertiesConfig.getConfig(PropertiesConfig.folderkey);
		
		if (path == null) {
			picAction.showMsg("No Continue", picPanel);
			return;
		}
		int index = Integer.parseInt(PropertiesConfig.getConfig(PropertiesConfig.indexkey));

		setPath(path, index - 1);
	}
 

	public void setFileOrder(int orderByDate, MainWin mainwin) {
		
		picAction.setFileOrder(orderByDate, mainwin);
		picAction.showMsg("Sorted.", mainwin.picPanel);
		mainwin.btnNext.requestFocus();
	}

	public void addPopMenuMouseEvent(final JMenuItem mntmPick) {
//		mntmPick.setForeground(IPic.COLOR_MAIN);
		mntmPick.setOpaque(false);
		mntmPick.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mntmPick.setFont(IPic.menuBold);
			}

			@Override
			public void mouseExited(MouseEvent e) {
//				mntmPick.setForeground(Color.CYAN);//
				mntmPick.setFont(IPic.menuPlain);
			}
		});
	}
	public void btnNextkeyReleased(int keyCode, MainWin mainwin) {
		// next
//		System.out.println(keyCode);
		switch (keyCode){
		
		case 39 : case 40: case 46: case 10:			
			doNext(mainwin.picPanel);
			break;
		case 37 : case 38 : case 44:doPre(mainwin.picPanel);
			break;
			
		case 82:// r key
			this.doRandom(mainwin.picPanel);
			break;
		case 75: case 59 :// k key
			this.doRecycle(mainwin.picPanel);
			break;
		case 68: // d key
			doDel(mainwin);
			break;
		case 69: // e key
			System.exit(0);
			break;
		case 77:
			// m key
			miniAndStop(mainwin);
			break;			
		case 81:// q key
			this.picAction.saveIndex(currIndex, mainwin.frmFoxpic.getTitle(), mainwin.picPanel);
			
			break;
//		case 84:  // t key
//			this.myaction.saveBmp(currIndex);
//			break;  

		case 65:  // a key 
			
			picAction.showMsg(this.picAction.delRecycle(), mainwin.picPanel);
			break;  
		case 8:  // delete key 
			this.doRestoreFile(mainwin.picPanel);
			break; 

		case 93:		 // ] key
			this.doNext10(1, mainwin.picPanel);
			break; 
		case 91:		 // [ key
			this.doNext10(-1, mainwin.picPanel);
			break; 
		case 70:		 // F key
			this.picAction.setBounds(mainwin);
			break; 
		case 79:		 // o key
			this.picAction.openByFinder();
			break; 
		}


	} 

	public void setMacIcon(JFrame frmFoxpic) {
		if (PicAction.ISMAC){
			PicIcon.setMacIcon();
		} else {
			URL stream = this.getClass().getResource(IPic.ICON);
			
			frmFoxpic.setIconImage(new ImageIcon(stream).getImage());

		}
	}

	public void setImagePattern(int flg, MainWin mainWin, int currIndex) {
		
		mainWin.chckbxmntmThumb.setSelected(false);
		mainWin.chckbxmntmPiP.setSelected(false);
		ImgBean imgBean = picAction.getImgBean();
		if (flg == PicAction.PATTERN_BY){
			 
			PicAction.PATTERN_BY = IPic.PATTERN_NONE;
			imgBean.clearThumb();
  
			imgBean.setImgInImage(null);
			mainWin.frmFoxpic.repaint();
			return;
		}

		PicAction.PATTERN_BY = flg;
		switch (flg){
		
			case IPic.PATTERN_PIP:
				 
				mainWin.chckbxmntmPiP.setSelected(true);
				imgBean.clearThumb();
		 
				break;
			case IPic.PATTERN_THUMB:
			 
				mainWin.chckbxmntmThumb.setSelected(true); 
				imgBean.setImgInImage(null);
				this.picAction.setThumbImg(currIndex - Util.getThumbIndex(currIndex), mainWin.picPanel, 1);
				 
				mainWin.frmFoxpic.repaint();
				break; 
		}
		  
	}

	public void addInterval(JTextField textInterval, KeyEvent e) {
		
		String inter = textInterval.getText().trim();
		if (inter.equals("")) return;
		String rtn = "";
		double interD = Double.valueOf(inter);
		BigDecimal intmp = BigDecimal.valueOf(interD);
		switch (e.getKeyCode()){
				
			case 39 : case 40: case 46: case 10:			
//				 Next 
				if (interD > 1){ 
					rtn = String.valueOf(intmp.subtract(addTen).intValue());
				} else {
					rtn = intmp.subtract(add).toString();
				}
				break;
			case 37 : case 38 : case 44:
//				 Pre 
				if (interD >= 1){ 
					rtn = String.valueOf(intmp.add(addTen).intValue());
				} else {
					rtn = intmp.add(add).toString();
					if (Double.valueOf(rtn) == 1) rtn = "1";
				}
				break;
		}
		if (Double.valueOf(rtn) < 0) rtn = inter;
		textInterval.setText(rtn);
	}
	public void printHelp() {

		System.out.println(" R key --- Random");
		System.out.println(" K key --- Recycle(or ; key)");
		System.out.println(" D key --- Delete");
		System.out.println(" E key --- Exit(without saving index)");
		System.out.println(" M key --- Minize window");
		System.out.println(" Q key --- Exit(with saving index)");
		System.out.println(" A key --- Delete All");
		System.out.println(" [ key --- Previous 10");
		System.out.println(" ] key --- Next 10");
		System.out.println(" F key --- Fit image");
		System.out.println(" O key --- Open by finder");
		System.out.println(" Backspce key --- Restore deleted pic");
	}

	public void setImageSize(int wid, int hei) {
		
		switch (this.picAction.mouseMotion){
		case IPic.ISENLARGE_DOWN: 
			picAction.setPanelInfo(wid, hei, currIndex);
			break;
		case IPic.ISENLARGE_UP:
			picAction.setPanelInfo(wid, hei, currIndex);
			break;
		} 
	}
}
