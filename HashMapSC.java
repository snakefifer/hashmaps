import java.util.ArrayList;
import java.util.LinkedList;

public class HashMapSC<K extends Comparable <K>, V extends Comparable <V>> {

    private int size;
	private double loadFactor;
	private LinkedList<MapEntry<K,V>>[] hashTable;
	public static int iterations;

	public HashMapSC() {
		this(100, 0.9);
	}
  
	public HashMapSC(int c) {
		this(c, 0.9);
	}
	
	public HashMapSC(int c, double lf) {
		hashTable = new LinkedList[trimToPowerOf2(c)];
		loadFactor = lf;
		size = 0;
	}
	
	private int trimToPowerOf2(int c) {
		int capacity = 1;
		while (capacity < c)
			capacity  = capacity << 1;
		return capacity;
	}
	
	private int hash(int hashCode) {
		return hashCode & (hashTable.length-1);
	}
	
	private void rehash() {
		ArrayList<MapEntry<K,V>> list = toList();
		hashTable = new LinkedList[hashTable.length << 1];
		size = 0;
		for(MapEntry<K,V> entry: list)
			put(entry.getKey(), entry.getValue());
	}
	
	public int size() {
		return size;
	}
	
	public void clear() {
		size = 0;
		for(int i = 0; i < hashTable.length; i++)
			if(hashTable[i] != null)
				hashTable[i].clear();
	}
	
	public boolean isEmpty() {
		return (size == 0);
	}
	
	public boolean containsKey(K key) {
		if(get(key) != null)
			return true;
		return false;
	}
	
	public V get(K key) {
		iterations = 0;
		int HTIndex = hash(key.hashCode());
		if(hashTable[HTIndex] != null) {
			LinkedList<MapEntry<K,V>> ll = hashTable[HTIndex];
			for(MapEntry<K,V> entry: ll) {
				iterations++;
				if(entry.getKey().equals(key))
					return entry.getValue();
			}
		}
		return null;
	}
	
	public V put(K key, V value) {
		iterations = 0;
		if(get(key) != null) {
			int HTIndex = hash(key.hashCode());
			LinkedList<MapEntry<K,V>> ll;
			ll = hashTable[HTIndex];
			for(MapEntry<K,V> entry: ll) {
				iterations++;
				if(entry.getKey().equals(key)) {
					V old = entry.getValue();
					entry.setValue(value);
					return old;
				}
			}
		}
		if(size >= hashTable.length * loadFactor)
			rehash();
		int HTIndex = hash(key.hashCode());
		if(hashTable[HTIndex] == null){
			hashTable[HTIndex] = new LinkedList<>();
		}
		hashTable[HTIndex].add(new MapEntry<>(key, value));
		size++;
		return value;
	}

	public ArrayList<MapEntry<K,V>> toList(){
		ArrayList<MapEntry<K,V>> list = new ArrayList<>();
		for(int i = 0; i < hashTable.length; i++) {
			if(hashTable[i] != null) {
				LinkedList<MapEntry<K,V>> ll = hashTable[i];
				for(MapEntry<K,V> entry: ll)
					list.add(entry);
			}
		} 
		return list;
	}
	
	public String toString() {
		String out = "[";
		for(int i = 0; i < hashTable.length; i++) {
			if(hashTable[i] != null) {
				for(MapEntry<K,V> entry: hashTable[i])
					out += entry.toString();
				out += "\n";
			}
		}
		out += "]";
		return out;
	}

}