package com.cy.pictool.resource.icon;

import javax.swing.ImageIcon;
import com.apple.eawt.*;
import com.cy.pictool.bean.IPic;

public class PicIcon {
	
	public static void setMacIcon(){
		Application.getApplication().setDockIconImage(new ImageIcon(
				PicIcon.class.getClass().getResource(IPic.ICON)).getImage());
	}

}
