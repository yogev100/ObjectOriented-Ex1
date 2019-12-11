
package myMath;

import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real
 * number and a is an integer (summed a none negative), see:
 * https://en.wikipedia.org/wiki/Monomial The class implements function and
 * support simple operations as: construction, value at x, derivative, add and
 * multiply.
 * 
 * @author Boaz
 *
 */
public class Monom implements function {
	public static final Monom ZERO = new Monom(0, 0);
	public static final Monom MINUS1 = new Monom(-1, 0);
	public static final double EPSILON = 0.00000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();

	public static Comparator<Monom> getComp() {
		return _Comp;
	}

	public Monom(double a, int b) {
		// if the coefficient is 0, all the monom is 0.
		if (a == 0) {
			this.set_coefficient(0);
			this.set_power(0);
		} else {
			this.set_coefficient(a);
			this.set_power(b);
		}
	}

	public Monom(Monom ot) {
		// if the coefficient is 0, all the monom is 0.
		if (ot._coefficient == 0) {
			this.set_coefficient(0);
			this.set_power(0);
		} else {
			this.set_coefficient(ot.get_coefficient());
			this.set_power(ot.get_power());
		}
	}

	public double get_coefficient() {
		return this._coefficient;
	}

	public int get_power() {
		return this._power;
	}

	/**
	 * this method returns the derivative monom of this.
	 * 
	 * @return
	 */
	public Monom derivative() {
		if (this.get_power() == 0) {
			return getNewZeroMonom();
		}
		return new Monom(this.get_coefficient() * this.get_power(), this.get_power() - 1);
	}

	/**
	 * return the y value of the monom in point x
	 * 
	 * @param x the x value in f(x)=y function of monom.
	 */
	public double f(double x) {
		double ans = 0;
		double p = this.get_power();
		ans = this.get_coefficient() * Math.pow(x, p);
		return ans;
	}

	/**
	 * function that checks if the this monom is zero.
	 * 
	 * @return true if this monon is zero, else false.
	 */
	public boolean isZero() {
		return this.get_coefficient() == 0;
	}

	// ***************** add your code below **********************

	/**
	 * function thats get monom as a string, and define a coefficient and power.
	 * 
	 * @param s represent a monom as a string.
	 */
	public Monom(String s) {
		// scan the string in order to check if there is an illegal sign.
		for (int i = 0; i < s.length(); i++) {
			if (!((s.charAt(i) >= '0' && s.charAt(i) <= '9') || s.charAt(i) == 'x' || s.charAt(i) == '^'
					|| s.charAt(i) == '.' || s.charAt(i) == '-')) {
				throw new ArithmeticException("Illegal Input");
			}
			if(s.charAt(i)=='-'&&i!=0) throw new ArithmeticException("Illegal Input");
		}
		/*
		 * dot represent the '.' index. "prenum" is the number before the dot,
		 * "afternum" its after the dot. "negativeflag" has been created in order to
		 * know the positively of the monom
		 */
		int i = 0, dot = 0;
		double prenum = 0, postnum = 0, num = 0, power = 1;
		boolean negativeflag = false;
		// Zero monom
		if (s.charAt(0) == '0' || s == "") {
			this._coefficient = 0;
			this._power = 0;
			return;
		}
		// negative monom
		if (s.charAt(0) == '-') {
			negativeflag = true;
			i++;
		}
		// coefficient is 1
		if (s.charAt(i) == 'x')
			num = 1;

		// creating coefficient
		while (s.charAt(i) != 'x' && s.charAt(i) != '.') {

			if (negativeflag)
				if (i == s.length() - 1) {
					prenum = cast(s.substring(1, i + 1));
					dot = i + 1;
				} else if (s.charAt(i + 1) == '.' || s.charAt(i + 1) == 'x') {
					prenum = cast(s.substring(1, i + 1));
					dot = i + 1;
				}
			if (!negativeflag)
				if (i == s.length() - 1) {
					prenum = cast(s.substring(0, i + 1));
					dot = i + 1;
				} else if (s.charAt(i + 1) == '.' || s.charAt(i + 1) == 'x') {
					prenum = cast(s.substring(0, i + 1));
					dot = i + 1;
				}
			i++;
			if (i == s.length()) {
				i--;
				break;
			}
		}

		while (i < s.length() - 1 && s.charAt(i) != 'x')
			i++;
		if (dot < s.length())
			if (s.charAt(dot) != 'x') {
				if (i != s.length() - 1 && s.charAt(i) == 'x')
					postnum = afterDotCast(s.substring(dot + 1, i));
				else if (i == s.length() - 1 && s.charAt(i) != 'x')
					postnum = afterDotCast(s.substring(dot + 1, i + 1));
				else if (i == s.length() - 1 && s.charAt(i) == 'x')
					postnum = afterDotCast(s.substring(dot + 1, i));
			}

		if (i == s.length() - 1 && s.charAt(i) != 'x')
			power = 0;

		// if coefficient didn't changed so create a full double number
		if (num == 0)
			num = prenum + postnum;

		if (negativeflag)
			num *= (-1);

		this._coefficient = num;
		i += 2;
		// creating power
		if (i < s.length())
			power = cast(s.substring(i, s.length()));
		this._power = (int) power;
	}

