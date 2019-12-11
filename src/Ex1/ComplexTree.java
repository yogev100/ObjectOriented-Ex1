package Ex1;
/**
 * A side class that define the complex function as a tree
 * @author Yogev
 *
 */
public class ComplexTree{
	Node root;

	public ComplexTree() {
		this.root=null;

	}
	/**
	 * function that add a node and in each adding - the new node is become root.
	 * @param n - the new node that we adding to this complex tree
	 */
	public void add(Node n) {
		if(this.root==null) {
			this.root=new Node(n.f1,n.f2,n.o);
		}
		else {
			Node tmp=new Node(null,n.f2,n.o);//because root exist
			n=this.root;
			this.root=tmp;
			n.parent=this.root;
			this.root.next=n;
		}
	}


}