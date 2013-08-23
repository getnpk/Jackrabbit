import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.util.Calendar;
import javax.jcr.Binary;
import javax.jcr.LoginException;
import javax.jcr.Repository; 
import javax.jcr.Session; 
import javax.jcr.SimpleCredentials; 
import javax.jcr.Node; 
import javax.jcr.PathNotFoundException;
import javax.jcr.PropertyIterator;
import org.apache.jackrabbit.core.TransientRepository; 
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.core.WorkspaceImpl;
import org.apache.jackrabbit.rmi.repository.URLRemoteRepository;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nitinkp
 */
public class Test {

    public static void main(String[] args) throws Exception{
    
        Repository repository = new URLRemoteRepository("http://localhost:10000/rmi");
        Session session = repository.login(new SimpleCredentials("admin","admin".toCharArray()));
        
        Node testNode = null;
        try {
            testNode = session.getNode("/test");
            testNode.remove();
        } catch (PathNotFoundException e) {}
        
        Node rootNode = session.getRootNode();
        testNode = rootNode.addNode("test");

        File f = new File("newsfile");
        Node newsNode = testNode.addNode(f.getName(), "nt:file");
        Node resourceNode = newsNode.addNode("jcr:content","nt:resource");
        resourceNode.setProperty("jcr:mimeType", "text/plain");
        Binary binary = session.getValueFactory().createBinary(new FileInputStream(f));
        resourceNode.setProperty("jcr:data", binary);

        f = new File("testfile");
        newsNode = testNode.addNode(f.getName(), "nt:file");
        resourceNode = newsNode.addNode("jcr:content", "nt:resource");
        resourceNode.setProperty("jcr:mimeType", "text/plain");
        binary = session.getValueFactory().createBinary(new FileInputStream(f));
        resourceNode.setProperty("jcr:data", binary);
        session.save();
        
    }
}
