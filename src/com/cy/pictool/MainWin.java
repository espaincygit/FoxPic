package com.cy.pictool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import com.cy.pictool.action.FrameAction;
import com.cy.pictool.action.PicAction;
import com.cy.pictool.bean.IPic;
import com.cy.pictool.component.CtrlPanel;
import com.cy.pictool.component.MyButton;
import com.cy.pictool.component.MyJPopupMenu;
import com.cy.pictool.component.PathDragDropListener;
import com.cy.pictool.component.PicPanel;
import com.cy.pictool.util.PropertiesConfig;

public final class MainWin {

	public JFrame frmFoxpic;
	public JFileChooser jfchooser;
	public PicPanel picPanel;
	private JPanel panel_1;

	private final FrameAction fraAction = FrameAction.getInstance();

	public JButton btnAuto;
	public JButton btnDelete;
	private JPanel panel_3;
	private JPanel panel_4;
	public JButton btnNext;
	public JLabel lblNo;
//	public static boolean RESIZED = false;
	public JTextField textInterval;
	private JPopupMenu popupMenu;
	private JMenuItem mntmPick;
	protected boolean isMousePressed;
	protected Point newPos;
	private JPanel panel_5;
	private JLabel lblFromHead;
//	private Color transBlack = Color.RED;
	public JCheckBoxMenuItem chckbxmntmPiP;
 
	private JCheckBoxMenuItem chckbxmntmVertical;
	private JCheckBoxMenuItem chckbxmntmHerizon;
	private JSeparator separator;
	
	private final int winlong = 878;//845; 
	private final int winshort = 561;//528; 
	private JPanel panel_6;
	private JLabel labelNext100;
	private JMenuItem mntmAlwaysOnTop;
//	private JSeparator separator_1;
//	private int onTop = 0;
	private JButton buttonPick;
	public JCheckBoxMenuItem chckbxmntmOrderByDate;
	public JCheckBoxMenuItem chckbxmntmOrderByName;
	public JCheckBoxMenuItem chckbxmntmOrderByNone;
	private JSeparator separator_2;
//	private JMenuItem mntmContinueLastv;
//	private JSeparator separator_3;
	public JCheckBoxMenuItem chckbxmntmOderBySize;
	public JCheckBoxMenuItem chckbxmntmThumb;
	public JPanel panel_2 ;
	
	private static MainWin instance;
	private JSeparator separator_3;
	

	public JMenuItem[] menuItem = new JMenuItem[10];
	
	public static MainWin getInstance() {
		return instance;
	}


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWin window = new MainWin();
					window.frmFoxpic.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainWin() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		PicAction.ISMAC = fraAction.getPicAction().isMac();
		if (!PicAction.ISMAC) this.fraAction.getPicAction().setWinStyle();

		frmFoxpic = new JFrame();
		/*frmFoxpic.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
//				RESIZED = 1;
//				frmFoxpic.repaint();
				
			}
		});*/

		frmFoxpic.setTitle("FoxPic");
		frmFoxpic.setBackground(UIManager.getColor("ColorChooser.background"));
		frmFoxpic.setBounds(0, IPic.WIN_Y, this.winshort, this.winlong);
		frmFoxpic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		frmFoxpic.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		panel_1 = new JPanel();
		panel_1.setForeground(Color.WHITE);
		panel_1.setBackground(Color.BLACK);
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		picPanel = new PicPanel();
		
		picPanel.setBackground(Color.BLACK);

		panel_1.add(picPanel);

		popupMenu = new MyJPopupMenu();
		addPopup(picPanel, popupMenu);

		mntmPick = new JMenuItem("Load");
		mntmPick.setFont(IPic.menuPlain);
		mntmPick.setBackground(Color.DARK_GRAY);
		mntmPick.setForeground(Color.WHITE);
		popupMenu.add(mntmPick);
		
		separator_3 = new JSeparator();
		popupMenu.add(separator_3);

		chckbxmntmPiP = new JCheckBoxMenuItem("Pic in Pic");
		chckbxmntmPiP.setFont(IPic.menuPlain);
		chckbxmntmPiP.setBackground(Color.DARK_GRAY);
		chckbxmntmPiP.setForeground(Color.WHITE);
		popupMenu.add(chckbxmntmPiP);

		
		chckbxmntmThumb = new JCheckBoxMenuItem("Thumb");
		chckbxmntmThumb.setFont(IPic.menuPlain);
		chckbxmntmThumb.setBackground(Color.DARK_GRAY);
		chckbxmntmThumb.setForeground(Color.WHITE);
		popupMenu.add(chckbxmntmThumb);

