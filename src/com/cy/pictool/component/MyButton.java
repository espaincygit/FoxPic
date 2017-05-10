package com.cy.pictool.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JToolTip;

import com.cy.pictool.action.PicAction;
import com.cy.pictool.bean.IPic;

public final class MyButton extends JButton {

	// private float lighter = 0f;
	// private float heavier = 0.204f;

	// private Color transWhite = new Color(lighter, lighter, lighter, 0.2f);
	// private Color transBlack = new Color(heavier, heavier, heavier, 0.7f);
	// private Color Black = new Color(204, 204, 204);
	// Point2D.Float p1;
	// Point2D.Float p2;
	// GradientPaint gp1;
	// Rectangle2D.Float rect1;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isFocused = false;
	private boolean hover = false;
	private static final int BUTTON_LENGTH = 75;
	private static final int BUTTON_HEIGHT = 22;
	private final Font focusFont = new Font("Tahoma", Font.BOLD, 13);
	private final Font lostFont = new Font("Tahoma", Font.PLAIN, 13);
	private final Color buttonColor = new Color(0.652f, 0.652f, 0.652f);
	private final Color buttonColorDeep = new Color(0.452f, 0.452f, 0.452f);
//	private final Color buttonGray = 
	

	public MyButton(String butName) {
		super(butName);
		this.setDoubleBuffered(true);
		// p1 = new Point2D.Float(0f, 0f);
		// p2 = new Point2D.Float(0f, 60);// this.getHeight() + 20);

		// gp1 = new GradientPaint(p1, transWhite, p2, transBlack, false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
		
//		BUTTON_LENGTH = this.getWidth();
		setPreferredSize(new Dimension(BUTTON_LENGTH, BUTTON_HEIGHT));
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {

				isFocused = true;
				repaint();
			}

			@Override
			public void focusLost(FocusEvent e) {
				isFocused = false;
				repaint();
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hover = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				hover = false;
				repaint();
			}
		});
	}

	@Override
	public void paintComponent(Graphics g1) {

//		System.out.println("paintComponent");
		Graphics2D g2 = (Graphics2D) g1;  // .fill(rect1);

//		Rectangle2D r2d = new Rectangle2D.Float(10, 10, 50, 24);
//		Shape clip = g2.getClip();
//		g2.clip(r2d);
		
		
//		setForeground(Color.WHITE);
		if (isFocused) {

			g2.setPaint(buttonColorDeep);
			setForeground(IPic.COLOR_MAIN);
			setFont(this.focusFont);
		} else {
			if (hover) {

				setForeground(Color.WHITE);
				setFont(this.focusFont);
			} else {
				setForeground(Color.WHITE);
				setFont(this.lostFont);
			}

			g2.setPaint(buttonColor);
		}
		

//		g2.fillRect(0, 0, 
//				BUTTON_LENGTH
//				, BUTTON_HEIGHT);
		
//		g2.setColor(Color.WHITE);
//		g2.setClip(clip);
//		g1.drawString(this.getText(), 0, 0);
//		g2.dispose();
		super.paintComponent(g1);

	}
	public JToolTip createToolTip() {
        JToolTip tip = super.createToolTip();
        
        PicAction.getInstance().setToolTip(tip);
        return tip;
    }
	protected void paintBorder(Graphics g) {

//		System.out.println("paintBorder");
		if (isFocused) {
			g.setColor(IPic.COLOR_MAIN);
			
		} else {
			if (hover) {
				g.setColor(IPic.COLOR_MAIN);
 
			} else {
				g.setColor(Color.LIGHT_GRAY);

			}
			
		}
		// .fill(rect1);
//		g.drawRect(0, 0, 
//				BUTTON_LENGTH - 1, BUTTON_HEIGHT - 1);
		g.drawRoundRect(0, 0, BUTTON_LENGTH - 1, BUTTON_HEIGHT - 1,
		 5, 5);//.fill(rect1);

	}
}
