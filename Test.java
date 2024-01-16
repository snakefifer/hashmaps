import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Test {
    public static void main(String[] args) {

        TreeMap<String, String> tree = new TreeMap<>(new StringComparator());
        HashMapSC<String, String> hmsc = new HashMapSC<>(100000);
        HashMapQP<String, String> hmqp = new HashMapQP<>(100000);

        readFile(tree, hmsc, hmqp, "emails.txt");
        ArrayList<MapEntry<String,String>> mailList = new ArrayList<>();
        testGet(mailList, tree, hmsc, hmqp, "mailingList.txt");
        testCollide();

    }

    public static void readFile(TreeMap<String, String> treeMap, HashMapSC<String, String> hashMapSC, 
                                HashMapQP<String, String> hashMapQP, String filename) {
        File file = new File(filename);
        try {
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()) {
                String[] line = reader.nextLine().split(" ");
                String email = line[0];
                String password = line[1];
                treeMap.add(email, password);
                hashMapSC.put(email, password);
                hashMapQP.put(email, password);
    
            }
            reader.close();
        } catch(FileNotFoundException e) {
            System.out.println("File Not Found.");
            System.exit(0);
        }
    }

    public static void testGet(ArrayList<MapEntry<String,String>> MailList, TreeMap<String, String> treeMap, 
                                HashMapSC<String, String> hashMapSC, HashMapQP<String, String> hashMapQP, String filename) {
        
        File file = new File(filename);
        try {
            Scanner reader = new Scanner(file);
            int i = 0;
            while(reader.hasNextLine() && i < 100) {
                if(Math.random() < 0.5) {
                    String[] token = reader.nextLine().split(" ");
                    String email = token[0];
                    String password = token[1];
                    MapEntry<String, String> entry = new MapEntry<>(email, password);
                    MailList.add(entry);
                    i++;
                }
            }
            reader.close();
        } catch(FileNotFoundException e) {
            System.out.println("File Not Found.");
            System.exit(0);
        }
        System.out.println("Testing get()");
        System.out.printf("%-20s\t%-10s%-10s%-10s", "Username", "TreeMap", "HashMapSC", "HashMapQP");
        int tree = 0;
        int hmqp = 0;
        int hmsc = 0;
        for(int i = 0; i < 100; i++){
            MapEntry<String, String> entry = MailList.get(i);
            treeMap.getKey(entry.getKey());
            hashMapQP.get(entry.getKey());
            hashMapSC.get(entry.getKey());
            tree += TreeMap.iterations;
            hmqp += HashMapQP.iterations;
            hmsc += HashMapSC.iterations;
            if(i % 5 == 0)
                System.out.printf("\n%-20s\t%-10d%-10d%-10d", entry.getKey(), TreeMap.iterations, HashMapSC.iterations, HashMapQP.iterations);
        }
        System.out.printf("\n%-20s\t%-10d%-10d%-10d\n", "Average", tree / 100, hmsc / 100, hmqp / 100);

    }

    public static void testCollide() {
        System.out.println("\nTesting put() - number of collisions");
        System.out.printf("%-10s%-10s%-10s" ,"Size", "HashMapSC", "HashMapQP");
        for(int i = 50000; i <= 500000; i += 50000) {
            HashMapQP<String,String> hashMapQP = new HashMapQP<>(i);
            HashMapSC<String,String> hashMapSC = new HashMapSC<>(i);
            File file = new File("emails.txt");
            try {
                Scanner reader = new Scanner(file);
                int hmqp = 0;
                int hmsc = 0;
                while(reader.hasNextLine()){
                    String[] token = reader.nextLine().split(" ");
                    String email = token[0];
                    String password = token[1];
                    hashMapQP.put(email, password);
                    hashMapSC.put(email, password);
                    hmqp += HashMapQP.iterations;
                    hmsc += HashMapSC.iterations;
                }
                System.out.printf("\n%-10d%-10d%-10d", i, hmsc, hmqp);
                reader.close();
            } catch(FileNotFoundException e) {
                System.out.println("File Not Found.");
                System.exit(0);
            }
        }
    }
    
}

/**
 * Discuss the results you obtained for the get method in the three data structures.
 * TreeMap: the average of 22 iterations to find the key is not bad, it is an efficient data structure.
 * HashMapSC: an average of 1 iteration proves how efficient the hashmap is to search for a key.
 * HashMapQP: also an average of 1 iteration, extremely efficient for the get method.
 * Discuss the performance of the two implementations of the hash table based on the number of collisions.
 * The bigger the size of the list, the less collisions there were, and overall the HashMapSC had an average
 * of less collisions than the HashMap with quadratic probing.
 */