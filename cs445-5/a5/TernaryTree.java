//Twesha Mitra- Assignment 5
package cs445.a5;
import java.util.Iterator;
import java.util.NoSuchElementException;

import StackAndQueuePackage.*;
public class TernaryTree<T> implements TernaryTreeInterface<T> {
	private TernaryNode<T> root;
	
	//initializes an empty tree
	public TernaryTree(){
		root=null;
	}
    
    //initializes a tree whose root node contains rootData
	public TernaryTree(T rootData){
		root=new TernaryNode<>(rootData);
	}
    
	//initializes a tree whose root node contains rootData and whose child subtrees are leftTree, middleTree, and rightTree, respectively.
	public TernaryTree(T rootData, TernaryTree<T> leftTree, TernaryTree<T> middleTree, TernaryTree<T> rightTree){
		privateSetTree(rootData, leftTree, middleTree, rightTree);
	}
    
	//Sets the ternary tree to a one node ternary tree with the given data.
	public void setTree(T rootData){
		root= new TernaryNode<>(rootData);
	}
	
	public void setTree(T rootData, TernaryTreeInterface<T> leftTree, TernaryTreeInterface<T> middleTree, TernaryTreeInterface<T> rightTree){
		privateSetTree(rootData, (TernaryTree<T>) leftTree, (TernaryTree<T>) middleTree, (TernaryTree<T>) rightTree);
	}
	
	private void privateSetTree(T rootData, TernaryTree<T> leftTree, TernaryTree<T> middleTree, TernaryTree<T> rightTree){
		root= new TernaryNode<>(rootData);
		
		if(leftTree!=null && !leftTree.isEmpty()){
			root.setLeftChild(leftTree.root);
		}
		
		if(rightTree!=null && !rightTree.isEmpty()){
			if(rightTree!=leftTree){
				root.setRightChild(rightTree.root);
			} else {
				root.setRightChild(rightTree.root.copy());
			}	
		}
		
		if(middleTree!=null && !middleTree.isEmpty()){
			if((middleTree!=leftTree) && (middleTree!=rightTree)){
				root.setMiddleChild(middleTree.root);
			} else {
				root.setMiddleChild(middleTree.root.copy());
			}
		}
		
		if((leftTree!=null) && (leftTree!=this)){
			leftTree.clear();
		}
		
		if((middleTree!=null) && (middleTree!=this)){
			middleTree.clear();
		}
		
		if((rightTree!=null) && (rightTree!=this)){
			rightTree.clear();
		}
	}
	
    //gets the data in the root node
	public T getRootData(){
		if(isEmpty()){
			throw new EmptyTreeException();
		} else {
			return root.getData();
		}
	}
   
    //determines whether the tree is empty (i.e., has not nodes)
	public boolean isEmpty(){
		return root==null;
	}
    
	//removes all data and nodes from the tree
	public void clear(){
		root=null;
	}
	
	protected void setRootData(T rootData){
		root.setData(rootData);
	}
	
	protected void setRootNode(TernaryNode<T> rootNode){
		root=rootNode;
	}
	
	protected TernaryNode<T> getRootNode(){
		return root;
	}
    
	//gets the height of the tree (i.e., the maximum number of nodes passed from root to leaf, inclusive)
	public int getHeight(){
		return root.getHeight();
	}
	
    //counts the total number of nodes in the tree
	public int getNumberOfNodes(){
		return root.getNumberOfNodes();
	}
    
    //creates an iterator to traverse the tree in pre order fashion
    public Iterator<T> getPreorderIterator(){
        return new PreorderIterator();
    }
    
    /*The InorderIterator is not supported for a ternary tree because you cannot traverse through a ternary tree in this fashion. To traverse in an inorder fasion, the iterator visits the left subtree first, then the root, and then the right subtree. However, in a ternary tree, we have a middle subtree. There's no logical way to do inorder traversal for a ternary tree because after traversing through the left subtree, it would go to the root and go to the right subtree. It would skip the middle subtree and those nodes would be ignored. Alternatively, it might take the might visit the root again after traversing through the middle subtree. This defeats the purpose of traversing because a traversal only visits each node once. So, for these reasons, the InorderIterator is not available for a ternary tree.
     */
    public Iterator<T> getInorderIterator(){
        throw new UnsupportedOperationException();
    }
    
	//creates an iterator to traverse the tree in post order fashion
    public Iterator<T> getPostorderIterator(){
        return new PostorderIterator();
    }
    
    //creates an iterator to traverse the tree in level order fashion
    public Iterator<T> getLevelOrderIterator(){
        return new LevelOrderIterator();
    }
    
    
    private class PreorderIterator implements Iterator<T>{
        private StackInterface<TernaryNode<T>> nodeStack;
        
        public PreorderIterator(){
            nodeStack= new LinkedStack<>();
            if(root!=null){
                nodeStack.push(root);
            }
        }
        
