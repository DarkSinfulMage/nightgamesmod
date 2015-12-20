package nightgames.gui;

import nightgames.Resources.ResourceLoader;
import nightgames.actions.Action;
import nightgames.actions.Locate;
import nightgames.characters.Attribute;
import nightgames.characters.Character;
import nightgames.characters.Meter;
import nightgames.characters.Player;
import nightgames.characters.Trait;
import nightgames.combat.Combat;
import nightgames.combat.Encounter;
import nightgames.daytime.Activity;
import nightgames.daytime.Store;
import nightgames.debug.DebugGUIPanel;
import nightgames.global.DebugFlags;
import nightgames.global.Encs;
import nightgames.global.Flag;
import nightgames.global.Global;
import nightgames.global.Modifier;
import nightgames.global.Prematch;
import nightgames.items.Item;
import nightgames.items.clothing.Clothing;
import nightgames.skills.Skill;
import nightgames.trap.Trap;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.SwingConstants;
import javax.swing.UIManager;


public class GUI extends JFrame implements Observer {
        protected Combat combat;
	private Player player;
	private ArrayList<ArrayList<SkillButton>> skills;
	JPanel commandPanel;
	private JTextPane textPane;
	private JLabel stamina;
	private JLabel arousal;
	private JLabel mojo;
	private JLabel willpower;
	private JLabel lvl;
	private JLabel xp;
	private JProgressBar staminaBar;
	private JProgressBar arousalBar;
	private JProgressBar mojoBar;
	private JProgressBar willpowerBar;
	private JPanel topPanel;
	private JLabel loclbl;
	private JLabel timeLabel;
	private JLabel cashLabel;
	private Panel panel0;
	private CreationGUI creation;
	private JScrollPane textScroll;
	private JPanel mainpanel;
	private JToggleButton invbtn;
	private JToggleButton stsbtn;
	private JPanel statusPanel;
	private JPanel centerPanel;
	private JPanel clothesPanel;
	private JLabel clothesdisplay;
	private JPanel optionsPanel;
	private JPanel portraitPanel;
	private JLabel portrait;
        private JLabel map;
	private JPanel imgPanel;
	private JLabel imgLabel;
	private JRadioButton rdnormal;
	private JRadioButton rddumb;
	private JRadioButton rdeasy;
	private JRadioButton rdhard;
	private JRadioButton rdMsgOn;
	private JRadioButton rdMsgOff;
	private JRadioButton rdautosaveon;
	private JRadioButton rdautosaveoff;
	private JRadioButton rdporon;
	private JRadioButton rdporoff;
	private JRadioButton rdimgon;
	private JRadioButton rdimgoff;
	private JRadioButton rdfntnorm;
	private JRadioButton rdnfntlrg;
	private JSlider malePrefSlider;
	private int width;
	private int height;
	public int fontsize;
	private JMenuItem mntmQuitMatch;
	private boolean skippedFeat;
        