		chckbxmntmThumb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				myaction.setImagePattern(PicAction.PATTERN_THUMB, picPanel);
				setImagePattern(IPic.PATTERN_THUMB);
			}
		});
		
		
		separator = new JSeparator();
		popupMenu.add(separator);


		chckbxmntmVertical = new JCheckBoxMenuItem("Vertical");
		chckbxmntmVertical.setFont(IPic.menuPlain);
		chckbxmntmVertical.setBackground(Color.DARK_GRAY);
		chckbxmntmVertical.setForeground(Color.WHITE);
		popupMenu.add(chckbxmntmVertical);
		
		chckbxmntmHerizon = new JCheckBoxMenuItem("Herizon");
		chckbxmntmHerizon.setFont(IPic.menuPlain);
		chckbxmntmHerizon.setBackground(Color.DARK_GRAY);
		chckbxmntmHerizon.setForeground(Color.WHITE);
		popupMenu.add(chckbxmntmHerizon);
		
//		separator_1 = new JSeparator();
//		popupMenu.add(separator_1);
		
		mntmAlwaysOnTop = new JCheckBoxMenuItem("Always on Top");
		mntmAlwaysOnTop.setFont(IPic.menuPlain);
		mntmAlwaysOnTop.setForeground(Color.WHITE);
		mntmAlwaysOnTop.setBackground(Color.DARK_GRAY);
//		popupMenu.add(mntmAlwaysOnTop);
		
		separator_2 = new JSeparator();
		popupMenu.add(separator_2);

		chckbxmntmOrderByDate = new JCheckBoxMenuItem("Order by Date");
		chckbxmntmOrderByDate.setFont(IPic.menuPlain);
		chckbxmntmOrderByDate.setBackground(Color.DARK_GRAY);
		chckbxmntmOrderByDate.setForeground(Color.WHITE);
		popupMenu.add(chckbxmntmOrderByDate);
		chckbxmntmOrderByDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				myaction.setFileOrder(PicAction.ORDER_BY_DATE);
				fraAction.setFileOrder(IPic.ORDER_BY_DATE, instance);
			}
		});
		
		chckbxmntmOrderByName = new JCheckBoxMenuItem("Order by Name");
		chckbxmntmOrderByName.setFont(IPic.menuPlain);
		chckbxmntmOrderByName.setForeground(Color.WHITE);
		chckbxmntmOrderByName.setBackground(Color.DARK_GRAY);
		popupMenu.add(chckbxmntmOrderByName);
		chckbxmntmOrderByName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				myaction.setFileOrder(PicAction.ORDER_BY_NAME);
				fraAction.setFileOrder(IPic.ORDER_BY_NAME, instance);
  
			}
		});
		
		chckbxmntmOderBySize = new JCheckBoxMenuItem("Order by Size");
		chckbxmntmOderBySize.setFont(IPic.menuPlain);
		chckbxmntmOderBySize.setForeground(Color.WHITE);
		chckbxmntmOderBySize.setBackground(Color.DARK_GRAY);
		popupMenu.add(chckbxmntmOderBySize);
		chckbxmntmOderBySize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				myaction.setFileOrder(PicAction.ORDER_BY_SIZE);
				fraAction.setFileOrder(IPic.ORDER_BY_SIZE, instance);
  
			}
		});
		
		chckbxmntmOrderByNone = new JCheckBoxMenuItem("Order by None");
		chckbxmntmOrderByNone.setFont(IPic.menuPlain);
		chckbxmntmOrderByNone.setBackground(Color.DARK_GRAY);
		chckbxmntmOrderByNone.setForeground(Color.WHITE);
//		popupMenu.add(chckbxmntmOrderByNone);
		chckbxmntmOrderByNone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				myaction.setFileOrder(PicAction.ORDER_BY_NONE);
				fraAction.setFileOrder(IPic.ORDER_BY_NONE, instance);
  
			}
		});
		mntmAlwaysOnTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				mntmAlwaysOnTop.setSelected(true);
//				setTop();
			}
		});
		chckbxmntmHerizon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 
				chckbxmntmHerizon.setSelected(true);
				chckbxmntmVertical.setSelected(false); 
				frmFoxpic.setBounds(winshort, 22, 1440 - winshort, winlong);
