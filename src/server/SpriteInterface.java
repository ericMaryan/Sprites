/* File Name: SpriteInterface.java
 * Author Name: Eric Maryan
 * Date: 2017-03-04
 * Description: This interface defines the methods required for implementation of a sprite.
 */
package server;

import java.awt.Color;
import java.rmi.Remote;
import java.util.ArrayList;

/** This interface defines the methods required for implementation of a sprite.
 * 
 * @author Eric Maryan
 *
 */
public interface SpriteInterface extends Remote{

	/** Returns width of GUI window.
	 * 
	 * @return GUI width
	 * @throws java.rmi.RemoteException
	 */
	public int getWidth() throws java.rmi.RemoteException;
	

	/** Returns height of GUI window.
	 * 
	 * @return GUI height
	 * @throws java.rmi.RemoteException
	 */
	public int getHeight() throws java.rmi.RemoteException;
	
	/** Generates color for sprite. Color is set in sequence of RED -> BLUE -> GREEN.
	 * 
	 * @return color of sprite
	 * @throws java.rmi.RemoteException
	 */
	public Color decideColor() throws java.rmi.RemoteException;
	
	/** Creates a new sprite.
	 * 
	  @param x X coordinate
	 * @param y Y coordinate
	 * @param color Sprite color
	 * @throws java.rmi.RemoteException
	 */
	public void newSprite(int x, int y) throws java.rmi.RemoteException;
	
	/** Returns ArrayList of sprites
	 * 
	 * @return an ArrayList of sprites
	 * @throws java.rmi.RemoteException
	 */
	public ArrayList<Sprite> getSprites() throws java.rmi.RemoteException;

	/** Moves a sprite.
	 * 
	 * @throws java.rmi.RemoteException
	 */
	public void move() throws java.rmi.RemoteException;
}