	/**
	 * This function add m monom to this monom, only if they got the same power.
	 * 
	 * @param m is the monom that we adding to this.
	 */
	public void add(Monom m) {
		if (m._power == this._power)
			this._coefficient += m._coefficient;
		else
			throw new RuntimeException("ERR the power of Monoms should  be even if you want to add ");
	}

	/**
	 * This function multiply m monom to this monom.
	 * 
	 * @param d the monom that we multiplying with this monom.
	 */
	public void multipy(Monom d) {
		this._power += d._power;
		this._coefficient *= d._coefficient;
	}

	/**
	 * Presenting this monom as a string.
	 */
	public String toString() {
		if (this._coefficient == 0)
			return "0";
		else if (this._power == 1 && (this._coefficient == 1 || this._coefficient == -1))
			return (this._coefficient == 1) ? "x" : "-x";
		else if (this._power == 1 && this._coefficient != 1)
			return this._coefficient + "x";
		else if (this._power != 0 && (this._coefficient == -1 || this._coefficient == 1) && this._power != 1)
			return (this._coefficient == 1) ? "x^" + this._power : "-x^" + this._power;
		else if (this._power == 0)
			return "" + this._coefficient;
		else
			return this._coefficient + "x^" + this._power;
	}
	// you may (always) add other methods.

	/**
	 * This function checking if m monom and this monom are equals.
	 * 
	 * @param m us the monom the we equaling to this monom.
	 * @return true if m and this monom are equal, else false.
	 */
	public boolean equals(Monom m) {
		if (this._coefficient == m._coefficient && this._power == m._power)
			return true;
		else if (this._coefficient == m._coefficient + EPSILON && this._power == m._power)
			return true;
		else if (this._coefficient == m._coefficient - EPSILON && this._power == m._power)
			return true;
		else
			return false;
	}

	/**
	 * This function is casting integer number,from string to int.
	 * 
	 * @param num is the number that we casting.
	 * @return the number thar we casted as a double.
	 */
	public double cast(String num) {
		// casting by ASCII table
		double ans = 0;
		for (int i = 0; i < num.length(); i++) {
			ans = ans + (int) (num.charAt(i) - 48) * Math.pow(10, (num.length() - i - 1));
		}
		return ans;
	}

	/**
	 * This function casting number that lower than 1 from string to double.
	 * 
	 * @param num is the number that we casting.
	 * @return the number that we casted as double<1.
	 */
	public static double afterDotCast(String num) {
		// casting by ASCII table
		double ans = 0;
		for (int i = 0; i < num.length(); i++) {
			ans = ans + (int) (num.charAt(num.length() - i - 1) - 48) * Math.pow(10, (-1) * (num.length() - i));
		}
		return ans;
	}
	// ****************** Private Methods and Data *****************

	private void set_coefficient(double a) {
		this._coefficient = a;
	}

	private void set_power(int p) {
		if (p < 0) {
			throw new RuntimeException("ERR the power of Monom should not be negative, got: " + p);
		}
		this._power = p;
	}

	private static Monom getNewZeroMonom() {
		return new Monom(ZERO);
	}

	private double _coefficient;
	private int _power;

}