//				frmFoxpic.setBounds(0, 0, winlong - 150, winshort);
			}
		});
		chckbxmntmVertical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				chckbxmntmVertical.setSelected(true);
				chckbxmntmHerizon.setSelected(false); 
				frmFoxpic.setBounds(0, 22, winshort, winlong);
			
			}
		});
		
		picPanel.setLayout(new BorderLayout(0, 0));
		chckbxmntmPiP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

//				myaction.setImagePattern(PicAction.PATTERN_PIP);
				setImagePattern(IPic.PATTERN_PIP);
			}
		});
		mntmPick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fraAction.doPick(instance);

			}
		});

		panel_2 = new CtrlPanel();
		panel.add(panel_2, BorderLayout.SOUTH);

		jfchooser = new JFileChooser();
		 
		setDefaultFolder(jfchooser); 
		jfchooser.setDialogTitle("Please choose folder");
		jfchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		panel_2.setLayout(new BorderLayout(0, 0));

		panel_3 = new JPanel();
		panel_3.setBackground(SystemColor.windowBorder);
		panel_2.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));

		lblNo = new JLabel("No");
		lblNo.setForeground(Color.WHITE);
		lblNo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel_3.add(lblNo, BorderLayout.WEST);

		panel_4 = new JPanel();
		panel_4.setBackground(new Color(204, 204, 204));
		panel_2.add(panel_4, BorderLayout.EAST);
		panel_2.setOpaque(false);// 2014-9-6
		panel_3.setOpaque(false);
		panel_4.setOpaque(false);
		panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_5.setBackground(SystemColor.windowBorder);
		panel_4.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));

		lblFromHead = new JLabel(" < "){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public JToolTip createToolTip() {
		        JToolTip tip = super.createToolTip();
		        fraAction.getPicAction().setToolTip(tip);
		        
		        return tip;
		    }
			
		};
		lblFromHead.setForeground(Color.WHITE);
		lblFromHead.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
//				if (arg0.getClickCount() == 2) {
					fraAction.currIndex = -1;
//					picPanel.requestFocus();

					fraAction.doNext(picPanel);
//				}

			}
			@Override
	        public void mouseEntered(MouseEvent e) {
				lblFromHead.setForeground(IPic.COLOR_MAIN);
	        }

	        @Override
	        public void mouseExited(MouseEvent e) {
	        	lblFromHead.setForeground(Color.WHITE);
	        }
			
		});
		lblFromHead.setToolTipText("From the beginning");
		lblFromHead.setBackground(SystemColor.windowBorder);
		panel_5.add(lblFromHead, BorderLayout.NORTH);
		panel_5.setOpaque(false);
		panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_6.setBackground(SystemColor.windowBorder);
		panel_4.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		labelNext100 = new JLabel(" > "){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public JToolTip createToolTip() {
		        JToolTip tip = super.createToolTip();
		        PicAction.getInstance().setToolTip(tip);
		        
		        return tip;
		    }
			
		};
		labelNext100.setForeground(Color.WHITE);
		labelNext100.setToolTipText("Next 100");
//		labelNext100.setBackground(SystemColor.windowBorder);
		
		this.fraAction.addlabelNext100Listener(this.labelNext100, picPanel);
		
		panel_6.add(labelNext100, BorderLayout.NORTH);
		panel_6.setOpaque(false);
		buttonPick = new MyButton("Ctnu");
		buttonPick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				fraAction.doContinue(picPanel);
			}
		});  
		buttonPick.addKeyListener(new KeyAdapter() {
			 
			@Override
			public void keyPressed(KeyEvent arg0) {
				
				fraAction.btnNextkeyReleased(arg0.getKeyCode(), instance);

			}
		});
		this.buttonPick.setToolTipText("Press to Continue");
		buttonPick.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		buttonPick.setBackground(new Color(204, 204, 204));
		panel_4.add(buttonPick);
//		setButtonShape(this.buttonPick);

		btnNext = new MyButton("Next");
		panel_4.add(btnNext);
		btnNext.setBackground(new Color(204, 204, 204));
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNext.setEnabled(false);
		btnNext.setToolTipText("Press to next");
		btnNext.addKeyListener(new KeyAdapter() {
 
			@Override
			public void keyPressed(KeyEvent arg0) {
				
				fraAction.btnNextkeyReleased(arg0.getKeyCode(), instance);

			}
		});
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fraAction.doNext(picPanel);
			}

		});
//		setButtonShape(this.btnNext);

		btnDelete = new JButton("Del");
		btnDelete.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				fraAction.btnNextkeyReleased(e.getKeyCode(), instance);

			}
		});
		this.btnDelete.setToolTipText("Press to delete");
