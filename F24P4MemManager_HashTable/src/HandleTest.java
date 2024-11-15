import student.TestCase;
/**
 *  test class for handle
 * 
 *  @author 14342
 *  @version Nov 14, 2024
 */
public class HandleTest extends TestCase
{
    //~ Fields ................................................................
    private Handle handle;
    //~ Constructors ..........................................................
    /**
     * setUp method for the test class
     */
    public void setUp()
    {
        handle = new Handle(0, 5);
    }
    //~Public  Methods ........................................................
    /**
     * test method for get and set position
     */
    public void testPosition()
    {
        assertEquals(handle.getPosition(), 0);
        handle.setPosition(1);
        assertEquals(handle.getPosition(), 1);
    }
    /**
     * test method for get and set length
     */
    public void testLength()
    {
        assertEquals(handle.getLength(), 5);
        handle.setLength(10);
        assertEquals(handle.getLength(), 10);
    }
}
