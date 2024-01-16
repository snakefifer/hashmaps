import java.util.Comparator;

public class TreeMap<K extends Comparable<K>, V extends Comparable<V>> {

    private class TreeNode {

		MapEntry<K, V> value;
		TreeNode left;
		TreeNode right;

		TreeNode(MapEntry<K,V> val){
			value = val;
			left = right = null;
		}
        
	}

    private TreeNode root;
    private int size;
    private Comparator<K> comp;
	public static int iterations;

    public TreeMap(Comparator<K> c) {
        comp = c;
		root = null;
		size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
		return (size == 0); 
	}
	
	public void clear() {
		root = null; 
		size = 0;
	}

    public boolean containsKey(K key) {
        TreeNode node = root;
		while (node != null) {
			if(comp.compare(key, node.value.getKey()) < 0)
				node = node.left;
			else if(comp.compare(key, node.value.getKey()) > 0)
				node = node.right;
			else
				return true;
		}
		return false;
    }

    public V getKey(K key) {
		iterations = 0;
        TreeNode node = root;
		while (node != null) {
            iterations++;
			if(key.compareTo(node.value.getKey()) < 0)
				node = node.left;
			else if (key.compareTo(node.value.getKey()) > 0)
				node = node.right;
			else
				return node.value.getValue();
		}
		return null;
    }

	public boolean add(K key, V value){
        return add(new MapEntry<>(key, value));
    }

    public boolean add(MapEntry<K,V> value) {
        if (root == null) {
			root = new TreeNode(value);
		} else {
			TreeNode parent, node;
			parent = null; 
			node = root;
			while(node != null) {
				parent = node;
				if(value.getKey().compareTo(node.value.getKey()) < 0) {
					node = node.left; 
				}
				else if (value.getKey().compareTo(node.value.getKey()) > 0) {
					node = node.right; 
				}
				else
					return false;
			}
			if (value.getKey().compareTo(parent.value.getKey()) < 0)
				parent.left = new TreeNode(value);
			else
				parent.right = new TreeNode(value);
		}
		size++;
		return true;
    }

    public boolean remove(MapEntry<K,V> value) {
		TreeNode parent, node;
		parent = null; 
		node = root;
		while(node != null) {
			if(value.getKey().compareTo(node.value.getKey()) < 0) {
				parent = node;
				node = node.left;
			} else if(value.getKey().compareTo(node.value.getKey()) > 0) {
				parent = node;
				node = node.right;
			} else {
				break;
			}
		}
		if(node == null)
			return false;
		if(node.left == null && node.right == null){
			if(parent == null) {
				root = null;
			} else {
				changeChild(parent, node, null);
			}
		} else if(node.left == null){
			if(parent == null) {
				root = node.right;
			} else {
				changeChild(parent, node, node.right);
			}
		} else if(node.right == null){
			if (parent == null) {
				root = node.left;
			} else {
				changeChild(parent, node, node.left);
			}
		} else {
			TreeNode rightMostParent = node;
			TreeNode rightMost = node.left;
			while (rightMost.right != null) {
				rightMostParent = rightMost;
				rightMost = rightMost.right;
			}
			node.value = rightMost.value;
			changeChild(rightMostParent, rightMost, rightMost.left);
		}
		size--;
		return true;
	}

	private void changeChild(TreeNode parent, TreeNode node, TreeNode newChild) {
		if(parent.left == node)
			parent.left = newChild;
		else
			parent.right = newChild;
	}

    public void inorder() {
		inorder(root);
	}
	private void inorder(TreeNode node) {
		if(node != null) {
			inorder(node.left);
			System.out.print(node.value + " ");
			inorder(node.right);
		}
	}
	
	public void preorder() {
		preorder(root);
	}

	private void preorder(TreeNode node) {
		if(node != null) {
			System.out.print(node.value + " ");
			preorder(node.left);
			preorder(node.right);
		}
	}
	
	public void postorder() {
		postorder(root);
	}

	private void postorder(TreeNode node)  {
		if(node != null) {
			postorder(node.left);
			postorder(node.right);
			System.out.print(node.value + " ");	
		}
	}

}