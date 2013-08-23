/**
 *
 * @author nitinkp
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jcr.LoginException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session; 
import org.apache.jackrabbit.core.TransientRepository; 

//Logs in to a content repository and prints a status message.
public class FirstHop {

    public static void main(String[] args){
    
        Repository repo = new TransientRepository();
        Session session = null;
        try {
            session = repo.login();
            String user = session.getUserID(); 
            String name = repo.getDescriptor(Repository.REP_NAME_DESC); 
            System.out.println("Logged in as " + user + " into Repo " + name);
        } catch (LoginException ex) {
            Logger.getLogger(FirstHop.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RepositoryException ex) {
            Logger.getLogger(FirstHop.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            session.logout();
        }
        
    }
}
