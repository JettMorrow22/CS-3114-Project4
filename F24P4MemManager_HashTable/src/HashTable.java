/**
 * HashTable from project 1
 *
 * @author Jett Morrow jettmorrow Adam Schantz adams03
 * @version 11/12/2024
 */
public class HashTable {

    private Record[] records;
    private int tableCap;
    private int tableSize;
    private Record tombstone;

    /**
     * Constructor for hash
     * initializes the Record[] with param size and tableCap and tableSize
     * 
     * @param hashSize
     *            the length of the Record[]
     */
    public HashTable(int hashSize) {
        records = new Record[hashSize];
        tableCap = hashSize;
        tableSize = 0;
        tombstone = new Record(-1, null);
    }


    /**
     * Attempts to insert key into the hash table
     * 
     * @param artist
     *            record to be added
     * @return true if key was added, false if not
     */
    public boolean insert(Record record) {

        int home = hash(record.getKey(), tableCap);
        int index = home;
        int i = 1;

        // find empty position
        // either index is empty, tombstone, cur Key, or random key
        while (records[index] != null) {

            // if we find tombstone remember it and check for dup
            if (records[index] == tombstone) {
                // if no dup then break and add it
                if (find(record.getKey()) == null) {
                    break;
                }
                else {
                    // dup return false
                    return false;
                }
            }
            else if (records[index].getKey() == record.getKey()) {
                // we at a duplicate
                return false;
            }

            // quadratic prob
            index = (home + probe(i)) % tableCap;
            i++;
        }

        // add the key to table and increment size
        records[index] = record;
        tableSize++;
        return true;
    }


    /**
     * Find function to determine if a key value is in the hashtable
     * 
     * @param key
     *            takes in a key value to search the hash
     * @return the record if found, or null if not found
     */
    public Record find(int id) {
        int home = hash(id, tableCap);
        int index = home;
        int i = 1;

        while (records[index] != null) {
            if (records[index].getKey()  == id) {
                return records[index];
            }

            index = (home + probe(i)) % tableCap;
            i++;
        }

        // was never found
        return null;
    }


    /**
     * method to determine if the table needs resizing before an insertion
     * if so it doubles the cap and rehashes everything except TOMBSTONE and
     * null
     * 
     * @return true if it needs to be resized and gets resized false if not
     */
    public boolean checkAndResize() {
        // check if we need to double records size then add it
        if (tableSize >= tableCap / 2) {
            // create new records array
            Record[] newRecords = new Record[tableCap * 2];

            // rehash all records, except tombstons from records into newRecords
            for (Record r : records) {
                // if tombstone or empty skip it
                if (r == null) {
                    continue;
                }

                if (r == tombstone) {
                    continue;
                }

                // if valid key, rehash and place in newRecords
                int home = hash(r.getKey(), tableCap * 2);
                int index = home;
                int i = 1;
                while (newRecords[index] != null) {
                    index = (home + probe(i)) % (tableCap * 2);
                    i++;
                }

                newRecords[index] = new Record(r.getKey(), r.getHandle());
            }

            // update records and tableCap
            records = newRecords;
            tableCap = tableCap * 2;
            return true;
        }
        return false;

    }


    /**
     * this method attempts to remove key from the hashTable
     * if found it replaces the record with TOMBSTONE and return true
     * if not return false
     * 
     * @param key
     *            the key to be searching for
     * @return record if found, null if not
     */
    public Record remove(int id) {
        // search for key in table, if it exists remove it
        int home = hash(id, tableCap);
        int index = home;
        int i = 1;
        while (records[index] != null) {
            if (records[index].getKey() == id) {
                Record temp = records[index];
                records[index] = tombstone;
                tableSize--;
                return temp;
            }
            index = (home + probe(i)) % tableCap;
            i++;
        }
        return null;
    }


    /**
     * Print method, prints each non null record as the index of their array
     * then the key
     * 
     * @return String[] to represent each record
     */
    public String[] printRecords() {
        // account for tombstones
        String[] res = new String[tableCap];

        for (int x = 0; x < tableCap; x++) {
            String line = "";

            if (records[x] == null) {
                continue;
            }
            else if (records[x] == tombstone) {
                line += x + ": TOMBSTONE";
            }
            else {
                line += x + ": " + records[x].getKey();
            }
            res[x] = line;
        }

        return res;
    }


    /**
     * Compute the hash function
     * 
     * @param id
     *            The id we are hashing
     * @return
     *         The hash function value (the home slot in the table for this key)
     */
    public int hash(int id, int hashSize) {
        return id % hashSize;
    }


    /**
     * returns the next probing index offset based on the count
     * 
     * @param i
     *            the current
     * @return the offset
     */
    public int probe(int i) {
        return (i * i + i) / 2;
    }


    /**
     * basic getter for records
     * 
     * @return records
     */
    public Record[] getRecords() {
        return records;
    }


    /**
     * basic getter for tableCap
     * 
     * @return tableCap
     */
    public int getTableCap() {
        return tableCap;
    }


    /**
     * basic getter for tableSize
     * 
     * @return tableSize
     */
    public int getTableSize() {
        return tableSize;
    }
}
