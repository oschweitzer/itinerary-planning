package view;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * The Class MenuBar.
 */
public class MenuBar extends JMenuBar
{

	private static final long serialVersionUID = 1L;

	/** The help. */
	private JMenu fileMenu, viewMenu, help;
	
	/** The exit. */
	private JMenuItem fileExportItinerary, fileChangeMap, about, helpitem, exit; 
	
	/** The view anti aliasing. */
	private JCheckBoxMenuItem viewAntiAliasing;
	
	/**
	 * Instantiates a new menu bar.
	 */
	public MenuBar()
	{
		super();
		fileMenu = new JMenu("File");
	
		
		exit = new JMenuItem("Exit");
		fileExportItinerary = new JMenuItem("Export Itinerary");
		fileChangeMap = new JMenuItem("Change Map");		
		fileMenu.add(fileExportItinerary);
		fileMenu.add(fileChangeMap);
		fileMenu.add(exit);
		
		
		viewMenu = new JMenu("View");
		viewAntiAliasing = new JCheckBoxMenuItem("AntiAliasing");
		viewAntiAliasing.setState(true);
		viewMenu.add(viewAntiAliasing);
		
		help = new JMenu("?");
		helpitem = new JMenuItem("Help me !");
		about = new JMenuItem("About");
		help.add(helpitem);
		help.add(about);
		
		add(fileMenu);
		add(viewMenu);
		add(help);
	}
	
	/**
	 * Gets the export item.
	 *
	 * @return the export item
	 */
	public JMenuItem getExportItem(){
		return fileExportItinerary;
	}
	
	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public JMenuItem[] getItems(){
		return new JMenuItem[]{fileExportItinerary, fileChangeMap, viewAntiAliasing, about, helpitem, exit};
		
	}
}
