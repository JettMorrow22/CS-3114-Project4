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

        // id DNE so add too memory pool then Hash
        // create handle by adding to memoryPool
        Handle handle = new Handle(0, 0);

        if (hashTable.checkAndResize()) {
            output.println("Hash table expanded to " + hashTable.getTableCap()
                + " records");
        }

        hashTable.insert(new Record(id, handle));
        output.println("Successfully inserted record with ID " + id);
        output.println(seminar.toString());
    }


    // TODO we have to remove the record from the memory pool and update
    // freeBlock list
    public void delete(int id, PrintWriter output) {
        Record removedRecord = hashTable.remove(id);
        // record was not found
        if (removedRecord == null) {
            output.println("Delete FAILED -- There is no record with ID " + id);
        }
        else { // record was found
            output.println("Record with ID " + id
                + " successfully deleted from the database");

            // TODO
            // we need to remove the stuff from memory pool and free block list
        }
    }


    // TODO
    public void search(int id, PrintWriter output) {
        Record foundRecord = hashTable.find(id);
        // record not in hashTable
        if (foundRecord == null) {
            output.println("Search FAILED -- There is no record with ID " + id);
        }
        else {
            output.println("Found record with ID " + id + ":");
            // WE NEED TO USE THE HANDLE TO GRAB THE SEMINAR FROM THE MEMORY
            // POOL TO PRINT
        }
    }
}
