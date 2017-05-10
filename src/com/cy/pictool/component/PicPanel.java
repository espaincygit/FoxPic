package com.cy.pictool.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Timer;

import javax.swing.JPanel;
import javax.swing.JToolTip;

import com.cy.pictool.MainWin;
import com.cy.pictool.action.FrameAction;
import com.cy.pictool.action.PicAction;
import com.cy.pictool.action.PicSlider;
import com.cy.pictool.bean.IPic;
import com.cy.pictool.bean.ImgBean;
import com.cy.pictool.util.Util;

public final class PicPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int MSG_HEIGHT = 45;

	private final Font msgFont = new Font("Tahoma", Font.PLAIN, 20);

	private final BasicStroke stroke = new BasicStroke(3.0f);
	
//	public static int ISPAINT = 0;
	 
	public PicPanel() {
		this.setDoubleBuffered(true);
		FrameAction.getInstance().getPicAction().setListenser(this);
	}
	@Override
	public void paintComponent(Graphics g1) {
//		System.out.println("paintComponent     "  );		
		MainWin mainwin = MainWin.getInstance();
		if (mainwin == null) return;

//		if (
//				MainWin.RESIZED == 1 && 
//		this.imgPath != null 
//				&& !MainWin.RUN_STATE
//		) {
		int wid = this.getWidth();
		int hei = this.getHeight();
		
		FrameAction frmaction = mainwin.getFraAction();
		
		frmaction.setImageSize(wid, hei);
		
		PicAction.MSG_QUEUE = 0;
		this.drawComponent(g1, frmaction.getPicAction().getImgBean(), wid, hei);
		
//		MainWin.RESIZED = 1;	
		mainwin.panel_2.repaint();
//		MainWin.RESIZED = 0;

//		super.paintComponent(g1);

//		ISPAINT = 0;
		if (FrameAction.RUN_STATE && frmaction.timer == null) {

			int acoun = Util.getInterval(mainwin.textInterval.getText());
			
			frmaction.timer = new Timer();
			frmaction.timer.schedule(new PicSlider(), acoun,
					Integer.MAX_VALUE);
		}
//		}
	}

	private void drawComponent(Graphics g1, ImgBean imgBean, int panelwidth, int panelheight) {
//		System.out.println(FrameAction.getInstance().currIndex);
//		if (null == this.imgPath) {
//			g1.setColor(Color.BLACK);
//			g1.fillRect(0, 0, this.getWidth(), this.getHeight());
//			super.paintComponent(g1);
//			return;
//		}
		float mainImageX = 0;
		float mainImageY = 0;
 
//		float panelwidth = this.getSize().width;
//		float panelheight = this.getSize().height;

		Graphics2D graphics = (Graphics2D) g1;
		
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
         
//		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		// Name : sssss.jpg
		// size:545kb
		 
//		if (null == image) {
//			drawThumb(graphics, 0, thumbX,  (int)panelwidth);
//
//			drawCaption(graphics);
//			return;
//		}
		float picwidth = imgBean.getImgWidth();
		float picheight = imgBean.getImgHeight();


		mainImageX = (panelwidth - picwidth) / 2;

		mainImageY = (panelheight - picheight) / 2;
 
		if (null != imgBean.image){
			
			this.drawImgBackGround(graphics, panelwidth, panelheight);
			graphics.drawImage(imgBean.image, (int) mainImageX, (int)mainImageY, (int) picwidth, (int) picheight,
				this);

		} else {
//			graphics.drawRect(0, 0, (int)panelwidth, (int)panelheight);
			this.drawMsg("No image", graphics, this);

		}
		if (
				panelheight - picheight > 0 && 
				imgBean.imgRefl != null && null != imgBean.image) {
			drawReflection(graphics, (Image)imgBean.imgRefl, picwidth, picheight, (int)mainImageX, (int)mainImageY);
		}
 
		
		float offset = 0;
		if (mainImageY + picheight < panelheight - IPic.BackPanelHeight) { 
			offset = panelheight - IPic.BackPanelHeight - mainImageY - picheight;
		}
		
		if (imgBean.imgName != null)
			drawBlackPanel(graphics, offset, panelwidth, panelheight);
		if (PicAction.PATTERN_BY == IPic.PATTERN_PIP){

			drawPiP(graphics, panelwidth, panelheight, imgBean);
 
		}
//		}
		// added 2014/8/8
//		int thumbX = (int) (panelheight * 0.8);
		drawThumb(graphics, panelheight,  (int)panelwidth, imgBean);
		
		drawCaption(graphics, imgBean, panelwidth, panelheight); 
		graphics = null;
		g1 = null;
		 
	}
	private void drawPiP(Graphics2D graphics, float panelwidth,
			float panelheight, ImgBean imgBean) {
		int imiY = (int) (panelheight - (IPic.BackPanelHeight + 20 + imgBean
				.getImiH()));
		int imiX = 0;
		if (imgBean.getImiH() < imgBean.getImiW()) {

			imiX = (int) (panelwidth / 2);
		} else {
			imiX = (int) (panelwidth - (panelwidth / 4));
		}
		if (imgBean.imgInImage != null) {
			graphics.drawImage(imgBean.imgInImage, (int) imiX, imiY,
				(int) imgBean.getImiW(), imgBean.getImiH(), this);
		} else {
			graphics.setColor(IPic.transWhite);
			graphics.fillRect(imiX, imiY, imgBean.getImiW(), imgBean.getImiH());
		}
		graphics.setColor(IPic.COLOR_MAIN);
//		int wid = imiX + this.getImiW();
//		int heit = imiY + this.getImiH();
//		graphics.drawLine(imiX, imiY, wid, imiY);
//		graphics.drawLine(imiX, imiY, imiX, heit);
//		graphics.drawLine(imiX, heit, wid, heit);
//		graphics.drawLine(wid, imiY, wid, heit);
		
		graphics.drawRect(imiX, imiY, imgBean.getImiW(), imgBean.getImiH());
	}

	private void drawCaption(Graphics2D graphics, ImgBean imgBean, int panelwidth, int panelheight) {
		
		if (imgBean.imgSize == null) return;
		int offset = getThumbOffset(panelwidth);
		
		graphics.setFont(new Font("Tahoma", Font.BOLD, 14));
		graphics.setColor(Color.white);
		graphics.drawString("Name:", 10, panelheight - 40 - offset);

		graphics.setFont(new Font("Tahoma", Font.PLAIN, 16));
		graphics.drawString(Util.getName(imgBean.imgPath), 60, panelheight - 40 - offset);

		graphics.setFont(new Font("Tahoma", Font.BOLD, 12));
		graphics.drawString(imgBean.imgSize, 10, panelheight - 20 - offset);
		
//		this.setToolTipText(imgBean.imgPath);
	}
	
	protected int getThumbOffset(int panelwidth){
		 
		if (PicAction.PATTERN_BY == IPic.PATTERN_THUMB) {
			return (int)(panelwidth / 10 * 1.3);
		} else {
			return 0;
		}
		 
	}

	// added 2014/8/8
	private void drawThumb(Graphics2D graphics, int imgY, int width, ImgBean imgBean) {
		if (imgBean.imgThumb == null) return;

		if (PicAction.PATTERN_BY != IPic.PATTERN_THUMB) return;
//		BigDecimal bdwidth = new BigDecimal(width);
		int fixedWidth = width / 10; //
		
		int imgX = (int)((width % 10) / 2);
//		int fixedWidth = bdwidth.divide(new BigDecimal("10")).intValue();
		int thumbIndex = Util.getThumbIndex(FrameAction.getInstance().currIndex);
		int imgHeight = (int) (fixedWidth * 1.3);
		imgY -= imgHeight;
		for (int i = 0; i < imgBean.imgThumb.length; i++){
			
			Image imgtmp = imgBean.imgThumb[i];
			
			if (imgtmp != null) {
				int realHeight = imgtmp.getHeight(this);
				int realWidth = imgtmp.getWidth(this);

				int thumbHei = (int) (fixedWidth * realHeight / realWidth);
				int thumbY = imgY;
				if (realHeight < realWidth){ 
//					imiWtemp = (int)(widt * imiH * 0.7 / heig);
//					imiXtemp = imiX + (widt - imgWid) / 2;
					graphics.setColor(IPic.transGrey);
					graphics.fillRect(imgX, imgY, fixedWidth, imgHeight);
					thumbY = imgY + (imgHeight - thumbHei) / 2;
				} 
//				else {
//					int imgtmpHeight = (int) (fixedWidth * realHeight / realWidth);
//					if (imgHeight < thumbHei) {
//						thumbY = imgY - (thumbHei - imgHeight);

//					} 
//					imgHeight = thumbHei ;//+ thumbY;
					 
//				}
				graphics.drawImage(imgBean.imgThumb[i], imgX, thumbY,
						fixedWidth, thumbHei, this);

			}
			if (i == thumbIndex){ 
				graphics.setColor(IPic.COLOR_MAIN);  
				graphics.setStroke(stroke);

				graphics.drawRect(imgX, imgY, fixedWidth - 2, imgHeight - 2);
				  
				thumbIndex = -1;
			}
			 
			imgX += fixedWidth;
		}
		
	}

	private void drawReflection(Graphics2D graphics, Image imgRefl2,
			float picwidth, float picheightf, int x, int y) {
		int picheight = (int)picheightf;
		graphics.translate(0 , 3 * picheight + y);
		graphics.scale(1, -1);

//		int tmpHei = (int)(picheight * 1.2);
//		int tmpHeiRev = (int)(picheight * 0.8);
//		graphics.drawImage(imgRefl2, x, (int)picheight,
//				(int)picwidth, (int)picheight, this);
		graphics.drawImage(imgRefl2, x, picheight,
				(int)picwidth, picheight, this);

		graphics.scale(1, -1);

		graphics.translate(0, -3 * picheight - y);

	}

