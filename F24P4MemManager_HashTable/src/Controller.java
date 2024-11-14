import java.io.PrintWriter;

/**
 * Controller class
 *
 * @author Jett Morrow jettmorrow Adam Schantz adams03
 * @version 11/12/2024
 */
public class Controller {

    private HashTable hashTable;
    // private memoryPool

    public Controller(int memorySize, int hashTableSize) {
        hashTable = new HashTable(hashTableSize);
    }


    public void insert(int id, Seminar seminar, PrintWriter output) {

        // the id already exists in the hash
        if (hashTable.find(id) != null) {
            output.println("Insert FAILED - There is already a record with ID "
                + id);
            return;
        }

        //id DNE so add too memory pool then Hash
        //create handle by adding to memoryPool
        Handle handle = new Handle(0, 0);
        
        if (hashTable.checkAndResize()) {
            output.println("Hash table expanded to " + hashTable.getTableCap() + " records");
        }
        
        hashTable.insert(new Record(id, handle));
        output.println("Successfully inserted record with ID " + id);
        output.println(seminar.toString());
    }
    
    
    public void delete(int id, PrintWriter output) {
        
    }
}
