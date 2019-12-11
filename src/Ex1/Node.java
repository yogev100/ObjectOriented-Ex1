package Ex1;

/**
 * A side class that represent a single element in complex tree
 * @author Yogev
 *
 */
public class Node {
	Node next,parent;
	function f1,f2;
	Operation o;
	
	/**
	 * A class constructor 
	 * @param f1 - the left function
	 * @param f2 - the right function
	 * @param o - the operation between left and right
	 */
	public Node(function f1,function f2,Operation o) {
		this.next=this.parent=null;
		if(f1!=null){
			this.f1=f1.copy();
		}
		if(f2!=null) {
			this.f2=f2.copy();
		}
		this.o=o;
	}
	
	/**
	 * A copy constructor 
	 * @param ot - the node that we want to copy to this node
	 */
	public Node(Node ot) {
		this.next=ot.next;
		this.parent=ot.parent;
		if(f1!=null){
			this.f1=ot.f1.copy();
		}
		if(f2!=null) {
			this.f2=ot.f2.copy();
		}
		this.o=ot.o;
	}
	
	/**
	 * A function that compare between two nodes
	 * @param n
	 * @return
	 */
	public boolean equals(Node n) {
		if(n.o==Operation.Plus||n.o==Operation.Times||n.o==Operation.Max||n.o==Operation.Min) {
			if((!this.f1.equals(n.f1))||(!this.f2.equals(n.f2))) {
				if((this.f1.equals(n.f2))&&(this.f2.equals(n.f1))&&this.o==n.o) return true;
				else return false;
			}
			else if(this.f1.equals(n.f1)&&this.f2.equals(n.f2)&&this.o==n.o) return true;
			else return false;
		}
		else if((!this.f1.equals(n.f1))||((this.f2!=null&&n.f2!=null)&&(!this.f2.equals(n.f2)))||this.o!=n.o) return false;
		return true;
	}

}
