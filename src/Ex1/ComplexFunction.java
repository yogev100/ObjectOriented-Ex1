package Ex1;

import javax.management.RuntimeErrorException;

public class ComplexFunction implements complex_function {
	ComplexTree tree=new ComplexTree();

	/**
	 * A default constructor
	 */
	public ComplexFunction() {
		tree.root=null;
	}

	/**
	 * A two functions constructor
	 * @param f1 - the left function of this complex function
	 * @param f2 - the right function of this complex function
	 * @param op - the operation between the two this functions
	 */
	public ComplexFunction(function f1,function f2,Operation op) {
		tree.root=new Node(f1,f2,op);
	}

	/**
	 *  A one function constructor
	 * @param f1 - the only function of this complex function , that defined as left function
	 */
	public ComplexFunction(function f1) {
		tree.root=new Node(f1,null,Operation.None);
	}

	@Override
	public double f(double x) {
		return treefx(tree.root, x);
	}

	@Override
	public function initFromString(String s) {
		if(s.equals("")) {
			return null;
		}
		for (int count=0, i = 0; i < s.length(); i++) {//checking the '()' balance
			if(s.charAt(i)=='(') count++;
			if(s.charAt(i)==')') {
				if(count>0)count--;
				else throw new ArithmeticException("its illegal complex function");
			}
			if(i==s.length()-1&&count!=0) throw new ArithmeticException("its illegal complex function");
		}
		if(s.charAt(0)>'z'||s.charAt(0)<'a'||s.charAt(0)=='x') {//return polynom if there is no operator
			return new Polynom(s);
		}
		ComplexFunction func =new ComplexFunction();

		return IFS(s, func);
	}

	/**
	 * Recursive function, that get complex function as string,
	 * and recurse into the string if there's another complex string in it.
	 * First - saving the most outer operation, than saving right and left function.
	 * @param s - The string of the complex function.
	 * @param f - The function that we work on, through out the recurse.
	 * @return the f complex function that we worked on.
	 */
	private function IFS(String s,ComplexFunction f) {
		int count=0; //counting the ',' that we need to skip

		boolean flagl=false, flagr=false;//checking if the right and the left function are complex.(true=complex)

		Operation tmp=Operation.None;//The outer Operation of the string

		function lft2 = new ComplexFunction(), rght2 = new ComplexFunction();/*Cf's that we returns only if 
		we need to recurse(inner Complex function and flag=true).*/ 

		Polynom lft1=new Polynom(), rght1=new Polynom();//if the left or right functions are polynom type.

		/*this conditions treat to the outer operation, the operation saved and cut the string
		 * from the index after we cut it.
		 */
		if(s.length()>4&&s.substring(0, 4).equals("max(")) {
			s=s.substring(4);
			tmp=Operation.Max;
		}
		else if(s.length()>4&&s.substring(0, 4).equals("min(")) {
			s=s.substring(4);
			tmp=Operation.Min;
		}
		else if(s.length()>5&&s.substring(0, 5).equals("plus(")) {
			s=s.substring(5);
			tmp=Operation.Plus;
		}
		else if(s.length()>4&&s.substring(0, 4).equals("mul(")) {
			s=s.substring(4);
			tmp=Operation.Times;
		}
		else if(s.length()>4&&s.substring(0, 4).equals("div(")) {
			s=s.substring(4);
			tmp=Operation.Divid;
		}
		else if(s.length()>5&&s.substring(0, 5).equals("comp(")) {
			s=s.substring(5);
			tmp=Operation.Comp;
		}
		/*
		 * the main loop that first checks if the current string start with operation,
		 * if its right - checks which operation it is and increase the count by 1 (because ',' exist)
		 * and skip the function till the next ','
		 */
		for (int j = 0; j < s.length(); j++) {
			if(s.charAt(0)>'a'&&s.charAt(0)<'z'&&s.charAt(0)!='x') {
				flagl=true;
			}
			if((j+4)<s.length()&&s.substring(j, j+4).equals("max(")) {
				j=j+3;
				count++;
			}
			else if((j+4)<s.length()&&s.substring(j, j+4).equals("min(")) {
				j=j+3;
				count++;
			}
			else if((j+5)<s.length()&&s.substring(j, j+5).equals("plus(")) {
				j=j+4;
				count++;
			}
			else if((j+4)<s.length()&&s.substring(j, j+4).equals("mul(")) {
				j=j+3;
				count++;
			}
			else if((j+4)<s.length()&&s.substring(j, j+4).equals("div(")) {
				j=j+3;
				count++;
			}
			else if((j+5)<s.length()&&s.substring(j, j+5).equals("comp(")) {
				j=j+4;
				count++;
			}
			/*
			 * the main condition that checks if the j integer stand on ',' character
			 * if its right - if the count=0 (its mean that there is no ',' anymore) ,
			 * and then the algorithm enter to the right condition
			 */
			if(j<s.length()&&s.charAt(j)==',') {
				if(count==0) {
					if(!flagl) {
						lft1 = new Polynom(s.substring(0, j));//if the left is polynom
					}
					else {
						lft2 = IFS(s.substring(0, j), f);//if the left is complex function - recurse situation
					}
					if((j+1)<s.length()&&s.charAt(j+1)>'a'&& s.charAt(j+1)<'z'&&(s.charAt(j+1)!='x')) {/*if the
					 right function has complex function*/
						flagr=true;
					}
					if(flagl&&flagr) {//if both are complex functions - add
						rght2 = IFS(s.substring(j+1, s.length()-1), f);
						return new ComplexFunction(lft2, rght2, tmp);
					}
					else if((!flagl)&&flagr) {//if left is polynom and right is complex function
						rght2 = IFS(s.substring(j+1, s.length()-1), f);// recurse situation
						return new ComplexFunction(lft1, rght2, tmp);
					}
					else if(flagl&&(!flagr)) {//if left is complex function and right is polynom
						rght1 = new Polynom(s.substring(j+1, s.length()-1));
						return new ComplexFunction(lft2, rght1, tmp);
					}
					else if((!flagl)&&(!flagr)) {//if both are polynoms (stop condition of the recurse)
						rght1 = new Polynom(s.substring(j+1, s.length()-1));
						return new ComplexFunction(lft1, rght1, tmp);
					}
				}
				else count--;//its no the last ',' , so we need to continue
			}
		}
		return f;
	}

