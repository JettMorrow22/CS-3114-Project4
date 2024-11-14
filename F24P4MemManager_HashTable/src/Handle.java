/**
 * Handle Class
 *
 * @author Jett Morrow jettmorrow Adam Schantz adams03
 * @version 11/12/2024
 */
public class Handle {

    private int position;
    private int length;

    /**
     * Initializes Handle with param position and length in bytes
     * 
     * @param p
     *            position
     * @param l
     *            length
     */
    public Handle(int p, int l) {
        position = p;
        length = l;
    }


    /**
     * Constructor with no params
     */
    public Handle() {
        position = 0;
        length = 0;
    }


    /**
     * get position field
     * 
     * @return position
     */
    public int getPosition() {
        return position;
    }


    /**
     * get Length field
     * 
     * @return length
     */
    public int getLength() {
        return length;
    }


    /**
     * set position field to param value
     * 
     * @param position
     *            new value
     */
    public void setPosition(int position) {
        this.position = position;
    }


    /**
     * set length field to param value
     * 
     * @param length
     *            new value
     */
    public void setLength(int length) {
        this.length = length;
    }

}