	public GUI() {
            
                //frame title
                this.setTitle("NightGames Mod Mod v0.1");
                
                //closing operation
                setDefaultCloseOperation(3);
                
                //resolution resolver
                
                height = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.85);
                width = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.85);
                
		setPreferredSize(new Dimension(width, height));
                
                
                //center the window on the monitor
                
                int y = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
                int x = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
                
                int x1 = (x / 2) - (width / 2);
                int y1 = (y / 2) - (height / 2);
                
                int centerX = (x1);
                int centerY = (y1);
                
                this.setLocation(centerX, centerY);
                
                //menu bar
                
		getContentPane().setLayout(new BoxLayout(getContentPane(), 1));
                
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
                
                //menu bar - new game
                
		JMenuItem mntmNewgame = new JMenuItem("New Game");
                
		mntmNewgame.setForeground(Color.WHITE);
		mntmNewgame.setBackground(new Color(35, 35, 35));
                mntmNewgame.setHorizontalAlignment(SwingConstants.CENTER);
                
		mntmNewgame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Global.inGame()) {
					int result = JOptionPane.showConfirmDialog(GUI.this,
							"Do you want to restart the game? You'll lose any unsaved progress.", "Start new game?",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if (result == JOptionPane.OK_OPTION) {
						Global.reset();
					}
				}
			}
		});
                
		menuBar.add(mntmNewgame);
                
                //menu bar - load game - can't change because can't figure out where the frame is with swing
                
		JMenuItem mntmLoad = new JMenuItem("Load");                     //Initializer
                
		mntmLoad.setForeground(Color.WHITE);                            //Formatting
		mntmLoad.setBackground(new Color(35, 35, 35));
                mntmLoad.setHorizontalAlignment(SwingConstants.CENTER);
                
		mntmLoad.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        Global.load();
                    }
		});
                
                menuBar.add(mntmLoad);
                
                //menu bar - options
                
		JMenuItem mntmOptions = new JMenuItem("Options");
		mntmOptions.setForeground(Color.WHITE);
		mntmOptions.setBackground(new Color(35, 35, 35));
                
		menuBar.add(mntmOptions);
                
                
                //options submenu creator
                
		optionsPanel = new JPanel();
		optionsPanel.setLayout(new GridLayout(0, 3, 0, 0));
                
                //AILabel - options submenu - visible
                
		JLabel AILabel = new JLabel("AI Mode");
		ButtonGroup ai = new ButtonGroup();
		rdnormal = new JRadioButton("Normal");
		rddumb = new JRadioButton("Old");
		ai.add(rdnormal);
		ai.add(rddumb);
		optionsPanel.add(AILabel);
		optionsPanel.add(rdnormal);
		optionsPanel.add(rddumb);

                //difficultyLabel - options submenu - visible
                
		JLabel difficultyLabel = new JLabel("Difficulty");
		ButtonGroup diff = new ButtonGroup();
		rdeasy = new JRadioButton("Normal");
		rdhard = new JRadioButton("Hard");
		diff.add(rdeasy);
		diff.add(rdhard);
		optionsPanel.add(difficultyLabel);
		optionsPanel.add(rdeasy);
		optionsPanel.add(rdhard);
                
                //systemMessageLabel - options submenu - visible
                
		JLabel systemMessageLabel = new JLabel("System Messages");
		ButtonGroup sysMsgG = new ButtonGroup();
		rdMsgOn = new JRadioButton("On");
		rdMsgOff = new JRadioButton("Off");
		sysMsgG.add(rdMsgOn);
		sysMsgG.add(rdMsgOff);
		optionsPanel.add(systemMessageLabel);
		optionsPanel.add(rdMsgOn);
		optionsPanel.add(rdMsgOff);

                //autosave - options submenu - visible -(not currently working?)
                
		JLabel lblauto = new JLabel("Autosave (saves to auto.sav)");
		ButtonGroup auto = new ButtonGroup();
		rdautosaveon = new JRadioButton("on");
		rdautosaveoff = new JRadioButton("off");
		auto.add(rdautosaveon);
		auto.add(rdautosaveoff);
		optionsPanel.add(lblauto);
		optionsPanel.add(rdautosaveon);
		optionsPanel.add(rdautosaveoff);
                
                //portraitsLabel - options submenu - visible
                
		JLabel portraitsLabel = new JLabel("Portraits");
                
                //portraits - options submenu - visible
                
		ButtonGroup portraitsButton = new ButtonGroup();
                
                //rdpron / rdporoff - options submenu - visible
                
		rdporon = new JRadioButton("on");
		rdporoff = new JRadioButton("off");
		portraitsButton.add(rdporon);
		portraitsButton.add(rdporoff);
		optionsPanel.add(portraitsLabel);
		optionsPanel.add(rdporon);
		optionsPanel.add(rdporoff);
                
                //imageLabel - options submenu - visible
		JLabel imageLabel = new JLabel("Images");
		ButtonGroup image = new ButtonGroup();
		rdimgon = new JRadioButton("on");
		rdimgoff = new JRadioButton("off");
		image.add(rdimgon);
		image.add(rdimgoff);
		optionsPanel.add(imageLabel);
		optionsPanel.add(rdimgon);
		optionsPanel.add(rdimgoff);
                
                //fontSizeLabel - options submenu - visible
		JLabel fontSizeLabel = new JLabel("Font Size");
		ButtonGroup size = new ButtonGroup();
		rdfntnorm = new JRadioButton("normal");
		rdnfntlrg = new JRadioButton("large");
		size.add(rdfntnorm);
		size.add(rdnfntlrg);
		optionsPanel.add(fontSizeLabel);
		optionsPanel.add(rdfntnorm);
		optionsPanel.add(rdnfntlrg);
                
                //m/f preference (no (other) males in the games yet... good for modders?)

                //malePrefLabel - options submenu - visible
		JLabel malePrefLabel = new JLabel("Female vs. Male Preference");
		optionsPanel.add(malePrefLabel);
		malePrefSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 1);
		malePrefSlider.setMajorTickSpacing(5);
		malePrefSlider.setMinorTickSpacing(1);
		malePrefSlider.setPaintTicks(true);
		malePrefSlider.setPaintLabels(true);
		malePrefSlider.setLabelTable(new Hashtable<Integer, JLabel>() {
			{
				put(0, new JLabel("Female"));
				put(5, new JLabel("Mixed"));
				put(10, new JLabel("Male"));
			}
		});
		malePrefSlider.setValue(Math.round(Global.getValue(Flag.malePref)));
		malePrefSlider.setToolTipText("This setting affects the gender your opponents will gravitate towards once that"
				+ " option becomes available.");
		malePrefSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Global.setCounter(Flag.malePref, malePrefSlider.getValue());
			}
		});
                
                //malePrefPanel - options submenu - visible
		optionsPanel.add(malePrefSlider);
		mntmOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Global.checkFlag(Flag.systemMessages)) {
					rdMsgOn.setSelected(true);
				} else {
					rdMsgOff.setSelected(true);
				}

				if (Global.checkFlag(Flag.hardmode)) {
					rdhard.setSelected(true);
				} else {
					rdeasy.setSelected(true);
				}

				if (Global.checkFlag(Flag.dumbmode)) {
					rddumb.setSelected(true);
				} else {
					rdnormal.setSelected(true);
				}
				if (Global.checkFlag(Flag.autosave)) {
					rdautosaveon.setSelected(true);
				} else {
					rdautosaveoff.setSelected(true);
				}
				if (Global.checkFlag(Flag.noportraits)) {
					rdporoff.setSelected(true);
				} else {
					rdporon.setSelected(true);
				}
				if (Global.checkFlag(Flag.noimage)) {
					rdimgoff.setSelected(true);
				} else {
					rdimgon.setSelected(true);
				}
				if (Global.checkFlag(Flag.largefonts)) {
					rdnfntlrg.setSelected(true);
				} else {
					rdfntnorm.setSelected(true);
				}
				malePrefSlider.setValue(Math.round(Global.getValue(Flag.malePref)));
				int result = JOptionPane.showConfirmDialog(GUI.this, optionsPanel, "Options",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (result == JOptionPane.OK_OPTION) {
					if (rdMsgOn.isSelected()) {
						Global.flag(Flag.systemMessages);
					} else {
						Global.unflag(Flag.systemMessages);
					}
					if (rdnormal.isSelected()) {
						Global.unflag(Flag.dumbmode);
					} else {
						Global.flag(Flag.dumbmode);
					}
					if (rdhard.isSelected()) {
						Global.flag(Flag.hardmode);
					} else {
						Global.unflag(Flag.hardmode);
					}
					if (rdautosaveoff.isSelected()) {
						Global.unflag(Flag.autosave);
					} else {
						Global.flag(Flag.autosave);
					}
					if (rdporon.isSelected()) {
						Global.unflag(Flag.noportraits);
					} else {
						Global.flag(Flag.noportraits);
						portraitPanel.remove(portrait);
						portraitPanel.repaint();
					}
					if (rdimgon.isSelected()) {
						Global.unflag(Flag.noimage);
					} else {
						Global.flag(Flag.noimage);
						if (imgLabel != null)
							imgPanel.remove(imgLabel);
						imgPanel.repaint();
					}
					if (rdnfntlrg.isSelected()) {
						Global.flag(Flag.largefonts);
						fontsize = 6;
					} else {
						Global.unflag(Flag.largefonts);
						fontsize = 5;
					}
				}
			}
		});
                
                //menu bar - credits
                
		JMenuItem mntmCredits = new JMenuItem("Credits");
		mntmCredits.setForeground(Color.WHITE);
		mntmCredits.setBackground(new Color(35, 35, 35));
		menuBar.add(mntmCredits);

                //menu bar - quit match
                
		mntmQuitMatch = new JMenuItem("Quit Match");
		mntmQuitMatch.setEnabled(false);
		mntmQuitMatch.setForeground(Color.WHITE);
		mntmQuitMatch.setBackground(new Color(35, 35, 35));
		mntmQuitMatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = JOptionPane.showConfirmDialog(GUI.this,
						"Do you want to quit for the night? Your opponents will continue to fight and gain exp.",
						"Retire early?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (result == JOptionPane.OK_OPTION) {
					Global.getMatch().quit();
				}
			}
		});
		menuBar.add(mntmQuitMatch);
		mntmCredits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel = new JPanel();
				panel.add (new JLabel("<html>Night Games created by The Silver Bard<br>"
						+ "Reyka and Samantha created by DNDW<br>"
	            		+ "Upgraded Strapon created by ElfBoyEni<br>"
						+ "Strapon victory scenes created by Legion<br>"
						+ "Advanced AI by Jos<br>"
						+ "Magic Training scenes by Legion<br>"
						+ "Jewel 2nd Victory scene by Legion<br>"
						+ "Video Games scenes 1-9 by Onyxdime<br>"
						+ "Kat Penetration Victory and Defeat scenes by Onyxdime<br>"
						+ "Kat Non-Penetration Draw scene by Onyxdime<br>"
						+ "Mara/Angel threesome scene by Onyxdime<br>"
						+ "Footfetish expansion scenes by Sakruff<br>"
						+ "Mod by Nergantre<br>"
						+ "A ton of testing by Bronzechair</html>"));
				Object[] options = { "OK", "DEBUG" };
				Object[] okOnly = { "OK" };
	            int results = JOptionPane.showOptionDialog(GUI.this, panel, "Credits",
				        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				        options, options[0]);
	            if (results == 1 && Global.inGame()) {
	            	JPanel debugPanel = new DebugGUIPanel();
	            	JOptionPane.showOptionDialog(GUI.this, debugPanel, "Debug", JOptionPane.PLAIN_MESSAGE,
	                        JOptionPane.INFORMATION_MESSAGE,
	                        null,
	                        okOnly,
	                        okOnly[0]);
	            } else if (results == 1) {
	            	JOptionPane.showOptionDialog(GUI.this, "Not in game", "Debug", JOptionPane.PLAIN_MESSAGE,
	                        JOptionPane.INFORMATION_MESSAGE,
	                        null,
	                        okOnly,
	                        okOnly[0]);
	            }
			}
		});
                
                //panel layouts
                
                
                //mainpanel - everything is contained within it
                
		this.mainpanel = new JPanel();
		getContentPane().add(this.mainpanel);
		this.mainpanel.setLayout(new BoxLayout(this.mainpanel, 1));
                
                //panel0 - invisible, only handles topPanel
                
		this.panel0 = new Panel();
		this.mainpanel.add(this.panel0);
		this.panel0.setLayout(new BoxLayout(this.panel0, 0));
                
                //topPanel - invisible, menus

		this.topPanel = new JPanel();
		this.panel0.add(this.topPanel);
		this.topPanel.setLayout(new GridLayout(0, 1, 0, 0));

                //centerPanel - invisible, body of GUI
                
		this.centerPanel = new JPanel();
		this.mainpanel.add(centerPanel);
		this.centerPanel.setLayout(new BorderLayout(0, 0));
                
                //statusPanel - visible, character status
                
		this.statusPanel = new JPanel();
		this.statusPanel.setLayout(new BoxLayout(this.statusPanel, 1));
                
                //clothesPanel - ??? (invisible & not added, probably to-do)
                
		this.clothesPanel = new JPanel();
		this.clothesPanel.setLayout(new GridLayout(0, 1));
		this.clothesPanel.setVisible(true);
		clothesPanel.setBackground(new Color(25, 25, 50));

                //portraitPanel - invisible, contains imgPanel, west panel
                
		portraitPanel = new JPanel();
		portraitPanel.setLayout(new BorderLayout(0, 0));
                if (width < 720){
                    portraitPanel.setSize(new Dimension(height, width/6));
                }
                
		portraitPanel.setBackground(new Color(0, 10, 30));
		portrait = new JLabel("");
		portrait.setVerticalAlignment(SwingConstants.TOP);
		portraitPanel.add(portrait, BorderLayout.WEST);
                
                //imgPanel - visible, contains imgLabel
                
		imgPanel = new JPanel();
		imgPanel.setLayout(new BorderLayout(0, 0));
		imgPanel.setBackground(new Color(0, 10, 30));                   //probably not doing anything

                
                if (width < 720){
                    portraitPanel.setSize(new Dimension(height, width/6));
                    System.out.println("Oh god so tiny");
                }
                //imgLabel - probably contains the portrait image (?)
                
		imgLabel = new JLabel("");
		imgPanel.add(imgLabel, BorderLayout.NORTH);
		portraitPanel.add(imgPanel, BorderLayout.CENTER);
                
                if (width < 720){
                    portraitPanel.setSize(new Dimension(height, width/6));
                    System.out.println("Oh god so tiny");
                }
                
                //textScroll
                
		this.textScroll = new JScrollPane();
		imgPanel.add(textScroll, BorderLayout.CENTER);
		centerPanel.add(portraitPanel, BorderLayout.CENTER);

                //textPane
                
		this.textPane = new JTextPane();
		DefaultCaret caret = (DefaultCaret) textPane.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		this.textPane.setForeground(new Color(240, 240, 255));
		this.textPane.setBackground(new Color(18, 30, 49));
		this.textPane.setPreferredSize(new Dimension(width, 400));
		this.textPane.setEditable(false);
		this.textPane.setContentType("text/html");
		this.textScroll.setViewportView(this.textPane);
		fontsize = 5;

		JButton debug = new JButton("Debug");
		debug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Global.getMatch().resume();
			}
		});

                //commandPanel - visible, contains the player's command buttons
                
		this.commandPanel = new JPanel();
		this.commandPanel.setBackground(new Color(0, 10, 30));
		this.commandPanel.setPreferredSize(new Dimension(this.width, 80));


		this.commandPanel.setBorder(new CompoundBorder());
		this.mainpanel.add(commandPanel);

		skills = new ArrayList<ArrayList<SkillButton>>();
		createCharacter();
		setVisible(true);
		pack();
	}

        //combat GUI
        
	public Combat beginCombat(Character player, Character enemy) {
                unloadMap();
		this.combat = new Combat(player, enemy, player.location());
		this.combat.addObserver(this);
		loadPortrait(combat, player, enemy);
		return this.combat;
	}

        //image loader
        
	public void displayImage(String path, String artist) {
            BufferedImage pic = null;
		try {
			pic = ImageIO.read(ResourceLoader.getFileResourceAsStream("assets/" + path));
		} catch (IOException localIOException9) {
		} catch (IllegalArgumentException e) {
		}
		clearImage();
		if (pic != null) {
			imgLabel = new JLabel(new ImageIcon(pic));
			imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
			imgPanel.add(imgLabel, BorderLayout.NORTH);
			imgLabel.setToolTipText(artist);
		}
		this.centerPanel.revalidate();
		this.portraitPanel.revalidate();
		this.portraitPanel.repaint();

	}

        //image unloader
        
	public void clearImage() {
		if (imgLabel != null) {
			imgPanel.remove(imgLabel);
			imgLabel = null;
		}
	}

        //image reloader (debug mode only)
        
	public void resetPortrait() {
		if (Global.isDebugOn(DebugFlags.DEBUG_IMAGES)) {
			System.out.println("Resetting Images\n");
		}
		portrait.setIcon(null);
		portraitPanel.remove(portrait);
	}

        //portrait loader
        
	public void loadPortrait(Combat c, Character player, Character enemy) {
		if (!Global.checkFlag(Flag.noimage) && !Global.checkFlag(Flag.noportraits)) {
			String imagepath = null;
			if (!player.human()) {
				imagepath = player.getPortrait(c);
			} else if (!enemy.human()) {
				imagepath = enemy.getPortrait(c);
			}
			if (imagepath != null) {
				BufferedImage face = null;
				try {
					face = ImageIO.read(ResourceLoader.getFileResourceAsStream("assets/" + imagepath));
				} catch (IOException localIOException9) {
				} catch (IllegalArgumentException badArg) {

				}
				if (face != null) {
					if (Global.isDebugOn(DebugFlags.DEBUG_IMAGES)) {
						System.out.println("Loading Portrait " + imagepath + " \n");
					}
					portrait.setIcon(null);
					portraitPanel.remove(portrait);
                                        
                                        if (width > 720){
                                            portrait = new JLabel(new ImageIcon(face));
                                            portrait.setVerticalAlignment(SwingConstants.TOP);
                                        }
                                        else
                                        {
                                            Image scaledFace = face.getScaledInstance(width/6, height/4, Image.SCALE_SMOOTH);
                                            portrait = new JLabel(new ImageIcon(scaledFace));
                                            portrait.setVerticalAlignment(SwingConstants.TOP);
                                            System.out.println("Portrait resizing active.");
                                        }
					portraitPanel.add(portrait, BorderLayout.WEST);
				}
			}
			this.centerPanel.revalidate();
			this.portraitPanel.revalidate();
			this.portraitPanel.repaint();
		}
	}
        
        //Map loader
        
        public void loadMap(){
            if(!Global.checkFlag(Flag.noimage))
            {
                BufferedImage mapPath = null;
                try{
                    mapPath = ImageIO.read(ResourceLoader.getFileResourceAsStream("assets//map.png"));
                } catch (IOException localIOException10) {
                } catch (IllegalArgumentException badArg) {
                    
                }
                
                if (mapPath != null)
                {
                    if (width > 720) {
                        map = new JLabel(new ImageIcon(mapPath));
                        map.setVerticalAlignment(SwingConstants.BOTTOM);
                    }
                    
                    else{
                        Image scaledMap = mapPath.getScaledInstance(width/6, height/4, Image.SCALE_SMOOTH);
                        map = new JLabel(new ImageIcon(scaledMap));
                        map.setVerticalAlignment(SwingConstants.BOTTOM);
                    }
                

                    if (width > 720) {
                        map = new JLabel(new ImageIcon(mapPath));
                        map.setVerticalAlignment(SwingConstants.BOTTOM);
                    }

                    else
                    {
                        Image scaledMap = mapPath.getScaledInstance(width/6, height/4, Image.SCALE_SMOOTH);
                        map = new JLabel(new ImageIcon(scaledMap));
                        map.setVerticalAlignment(SwingConstants.BOTTOM);
                    }
                
                    portraitPanel.add(map, BorderLayout.WEST);
                    
                }
                
            }
        }
        
        //Map unloader

        public void unloadMap(){
            if(!Global.checkFlag(Flag.noimage))
            {
                BufferedImage mapPath = null;
                try{
                    mapPath = ImageIO.read(ResourceLoader.getFileResourceAsStream("assets//map.png"));
                } catch (IOException localIOException10) {
                } catch (IllegalArgumentException badArg) {
                    
                }
                
                if (mapPath != null)
                    portraitPanel.remove(map);
            }
            
        }

        //Combat GUI
        
	public Combat beginCombat(Character player, Character enemy, int code) {
		unloadMap();
                this.combat = new Combat(player, enemy, player.location(), code);
		this.combat.addObserver(this);
		message(this.combat.getMessage());
		loadPortrait(combat, player, enemy);
		return this.combat;
	}

        //Combat spectate ???
        
	public void watchCombat(Combat c) {
		this.combat = c;
		this.combat.addObserver(this);
		loadPortrait(c, c.p1, c.p2);
	}

        
        //getLabelString - handles all the meters (bars)
        
	public String getLabelString(Meter meter) {
		if (meter.getOverflow() > 0) {
			return "(" + Integer.toString(meter.get() + meter.getOverflow()) + ")/" + meter.max();
		}
		return Integer.toString(meter.get()) + "/" + meter.max();
	}

        
        
	public void populatePlayer(Player player) {
		if (Global.checkFlag(Flag.largefonts)) {
			fontsize = 6;
		} else {
			fontsize = 5;
		}
		getContentPane().remove(this.creation);
		getContentPane().add(this.mainpanel);
		getContentPane().validate();
		this.player = player;
		player.gui = this;
		player.addObserver(this);
		JPanel meter = new JPanel();
		meter.setBackground(new Color(0, 10, 30));
		this.topPanel.add(meter);
		meter.setLayout(new GridLayout(0, 4, 0, 0));

		this.stamina = new JLabel("Stamina: " + getLabelString(player.getStamina()));
		this.stamina.setFont(new Font("Sylfaen", 1, 15));
		this.stamina.setHorizontalAlignment(0);
		this.stamina.setForeground(new Color(164, 8, 2));
		this.stamina.setToolTipText(
				"Stamina represents your endurance and ability to keep fighting. If it drops to zero, you'll be temporarily stunned.");
		meter.add(this.stamina);
                
		this.arousal = new JLabel("Arousal: " + getLabelString(player.getArousal()));
		this.arousal.setFont(new Font("Sylfaen", 1, 15));
		this.arousal.setHorizontalAlignment(0);
		this.arousal.setForeground(new Color(254, 1, 107));
		this.arousal.setToolTipText(
				"Arousal is raised when your opponent pleasures or seduces you. If it hits your max, you'll orgasm and lose the fight.");
		meter.add(this.arousal);

		this.mojo = new JLabel("Mojo: " + getLabelString(player.getMojo()));
		this.mojo.setFont(new Font("Sylfaen", 1, 15));
		this.mojo.setHorizontalAlignment(0);
		this.mojo.setForeground(new Color(51, 153, 255));
		this.mojo.setToolTipText(
				"Mojo is the abstract representation of your momentum and style. It increases with normal techniques and is used to power special moves");
		meter.add(this.mojo);
                
		this.willpower = new JLabel("Willpower: " + getLabelString(player.getWillpower()));
		this.willpower.setFont(new Font("Sylfaen", 1, 15));
		this.willpower.setHorizontalAlignment(0);
		this.willpower.setForeground(new Color(68, 170, 85));
		this.willpower
				.setToolTipText("Willpower is a representation of your will to fight. When this reaches 0, you lose.");
		meter.add(this.willpower);

		this.staminaBar = new JProgressBar();
		this.staminaBar.setBorder(new SoftBevelBorder(1, null, null, null, null));
		this.staminaBar.setForeground(new Color(164, 8, 2));
		this.staminaBar.setBackground(new Color(50, 50, 50));
		meter.add(this.staminaBar);
		this.staminaBar.setMaximum(player.getStamina().max());
		this.staminaBar.setValue(player.getStamina().get());

		this.arousalBar = new JProgressBar();
		this.arousalBar.setBorder(new SoftBevelBorder(1, null, null, null, null));
		this.arousalBar.setForeground(new Color(254, 1, 107));
		this.arousalBar.setBackground(new Color(50, 50, 50));
		meter.add(this.arousalBar);
		this.arousalBar.setMaximum(player.getArousal().max());
		this.arousalBar.setValue(player.getArousal().get());

		this.mojoBar = new JProgressBar();
		this.mojoBar.setBorder(new SoftBevelBorder(1, null, null, null, null));
		this.mojoBar.setForeground(new Color(51, 153, 255));
		this.mojoBar.setBackground(new Color(50, 50, 50));
		meter.add(this.mojoBar);
		this.mojoBar.setMaximum(player.getMojo().max());
		this.mojoBar.setValue(player.getMojo().get());

		this.willpowerBar = new JProgressBar();
		this.willpowerBar.setBorder(new SoftBevelBorder(1, null, null, null, null));
		this.willpowerBar.setForeground(new Color(68, 170, 85));
		this.willpowerBar.setBackground(new Color(50, 50, 50));
		meter.add(this.willpowerBar);
		this.willpowerBar.setMaximum(player.getWillpower().max());
		this.willpowerBar.setValue(player.getWillpower().get());

		JPanel bio = new JPanel();
		this.topPanel.add(bio);
		bio.setLayout(new GridLayout(2, 0, 0, 0));
		bio.setBackground(new Color(0, 10, 30));

		JLabel name = new JLabel(player.name());
		name.setHorizontalAlignment(2);
		name.setFont(new Font("Sylfaen", 1, 15));
		name.setForeground(new Color(240, 240, 255));
		bio.add(name);
		this.lvl = new JLabel("Lvl: " + player.getLevel());
		this.lvl.setFont(new Font("Sylfaen", 1, 15));
		this.lvl.setForeground(new Color(240, 240, 255));
                
		bio.add(this.lvl);
		this.xp = new JLabel("XP: " + player.getXP());
		this.xp.setFont(new Font("Sylfaen", 1, 15));
		this.xp.setForeground(new Color(240, 240, 255));
		bio.add(this.xp);
                
                UIManager.put("ToggleButton.select", new Color(75, 88, 102));
		this.stsbtn = new JToggleButton("Status");
		this.stsbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (GUI.this.stsbtn.isSelected()) {
					GUI.this.centerPanel.add(GUI.this.statusPanel, "East");
				} else {
					GUI.this.centerPanel.remove(GUI.this.statusPanel);
				}
				GUI.this.refresh();
				GUI.this.centerPanel.validate();
			}
		});
                bio.add(this.stsbtn);
		this.loclbl = new JLabel();
		this.loclbl.setFont(new Font("Sylfaen", 1, 16));
		this.loclbl.setForeground(new Color(240, 240, 255));
                
		this.stsbtn.setBackground(new Color(85, 98, 112));
                this.stsbtn.setForeground(new Color(240, 240, 255));
		bio.add(this.loclbl);

		this.timeLabel = new JLabel();
		this.timeLabel.setFont(new Font("Sylfaen", 1, 16));
		this.timeLabel.setForeground(new Color(240, 240, 255));
		bio.add(this.timeLabel);
		this.cashLabel = new JLabel();
		this.cashLabel.setFont(new Font("Sylfaen", 1, 16));
		this.cashLabel.setForeground(new Color(33, 180, 42));
		bio.add(this.cashLabel);
		removeClosetGUI();

		this.topPanel.validate();
	}

	public void createCharacter() {
		getContentPane().remove(this.mainpanel);
		this.creation = new CreationGUI();
		getContentPane().add(this.creation);
		getContentPane().validate();
	}

	public void purgePlayer() {
		getContentPane().remove(this.mainpanel);
		clearText();
		clearCommand();
		resetPortrait();
		clearImage();
		this.mntmQuitMatch.setEnabled(false);
		this.combat = null;
		this.topPanel.removeAll();
	}

	public void clearText() {
		this.textPane.setText("");
	}

	protected void clearTextIfNeeded() {
		int pos = textPane.getCaretPosition();
		textPane.setCaretPosition(textPane.getDocument().getLength());
		textPane.selectAll();
		int x = textPane.getSelectionEnd();
		textPane.select(x, x);
	}

	public void message(String text) {
		if (text.trim().length() == 0) {
			return;
		}

		HTMLDocument doc = (HTMLDocument) textPane.getDocument();
		HTMLEditorKit editorKit = (HTMLEditorKit) textPane.getEditorKit();
		try {
			editorKit.insertHTML(doc, doc.getLength(),
					"<font face='Georgia'><font color='white'><font size='" + fontsize + "'>" + text + "<br>", 0, 0,
					null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void combatMessage(String text) {

		HTMLDocument doc = (HTMLDocument) textPane.getDocument();
		HTMLEditorKit editorKit = (HTMLEditorKit) textPane.getEditorKit();
		try {
			editorKit.insertHTML(doc, doc.getLength(),
					"<font face='Georgia'><font color='white'><font size='" + fontsize + "'>" + text + "<br>", 0, 0,
					null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clearCommand() {
		skills.clear();
		this.commandPanel.removeAll();
		this.commandPanel.repaint();
	}

	public void addSkill(Skill action, Combat com) {
		int index = 0;
		boolean placed = false;
		while (!placed) {
			if (skills.size() <= index) {
				skills.add(new ArrayList<SkillButton>());
			}
			if (skills.get(index).size() >= 25) {
				index++;
			} else {
				skills.get(index).add(new SkillButton(action, com));
				placed = true;
			}
		}
	}

	public void showSkills(int index) {
		for (SkillButton button : skills.get(index)) {
			commandPanel.add(button);
		}
		if (index > 0) {
			commandPanel.add(new PageButton("<-", index - 1));
		}
		if (skills.size() > index + 1) {
			commandPanel.add(new PageButton("->", index + 1));
		}
		Global.getMatch().pause();
		this.commandPanel.repaint();
		this.commandPanel.revalidate();
	}

	public void addAction(Action action, Character user) {
		this.commandPanel.add(new ActionButton(action, user));
		Global.getMatch().pause();
		this.commandPanel.revalidate();
	}

	public void addActivity(Activity act) {
		this.commandPanel.add(new ActivityButton(act));
		this.commandPanel.revalidate();
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void next(Combat combat) {
		refresh();
		clearCommand();
		this.commandPanel.add(new NextButton(combat));
		Global.getMatch().pause();
		this.commandPanel.revalidate();
	}

	public void next(Activity event) {
		event.next();
		clearCommand();
		this.commandPanel.add(new EventButton(event, "Next"));
		this.commandPanel.revalidate();
	}

	public void choose(String choice) {
		this.commandPanel.add(new SceneButton(choice));
		this.commandPanel.revalidate();
	}

	public void choose(Activity event, String choice) {
		this.commandPanel.add(new EventButton(event, choice));
		this.commandPanel.revalidate();
	}

	public void choose(Action event, String choice, Character self) {
		this.commandPanel.add(new LocatorButton(event, choice, self));
		this.commandPanel.revalidate();
	}

	public void sale(Store shop, Item i) {
		this.commandPanel.add(new ItemButton(shop, i));
		this.commandPanel.revalidate();
	}

	public void sale(Store shop, Clothing i) {
		this.commandPanel.add(new ItemButton(shop, i));
		this.commandPanel.revalidate();
	}

	public void promptFF(Encounter enc, Character target) {
		clearCommand();
		this.commandPanel.add(new EncounterButton("Fight", enc, target, Encs.fight));
		this.commandPanel.add(new EncounterButton("Flee", enc, target, Encs.flee));
		Global.getMatch().pause();
		this.commandPanel.revalidate();
	}

	public void promptAmbush(Encounter enc, Character target) {
		clearCommand();
		this.commandPanel.add(new EncounterButton("Attack " + target.name(), enc, target, Encs.ambush));
		this.commandPanel.add(new EncounterButton("Wait", enc, target, Encs.wait));
		Global.getMatch().pause();
		this.commandPanel.revalidate();
	}

	public void promptOpportunity(Encounter enc, Character target, Trap trap) {
		clearCommand();
		this.commandPanel.add(new EncounterButton("Attack " + target.name(), enc, target, Encs.capitalize, trap));
		this.commandPanel.add(new EncounterButton("Wait", enc, target, Encs.wait));
		Global.getMatch().pause();
		this.commandPanel.revalidate();
	}

	public void promptShower(Encounter encounter, Character target) {
		clearCommand();
		this.commandPanel.add(new EncounterButton("Suprise Her", encounter, target, Encs.showerattack));
		if (!target.mostlyNude()) {
			this.commandPanel.add(new EncounterButton("Steal Clothes", encounter, target, Encs.stealclothes));
		}
		if (this.player.has(Item.Aphrodisiac)) {
			this.commandPanel.add(new EncounterButton("Use Aphrodisiac", encounter, target, Encs.aphrodisiactrick));
		}
		this.commandPanel.add(new EncounterButton("Do Nothing", encounter, target, Encs.wait));
		Global.getMatch().pause();
		this.commandPanel.revalidate();
	}

	public void promptIntervene(Encounter enc, Character p1, Character p2) {
		clearCommand();
		this.commandPanel.add(new InterveneButton(enc, p1));
		this.commandPanel.add(new InterveneButton(enc, p2));
		Global.getMatch().pause();
		this.commandPanel.revalidate();
	}

	public void prompt(String message, ArrayList<JButton> choices) {
		clearText();
		clearCommand();
		message(message);
		for (JButton button : choices) {
			this.commandPanel.add(button);
		}
		this.commandPanel.revalidate();
	}

	public void ding() {
		if (this.player.availableAttributePoints > 0) {
			message(this.player.availableAttributePoints + " Attribute Points remain.\n");
			clearCommand();
			for (Attribute att : this.player.att.keySet()) {
				if (Attribute.isTrainable(att, this.player) && this.player.getPure(att) > 0) {
					this.commandPanel.add(new AttributeButton(att));
				}
			}
			this.commandPanel.add(new AttributeButton(Attribute.Willpower));
			if (Global.getMatch() != null) {
				Global.getMatch().pause();
			}
			this.commandPanel.revalidate();
		} else if (player.traitPoints > 0 && !skippedFeat) {
			clearCommand();
			for (Trait feat : Global.getFeats(this.player)) {
				if (!player.has(feat)) {
					this.commandPanel.add(new FeatButton(feat));
				}
				this.commandPanel.revalidate();
			}
			this.commandPanel.add(new SkipFeatButton(null));
			this.commandPanel.revalidate();
		} else {
			skippedFeat = false;
			clearCommand();
			Global.gui().message(Global.gainSkills(this.player));
			if (this.combat != null) {
				endCombat();
			} else if (Global.getMatch() != null){
				Global.getMatch().resume();
			} else if (Global.day != null){
				Global.getDay().plan();
			} else {
				new Prematch(Global.human);
			}
		}
	}

	public void endCombat() {
		this.combat = null;
		clearText();
		resetPortrait();
		clearImage();
                loadMap();
		this.centerPanel.revalidate();
		this.portraitPanel.revalidate();
		this.portraitPanel.repaint();
		Global.getMatch().resume();
	}

        //Night match initializer
        
	public void startMatch() {
		this.mntmQuitMatch.setEnabled(true);
                loadMap();
	}

	public void endMatch() {
		clearCommand();
		resetPortrait();
                unloadMap();
		this.mntmQuitMatch.setEnabled(false);
		this.centerPanel.revalidate();
		this.portraitPanel.revalidate();
		this.portraitPanel.repaint();
		this.commandPanel.add(new SleepButton());
		this.commandPanel.add(new SaveButton());
		this.commandPanel.revalidate();
	}

	public void refresh() {
		this.stamina.setText("Stamina: " + getLabelString(player.getStamina()));
		this.arousal.setText(("Arousal: " + getLabelString(player.getArousal())));
		this.mojo.setText("Mojo: " + getLabelString(player.getMojo()));
		this.willpower.setText("Willpower: " + getLabelString(player.getWillpower()));
		this.lvl.setText("Lvl: " + this.player.getLevel());
		this.xp.setText("XP: " + this.player.getXP());
		this.staminaBar.setMaximum(this.player.getStamina().max());
		this.staminaBar.setValue(this.player.getStamina().get());
		this.arousalBar.setMaximum(this.player.getArousal().max());
		this.arousalBar.setValue(this.player.getArousal().get());
		this.mojoBar.setMaximum(this.player.getMojo().max());
		this.mojoBar.setValue(this.player.getMojo().get());
		this.willpowerBar.setMaximum(this.player.getWillpower().max());
		this.willpowerBar.setValue(this.player.getWillpower().get());
		this.loclbl.setText(this.player.location().name);
		this.cashLabel.setText("$" + this.player.money);
		if (Global.getMatch() != null) {                    
			this.timeLabel.setText(Global.getMatch().getTime() + " pm");
                        
                        if (Global.getMatch().getTime().equals("11"))
                        {
                            this.timeLabel.setText(Global.getMatch().getTime() + " am");
                        }
                        
                        this.timeLabel.setForeground(new Color(0, 51, 102));
		}
		if (Global.getDay() != null) {                                  //not updating correctly during daytime
                        this.timeLabel.setText(Global.getDay().getTime() + " pm");
			this.timeLabel.setForeground(new Color(253, 184, 19));
		}
		displayStatus();
	}

	public void displayStatus() {
		this.statusPanel.removeAll();
		this.statusPanel.repaint();
		this.statusPanel.setPreferredSize(new Dimension(400, centerPanel.getHeight()));
                
                if (width < 720){
                    this.statusPanel.setMaximumSize(new Dimension(height, width/6));
                    System.out.println("STATUS PANEL");
                }

		JPanel inventoryPanel = new JPanel();
                
		JPanel statsPanel = new JPanel();
                
		JPanel currentStatusPanel = new JPanel();
                
		statusPanel.add(inventoryPanel);
                
		JSeparator sep = new JSeparator();
		sep.setMaximumSize(new Dimension(statusPanel.getWidth(), 2));
		statusPanel.add(sep);
		statusPanel.add(statsPanel);
                
		sep = new JSeparator();
		sep.setMaximumSize(new Dimension(statusPanel.getWidth(), 2));

		statusPanel.add(sep);
		statusPanel.add(currentStatusPanel);
                sep = new JSeparator();
                sep.setMaximumSize(new Dimension(statusPanel.getWidth(), 2));
                
                currentStatusPanel.setBackground(new Color(0, 10, 30));
                statsPanel.setBackground(new Color(18, 30, 49));
                inventoryPanel.setBackground(new Color (18, 30, 49));
                
                if (width < 720){
                    inventoryPanel.setSize(new Dimension(height, width/6));
                    System.out.println("Oh god so tiny");
                }

		Map<Item, Integer> items = this.player.getInventory();
		int count = 0;

		ArrayList<JLabel> itmlbls = new ArrayList<JLabel>();
		for (Item i : items.keySet()) {
			if (items.get(i) > 0) {
                            
                            JLabel dirtyTrick = new JLabel(i.getName() + ": " + items.get(i) + "\n");
                            
                            dirtyTrick.setForeground(new Color(240, 240, 255));
                            
                            itmlbls.add(count, dirtyTrick);
                            
                            //itmlbls.add(count, new JLabel(i.getName() + ": " + items.get(i) + "\n"));
                                
				itmlbls.get(count).setToolTipText(i.getDesc());
				inventoryPanel.add(itmlbls.get(count));
				count++;
			}
		}

		count = 0;
		ArrayList<JLabel> attlbls = new ArrayList<JLabel>();
		for (Attribute a : Attribute.values()) {
			int amt = player.get(a);
			if (amt > 0) {
                            
                                JLabel dirtyTrick = new JLabel(a.name() + ": " + amt);
                                
                                dirtyTrick.setForeground(new Color(240, 240, 255));
                                
                                attlbls.add(count, dirtyTrick);
                                
				//attlbls.add(count, new JLabel(a.name() + ": " + amt)); //stats are gray due to this
				statsPanel.add(attlbls.get(count));
				count++;
			}
		}
		
                //statusText - body, clothing and status description
                
                JTextPane statusText = new JTextPane();
		DefaultCaret caret = (DefaultCaret) statusText.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		statusText.setBackground(new Color(18, 30, 49));
		statusText.setEditable(false);
		statusText.setContentType("text/html");
		statusText.setPreferredSize(new Dimension(400, centerPanel.getHeight() / 2));
		statusText.setMaximumSize(new Dimension(400, centerPanel.getHeight() / 2));
                if (width < 720){
                    statusText.setSize(new Dimension(height, width/6));
                }
		HTMLDocument doc = (HTMLDocument) statusText.getDocument();
		HTMLEditorKit editorKit = (HTMLEditorKit) statusText.getEditorKit();
		try {
			editorKit.insertHTML(doc,
					doc.getLength(), "<font face='Georgia'><font color='white'><font size='3'>"
							+ player.getOutfit().describe(player) + "<br>" + player.describeStatus() + "<br>",
					0, 0, null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                
		currentStatusPanel.add(statusText);
                if (width < 720){
                    currentStatusPanel.setSize(new Dimension(height, width/6));
                    System.out.println("Oh god so tiny");
                }
		this.centerPanel.revalidate();
		this.statusPanel.revalidate();
		this.statusPanel.repaint();
	}

	public void update(Observable arg0, Object arg1) {
		refresh();
		if (this.combat != null) {
			if (combat.combatMessageChanged) {
				combatMessage(this.combat.getMessage());
				combat.combatMessageChanged = false;
			}
			if (Global.getMatch() != null && (this.combat.phase == 0) || (this.combat.phase == 2)) {
				next(this.combat);
			}
		}
	}

	private class NextButton extends JButton {

		private static final long serialVersionUID = 6773730244369679822L;
		private Combat combat;

		public NextButton(Combat combat) {
			super();
			setFont(new Font("Baskerville Old Face", 0, 18));
			this.combat = combat;
			this.setText("Next");
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GUI.this.clearCommand();
					if (GUI.NextButton.this.combat.phase == 0) {
						GUI.NextButton.this.combat.clear();
						GUI.this.clearText();
						GUI.NextButton.this.combat.turn();
					} else if (GUI.NextButton.this.combat.phase == 2) {
						GUI.this.clearCommand();
						if (!GUI.NextButton.this.combat.end()) {
							GUI.this.endCombat();
						}
					}
				}
			});
		}
	}

	private class EventButton extends JButton {

		private static final long serialVersionUID = 7130158464211753531L;
		protected Activity event;
		protected String choice;

		public EventButton(Activity event, String choice) {
			super();
			setFont(new Font("Baskerville Old Face", 0, 18));
			this.event = event;
			this.choice = choice;
			this.setText(choice);
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GUI.EventButton.this.event.visit(GUI.EventButton.this.choice);
				}
			});
		}
	}

	private class ItemButton extends GUI.EventButton {

		private static final long serialVersionUID = 3200753975433797292L;

		public ItemButton(Activity event, Item i) {
			super(event, i.getName());
			setFont(new Font("Baskerville Old Face", 0, 18));
			setToolTipText(i.getDesc());
		}

		public ItemButton(Activity event, Clothing i) {
			super(event, i.getName());
			setFont(new Font("Baskerville Old Face", 0, 18));
			setToolTipText(i.getToolTip());
		}
	}

	private class AttributeButton extends JButton {
		private Attribute att;

		public AttributeButton(Attribute att) {
			super();
			setFont(new Font("Baskerville Old Face", 0, 18));
			this.att = att;
			this.setText(att.name());
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GUI.this.clearTextIfNeeded();
					GUI.this.player.mod(GUI.AttributeButton.this.att, 1);
					GUI.this.player.availableAttributePoints -= 1;
					GUI.this.refresh();
					GUI.this.ding();
				}
			});
		}
	}

	private class FeatButton extends JButton {
		private Trait feat;

		public FeatButton(Trait feat) {
			super();
			setFont(new Font("Baskerville Old Face", 0, 18));
			this.feat = feat;
			this.setText(feat.toString());
			setToolTipText(feat.getDesc());
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GUI.this.player.add(FeatButton.this.feat);
					GUI.this.clearTextIfNeeded();
					Global.gui().message("Gained feat: " + FeatButton.this.feat);
					Global.gui().message(Global.gainSkills(GUI.this.player));
					GUI.this.player.traitPoints -= 1;
					GUI.this.refresh();
					GUI.this.ding();
				}
			});
		}
	}

	private class SkipFeatButton extends JButton {
		public SkipFeatButton(Trait feat) {
			super();
			setFont(new Font("Baskerville Old Face", 0, 18));
			this.setText("Skip");
			setToolTipText("Save the trait point for later.");
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GUI.this.skippedFeat = true;
					GUI.this.clearTextIfNeeded();
					GUI.this.ding();
				}
			});
		}
	}

	private class InterveneButton extends JButton {
		private Encounter enc;
		private Character assist;

		public InterveneButton(Encounter enc, Character assist) {
			super();
			setFont(new Font("Baskerville Old Face", 0, 18));
			this.enc = enc;
			this.assist = assist;
			this.setText("Help " + assist.name());
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GUI.InterveneButton.this.enc.intrude(GUI.this.player, GUI.InterveneButton.this.assist);
				}
			});
		}
	}

	private class ActivityButton extends JButton {
		private Activity act;

		public ActivityButton(Activity act) {
			super();
			setFont(new Font("Baskerville Old Face", 0, 18));
			this.act = act;
			this.setText(act.toString());
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GUI.ActivityButton.this.act.visit("Start");
				}
			});
		}
	}

	private class SleepButton extends JButton {

		public SleepButton() {
			super();
			setFont(new Font("Baskerville Old Face", 0, 18));
			this.setText("Go to sleep");
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Global.dawn();
				}
			});
		}
	}

	private class MatchButton extends JButton {

		public MatchButton() {
			super();
			setFont(new Font("Baskerville Old Face", 0, 18));
			this.setText("Start the match");
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Global.dusk(Modifier.normal);
				}
			});
		}
	}

	private class LocatorButton extends JButton {

		public LocatorButton(final Action event, final String choice, final Character self) {
			super();
			setFont(new Font("Baskerville Old Face", 0, 18));
			this.setText(choice);
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					((Locate) event).handleEvent(self, choice);
				}
			});
		}
	}

	private class PageButton extends JButton {
		private int page;

		public PageButton(String label, int page) {
			super();
			setFont(new Font("Baskerville Old Face", 0, 18));
			this.setText(label);
			this.page = page;
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GUI.this.commandPanel.removeAll();
					GUI.this.showSkills(PageButton.this.page);
				}
			});
		}
	}

	public void changeClothes(Character player, Activity event, String backOption) {
		clothesPanel.removeAll();
		clothesPanel.add(new ClothesChangeGUI(player, event, backOption));
		centerPanel.remove(((BorderLayout) centerPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER));
		this.centerPanel.add(clothesPanel, BorderLayout.CENTER);
		this.clothesPanel.setVisible(true);
		this.clothesPanel.repaint();
		this.centerPanel.repaint();
		this.centerPanel.revalidate();
	}

	public void removeClosetGUI() {
		clothesPanel.removeAll();
		centerPanel.remove(((BorderLayout) centerPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER));
		this.centerPanel.add(portraitPanel, BorderLayout.CENTER);
		this.clothesPanel.setVisible(false);
		this.centerPanel.repaint();
		this.centerPanel.revalidate();
		displayStatus();
	}

	public void systemMessage(String string) {
		if (Global.checkFlag(Flag.systemMessages)) {
			message(string);
		}
	}
}