	@Override
	public function copy() {
		ComplexFunction ot=new ComplexFunction();
		Node temp=this.tree.root;
		boolean flag=true;//if temp is a lone element
		if(temp==null) {//if its a null ComplexFunction
			return null;
		}
		//passing to the most inner complex function
		while(temp.next!=null) {
			temp=temp.next;
			flag=false;
		}
		//starting to add Nodes from the end
		while(temp.parent!=null) {
			ot.tree.add(temp);
			temp=temp.parent;
			flag=false;
		}
		//if temp is a lone element ( no have next and no parent)
		if(flag) ot.tree.add(temp);
		return ot;
	}

	@Override
	/**
	 * @param f2- the function that we want to execute the operation with this complex function
	 */
	public void plus(function f2) {
		Node n=new Node(null,f2,Operation.Plus);
		tree.add(n);
	}

	@Override
	/**
	 * @param f2- the function that we want to execute the operation with this complex function
	 */
	public void mul(function f2) {
		Node n=new Node(null,f2,Operation.Times);
		tree.add(n);

	}

	@Override
	/**
	 * @param f2- the function that we want to execute the operation with this complex function
	 */
	public void div(function f2) {
		Node n=new Node(null,f2,Operation.Divid);
		tree.add(n);
	}

	@Override
	/**
	 * @param f2- the function that we want to execute the operation with this complex function
	 */
	public void max(function f2) {
		Node n=new Node(null,f2,Operation.Max);
		tree.add(n);
	}

	@Override
	/**
	 * @param f2- the function that we want to execute the operation with this complex function
	 */
	public void min(function f2) {
		Node n=new Node(null,f2,Operation.Min);
		tree.add(n);
	}

	@Override
	/**
	 * @param f2- the function that we want to execute the operation with this complex function
	 */
	public void comp(function f2) {
		Node n=new Node(null,f2,Operation.Comp);
		tree.add(n);
	}

	@Override
	/**
	 * @return function that return the right function of this complex function
	 */
	public function right() {
		if(this.tree.root==null) {
			return null;
		}
		else return this.tree.root.f2;
	}

	@Override 
	/**
	 * @return function that return the left function of this complex function
	 */
	public function left() {
		if(this.tree.root==null) {
			return null;
		}
		if(this.tree.root.f1!=null) {
			return this.tree.root.f1;
		}
		else {
			ComplexFunction tmp = new ComplexFunction();
			tmp.tree.root=this.tree.root.next;
			if(this.tree.root.next.f1==null) {
				tmp.tree.root.f2=this.tree.root.next.f2;
			}
			else {
				tmp.tree.root.f2=this.tree.root.next.f2;
				tmp.tree.root.f1=this.tree.root.next.f1;
			}
			return tmp;	
		}
	}