//		panel_4.add(btnDelete);

		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDelete.setEnabled(false);
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setBackground(new Color(153, 153, 153));

		// addActionToButton(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int keycode = arg0.getModifiers();
				
				int rs = fraAction.doDel(instance);
				if (rs != 1 && keycode == 0){
					fraAction.doNext(picPanel);
				}
			}
		});

		btnAuto = new MyButton("Auto");
		btnAuto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				fraAction.btnNextkeyReleased(e.getKeyCode(), instance);

			}
		});
		this.btnAuto.setToolTipText("Press to slide");
		panel_4.add(btnAuto);
		btnAuto.setBackground(new Color(204, 204, 204));
		btnAuto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAuto.setEnabled(false);
		btnAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				fraAction.doAuto(instance);
			}
		});
 
//		setButtonShape(this.btnAuto);

		textInterval = new JTextField(); 
		textInterval.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fraAction.addInterval(textInterval, e);
			}
		});
		textInterval.setForeground(Color.WHITE);
		textInterval.setHorizontalAlignment(SwingConstants.CENTER);
		textInterval.setBackground(SystemColor.windowBorder);
		panel_4.add(textInterval);
		textInterval.setText("1");
		textInterval.setColumns(2);
		textInterval.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		textInterval.setOpaque(false);
		this.frmFoxpic.addWindowListener(new WindowAdapter(){
			 public void windowClosing(final WindowEvent event)
			    { 
//				 myaction.saveIndex(currIndex);
			    }
			
		});
//		myaction.setMainWin(this);
		this.chckbxmntmOrderByDate.setSelected(true);
		
//		separator_3 = new JSeparator();
//		popupMenu.add(separator_3);
//		
//		mntmContinueLastv = new JMenuItem("Continue LastV");
//		popupMenu.add(mntmContinueLastv);
//		mntmContinueLastv.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				doContinue();
//
//			}
//		});
//		picPanel.requestFocus();
//		this.setTop(1);
		
		PathDragDropListener pdd = new PathDragDropListener();
		
		new DropTarget(frmFoxpic, pdd); 	
		
		MainWin.instance = this;
		this.fraAction.printHelp();


		popupMenu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		int indexmenu = 0;
		menuItem[indexmenu++] = this.mntmPick;
		
		menuItem[indexmenu++] = this.chckbxmntmHerizon; 
		menuItem[indexmenu++] = this.chckbxmntmOderBySize;
		menuItem[indexmenu++] = this.chckbxmntmOrderByDate;
		menuItem[indexmenu++] = this.chckbxmntmOrderByName;
		menuItem[indexmenu++] = this.chckbxmntmOrderByNone;
		menuItem[indexmenu++] = this.chckbxmntmPiP;
		menuItem[indexmenu++] = this.chckbxmntmThumb;
		menuItem[indexmenu++] = this.chckbxmntmVertical;
		menuItem[indexmenu++] = this.mntmAlwaysOnTop;
		for (JMenuItem menu : menuItem){
			this.fraAction.addPopMenuMouseEvent(menu); 
		}
		
		setSeparatorColor(this.separator);
		setSeparatorColor(this.separator_2);
		setSeparatorColor(this.separator_3);
		this.frmFoxpic.setUndecorated(true);
		
		this.fraAction.setMacIcon(this.frmFoxpic);
		
	}

	public FrameAction getFraAction() {
		return fraAction;
	}
	private void setSeparatorColor(JSeparator separator) {
		separator.setBackground(IPic.transWhite);
		separator.setForeground(Color.PINK);
	}


	private void setDefaultFolder(JFileChooser jfchooser2) {
		
		if (PicAction.ISMAC){
			jfchooser2.setCurrentDirectory(new File(PropertiesConfig
				.getConfig("FOLDER.CHOOSE")));
		}
	}


	protected void setImagePattern(int patternThumb) {
		this.fraAction.setImagePattern(patternThumb, this, fraAction.currIndex);
		
	}
/*
	protected void setTop(int onTop) {

		if (onTop == 0){
			frmFoxpic.setAlwaysOnTop(true);
			mntmAlwaysOnTop.setSelected(true);
			onTop = 1;
		} else {
			frmFoxpic.setAlwaysOnTop(false);
			mntmAlwaysOnTop.setSelected(false);
			onTop = 0;
			
		}
	}
*/
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				((MyJPopupMenu)popup).createScreenshot();
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

}
