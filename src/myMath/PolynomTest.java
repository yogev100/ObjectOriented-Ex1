package myMath;

public class PolynomTest {
	public static void main(String[] args) {
		test1();
		test2();
		test3();
	}
	public static void test1() {
		System.out.println("*****  Test1:  *****");
		Polynom p1 = new Polynom();
		String[] monoms = {"1","x","x^2", "0.5x^2"};
		for(int i=0;i<monoms.length;i++) {
			Monom m = new Monom(monoms[1]);
			p1.add(m);
			p1.substract(p1);
			System.out.println(p1);
		}
	}
	public static void test2() {
		System.out.println("*****  Test2:  *****");
		Polynom p1 = new Polynom(), p2 =  new Polynom();
		String[] monoms1 = {"2", "-x","-3.2x^2","4","-1.5x^2"};
		String[] monoms2 = {"5", "1.7x","3.2x^2","-3","-1.5x^2"};
		for(int i=0;i<monoms1.length;i++) {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
		}
		for(int i=0;i<monoms2.length;i++) {
			Monom m = new Monom(monoms2[i]);
			p2.add(m);
		}
		System.out.println("p1: "+p1);
		System.out.println("p2: "+p2);
		p1.add(p2);
		System.out.println("p1+p2: "+p1);
		p1.multiply(p2);
		System.out.println("(p1+p2)*p2: "+p1);
	}
	
	public static void test3() {
		System.out.println("*****  Test3:  *****");
		String[] polynoms = {"x^4-4x^2", "x^2+1","-3.2x^2+4rx","4","-1.5x^2+x-1"};
		for(int i=0;i<polynoms.length;i++) {
			try {
				Polynom m = new Polynom(polynoms[i]);
				System.out.println(i+") "+m.toString());
			}catch(ArithmeticException e) {
				System.out.println(i+") Invalid syntax for polynom.");
			}
			
		}
	}
}