	@Override
	/**
	 * Function that return the most outer operation
	 * @return Operation object
	 */
	public Operation getOp() {
		if(this.tree.root==null) {
			return null;
		}
		return tree.root.o;
	}

	public String toString() {
		return printRec(tree.root);
	}

	/**
	 * Function that checks if this function and obj1 are logically equals.
	 * @return - a boolean object.
	 */
	public boolean equals(Object obj1) {
		if(obj1 instanceof function) {
			function other=(function)obj1;
			for (int i = -100; i < 101; i++) {/*loop that checks 
			in range -100 to 100 if this complex function and other function with the same value in f(x)*/

				if(i!=0) {
					if(this.f(i)!=other.f(i)) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Recursive side function that running over the tree and computing each complex function with its operation 
	 * from the most inner complex function to the outer complex function
	 * @param node - The specific complex function that we computing
	 * @param x - the value that inserted to this complex function
	 * @return the value of f(x) of this complex function as a double
	 */
	double treefx(Node node,double x) 
	{ 
		if (node == null) { //stop condition
			return 0; 
		}
		if(node.f1==null) {//if the left function is a complex function recurse on it till we get to polynom.
			switch(node.o) {
			case Plus: 
				return treefx(node.next,x)+node.f2.f(x);
			case Times: 
				return treefx(node.next,x)*node.f2.f(x);
			case Divid: 
				return treefx(node.next,x)/node.f2.f(x);
			case Max: 
				return (treefx(node.next,x)>node.f2.f(x)) ? treefx(node.next,x) : node.f2.f(x);
			case Min: 
				return (treefx(node.next,x)<node.f2.f(x)) ? treefx(node.next,x) : node.f2.f(x);
			case Comp: 
				return treefx(node.next,node.f2.f(x));
			default: 
				return treefx(node.next,x);
			}
		}
		else {
			//the most inner complex function in the tree
			Operation op=node.o;
			switch(op) {
			case Plus: 
				return node.f1.f(x)+node.f2.f(x);
			case Times: 
				return node.f1.f(x)*node.f2.f(x);
			case Divid: 
				return node.f1.f(x)/node.f2.f(x);
			case Max: 
				return (node.f1.f(x)>node.f2.f(x)) ? node.f1.f(x) : node.f2.f(x);
			case Min: 
				return (node.f1.f(x)<node.f2.f(x)) ? node.f1.f(x) : node.f2.f(x);
			case Comp: 
				return  node.f1.f(node.f2.f(x));
			default: 
				return node.f1.f(x);
			}
		}
	}

	/**
	 * Recursive side function that running over the tree and printing each complex function with its operation 
	 * from the most inner complex function to the outer complex function
	 * @param node - The specific complex function that we printing
	 * @return the object complex function as a string.
	 */
	private String printRec(Node node) 
	{ 
		if (node == null) 
			return "";
		if(node.f1==null) {
			switch(node.o) {
			case Plus: 
				return "plus("+printRec(node.next)+","+node.f2+")";
			case Times: 
				return "mul("+printRec(node.next)+","+node.f2+")";
			case Divid: 
				return "div("+printRec(node.next)+ ","+node.f2+")";
			case Max: 
				return "max("+printRec(node.next)+ ","+node.f2+")";
			case Min: 
				return "min("+printRec(node.next)+ ","+node.f2+")";
			case Comp: 
				return "comp("+printRec(node.next)+ ","+node.f2+")";
			default: 
				return printRec(node.next)+"("+node.f2+")";
			}
		}
		else {
			Operation op=node.o;
			switch(op) {
			case Plus: 
				return "plus("+node.f1+","+node.f2+")";
			case Times: 
				return "mul("+node.f1+","+node.f2+")";
			case Divid: 
				return "div("+node.f1+","+node.f2+")";
			case Max: 
				return "max("+node.f1+","+node.f2+")";
			case Min: 
				return "min("+node.f1+","+node.f2+")";
			case Comp: 
				return "comp("+node.f1+","+node.f2+")";
			default: 
				return node.f1+"";
			}
		}
	}


	public static void main(String[] args) {
		ComplexFunction g=new ComplexFunction();
		ComplexFunction s=(ComplexFunction) g.initFromString("plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)");
		System.out.println("plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)");
		System.out.println(s);
		
	}

}



