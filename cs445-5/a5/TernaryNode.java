//Twesha Mitra- Assignment 5
package cs445.a5;

class TernaryNode<T>{
	private T data;
	private TernaryNode<T> leftChild;
	private TernaryNode<T> middleChild;
	private TernaryNode<T> rightChild;
	
    
	public TernaryNode(){
		this(null);
	}
	
	public TernaryNode(T dataPortion){
		this(dataPortion, null, null, null);
	}
	
	public TernaryNode(T dataPortion, TernaryNode<T> newLeftChild, TernaryNode<T> newMiddleChild, TernaryNode<T> newRightChild){
		data=dataPortion;
		leftChild=newLeftChild;
		middleChild=newMiddleChild;
		rightChild=newRightChild;
	}
	
    //returns the data portion of the node
	public T getData(){
		return data;
	}
	
    //sets data portion in node to given data
	public void setData(T newData){
		data=newData;
	}
	
    //gets the leftChild
	public TernaryNode<T> getLeftChild(){
		return leftChild;
	}
	
    //gets the middleChild
	public TernaryNode<T> getMiddleChild(){
		return middleChild;
	}
	
    //gets the rigthChild
	public TernaryNode<T> getRightChild(){
		return rightChild;
	}
	
    //sets the leftChild to given data
	public void setLeftChild(TernaryNode<T> newLeftChild){
		leftChild=newLeftChild;
	}
	
    //sets the middleChild to given data
	public void setMiddleChild(TernaryNode<T> newMiddleChild){
		middleChild=newMiddleChild;
	}
	
    //sets the rightChild to given data
	public void setRightChild(TernaryNode<T> newRightChild){
		rightChild=newRightChild;
	}
	
    //returns true if root has left child
	public boolean hasLeftChild(){
		return leftChild!=null;
	}
	
    //returns true if root has middle child
	public boolean hasMiddleChild(){
		return middleChild!=null;
	}
	
    //returns true if root has right child
	public boolean hasRightChild(){
		return rightChild!=null;
	}
	
    //checks whether a root is a leaf (i.e., has no children
	public boolean isLeaf(){
		return (leftChild==null) && (middleChild==null) &&(rightChild==null);
	}
	
    //counts the number of nodes
	public int getNumberOfNodes(){
		int left=0;
		int middle=0;
		int right=0;
		
		if(leftChild!=null){
			left=leftChild.getNumberOfNodes();
		}
		
		if(middleChild!=null){
			middle=middleChild.getNumberOfNodes();
		}
		
		if(rightChild!=null){
			right=rightChild.getNumberOfNodes();
		}
		
		return 1+left+middle+right;
	}
	
    
	public int getHeight(){
		return getHeight(this);
	}
	
    //gets the maximum height
	public int getHeight(TernaryNode<T> node){
		int heighttree=0;
        int height1=0;
        int height2=0;
		
        if(node!=null){
            height1=Math.max(getHeight(node.getLeftChild()), getHeight(node.getMiddleChild()));
            height2=Math.max(height1, getHeight(node.getRightChild()));
            heighttree=1+height2;
        }
		
        return heighttree;
	}
	
	public TernaryNode<T> copy(){
		TernaryNode<T> newRoot= new TernaryNode<>(data);
		
		if(leftChild!=null){
			newRoot.setLeftChild(leftChild.copy());
		}
		
		if(middleChild!=null){
			newRoot.setMiddleChild(middleChild.copy());
		}
		
		if(rightChild!=null){
			newRoot.setRightChild(rightChild.copy());
		}
	
		return newRoot;
	}
		
	
	
}
