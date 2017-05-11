/* File Name: SpriteClient.java
 * Author Name: Eric Maryan
 * Date: 2017-03-04
 * Description: This class handles the client end of the connection and allows 
 * a user to create sprites by clicking anywhere in the GUI.
 */
package client;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import server.Sprite;
import server.SpriteInterface;

/** This class handles the client end of the connection and allows 
 *  a user to create sprites by clicking anywhere in the GUI.
 * 
 * @author Eric Maryan
 *
 */
public class SpriteClient extends JPanel{
	
	/** SerialVersionUID for serialization */
	private static final long serialVersionUID = 3275063674206175989L;
	
	/** Instance of SpriteInterface*/
	private SpriteInterface sprInt;
	
	/** The JFrame for the program */
    private JFrame frame;
    
    /** Port used to connect to server*/
    private static int port = 8089;
    
    /** Host of server*/
	private static String host = "localhost";
	
	/** Registry object */
	private Registry registry;
   
    /** The default constructor. Prepares the remote reference and other attributes. 
     *
     * @throws RemoteException
     * @throws NotBoundException 
     */
    public SpriteClient() throws RemoteException, NotBoundException {
    	registry = LocateRegistry.getRegistry(host, port);
 	    sprInt = (SpriteInterface) registry.lookup ("spriteServer");
        addMouseListener(new Mouse());
        initGUI();
    }
    
    /** Initializes the GUI portion of the client
     * 
     * @throws RemoteException
     */
    public void initGUI() throws RemoteException{
    	frame = new JFrame("Assignment 3: RMI & Hibernate");
        frame.setSize(sprInt.getWidth(), sprInt.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
    }
    
    /** Controls the animation for the program.
	 * 
	 */
	public void animate(){
	    while (true){
	    	// Continuously redraw the sprite
	    	repaint(); 
	    	try{
	    		Thread.sleep(40);
	    	}catch(InterruptedException exception){
	    		exception.printStackTrace();
	    	}
	    }
	}
	
	/** Draws the sprites to the GUI screen.
	 * 
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		// Draw each sprite in the sprite array list
		try {
			ArrayList<Sprite> arraySpriteDB = sprInt.getSprites();
			for(Sprite sprite: arraySpriteDB){
				sprite.draw(g);
			}
		} catch (RemoteException exception) {
			exception.printStackTrace();
		}
	}
	
	/** Inner class used to handle mouse events
	 * 
	 * @author Eric Maryan
	 *
	 */
	private class Mouse extends MouseAdapter {
		@Override
		public void mousePressed( final MouseEvent event ){
	        try {
	        	// Create new sprite at the coordinates of the mouse event.
				sprInt.newSprite(event.getX(), event.getY()); 
			} catch (RemoteException exception) {
				exception.printStackTrace();
			}
	    }
	}
	
    /** The main method for the program. 
     * 
     * @param args Standard arguments for the main method
     */
    public static void main(String[] args) {
    	
    	// Switch case handles port and host specifications on the command line
    	switch (args.length) {
		case 0:
			break;
		case 1: 
			host = args[0];
			break;
		case 2:
			host = args[0];
			port = Integer.parseInt(args[1]);
			break;
		default:
			System.out.println("usage: SpriteClient [hostname [portnum]]");
			break;
		}
    	
    	try {
    		SpriteClient sprCli = new SpriteClient();
    		sprCli.animate();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
    }
}