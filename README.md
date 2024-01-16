# Hashmaps

This Java program implements and compares two variations of HashMaps, namely `HashMapSC` (HashMap with Separate Chaining) and `HashMapQP` (HashMap with Quadratic Probing), along with a `TreeMap`. Additionally, it includes supporting classes such as `MapEntry`, `StringComparator`, and `TreeMap`.

## HashMapSC (HashMap with Separate Chaining)

The `HashMapSC` class employs separate chaining as a collision resolution technique. It uses linked lists to handle collisions, where each bucket maintains a linked list of key-value pairs that hash to the same index. The class provides methods for adding, getting, and removing entries, as well as functionalities to check size, clear, and convert to a list.

## HashMapQP (HashMap with Quadratic Probing)

The `HashMapQP` class utilizes quadratic probing to address collisions. Quadratic probing involves probing for the next available slot in the hash table by using a quadratic function to determine the offset. This implementation includes methods for adding, getting, and removing entries, as well as size checking, clearing, and conversion to a list.

## TreeMap

The `TreeMap` class implements a binary search tree (BST) with functionalities for adding, removing, and checking the presence of entries. The tree maintains order based on a specified comparator (in this case, `StringComparator`). The class also provides traversal methods such as inorder, preorder, and postorder.

## Supporting Classes

- `MapEntry`: Represents a key-value pair.
- `StringComparator`: Implements a comparator for string keys, considering only the part before the '@' symbol.
- `Test`: Contains a main method to demonstrate and compare the performance of `HashMapSC`, `HashMapQP`, and `TreeMap`. It reads input from files, tests the `get` method for each structure, and evaluates collision handling.

## Results and Discussion

The program outputs results and a discussion regarding the efficiency of the `get` method in each data structure. Notably, `HashMapSC` and `HashMapQP` show remarkable efficiency with an average of 1 iteration, while `TreeMap` exhibits reasonable performance with an average of 22 iterations.

Regarding collision handling, the program analyzes the number of collisions for different hash table sizes. Generally, larger hash table sizes result in fewer collisions. `HashMapSC` tends to have fewer collisions on average compared to `HashMapQP`.