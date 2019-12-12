package Ex1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Predicate;

/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{
	/**
	 * a list of monoms. 
	 */
	LinkedList<Monom> pol;
	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {
		this.pol=new LinkedList<>();
	}
	/**
	 *a constructor that gets a string, check if the string is a valid polynom and if
	 *is it- set each Monom to a LinkedList<Monom> object, check if the power of the next Monom is exist already in LinkedList<Monom>
	 *and sort it in the end in power order by the Monom_Comperator.
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) {
		String s1=s.replace(" ", "");//change because of initFromFile
		if(s1.charAt(0)=='+') {
			s1=s1.substring(1);
		}
		for (int i = 0; i < s1.length(); i++) {
			if(!(s1.charAt(i)=='-'||s1.charAt(i)=='^'||s1.charAt(i)=='x'||s1.charAt(i)=='+'||s1.charAt(i)=='.'||(s1.charAt(i)<='9'&&s1.charAt(i)>='0'))) {
				throw new NumberFormatException("its invalid polynom");//condition thats check if the string is valid Polynom.
			}
		}
		this.pol=new LinkedList<Monom>();
		boolean lastMonom=false;//variable that will change at the moment that this is the last monom.
		boolean neg=false;//variable that will change at the moment the current monom is negative.

		int pos=0;
		while(pos<s1.length()&&lastMonom==false) {//condition that will start a loop and cut the string at the moment that new monom is added. 

			if(s1.charAt(0)=='-') {
				neg=true;
				s1=s1.substring(1);
			}
			if(s1.charAt(pos)!='+'&&s1.charAt(pos)!='-'&&pos!=s1.length()-1) {
				pos++;
			}
			else {
				if(pos==s1.length()-1) {
					lastMonom=true;
				}
				else {
					if(neg) {

						Monom m=new Monom("-"+s1.substring(0,pos));
						neg=false;
						boolean updated=updatePower(m);
						if(updated==false) {
							pol.add(m);
						}
						if(s1.charAt(pos)=='+') {
							s1=s1.substring(pos+1);
							pos=0;
						}
						else {
							s1=s1.substring(pos);
							pos=0;
						}
					}

					else {
						Monom m=new Monom(s1.substring(0,pos));
						boolean updated=updatePower(m);
						if(updated==false) {
							pol.add(m);
						}
						if(s1.charAt(pos)=='+') {
							s1=s1.substring(pos+1);
							pos=0;
						}
						else {
							s1=s1.substring(pos);
							pos=0;
						}

					}
				}
			}
		}
		if(neg) {
			Monom m=new Monom("-"+s1);
			boolean updated=updatePower(m);
			if(updated==false) {
				pol.add(m);
			}
		}
		else {
			Monom m=new Monom(s1);
			boolean updated=updatePower(m);
			if(updated==false) {
				pol.add(m);
			}
		}
		sortPol();
	}
	/**
	 * function that get double variable x and returns the value of f in x point.
	 */
	@Override
	public double f(double x) {
		// TODO Auto-generated method stub
		if(pol.size()==0) return 0;
		double sum=0;
		Iterator<Monom> it=pol.iterator();
		while(it.hasNext()) {
			Monom temp=it.next();
			sum=sum+(temp.get_coefficient()*Math.pow(x, temp.get_power()));
		}

		return sum;
	}
	/**
	 * a function that gets a Polynom_able object,sum it with this Polynom and set the sum result.
	 */
	@Override
	public void add(Polynom_able p1) {

		if(p1 instanceof Polynom ) {
			Polynom_able n=p1;
			Iterator<Monom> it=n.iteretor();
			while(it.hasNext()) {
				Monom temp=it.next();
				boolean samePower=updatePower(temp);
				if(samePower) {
					continue;
				}
				else {
					pol.add(temp);
				}
			}
			sortPol();
		}
	}

	@Override
	/**
	 * a function that gets a Monom object,sum it with this Polynom and set the sum result.
	 * 
	 */
	public void add(Monom m1) {
		boolean samePower=updatePower(m1);
		if(samePower) {
			return;
		}
		else {
			pol.add(m1);
		}
		sortPol();
	}

	@Override
	/**
	 *  function that gets a Polynom_able object,substract it with this Polynom and set the substract result.
	 */
	public void substract(Polynom_able p1) {
		// TODO Auto-generated method stub
		if(p1 instanceof Polynom ) {
			Polynom_able n=p1;
			if(this.equals(n)) {
				this.pol.removeAll(pol);
				return;
			}
			Iterator<Monom> negCoff=n.iteretor();
			while(negCoff.hasNext()) {
				Monom temp=negCoff.next();
				Monom neg=new Monom("-1");
				temp.multipy(neg);
			}
			Iterator<Monom> it=n.iteretor();
			while(it.hasNext()) {
				Monom temp=it.next();
				boolean samePower=updatePower(temp);
				if(samePower) {
					continue;
				}
				else {
					pol.add(temp);
				}
			}
			sortPol();
		}

	}

	@Override
	/**
	 * a function that gets a Polynom_able, multiply each Monom with this Polynom and set the sum all the multiplies in this Polynom.
	 */
	public void multiply(Polynom_able p1) {
		if(p1 instanceof Polynom) {
			Polynom temp=(Polynom)p1;
			Polynom add=new Polynom();
			Iterator<Monom> it=temp.iteretor();
			while(it.hasNext()) {
				Polynom copy=(Polynom) this.copy();
				Monom m=it.next();
				copy.multiply(m);
				add.add(copy);
			}
			this.pol=add.pol;
		}

	}


	@Override
	/**
	 * a function that returns a boolean object if this polynom is logically equal to another function.
	 * @param p1-function that compared to this polynom .
	 * @return - a boolean object
	 */

	public boolean equals(Object p1) {
		if(p1 instanceof function) {
			function other=(function)p1;
			for (int i = -100; i < 100; i++) {/*loop that checks 
			in range -100 to 100 if this complex function and other function with the same value in f(x)*/

				if(i!=0) {

					if(!coeefequals(this.f(i), other.f(i))) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	
	}
	private boolean coeefequals(double x,double y) {
		int a=(int) (x*10000);
		int b=(int) (y*10000);
		x=a/10000;
		y=b/10000;
		if(x==y) return true;
		else return false;
	}
	
	private boolean coefequals(Monom tmp1,Monom tmp2) {
		if(tmp1.get_coefficient()>tmp2.get_coefficient()-0.000001
				&&tmp1.get_coefficient()<tmp2.get_coefficient()+0.000001) return true;
		else return false;
	}
	
	@Override
	/**
	 * a function that returns a boolean object - if the Polynom isZero or not.
	 */
	public boolean isZero() {
		if(pol==null) return true;
		Iterator<Monom> check=pol.iterator();
		if(check.hasNext()) {
			Monom temp=check.next();
			if(temp.get_coefficient()!=0) {
				return false;
			}
		}
		return true;
	}

	@Override
	/**
	 * a function that uses Newton's Method and check if there is exist a double variable that (x0<=x'<=x1) for with |f(x')| < eps.
	 */
	public double root(double x0, double x1, double eps) {
		if(this.f(x0)*this.f(x1)>0) throw new RuntimeException("no exist");
		Polynom der=(Polynom) this.derivative();
		double first=Math.pow(eps, 2);
		while(Math.abs(this.f(first))>eps) {
			double xn=first-(this.f(first)/der.f(first));
			first=xn;
		}
		return first;
	}

	@Override
	/**
	 *a function that create a deep copy of this Polynom.
	 */
	public Polynom_able copy() {
		Polynom_able n=new Polynom();
		Iterator<Monom> m=this.pol.iterator();
		while(m.hasNext()) {
			Monom t=m.next();
			Monom temp=new Monom(t.get_coefficient(),t.get_power());
			n.add(temp);
		}

		return n;
	}

	//@Override
	/**
	 * a function that compute a new Polynom which is the derivative of this Polynom.
	 */
	public Polynom_able derivative() {
		Polynom d=new Polynom();
		Iterator<Monom> it=this.pol.iterator();
		while(it.hasNext()) {
			Monom temp1=it.next();
			temp1=temp1.derivative();
			d.add(temp1);
		}
		Polynom_able der = d;
		return der;
	}

	@Override
	/**
	 * 
	 */
	public double area(double x0, double x1, double eps) {
		if(x0>x1) throw new RuntimeException("no area");
		if(x0==x1) return 0;
		double sum=0;
		x0=eps+x0;
		while(x0<=x1) {
			if(this.f(x0)>0) {
				double sur=this.f(x0)*eps;
				sum=sum+sur;
			}
			x0=x0+eps;
		}
		return sum;
	}


	@Override
	/**
	 * a function that returns an iterator type Monom.
	 */
	public Iterator<Monom> iteretor() {
		Iterator<Monom> it=pol.iterator();
		return it;
	}
	@Override
	/**
	 * a function that gets a Monom object,multiply the Monom with this Polynom and set the result on this Polynom.
	 */
	public void multiply(Monom m1) {
		Iterator<Monom> mulPol=pol.iterator();
		while(mulPol.hasNext()) {
			Monom temp1=mulPol.next();
			temp1.multipy(m1);
		}

	}
	/**
	 * a function that sort the Polynom in power order by the Comparator object .
	 */
	public void sortPol() {
		Comparator<Monom> compByPower=new Monom_Comperator();
		this.pol.sort(compByPower);
	}
	/**
	 * function that check if the Monom's power is exist in the Polynom.
	 * @param m- get Monom and return true/false if the Monom's power is exist.
	 * if exist, add to the Monom with the same power.
	 */
	public boolean updatePower(Monom m) {
		Iterator<Monom> check=pol.iterator();
		if(check==null) { 
			return false;
		}
		else {
			if(m.get_coefficient()==0) {
				return true;
			}
			while(check.hasNext()) {
				Monom temp=check.next();
				if(temp.get_power()==m.get_power()) {
					temp.add(m);
					if(temp.get_coefficient()==0) {
						pol.remove(temp);
					}
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * function that return the Polynom as string.
	 */
	public String toString() {
		if(this.pol==null) return "0";
		String s="";
		int count=0;
		Iterator<Monom> m=this.pol.iterator();
		while(m.hasNext()) {
			Monom temp=m.next();
			if(count==0) {
				s=s+temp;
			}
			else {
				if(temp.get_coefficient()>0) {
					s=s+"+" +temp;
				}
				else {
					s=s+temp;
				}
			}
			count++;
		}
		if(s.length()==0) {
			return "0";
		}
		return s;
	}
	@Override
	public function initFromString(String s) {
		function f=new Polynom(s);
		return f;
	}



}
