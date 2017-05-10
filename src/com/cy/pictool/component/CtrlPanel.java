package com.cy.pictool.component;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JToolTip;

import com.cy.pictool.MainWin;
import com.cy.pictool.action.PicAction;
import com.cy.pictool.bean.IPic;
import com.cy.pictool.bean.ImgBean;

public final class CtrlPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CtrlPanel() {
		this.setDoubleBuffered(true);
		this.setToolTipText("2013 - 2014 CopyRight@ChenYi");
		
	}

	@Override
	public void paintComponent(Graphics g1) {
		

		Graphics2D g2 = (Graphics2D) g1;
//		g2.setPaint(gp1);
//		g2.fill(rect1); 
		Rectangle2D.Float rect1 = new Rectangle2D.Float(0, 0,
				this.getWidth(), IPic.BackPanelHeight);
		MainWin mainwin = MainWin.getInstance();
		if (mainwin == null) return;

		ImgBean imgbean = PicAction.getInstance().getImgBean();
		BufferedImage bgImg = imgbean.getImgRefl();
		if (null != bgImg && imgbean.image != null){

			PicPanel picpanel = mainwin.picPanel;
			int height = picpanel.getHeight();
			int width = picpanel.getWidth();
			int imgHeight = imgbean.getImgHeight() ;
			int imgwidth = imgbean.getImgWidth() ;
			int selfHeight = this.getHeight();

			this.drawImgBackGround(g1, width, height, selfHeight);
			
			width = (width - imgwidth) / 2;
			int y = (height - imgHeight) / 2;
//				y = y == 0 ? 0 : -y;
				
			g2.translate(0 , 1 * imgHeight);
			g2.scale(1, -1);
 
			g2.drawImage(bgImg, 
				width, y, imgwidth, imgHeight,
				this);
				
			g2.scale(1, -1);

			g2.translate(0, -1 * imgHeight);
				//----------------------------------------------------------
			 
			float offset = 0;
			int panelHeight = 34;
			if (y + imgHeight < height - IPic.BackPanelHeight) { 
				offset = height - y - imgHeight;
			} else {
				offset = IPic.BackPanelHeight;
			}
//			offset += mainwin.picPanel.getThumbOffset();
			
			Point2D.Float p1 = new Point2D.Float(0f, 
//					-90f);
//					-height
					-offset);
			Point2D.Float p2 = new Point2D.Float(0f, 
//					PicPanel.BackPanelHeight + offset +
//					panelHeight
					50);
//					0 - height - 150);

			GradientPaint gp1 = new GradientPaint(p1, IPic.transWhite,
					p2, IPic.transBlack,
					false);
			Rectangle2D.Float rect2 = new Rectangle2D.Float(
					p1.x,
//					width,
					 -offset,
					this.getWidth(),
//					imgwidth, 
					
					offset + panelHeight
//					panelHeight + offset
					);
 
			g2.setPaint(gp1); 
			
			g2.fill(rect2);
//			}
			//----------------------------------------------------------
			
		} else {
			g2.setColor(Color.BLACK);
//			g2.setPaint(gp1);
			g2.fill(rect1);
		}
//		MainWin.RESIZED = 0;
//		super.paintComponent(g2);
	}
	public JToolTip createToolTip() {
        JToolTip tip = super.createToolTip();
        
        PicAction.getInstance().setToolTip(tip);
        return tip;
    }
	
	private void drawImgBackGround(Graphics graphics, int panelwidth, int panelheight, int selfHeight) {

		/*
		 * 
		 * ------------------------------- -panelheight -> p1
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * ------------------------------- 0
		 * 
		 * 
		 * ------------------------------- selfHeight
		 * 
		 * 
		 * 
		 * 
		 */
		Point2D.Float p1 = new Point2D.Float(0f, -panelheight);
		Point2D.Float p2 = new Point2D.Float(0f, 
				selfHeight);

//		GradientPaint gp1 = new GradientPaint(p1, IPic.COLOR_MAIN, p2, IPic.COLOR_MAIN,
		GradientPaint gp1 = new GradientPaint(p1, Color.BLACK, p2, IPic.COLOR_BG,
				false);
		Rectangle2D.Float rect1 = new Rectangle2D.Float(p1.x, p1.y,
				panelwidth, panelheight + selfHeight);

		Graphics2D g2 = (Graphics2D) graphics;
		g2.setPaint(gp1);
		g2.fill(rect1);

	}
}
