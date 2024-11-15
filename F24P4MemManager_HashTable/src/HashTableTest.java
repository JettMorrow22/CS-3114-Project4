import student.TestCase;


/**
 * test class for Hash
 * 
 * @author Jett Morrow & Adam Schantz
 * @version jettmorrow & adams03
 */
public class HashTableTest extends TestCase {
    private HashTable hashTable;
    private Record zero;
    private Record one;
    private Record five;
    private Record six;
    private Record seven;
    private Record nine;

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        hashTable = new HashTable(10);
        zero = new Record(0, null);
        one = new Record(1, null);
        five = new Record(5, null);
        six = new Record(6, null);
        seven = new Record(7, null);
        nine = new Record(9, null);
    }


    /**
     * method to test the quadratic probing with loop around
     * 
     * @throws Exception
     *             exception
     */
    public void testCollision() throws Exception {
        hashTable.insert(new Record(0, null)); // all these index 0
        hashTable.insert(new Record(10, null)); // all these index 0
        hashTable.insert(new Record(100, null));
        hashTable.insert(new Record(1000, null));
        hashTable.insert(new Record(10000, null));

        assertEquals(0, hashTable.getRecords()[0].getKey());
        assertEquals(10, hashTable.getRecords()[1].getKey());
        assertEquals(100, hashTable.getRecords()[3].getKey());
        assertEquals(1000, hashTable.getRecords()[6].getKey());
        assertEquals(10000, hashTable.getRecords()[5].getKey());
    }


    /**
     * Test cases for insert method
     * 
     * @throws Exception
     *             exception
     */
    public void testInsert() throws Exception {
        // inserting a key
        hashTable.insert(new Record(0, null)); // should be index 0
        assertEquals(0, hashTable.getRecords()[0].getKey());
        assertEquals(1, hashTable.getTableSize());

        hashTable.insert(new Record(1, null)); // should be index 1
        assertEquals(1, hashTable.getRecords()[1].getKey());
        assertEquals(2, hashTable.getTableSize());

        // inserting a duplicate
        hashTable.insert(new Record(0, null)); // should be index 0
        assertEquals(2, hashTable.getTableSize());

        // probing test
        hashTable.insert(new Record(10, null)); //at 3
        assertEquals(3, hashTable.getTableSize());
        assertEquals(10, hashTable.getRecords()[3].getKey());

        hashTable.insert(new Record(6, null)); // should be index 7
        assertEquals(6, hashTable.getRecords()[6].getKey());
        assertEquals(4, hashTable.getTableSize());
        assertEquals(10, hashTable.getTableCap());

        // check when adding to a tombstone
        hashTable.remove(10); // remove and add tombstone to 4
        assertTrue(hashTable.insert(new Record(3, null))); // try and
                                                                   // add to 4
        assertEquals(3, hashTable.getRecords()[3].getKey());
    }


    /**
     * test case for find method
     * 
     * @throws Exception
     *             exception
     */
    public void testFind() throws Exception {
        hashTable.insert(zero); //should be index 0
        hashTable.insert(one); // should be index 1
        hashTable.insert(new Record(10, null)); // prob to 3

        // in table
        assertEquals(0, hashTable.find(0).getKey());
        assertEquals(1, hashTable.find(1).getKey());
        assertEquals(10, hashTable.find(10).getKey());

        // not in table
        assertNull(hashTable.find(1000));
    }


    /**
     * test method to double check probing in find
     */
    public void testExtraFind() {
        hashTable.insert(zero); // should be index 0
        hashTable.insert(one); // should be index 1
        hashTable.insert(new Record(10, null)); // should be index 0, so
                                                       // prob to 1, then 4
        hashTable.insert(new Record(100, null)); // 0, 1, 3, 6

        assertEquals(100, hashTable.find(100).getKey());

    }


    /**
     * testcase for checkAndResize method without tombstones
     * 
     * @throws Exception
     *             exception
     */
    public void testCheckAndResize() throws Exception {
        hashTable.insert(zero); // should be index 0
        hashTable.insert(one); // should be index 1
        hashTable.insert(new Record(10, null)); // 0 , 1, 3

        assertFalse(hashTable.checkAndResize());
        hashTable.insert(seven); // should be index 7
        assertEquals(4, hashTable.getTableSize());
        assertEquals(10, hashTable.getTableCap());

        // if we were to add one more it would have to double in size
        hashTable.insert(five);
        assertTrue(hashTable.checkAndResize());
        assertEquals(20, hashTable.getTableCap());
        assertEquals(5, hashTable.getTableSize());
        // check if everything got rehashed correclty
        assertEquals(0, hashTable.getRecords()[0].getKey());
        assertEquals(1, hashTable.getRecords()[1].getKey());
        assertEquals(10, hashTable.getRecords()[10].getKey());
        assertEquals(7, hashTable.getRecords()[7].getKey());
        assertEquals(5, hashTable.getRecords()[5].getKey());
    }


    /**
     * test case for checkAndResize with tombstones and probing on second hash
     * 
     * @throws Exception
     *             exception
     */
    public void testCheckAndResizeTombstones() throws Exception {
        hashTable.insert(zero); // should be index 0
        hashTable.insert(one); // should be index 1
        hashTable.insert(new Record(10, null)); // home = 0, index 3

        // add some tombstones
        hashTable.insert(six);
        hashTable.remove(6); // TOMBSTONE @ 6

        // add records that will have to probe when rehashed
        hashTable.insert(new Record(11, null)); // home 1, prob 2
        hashTable.remove(11); // TOMBSTONE @ 2

        hashTable.insert(new Record(100, null)); // home 0, 1,3,6
        
        assertFalse(hashTable.checkAndResize());
        assertEquals(0, hashTable.getRecords()[0].getKey());
        assertEquals(1, hashTable.getRecords()[1].getKey());
        assertEquals(10, hashTable.getRecords()[3].getKey());
        assertEquals(100, hashTable.getRecords()[6].getKey());

        // check resizing with tombstones
        hashTable.insert(five); // index 5
        assertTrue(hashTable.checkAndResize());
        assertEquals(0, hashTable.getRecords()[0].getKey());
        assertEquals(1, hashTable.getRecords()[1].getKey());
        assertEquals(5, hashTable.getRecords()[5].getKey());
        assertEquals(10, hashTable.getRecords()[10].getKey());
        assertEquals(100, hashTable.getRecords()[3].getKey());
    }


    /**
     * test case for remove
     * 
     * @throws Exception
     *             exception
     */
    public void testRemove() throws Exception {
        hashTable.insert(zero); // should be index 0
        hashTable.insert(one); // should be index 1
        hashTable.insert(new Record(10, null)); // 0, 1, 3
        // remove home index, now tombstone at 1
        assertNotNull(hashTable.remove(1));
        assertEquals(-1, hashTable.getRecords()[1].getKey());
        assertEquals(2, hashTable.getTableSize());

        // remove something that is a tombstone now
        assertNull(hashTable.remove(1));

        // remove key that DNE
        assertNull(hashTable.remove(1000));
        assertEquals(2, hashTable.getTableSize());

        // remove Adamasa, tries 0 not it, prob 1 tombstone, checks 4 removes it
        assertNotNull(hashTable.remove(10));
        assertEquals(1, hashTable.getTableSize());
    }


    /**
     * test case for getRecords
     * 
     * @throws Exception
     *             exception
     */
    public void testGetRecords() throws Exception {
        hashTable.insert(zero); // should be index 0
        hashTable.insert(one); // should be index 1
        hashTable.insert(new Record(10, null)); // 0,1,3
        assertEquals(10, hashTable.getRecords().length);
    }


    /**
     * test case for getTableCap
     * 
     * @throws Exception
     *             exception
     */
    public void testGetTableCap() throws Exception {
        assertEquals(10, hashTable.getTableCap());
    }


    /**
     * test case for getTableSize
     * 
     * @throws Exception
     *             exception
     */
    public void testGetTableSize() throws Exception {
        hashTable.insert(zero); // should be index 0
        hashTable.insert(one); // should be index 1
        hashTable.insert(new Record(10, null)); // 0,1,3

        assertEquals(3, hashTable.getTableSize());
    }


    /**
     * method to test the Print method
     * 
     * @throws Exception
     *             exception
     */
    public void testPrint() throws Exception {
        hashTable.insert(zero); // should be index 0
        hashTable.insert(one); // should be index 1
        hashTable.insert(new Record(10, null)); // 0,1,3
        
        hashTable.remove(1);

        String one = "0: 0";
        String two = "1: TOMBSTONE";
        String three = "3: 10";

        String[] res = hashTable.printRecords();
        assertTrue(one.equals(res[0]));
        assertTrue(two.equals(res[1]));
        assertTrue(three.equals(res[3]));

    }


    /**
     * Check out the sfold method
     *
     * @throws Exception
     *             either a IOException or FileNotFoundException
     */
    public void testSfold() throws Exception {
        assertEquals(1, hashTable.hash(11, 10));
        assertEquals(4, hashTable.hash(4, 10));
        assertEquals(11, hashTable.hash(11, 20));
        assertEquals(1, hashTable.hash(21, 20));
        assertEquals(8, hashTable.hash(28, 20));
    }
}