        //returns true if the iteration has more elements
        public boolean hasNext(){
            return !nodeStack.isEmpty();
        }
        
        //returns the next element in the iteration
        public T next(){
            TernaryNode<T> nextNode;
            
            if(hasNext()){
                nextNode=nodeStack.pop();
                TernaryNode<T> leftChild= nextNode.getLeftChild();
                TernaryNode<T> middleChild= nextNode.getMiddleChild();
                TernaryNode<T> rightChild= nextNode.getRightChild();
                
                if(rightChild!=null){
                    nodeStack.push(rightChild);
                }
                
                if(middleChild!=null){
                    nodeStack.push(middleChild);
                }
                
                if(leftChild!=null){
                    nodeStack.push(leftChild);
                }
            } else {
                throw new NoSuchElementException();
            }
            return nextNode.getData();
        }
        
        //removes the last element returned by this iterator. However, it is not supported for TernaryTree.
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }
    
    public void iterativePreorderTraverse(){
        StackInterface<TernaryNode<T>> nodeStack= new LinkedStack<>();
        if(root!=null){
            nodeStack.push(root);
        }
        TernaryNode<T> nextNode;
        while(!nodeStack.isEmpty()){
            nextNode=nodeStack.pop();
            TernaryNode<T> leftChild=nextNode.getLeftChild();
            TernaryNode<T> middleChild=nextNode.getMiddleChild();
            TernaryNode<T> rightChild=nextNode.getRightChild();
            
            if(rightChild!=null){
                nodeStack.push(rightChild);
            }
            
            if(middleChild!=null){
                nodeStack.push(middleChild);
            }
            
            if(leftChild!=null){
                nodeStack.push(leftChild);
            }
            
            System.out.print(nextNode.getData() + " ");
            
        }
    }
    
    private class PostorderIterator implements Iterator<T>{
        private StackInterface<TernaryNode<T>> nodeStack;
        private TernaryNode<T> currentNode;
        
        public PostorderIterator(){
            nodeStack=new LinkedStack<>();
            currentNode=root;
        }
        
        //returns true if the iteration has more elements
        public boolean hasNext(){
            return !nodeStack.isEmpty() || (currentNode!=null);
        }
        
        //returns the next element in the iteration
        public T next(){
            TernaryNode<T> leftChild, middleChild, rightChild, nextNode= null;
            
            while(currentNode!=null){
                nodeStack.push(currentNode);
                leftChild=currentNode.getLeftChild();
                if(leftChild==null){
                    middleChild=currentNode.getMiddleChild();
                    if(middleChild==null){
                        currentNode=currentNode.getRightChild();
                    } else {
                        currentNode=currentNode.getMiddleChild();
                    }
                } else {
                    currentNode=leftChild;
                }
            }
            
            if(!nodeStack.isEmpty()){
                nextNode=nodeStack.pop();
                TernaryNode<T> parent=null;
                
                if(!nodeStack.isEmpty()){
                    parent=nodeStack.peek();
                    if (nextNode==parent.getLeftChild()){
                        if(parent.getMiddleChild()!=null)
                            currentNode=parent.getMiddleChild();
                        else
                            currentNode=parent.getRightChild();
                    }
                    else if(nextNode==parent.getMiddleChild())
                        currentNode=parent.getRightChild();
                    else
                        currentNode=null;
                } else {
                    currentNode=null;
                }
            } else {
                throw new NoSuchElementException();
            }
            return nextNode.getData();
        }
        
        //removes the last element returned by this iterator. However, it is not supported for TernaryTree.
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }
    private class LevelOrderIterator implements Iterator<T>{
        private QueueInterface<TernaryNode<T>> nodeQueue;
        
        public LevelOrderIterator(){
            nodeQueue= new LinkedQueue<>();
            if(root!=null){
                nodeQueue.enqueue(root);
            }
        }
        
        //returns true if the iteration has more elements
        public boolean hasNext(){
            return !nodeQueue.isEmpty();
        }
        
        //returns the next element in the iteration
        public T next(){
            TernaryNode<T> nextNode;
            
            if(hasNext()){
                nextNode=nodeQueue.dequeue();
                TernaryNode<T> leftChild= nextNode.getLeftChild();
                TernaryNode<T> middleChild= nextNode.getMiddleChild();
                TernaryNode<T> rightChild= nextNode.getRightChild();
                
                if(leftChild!=null){
                    nodeQueue.enqueue(leftChild);
                }
                
                if(middleChild!=null){
                    nodeQueue.enqueue(middleChild);
                }
                
                if(rightChild!=null){
                    nodeQueue.enqueue(rightChild);
                }
            } else {
                throw new NoSuchElementException();
            }
            
            return nextNode.getData();
        }
        
        //removes the last element returned by this iterator. However, it is not supported for TernaryTree.
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }
    
}	
