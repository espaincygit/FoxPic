package com.cy.pictool.action;

import java.util.TimerTask;

import com.cy.pictool.MainWin;
import com.cy.pictool.util.Util;

public final class PicSlider extends TimerTask{
	
	public int interval;
	 
	public static PicSlider getInstance(int inter) {
		PicSlider instance = new PicSlider();
		instance.interval = inter;
		return instance;
	}
//	private PicSlider(){
//		
//	}
	public PicSlider(){
		
	}
	  
	
	public void run() {

		if (!FrameAction.RUN_STATE) return;
		MainWin mw = MainWin.getInstance();

		FrameAction inst = mw.getFraAction();
		
		inst.timer.cancel();
		inst.timer = null;

		int isstop = Util.NoneFile;//PicAction.getInstance().getMainWin().doNext();
		 
//		if (isstop == Util.ListEnd){
//			PicAction.getInstance().mainWin.stopSlider();
//		} else {
		 
		while (isstop == Util.NoneFile){
			isstop = inst.doNext(mw.picPanel);
			if (isstop == Util.ListEnd) {
				inst.stopSlider(mw);
				return;
			}
//			while (PicPanel.ISPAINT != 0){
//				try {
//					Thread.sleep(40);
////					System.out.println("waiting");
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
		}
	
//		if (!MainWin.RUN_STATE) return;
//		inst.timer = new Timer();
//		inst.timer.schedule(PicSlider.getInstance(this.interval), this.interval,
//				Integer.MAX_VALUE);
//		}
	}
}