//	private void drawBlackTop(Graphics graphics, int y) {
//
//		int height = 50;
//		Point2D.Float p1 = new Point2D.Float(0f, y);
//		Point2D.Float p2 = new Point2D.Float(0f, y + height);
//
//		GradientPaint gp1 = new GradientPaint(p1, Color.BLACK, p2, transCol,
//				false);
//		Rectangle2D.Float rect1 = new Rectangle2D.Float(p1.x, y,
//				this.getWidth(), height);
//
//		Graphics2D g2 = (Graphics2D) graphics;
//		g2.setPaint(gp1);
//		g2.fill(rect1);
//
//	}
	private void drawImgBackGround(Graphics graphics, int panelwidth, int panelheight) {

		
		Point2D.Float p1 = new Point2D.Float(0f, 0f);
		Point2D.Float p2 = new Point2D.Float(0f, 
				panelheight);

		GradientPaint gp1 = new GradientPaint(p1, Color.BLACK, p2, IPic.COLOR_BG,
				false);
		Rectangle2D.Float rect1 = new Rectangle2D.Float(p1.x, p1.y,
				panelwidth, panelheight);

		Graphics2D g2 = (Graphics2D) graphics;
		g2.setPaint(gp1);
		g2.fill(rect1);

	}
	private void drawBlackPanel(Graphics graphics, float offset, int panelwidth, int panelheight) {

		int offsetthumb = 0;//getThumbOffset();
		
		Point2D.Float p1 = new Point2D.Float(0f, 
				panelheight
				- IPic.BackPanelHeight - offsetthumb - offset);
		Point2D.Float p2 = new Point2D.Float(0f, 
				panelheight + 50);

		GradientPaint gp1 = new GradientPaint(p1, IPic.transWhite, p2, IPic.transBlack,
				false);
		Rectangle2D.Float rect1 = new Rectangle2D.Float(p1.x, p1.y,
				panelwidth, IPic.BackPanelHeight + offsetthumb + offset);

		Graphics2D g2 = (Graphics2D) graphics;
		g2.setPaint(gp1);
		g2.fill(rect1);

	}
