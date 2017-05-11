/* File Name: SpriteServer.java
 * Author Name: Eric Maryan
 * Date: 2017-03-04
 * Description: This class handles the server end of the connection. 
 * Creates a registry and a remote reference.
 */
package server;

import java.rmi.registry.Registry;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/** This class handles the server end of the connection. 
 *  Creates a registry and a remote reference.
 * 
 * @author Eric Maryan
 *
 */
public class SpriteServer {
	
	/** The main method for the program. 
     * 
     * @param args Standard arguments for the main method
	 * @throws MalformedURLException 
     */
	public static void main(String[] args) throws MalformedURLException {
		
		// Port number
		int port = 8089;
		
		// Name used for remote reference
		String name = "spriteServer";
		
		// If port is specified
		if (args.length > 0){
			port = Integer.parseInt(args[0]);
		}
		
		try{
			SpriteImplementation sprite = new SpriteImplementation();
			Registry registry = LocateRegistry.createRegistry(port);
			registry.rebind(name, sprite);
			System.out.println ("Sprite Server is ready.");
		} catch(RemoteException exception){
			exception.printStackTrace();
		}
	}
}
