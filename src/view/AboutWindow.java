package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AboutWindow extends JFrame
{

	/**
	 * The class AboutWindow
	 */
	
	private static final long serialVersionUID = 1L;
	
	/** The JPanel of the AboutWindow */
	private JPanel jpAbout;
	
	/** The JLabel */
	private JLabel info;
	
		/**
		 * Instantiates a new About window.
		 *
		 */
		public AboutWindow()
		{
			super("About");
			
			String nativeLF = UIManager.getSystemLookAndFeelClassName();
			try {
				UIManager.setLookAndFeel(nativeLF);
			} 
			catch (InstantiationException e) {}
			catch (ClassNotFoundException e) {}
			catch (UnsupportedLookAndFeelException e) {}
			catch (IllegalAccessException e) {}
			
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			int w = this.getSize().width;
	        int h = this.getSize().height;
	        int x = (dim.width-w)/2;
	        int y = (dim.height-h)/2;
	        
			this.setLocation(x, y);
			
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			jpAbout = new JPanel();
		
			//Use of HTML to break lines.
			info = new JLabel("<html><body>Application created for the LO43 course by Max Etter & Olivier Schweitzer during the spring semester 2014 <br><br> Copyright © 2014</body></html>");
			
			jpAbout.add(info);

			add(jpAbout);
			
			this.pack();
		}
}