/*
	private BufferedImage getReflect(BufferedImage image) {
		
//		System.out.println("getReflect");
		BufferedImage reflection = null;
		try { 
			int imageWidth = image.getWidth();
			int imageHeight = image.getHeight();
			 
			float fadeHeight = 0.3f;

//			g2d.translate((width - imageWidth) / 2, height / 2 - imageHeight);
//			g2d.drawRenderedImage(image, null);
//			g2d.translate(0, 2 * imageHeight + gap);
//			g2d.scale(1, -1);

			reflection  = new BufferedImage(imageWidth,
					imageHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D rg = reflection.createGraphics();
//			rg.rotate(Math.toRadians(180), imageHeight / 2, imageWidth / 2);
			rg.drawRenderedImage(image, null);
			rg.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
			rg.setPaint(new GradientPaint(0, imageHeight * fadeHeight,
					PicAction.transWhite, 0, imageHeight,
					PicAction.transBlack));

			rg.fillRect(0, 0, imageWidth, imageHeight);
			rg.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error ex){
			ex.printStackTrace();
		}

		return reflection;
	}
*/	

	
	public void drawMsg(String delemsg, Graphics gra, PicPanel panel) {

//		Graphics gra = graphics.getGraphics();
		gra.setFont(msgFont);
		FontMetrics metrics = gra.getFontMetrics(gra.getFont());
		 
		int width = metrics.stringWidth(delemsg); 
		 
		gra.setColor(IPic.transGrey);
		
		int[] pos = getMsgPosition(width, panel);
		width += 22;
		
		gra.fillRoundRect(pos[0] - 10, pos[1] - 30 + 
				PicAction.MSG_QUEUE * MSG_HEIGHT, width, MSG_HEIGHT, 5, 5);
//		System.out.println(width);
		    
		gra.setColor(Color.WHITE);
		gra.drawString(delemsg, pos[0], pos[1] + PicAction.MSG_QUEUE * MSG_HEIGHT);
//		gra.dispose();
		PicAction.MSG_QUEUE++;
	}
	
	public int[] getMsgPosition(int msglength, PicPanel panel){
		
		int[] rtn = {0, 0};
		 
		rtn[0] = (panel.getWidth() - msglength) / 2;
		
		rtn[1] = (panel.getHeight() - MSG_HEIGHT) / 2;
		
		return rtn;
	}
	public JToolTip createToolTip() {
        JToolTip tip = super.createToolTip();
        
        PicAction.getInstance().setToolTip(tip);
        return tip;
    }
}
