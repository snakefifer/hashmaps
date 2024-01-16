import java.util.ArrayList;

public class HashMapQP<K extends Comparable <K>, V extends Comparable <V>> {

    private int size;
	private double loadFactor;
	private MapEntry<K,V>[] hashTable;
	public static int iterations;

	public HashMapQP() {
		this(100, 0.5);
	}
  
	public HashMapQP(int c) {
		this(c, 0.5);
	}
	
	public HashMapQP(int c, double lf) {
		hashTable = new MapEntry[trimToPowerOf2(c)];
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
		return hashCode & (hashTable.length - 1);
	}
	
	private void rehash() {
		ArrayList<MapEntry<K,V>> list = toList();
		hashTable = new MapEntry[hashTable.length << 1];
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
			hashTable[i] = null;
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
		int i = 0;
		while(hashTable[HTIndex] != null) {
			iterations++;
			if(hashTable[HTIndex].equals(key)) {
				return hashTable[HTIndex].getValue();
			} else {
				i++;
				HTIndex = (HTIndex + (i * i)) % size();
			}
		}
		return null;
	}
	
	public V put(K key, V value) {
		iterations = 0;
		int i = 0;
		int temp;
		if (get(key) != null) {
			int HTIndex = hash(key.hashCode());
			temp = HTIndex;
			while(hashTable[HTIndex] != null) {
				iterations++;
				if(hashTable[HTIndex].equals(key)) {
					V old = hashTable[HTIndex].getValue();
					hashTable[HTIndex]= new MapEntry<>(key,value);
					return old;
				} else {
					i++;
					HTIndex = (temp + (i * i)) % size();
				}
			}				
		}
		int x = 0;
		if (size >= hashTable.length * loadFactor)
			rehash();
		int HTIndex = hash(key.hashCode());
		temp = HTIndex;
		if(hashTable[HTIndex] == null) {
			hashTable[HTIndex]= new MapEntry<K,V>(key,value);
		} else {
			while(hashTable[HTIndex] != null) {
				x = x + 1;
				HTIndex = (temp + (x * x)) % size();
			}
		hashTable[temp + (x * x)] = new MapEntry<K,V>(key,value);
		}
		size++;
		return value;
	}

	public ArrayList<MapEntry<K,V>> toList() {
		ArrayList<MapEntry<K,V>> list = new ArrayList<>();
		for(int i = 0; i < hashTable.length; i++) {
			if(hashTable[i] != null) {
				MapEntry<K,V> ll = hashTable[i];
				list.add(ll);
			}
		} 
		return list;
	}
	
	public String toString() {
		String out = "[";
		for(int i = 0; i < hashTable.length; i++) {
			if(hashTable[i] != null) {
				out += hashTable[i].toString() + "\n";
			}
		}
		out += "]"; 
		return out;
	}

}