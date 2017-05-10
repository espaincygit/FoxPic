package com.cy.pictool.bean;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

public interface IPic {
	public static final int ORDER_BY_NONE = 0;
	public static final int ORDER_BY_NAME = 1;
	public static final int ORDER_BY_DATE = 2;

	public static final int ORDER_BY_SIZE = 3;
	public static final int PATTERN_PIP = 0;
	public static final int PATTERN_THUMB = 1;
	public static final int PATTERN_NONE = 2;

	public final static Color transWhite = new Color(0.238f, 0.238f, 0.238f, 0.2f);
//	private Color transCol = new Color(0.238f, 0.238f, 0.238f, 0.05f);
	public final static Color transBlack = new Color(0f, 0f, 0f, 0.9f);

	public final static Font menuBold = new Font("Tahoma", Font.BOLD, 13);
	public final static Font menuPlain = new Font("Tahoma", Font.PLAIN, 13);
	public static final Color transGrey = new Color(0.238f, 0.238f, 0.238f, 0.6f);
	public static final Color transVBar = new Color(Color.WHITE.getRed(), 
			Color.WHITE.getGreen(), Color.WHITE.getBlue(), 70);
	public static float BackPanelHeight = 70f;

	public static final Color COLOR_MAIN = new Color(130, 236, 255);
	int moveArea = 40;
	int ISMOVE = 1;
	int ISENLARGE_UP = 2;
	int ISENLARGE_DOWN = 3;
	
	public static final String ICON = "/com/cy/pictool/resource/icon/foxpic.png";
	public static final int WIN_Y = 22;

	Dimension demen = Toolkit.getDefaultToolkit().getScreenSize();
	public static final Color COLOR_BG = new Color(64, 67, 84);

	public static final String[] CMD_OPEN = { "/bin/sh", "-c", "" };
	
}
