package myMath;

import java.util.Iterator;
import java.util.LinkedList;

import myMath.Monom;

/**
 * This class represents a Polynom with add, multiply functionality, it also
 * should support the following: 1. Riemann's Integral:
 * https://en.wikipedia.org/wiki/Riemann_integral 2. Finding a numerical value
 * between two values (currently support root only f(x)=0). 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able {
	LinkedList<Monom> polynom;

	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {
		this.polynom = new LinkedList<Monom>();
	}

	/**
	 * init a Polynom from a String such as: {"x", "3+1.4X^3-34x",
	 * "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
	 * 
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) {
		int i = 0, j = 0;
		boolean nextflag = false;
		this.polynom = new LinkedList<Monom>();
		// check the positively of the first monom
		if (s.charAt(0) == '-' || s.charAt(0) == '+') {
			nextflag = (s.charAt(0) == '-') ? true : false;
			s = s.substring(1);
		}
		String leftpolynom = s;
		for (; j < s.length(); j++) {
			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~end string
			// option~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			if (j == s.length() - 1) {
				if (nextflag) {
					if (!samePow(new Monom('-' + leftpolynom.substring(0, i + 1)))) {
						this.polynom.add(new Monom('-' + leftpolynom.substring(0, i + 1)));
						leftpolynom = leftpolynom.substring(i + 1);
						i = 0;
						nextflag = false;
					}
				} else {
					if (!(samePow(new Monom(leftpolynom.substring(0, i + 1))))) {
						this.polynom.add(new Monom(leftpolynom.substring(0, i + 1)));
						leftpolynom = leftpolynom.substring(i + 1);
						i = 0;
					} else {
						leftpolynom = leftpolynom.substring(i + 1);
						i = 0;
					}
				}
			}
			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~plus
			// option~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			else if (leftpolynom.charAt(i) == '+') {
				// first monom
				if (polynom.size() == 0) {
					if (nextflag) {
						polynom.add(new Monom('-' + leftpolynom.substring(0, i)));
						leftpolynom = leftpolynom.substring(i + 1);
						i = 0;
						nextflag = false;

					} else {
						polynom.add(new Monom(leftpolynom.substring(0, i)));
						leftpolynom = leftpolynom.substring(i + 1);
						i = 0;

					}
				}
				// not first monom
				else {
					if (nextflag) {
						if (!samePow(new Monom('-' + leftpolynom.substring(0, i)))) {
							this.polynom.add(new Monom('-' + leftpolynom.substring(0, i)));
							leftpolynom = leftpolynom.substring(i + 1);
							i = 0;
							nextflag = false;
						} else {
							leftpolynom = leftpolynom.substring(i + 1);
							i = 0;
							nextflag = false;
						}
					} else {
						if (!(samePow(new Monom(leftpolynom.substring(0, i))))) {
							this.polynom.add(new Monom(leftpolynom.substring(0, i)));
							leftpolynom = leftpolynom.substring(i + 1);
							i = 0;
						} else {
							leftpolynom = leftpolynom.substring(i + 1);
							i = 0;
						}
					}
				}
			}
			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~minus
			// option~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			else if (leftpolynom.charAt(i) == '-') {
				// first monom
				if (polynom.size() == 0) {
					if (nextflag) {
						polynom.add(new Monom('-' + leftpolynom.substring(0, i)));
						leftpolynom = leftpolynom.substring(i + 1);
						i = 0;
						nextflag = false;

					} else {
						polynom.add(new Monom(leftpolynom.substring(0, i)));
						leftpolynom = leftpolynom.substring(i + 1);
						i = 0;
					}
				}
				// not first monom
				else {
					if (nextflag) {
						if (!samePow(new Monom('-' + leftpolynom.substring(0, i)))) {
							this.polynom.add(new Monom('-' + leftpolynom.substring(0, i)));
							leftpolynom = leftpolynom.substring(i + 1);
							i = 0;
							nextflag = false;
						} else {
							leftpolynom = leftpolynom.substring(i + 1);
							i = 0;
							nextflag = false;
						}
					} else {
						if (!(samePow(new Monom(leftpolynom.substring(0, i))))) {
							this.polynom.add(new Monom(leftpolynom.substring(0, i)));
							leftpolynom = leftpolynom.substring(i + 1);
							i = 0;
						} else {
							leftpolynom = leftpolynom.substring(i + 1);
							i = 0;
						}
					}
				}
				nextflag = true;
			} else
				i++;
		}
		// sort the monoms by power
		Monom_Comperator cmp = new Monom_Comperator();
		polynom.sort(cmp);
	}

	@Override
	public double f(double x) {
		Iterator<Monom> it = this.polynom.iterator();
		double y = 0;
		while (it.hasNext()) {
			double r = it.next().f(x);
			y += r;
		}
		return y;
	}

	@Override
	public void add(Polynom_able p1) {
		// checking powers and adding monoms to this polynom
		if (p1 instanceof Polynom) {
			Polynom tmp = (Polynom) p1;
			Iterator<Monom> it = tmp.iteretor();
			while (it.hasNext()) {
				Monom temp = it.next();
				if (!samePow(temp))
					this.polynom.add(temp);

			}
		}
		// sorting by power
		Monom_Comperator cmp = new Monom_Comperator();
		polynom.sort(cmp);
	}

	@Override
	public void add(Monom m1) {
		// checking powers and adding monom to the list
		if (this.polynom.size() == 0)
			this.polynom.add(m1);
		else if (!samePow(m1))
			this.polynom.add(m1);
		Monom_Comperator cmp = new Monom_Comperator();
		polynom.sort(cmp);
	}

	@Override
	public void substract(Polynom_able p1) {
		if (p1 instanceof Polynom) {
			Polynom n=(Polynom)p1;
			if(this.equals(n)) {
				this.polynom.removeAll(polynom);
				return;
			}
			// adding a (-1)* polynom
			Polynom p = (Polynom) n;
			Iterator<Monom> it = p.iteretor();
			while (it.hasNext()) {
				it.next().multipy(new Monom("-1"));
			}
			this.add(p1);
		}
	}

	@Override
	public void multiply(Polynom_able p1) {
		if (p1 instanceof Polynom) {
			Polynom p = (Polynom) p1;
			Polynom sum = new Polynom();
			Iterator<Monom> it = p.iteretor();
			while (it.hasNext()) {
				// multiply each monom with this polynom
				Polynom mine = (Polynom) this.copy();
				mine.multiply(it.next());
				sum.add(mine);
			}
			this.polynom = sum.polynom;
		}
	}

	@Override
	public boolean equals(Polynom_able p1) {
		if (p1 instanceof Polynom) {
			Polynom p = (Polynom) p1;
			Iterator<Monom> itp1 = p.polynom.iterator();
			Iterator<Monom> it = this.polynom.iterator();
			if (p.polynom.size() != this.polynom.size())
				return false;
			while (itp1.hasNext() && it.hasNext()) {
				Monom tmp1=it.next();
				Monom tmp2=itp1.next();
				
				if ((!coefequals(tmp1, tmp2))||tmp1.get_power()!=tmp2.get_power())
					return false;
			}
		}
		return true;
	}
	
	public boolean coefequals(Monom tmp1,Monom tmp2) {
		if(tmp1.get_coefficient()>tmp2.get_coefficient()-0.000001
				&&tmp1.get_coefficient()<tmp2.get_coefficient()+0.000001) return true;
		else return false;
	}

	@Override
	public boolean isZero() {
		if (this.polynom.size() == 0)
			return true;
		Iterator<Monom> it = this.polynom.iterator();
		while (it.hasNext()) {
			if (it.next().toString() != "0")
				return false;
		}
		return true;
	}

	@Override
	public double root(double x0, double x1, double eps) {
		// enter condition
		if (this.f(x0) * this.f(x1) > 0)
			throw new RuntimeException("Both f(x0) and f(x1) are positive/negative");
		/*
		 * we use Newton's method in order to find the y value of eps. Newton's method :
		 * xn+1=xn - (f(xn)/f'(xn))
		 */
		double start = Math.pow(eps, 2);
		double x2 = 0;
		while (Math.abs(this.f(start)) > eps) {
			x2 = start - (this.f(start) / this.derivative().f(start));
			start = x2;
		}
		return start;
	}

	@Override
	public Polynom_able copy() {
		Polynom t = new Polynom();
		Iterator<Monom> it = this.polynom.iterator();
		while (it.hasNext()) {
			Monom temp = it.next();
			Monom tmp = new Monom(temp.get_coefficient(), temp.get_power());
			t.add(tmp);
		}
		return t;
	}

	@Override
	public Polynom_able derivative() {
		// each monom derivative
		Iterator<Monom> it = this.polynom.iterator();
		Polynom tmp = new Polynom();
		while (it.hasNext()) {
			Monom temp = it.next();
			tmp.add(temp.derivative());
		}
		return tmp;
	}

	@Override
	public double area(double x0, double x1, double eps) {
		// sum the area by Riemann's sums method.
		double prg = x0;
		double area = 0;
		while (prg < x1) {
			if (this.f(prg) > 0) {
				area += eps * this.f(prg);
			}
			prg += eps;
		}
		return area;
	}

	@Override
	public Iterator<Monom> iteretor() {
		Iterator<Monom> s = this.polynom.iterator();
		return s;
	}

	@Override
	public void multiply(Monom m1) {
		Iterator<Monom> it = this.polynom.iterator();
		while (it.hasNext()) {
			it.next().multipy(m1);
		}
	}

	public String toString() {
		if (this.polynom.size() == 0)
			return "0";
		String s = "";
		Iterator<Monom> It = this.polynom.iterator();
		while (It.hasNext()) {
			Monom temp = It.next();
			if (temp.get_coefficient() > 0 && s != "")
				s += "+";
			if (temp.get_coefficient() != 0)
				s += temp.toString();
		}
		return s;
	}

	/**
	 * function that searching a monom with the same power on the list
	 * 
	 * @param m this is the monom with the power that we searching
	 * @return return true if found same power and also add otherwise false
	 */
	public boolean samePow(Monom m) {
		/*
		 * checking if there is same power monom if it does- sum the coefficient and
		 * return true. otherwise return false
		 */
		Iterator<Monom> itMo = this.polynom.iterator();
		while (itMo.hasNext()) {
			Monom tmp = itMo.next();
			if (tmp.get_power() == m.get_power()) {
				tmp.add(m);
				return true;
			}
		}
		return false;
	}

}
