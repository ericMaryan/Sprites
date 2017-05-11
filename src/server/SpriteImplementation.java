/* File Name: SpriteImplementation.java
 * Author Name: Eric Maryan
 * Date: 2017-03-04
 * Description: This class implements the SpriteInterface and handles the
 * methods required for the client's use.
 */

package server;

import java.awt.Color;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;

/** This class implements the SpriteInterface and handles the
 *  methods required for the client's use.
 * 
 * @author Eric Maryan
 */
public class SpriteImplementation extends UnicastRemoteObject implements SpriteInterface, Runnable {

	/** SerialVersionUID for serialization */
	private static final long serialVersionUID = 5300679392935215490L;

	/** An ArrayList of sprites */
	private ArrayList<Sprite> sprites;
	
	/** Width and height of GUI window */
	private int size = 500;
	
	/** A counter used for deciding the sprite's color*/
	private int i =0;
	
	/** A sessionFactory object */
	private SessionFactory factory;
	
	
	/** Default constructor. Contains setup for SessionFactory.
	 * 
	 * @throws RemoteException
	 */
	public SpriteImplementation() throws RemoteException {
		// A SessionFactory is set up once for an application!
    	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
    			.configure() // configures settings from hibernate.cfg.xml
    			.build();
    	try {
    		MetadataImplementor meta = (MetadataImplementor) new MetadataSources( registry ).addAnnotatedClass(Sprite.class).buildMetadata();
    		factory = meta.buildSessionFactory();
    	} catch (Exception exception) {
    		// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
    		// so destroy it manually.
    		StandardServiceRegistryBuilder.destroy( registry );
    		exception.printStackTrace();
    	}
    	
    	sprites = new ArrayList<Sprite>();
    	new Thread(this).start();
	}
	
	/** Generates color for sprite. Color is set in sequence of RED -> BLUE -> GREEN.
	 * 
	 * @return color of sprite
	 * @throws RemoteException
	 */
	@Override
	public synchronized Color decideColor() throws RemoteException {
		i++;
		
		// Sets color of sprite based on counter variable. Resets counter when last color is reached.
		
		if (i == 1){
			return Color.RED;  // Set color to red
		}
		else if (i == 2){
			return Color.BLUE; // Set color to blue
		}
		else if(i == 3){
			i = 0;
			return Color.GREEN; // Set color to green
		}
		else{
			return Color.RED; // Set color to red
		}
	}
	
	/** Creates a new sprite.
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param color Sprite color
	 * 
	 * @throws RemoteException
	 */
	@Override
	public void newSprite(int x, int y) throws RemoteException {
		Session session = factory.getCurrentSession();
		Sprite sprite = new Sprite(x, y, decideColor());
		try{
			// Create and save new sprite
			session.beginTransaction();
			session.save(sprite);
			sprites = (ArrayList<Sprite>) session.createQuery("from Sprite").list();
			session.getTransaction().commit();
		}catch(Exception exception){
			session.getTransaction().rollback();
			exception.printStackTrace();
		}
	}

	/** Moves a sprite.
	 * 
	 * @throws RemoteException
	 */
	@Override
	public void move() throws RemoteException {
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			for (Sprite sprite : sprites){
				// Call move function and update sprite
				sprite.move();
				session.update(sprite);
			}
			session.getTransaction().commit();
		} catch(Exception exception) {
			session.getTransaction().rollback();
			exception.printStackTrace();
		}
	}

	/** Run method from the Runnable interface.
	 * 
	 */
	@Override
	public void run() {
		while (true){
			try{
				// Move the sprites
				move();
			}catch(RemoteException exception){
				exception.printStackTrace();
			}
		}
	}
	
	/** Returns ArrayList of sprites
	 * 
	 * @return an ArrayList of sprites
	 * @throws RemoteException
	 */
	@Override
	public ArrayList<Sprite> getSprites() throws RemoteException{
		return sprites;
	}

	/** Returns width of GUI window.
	 * 
	 * @return GUI width
	 * @throws RemoteException
	 */
	@Override
	public int getWidth() throws RemoteException {
		return size;
	}

	/** Returns height of GUI window.
	 * 
	 * @return GUI height
	 * @throws RemoteException
	 */
	@Override
	public int getHeight() throws RemoteException {
		return size;
	}
}
