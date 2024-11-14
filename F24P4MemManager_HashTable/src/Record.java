/**
 * Record class
 *
 * @author Jett Morrow jettmorrow Adam Schantz adams03
 * @version 11/12/2024
 */
public class Record {

    private int key;
    private Handle handle;

    /**
     * Constructor for Record
     * key is ID of seminar and handle is handle of sem in memory pool
     * 
     * @param k
     *            key
     * @param h
     *            handle
     */
    public Record(int k, Handle h) {
        key = k;
        handle = h;
    }


    /**
     * getter for Key field
     * 
     * @return key
     */
    public int getKey() {
        return key;
    }


    /**
     * Getter for Handle field
     * 
     * @return handle
     */
    public Handle getHandle() {
        return handle;
    }


    /**
     * setter for handle field
     * 
     * @param handle
     *            new value
     */
    public void setHandle(Handle handle) {
        this.handle = handle;
    }

}
