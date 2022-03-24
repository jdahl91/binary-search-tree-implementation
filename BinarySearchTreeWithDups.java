/**
 * CS 111C Fall 2019
 * Project D: Trees and Big Data
 * Professor Jessica Masters
 * 
 * @author Joakim Dahl
 */
import java.util.*;

public class BinarySearchTreeWithDups<T extends Comparable<? super T>> extends BinarySearchTree<T>
		implements SearchTreeInterface<T>, java.io.Serializable {

	public BinarySearchTreeWithDups() {
		super();
	}

	public BinarySearchTreeWithDups(T rootEntry) {
		super(rootEntry);
		setRootNode(new BinaryNode<T>(rootEntry));
	}

	@Override
	public T add(T newEntry) {
		T result = newEntry;
		if (isEmpty()) {
			setRootNode(new BinaryNode<T>(newEntry));
		} else {
			addEntryHelperNonRecursive(newEntry);
		}
		return result;
	}
	
	private void addEntryHelperNonRecursive(T newEntry) {
		BinaryNode<T> current = getRootNode();
		
		while(current!=null) {
			int comparison = newEntry.compareTo(current.getData());
			
			if(comparison <= 0) {
				if(!current.hasLeftChild()) {
					current.setLeftChild(new BinaryNode<T>(newEntry));
					return;
				} else { 
					current = current.getLeftChild();
				}
			} else { 
				if(!current.hasRightChild()) { 
					current.setRightChild(new BinaryNode<T>(newEntry));
					return;
				} else { 
					current = current.getRightChild();
				}
			}
		}
	}

	public int countEntriesNonRecursive(T target) {
		int count = 0;
		BinaryNode<T> current = getRootNode();
		
		while(current!=null) {
			int comparison = target.compareTo(current.getData());
			
			if(comparison == 0) {
				count++;
				current = current.getLeftChild();
			} else if(comparison < 0) { 
				current = current.getLeftChild();
			} else { 
				current = current.getRightChild();
			}
		}
		return count;
	}

	public int countGreaterRecursive(T target) {
		BinaryNode<T> rootNode = getRootNode();
		return countGreaterRecursive(rootNode, target);
	}
	
	private int countGreaterRecursive(BinaryNode<T> currentNode, T target) {
		int count = 0;
		
		if(currentNode!=null) {
			int comparison = currentNode.getData().compareTo(target);
			
			if(comparison > 0) {
				count++;
				count = count + countGreaterRecursive(currentNode.getRightChild(), target) +
					countGreaterRecursive(currentNode.getLeftChild(), target);
			} else {
				count = count + countGreaterRecursive(currentNode.getRightChild(), target);
			}
		}
		return count; 
	}
		
	public int countGreaterWithStack(T target) {
		int count = 0;
		BinaryNode<T> rootNode = getRootNode();
		BinaryNode<T> current;
		Stack<BinaryNode<T>> nodeStack = new Stack<BinaryNode<T>>();
		nodeStack.push(rootNode);
		
		while(!nodeStack.isEmpty()) {
			current = nodeStack.pop();
			if(current!=null) {
				if(current.getData().compareTo(target) > 0) {
					count++;
					nodeStack.push(current.getRightChild());
					nodeStack.push(current.getLeftChild());
				} else {
					nodeStack.push(current.getRightChild());
				}
			}
		}
		return count;
	}
		
	/**
	 * Extra Credit Method
	 */
	public int countUniqueValues() {
		Set<T> uniqueValueTracker = new HashSet<T>();
		
		Iterator<T> iterator = getPreorderIterator();
		while(iterator.hasNext()) {
			uniqueValueTracker.add(iterator.next());
		}
		return uniqueValueTracker.size();
	}
	
	
	public int getLeftHeight() {
		BinaryNode<T> rootNode = getRootNode();
		if(rootNode==null) {
			return 0;
		} else if(!rootNode.hasLeftChild()) {
			return 0;
		} else {
			return rootNode.getLeftChild().getHeight();
		}
	}

	public int getRightHeight() {
		BinaryNode<T> rootNode = getRootNode();
		if(rootNode==null) {
			return 0;
		} else if(!rootNode.hasRightChild()) {
			return 0;
		} else {
			return rootNode.getRightChild().getHeight();
		}
	}
	
}