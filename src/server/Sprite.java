/* File Name: Sprite.java
 * Author Name: Eric Maryan
 * Date: 2017-03-04
 * Description: This class represents a Sprite and also sets its rules for movement.
 * This class also implements Hibernate in order to add Sprites to a database.
 */
package server;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** This class represents a Sprite and also sets its rules for movement.
 *  This class also implements Hibernate in order to add Sprites to a database.
 * 
 * @author Eric Maryan
 */
@Entity
@Table(name="Sprites")
public class Sprite implements Serializable{

	/** ID used for the sprite in the database */
	private int ID;
	
	/** Generates a random number */
	private final static Random random = new Random();
	
	/** Size of the sprite */
	private final static int SIZE = 10;
	
	/** Maximum speed of the sprite */
	private final static int MAX_SPEED = 5;
	
	/** X coordinate */
	private int x;
	
	/** Y coordinate */
	private int y;
	
	/** Direction sprite is moving on x-axis */
	private int dx;
	
	/** Direction sprite is moving on y-axis */
	private int dy;
	
	/** Color of the sprite */
	private Color color;	
	
	/** SerialVersionUID for serialization */
	private static final long serialVersionUID = -2286665675514446611L;
	
	/** Width and height of GUI window */
	private int windowSize = 500;

	/** Empty default constructor.
	 * 
	 */
	public Sprite(){}
	
	
	/** Parameterized constructor. Assigns coordinates and other attributes for sprites. 
	 * 
	 * @param x The sprite's x coordinate
	 * @param y The sprite's y coordinate
	 * @param color The sprite's color
	 *
	 * @throws RemoteException 
	 */
    public Sprite (int x, int y, Color color) throws RemoteException
    {
    	setX(x);
    	setY(y);
    	setColor(color);
        setDX(random.nextInt(2*MAX_SPEED) - MAX_SPEED);
        setDY(random.nextInt(2*MAX_SPEED) - MAX_SPEED);
    }
	
    /** Draws the sprite
     * 
     * @param g A Graphics object
     */
    public void draw(Graphics g){
        g.setColor(color);
	    g.fillOval(x, y, SIZE, SIZE);
    }
	
	  /** Controls the movement for the sprite
	 * 
     * @throws RemoteException 
     */
    public void move() throws RemoteException{

        // check for bounce and make the ball bounce if necessary
        
        if (x < 0 && dx < 0){
            //bounce off the left wall 
            x = 0;
            dx = -dx;
        }
        if (y < 0 && dy < 0){
            //bounce off the top wall
            y = 0;
            dy = -dy;
        }
        if (x > windowSize - SIZE && dx > 0){
            //bounce off the right wall
        	x = windowSize - SIZE;
        	dx = - dx;
        }       
        if (y > windowSize - SIZE && dy > 0){
            //bounce off the bottom wall
        	y = windowSize - SIZE;
        	dy = -dy;
        }

        //make the ball move
        x += dx;
        y += dy;
    }
    
    /** Returns color of the sprite
     * 
     * @return sprite color
     */
    public Color getColor(){
    	return this.color;
    }
    
    /** Returns sprite's ID
     * 
     * @return sprite's ID
     */
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getID(){
    	return this.ID;
    }

    /** Returns x coordinate
     * 
     * @return x coordinate
     */
    public int getX(){
    	return this.x;
    }
    
    /** Returns y coordinate
     * 
     * @return y coordinate
     */
    public int getY(){
    	return this.y;
    }
    
    /** Returns dx coordinate
     * 
     * @return dx coordinate
     */
    public int getDX(){
    	return this.dx;
    }
    
    /** Returns dy coordinate
     * 
     * @return dy coordinate
     */
    public int getDY(){
    	return this.dy;
    }
    
    /** Set sprite color
     * 
     * @param color sprite's color
     */
    public void setColor(Color color){
    	this.color = color;
    }
    
    /** Set sprite's ID
     * 
     * @param ID sprite's ID
     */
    public void setID(int ID){
    	this.ID = ID;
    }
    
    /** Set x coordinate
     * 
     * @param x x coordinate
     */
    public void setX(int x){
    	this.x = x;
    }
    
    /** Set y coordinate
     * 
     * @param y y coordinate
     */
    public void setY(int y){
    	this.y = y;
    }
    
    /** Set dx coordinate
     * 
     * @param dx dx coordinate
     */
    public void setDX(int dx){
    	this.dx = dx;
    }
     
    /** Set dy coordinate
     * 
     * @param dy dy coordinate
     */
    public void setDY(int dy){
    	this.dy = dy;
    }
}
