package cs445.a5;
import java.util.Iterator;
public class TernaryTest<T>{
    public static void main(String[]args){
        TernaryTree h= new TernaryTree("h");
        TernaryTree i= new TernaryTree("i");
        TernaryTree k= new TernaryTree("k");
        TernaryTree l= new TernaryTree("l");
        TernaryTree m= new TernaryTree("m");
        TernaryTree n= new TernaryTree("n");
        TernaryTree g= new TernaryTree("g");
          TernaryTree j= new TernaryTree("j", null, n, null);
        TernaryTree e= new TernaryTree("e", k, null, l);
        TernaryTree f= new TernaryTree("f", null, m, null);
        TernaryTree c= new TernaryTree("c", h,i,null );
        TernaryTree d= new TernaryTree("d", null, null, j);
        TernaryTree b= new TernaryTree("b", e, f, g);
        TernaryTree a= new TernaryTree("a", b, c, d);
        
        
        TernaryTree tree2= new TernaryTree(1);
        TernaryTree left= new TernaryTree(2);
        TernaryTree middle= new TernaryTree(3);
        TernaryTree right= new TernaryTree(4);
        TernaryTree leftchild= new TernaryTree(5);
        TernaryTree middlechild= new TernaryTree(6);
        TernaryTree rightchild= new TernaryTree(7);
        TernaryTree leftchild2= new TernaryTree(8);
        TernaryTree leftsub= new TernaryTree(left, leftchild, middlechild,rightchild);
        
        TernaryTree middleSub= new TernaryTree(middle, leftchild2, null, null);
        TernaryTree tree1= new TernaryTree(1, leftsub ,middleSub,right);
        
        System.out.println("Root: " + a.getRootData());
        System.out.println("Height: " + a.getHeight());
        System.out.println("Number of Nodes: "+ a.getNumberOfNodes());
        System.out.print("Preorder: ");
        Iterator preorder=a.getPreorderIterator();
        while(preorder.hasNext()){
            System.out.print(preorder.next()+ " ");
        }
        System.out.print("\nLevel Order: ");
        Iterator levelorder=a.getLevelOrderIterator();
        while(levelorder.hasNext()){
            System.out.print(levelorder.next()+ " ");
        }
        System.out.print("\nPostorder: ");
        Iterator postorder=a.getPostorderIterator();
        while(postorder.hasNext()){
            System.out.print(postorder.next()+ " ");
        }
        System.out.println("\n");

    }
}
