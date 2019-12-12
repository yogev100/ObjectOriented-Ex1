
package Ex1;

import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	/**
	 * copy constructor
	 * @param ot - monom type variable
	 */
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	/**
	 * this method returns the value of f(x) in x point.
	 */
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ***************** add your code below **********************
	/**
	 * function that check if the string is valid, and split the string to some cases and in the end,
	 * put the right coefficient and power to the new monom.
	 * if the string includes invalid number, the function will throw an exception
	 * @param s - get monom as string and build a monom object
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
		if ((s.charAt(0) == '0' || s == "")&&(s.length()-1>0&&s.charAt(1)!='.')) {
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
 * a void function that gets a monom object and add it to this monom
 * @param m - A monom type object that have been added to our monom
 */
	public void add(Monom m) {
		if(this._power!=m._power) {
			throw new  ArithmeticException("its not a monom");
		}
		else {
			this._coefficient=this._coefficient+m._coefficient;
		}
	}

	public void multipy(Monom d) {
		this._coefficient=this._coefficient*d._coefficient;
		this._power=this._power+d._power;
	}

	public String toString() {
		String ans = "";
		if(this._coefficient==0) return "0";
		if(this._coefficient==1&&this._power==1)ans= "x"; 
		if(this._coefficient==-1&&this._power==1)ans= "-x"; 
		if(this._power==0)ans= this._coefficient+"";
		if(this._coefficient!=1&&this._coefficient!=-1&&this._power>1)

			ans= this._coefficient+"x^"+this._power;
		if(this._coefficient==1&&this._power>1)
			ans="x^"+this._power;
		if(this._coefficient==-1&&this._power>1)
			ans="-x^"+this._power;
		if((this._coefficient<1&&this._coefficient>0&&this._power==1)||this._coefficient>1&&this._power==1)
			ans=this._coefficient+"x";
		if((this._coefficient>-1&&this._coefficient<0&&this._power==1)||this._coefficient<-1&&this._power==1)
			ans=this._coefficient+"x";

		return ans;

	}
	// you may (always) add other methods.
	
	/**
	 * This function is casting integer number,from string to int.
	 * 
	 * @param num is the number that we casting.
	 * @return the number thar we casted as a double.
	 */
	private double cast(String num) {
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
	private static double afterDotCast(String num) {
		// casting by ASCII table
		double ans = 0;
		for (int i = 0; i < num.length(); i++) {
			ans = ans + (int) (num.charAt(num.length() - i - 1) - 48) * Math.pow(10, (-1) * (num.length() - i));
		}
		return ans;
	}

	/**
	 * a function that returns a boolean object if this monom is logically equal to another function.
	 * @param p1 - function that compared to this monom .
	 * @return a boolean object
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
	//****************** Private Methods and Data *****************

	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new ArithmeticException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
	@Override
	public function initFromString(String s) {
		function f=new Monom(s);
		return f;
	}
	@Override
	public function copy() {
		function f=new Monom(this.toString());
		return f;
	}


}
=======

package Ex1;

import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	/**
	 * copy constructor
	 * @param ot - monom type variable
	 */
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	/**
	 * this method returns the value of f(x) in x point.
	 */
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ***************** add your code below **********************
	/**
	 * function that check if the string is valid, and split the string to some cases and in the end,
	 * put the right coefficient and power to the new monom.
	 * if the string includes invalid number, the function will throw an exception
	 * @param s - get monom as string and build a monom object
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
		if ((s.charAt(0) == '0' || s == "")&&(s.length()-1>0&&s.charAt(1)!='.')) {
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
 * a void function that gets a monom object and add it to this monom
 * @param m - A monom type object that have been added to our monom
 */
	public void add(Monom m) {
		if(this._power!=m._power) {
			throw new  ArithmeticException("its not a monom");
		}
		else {
			this._coefficient=this._coefficient+m._coefficient;
		}
	}

	public void multipy(Monom d) {
		this._coefficient=this._coefficient*d._coefficient;
		this._power=this._power+d._power;
	}

	public String toString() {
		String ans = "";
		if(this._coefficient==0) return "0";
		if(this._coefficient==1&&this._power==1)ans= "x"; 
		if(this._coefficient==-1&&this._power==1)ans= "-x"; 
		if(this._power==0)ans= this._coefficient+"";
		if(this._coefficient!=1&&this._coefficient!=-1&&this._power>1)

			ans= this._coefficient+"x^"+this._power;
		if(this._coefficient==1&&this._power>1)
			ans="x^"+this._power;
		if(this._coefficient==-1&&this._power>1)
			ans="-x^"+this._power;
		if((this._coefficient<1&&this._coefficient>0&&this._power==1)||this._coefficient>1&&this._power==1)
			ans=this._coefficient+"x";
		if((this._coefficient>-1&&this._coefficient<0&&this._power==1)||this._coefficient<-1&&this._power==1)
			ans=this._coefficient+"x";

		return ans;

	}
	// you may (always) add other methods.
	
	/**
	 * This function is casting integer number,from string to int.
	 * 
	 * @param num is the number that we casting.
	 * @return the number thar we casted as a double.
	 */
	private double cast(String num) {
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
	private static double afterDotCast(String num) {
		// casting by ASCII table
		double ans = 0;
		for (int i = 0; i < num.length(); i++) {
			ans = ans + (int) (num.charAt(num.length() - i - 1) - 48) * Math.pow(10, (-1) * (num.length() - i));
		}
		return ans;
	}

	/**
	 * auxiliary function thats compare this monom to another monom and returns a boolean variable.
	 * @param m - monom that compared to this monom .
	 * @return
	 */
	public boolean equals(Monom m) {
		if(this._coefficient==0&&m._coefficient==0) {
			return true;
		}
		if(this._coefficient!=m._coefficient||this._power!=m._power) {
			return false;
		}
		return true;
	}
	//****************** Private Methods and Data *****************

	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new ArithmeticException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
	@Override
	public function initFromString(String s) {
		function f=new Monom(s);
		return f;
	}
	@Override
	public function copy() {
		function f=new Monom(this.toString());
		return f;
	}



}
>>>>>>> 7ff7a49d6e31b3832f452495384a34a65fe45e1e
