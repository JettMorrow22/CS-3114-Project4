import student.TestCase;
public class RecordTest extends TestCase
{
    //~ Fields ................................................................
    private Record rec;
    private Handle handle;
    //~ Constructors ..........................................................
    /**
     * setUp method for the test class
     */
    public void setUp()
    {
        handle = new Handle(0, 5);
        rec = new Record(1, handle);
    }
    //~Public  Methods ........................................................
    /**
     * test method for get key and handle
     */
    public void testGet()
    {
        assertEquals(rec.getKey(), 1);
        assertEquals(rec.getHandle().getPosition(), 0);
        assertEquals(rec.getHandle().getLength(), 5);
    }
    /**
     * test method for setHandle
     */
    public void testSetHandle()
    {
        Handle newHand = new Handle(1, 10);
        rec.setHandle(newHand);
        assertEquals(rec.getHandle().getPosition(), 1);
        assertEquals(rec.getHandle().getLength(), 10);
    }
}
