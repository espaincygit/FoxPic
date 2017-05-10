package com.cy.pictool.component;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.cy.pictool.MainWin;
import com.cy.pictool.bean.IPic;

public class MyJPopupMenu extends JPopupMenu {

	private BufferedImage screenshot;
	protected boolean hover;
	private Point2D.Float p1 = new Point2D.Float(0f, 0);
	private Point2D.Float p2 = new Point2D.Float(70f,  	0); 

	private GradientPaint gp1 = new GradientPaint(p1, IPic.transVBar,
			p2, IPic.transGrey,
			false);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyJPopupMenu() {
//		setBorderPainted(false);
		this.setDoubleBuffered(true);
		
		this.addPopupMenuListener(new PopupMenuListener() {
	        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
//	            System.out.println("Popup menu will be visible!");
	        	MainWin mw = MainWin.getInstance();
	        	if (mw == null) return;
	        	JMenuItem[] menu = mw.menuItem;
	        	for (JMenuItem me : menu){
	        		me.setFont(IPic.menuPlain);
	        	}
	        }
	        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
//	            System.out.println("Popup menu will be invisible!");
	        	
	        }
	        public void popupMenuCanceled(PopupMenuEvent e) {
//	            System.out.println("Popup menu is hidden!");
	        }
		});
	}

	protected void cleanScreenshot() {
		this.screenshot = null;
	}

	public void createScreenshot() {

//		Dimension demen = Toolkit.getDefaultToolkit().getScreenSize();
		try {
			this.screenshot = (new Robot()).createScreenCapture(new Rectangle(
					0, 0, (int) IPic.demen.getWidth(), (int) IPic.demen.getHeight()));
		} catch (AWTException e) {
			e.printStackTrace();
		}
 
	}

	@Override
	public void paintComponent(Graphics g1) {

		if (null == screenshot) {
//			createScreenshot();
			return;
		}
		
		int height = this.getHeight();
		int width = this.getWidth();
		int x = this.getLocationOnScreen().x;
		int y = this.getLocationOnScreen().y;

		g1.drawImage(screenshot, -x, -y, screenshot.getWidth(),
				screenshot.getHeight(), this);
		
		g1.setColor(IPic.transGrey);
		g1.fillRect(0, 0, width, height);
		
		Rectangle2D.Float rect2 = new Rectangle2D.Float(p1.x, p1.y,
				width, height);

		Graphics2D g2 = (Graphics2D)g1;
		g2 .setPaint(gp1); 
		
		g2.fill(rect2);
		
		int blockWidth = 5;
		
		g2.setColor(Color.WHITE);
		g2.fillRect(width - blockWidth * 2, 0, blockWidth, blockWidth);
		g2.fillRect(width - blockWidth, blockWidth, blockWidth, blockWidth);
		
		g2.fillRect(width - blockWidth * 4, 0, blockWidth, blockWidth);
		g2.fillRect(width - blockWidth * 3, blockWidth, blockWidth, blockWidth);
		g2.fillRect(width - blockWidth * 5, blockWidth, blockWidth, blockWidth);
		
		g2.fillRect(width - blockWidth * 2, blockWidth * 2, blockWidth, blockWidth);

		g2.fillRect(width - blockWidth * 3, blockWidth * 3, blockWidth, blockWidth);
		g2.fillRect(width - blockWidth, blockWidth * 4, blockWidth, blockWidth);
		

//		g1.setColor(Color.LIGHT_GRAY);
//		g1.drawLine(15, 0, 15, height);
		
//		g1.setColor(Color.WHITE);
//		g2.setStroke(new BasicStroke(3.0f));
//		g2.fillRect(25, 5, 10, 10);
//		y = 32;
//		g1.drawRect(3, y, 13, 13);
	}
/*
	protected void paintBorder(Graphics g1) {

		g1.setColor(IPic.COLOR_MAIN);
		g1.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
	}
	*/
}
