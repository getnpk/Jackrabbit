import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import javax.jcr.Repository; 
import javax.jcr.Session; 
import javax.jcr.SimpleCredentials; 
import javax.jcr.Node; 
import javax.jcr.PropertyIterator;
import org.apache.jackrabbit.core.TransientRepository; 
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.core.WorkspaceImpl;
/** 
* Second hop example. Stores, retrieves, and removes example content. 
*/


public class ImportFile { 

    static boolean isRepositoryRunning(String repositoryHome) {
        File lock = new File(repositoryHome + "/lock.properties");
        if (lock.exists()) {
            lock.delete();
        }
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lock.exists();
    }
    
    public static void main(String[] args) throws Exception { 

    Repository repository = JcrUtils.getRepository("http://localhost:10000/rmi");
    
        //Repository repository = new TransientRepository(); 
    Session session = repository.login( new SimpleCredentials("admin", "admin".toCharArray())); 
    
    System.out.println(session.getWorkspace().getName());
        try { 
            
            
            Node root = session.getRootNode(); 
            File file = new File("testfile");
            
            if (! root.hasNode("testfile")){
                
                Node fileNode = root.addNode(file.getName(), "nt:file");

                //create the mandatory child node - jcr:content
                Node resNode = fileNode.addNode ("jcr:content", "nt:resource");
                resNode.setProperty ("jcr:mimeType", "text/plain"); 
                resNode.setProperty ("jcr:encoding", "UTF-8"); 
                resNode.setProperty ("jcr:data", new FileInputStream(file));
                Calendar lastModified = Calendar.getInstance ();
                lastModified.setTimeInMillis (file.lastModified ());
                resNode.setProperty ("jcr:lastModified", lastModified);

                session.save(); 
            }
            // Retrieve content 
            Node node = root.getNode(file.getName()); 
            System.out.println(node.getPath()); 
            //System.out.println(node.getProperty("").getString()); 
            PropertyIterator p = node.getProperties();
            while (p.hasNext()){
                System.out.println(p.nextProperty().getValue());
            }
            // Remove content 
            //root.getNode("hello").remove(); 
            session.save(); 

            } finally { 
                session.logout(); 
            } 
    } 

} 