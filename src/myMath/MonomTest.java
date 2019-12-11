package myMath;
import java.util.ArrayList;
/**
 * This class represents a simple (naive) tester for the Monom class, 
 * Note: <br>
 * (i) The class is NOT a JUNIT - (i.e., educational reasons) - should be changed to a proper JUnit in Ex1. <br>
 * (ii) This tester should be extend in order to test ALL the methods and functionality of the Monom class.  <br>
 * (iii) Expected output:  <br>
 * *****  Test1:  *****  <br>
0) 2.0    	isZero: false	 f(0) = 2.0  <br>
1) -1.0x    	isZero: false	 f(1) = -1.0  <br>
2) -3.2x^2    	isZero: false	 f(2) = -12.8  <br>
3) 0    	isZero: true	 f(3) = 0.0  <br>
*****  Test2:  *****  <br>
0) 0    	isZero: true  	eq: true  <br>
1) -1.0    	isZero: false  	eq: true  <br>
2) -1.3x    	isZero: false  	eq: true  <br>
3) -2.2x^2    	isZero: false  	eq: true  <br>
*****  Test3:  *****
0)Multiply: 0    	add: 2.0  	ToString: 0 , 2.0 <br>
1)Multiply: not same power   	add: -1.0  	ToString: x-1.0 <br>
2)Multiply: 4.16x^4    	add: -4.5x^2  	ToString: 4.16x^4 , -4.5x^2 <br>
3)Multiply: -8.0x^6    	add: 2.0x^3  	ToString: -8.0x^6 , 2.0x^3 <br>
 */
public class MonomTest {
	public static void main(String[] args) {
		test1();
		test2();
		test3();
	}
	private static void test1() {
		System.out.println("*****  Test1:  *****");
		String[] monoms = {"2", "-x","-3.2x^2","0"};
		for(int i=0;i<monoms.length;i++) {
			Monom m = new Monom(monoms[i]);
			String s = m.toString();
			m = new Monom(s);
			double fx = m.f(i);
			System.out.println(i+") "+m +"    \tisZero: "+m.isZero()+"\t f("+i+") = "+fx);
		}
	}
	private static void test2() {
		System.out.println("*****  Test2:  *****");
		ArrayList<Monom> monoms = new ArrayList<Monom>();
		monoms.add(new Monom(0,5));
		monoms.add(new Monom(-1,0));
		monoms.add(new Monom(-1.3,1));
		monoms.add(new Monom(-2.2,2));
		
		for(int i=0;i<monoms.size();i++) {
			Monom m = new Monom(monoms.get(i));
			String s = m.toString();
			Monom m1 = new Monom(s);
			boolean e = m.equals(m1);
			System.out.println(i+") "+m +"    \tisZero: "+m.isZero()+"  \teq: "+e);
		}
	}
	private static void test3() {
		System.out.println("*****  Test3:  *****");
		String[] monoms1 = {"2", "-x","-3.2x^2","4x^3"};
		ArrayList<Monom> monoms2 = new ArrayList<Monom>();
		monoms2.add(new Monom(0,5));
		monoms2.add(new Monom(-1,0));
		monoms2.add(new Monom(-1.3,2));
		monoms2.add(new Monom(-2,3));
		for(int i=0;i<monoms2.size();i++) {
			Monom m1 = new Monom(monoms2.get(i));
			Monom m2 = new Monom(monoms1[i]);
			Monom m3 = new Monom(monoms2.get(i));
			try {
			m1.multipy(m2);
			m3.add(m2);
			System.out.println(i+")Multiply: "+ m1 +"    \tadd: "+m3+"  \tToString: "+m1.toString()+" , "+m3.toString());
			}catch(Exception e) {
				System.out.println(i+")Multiply: not same power" +"   \tadd: "+m3+"  \tToString: -- , "+m3.toString());
			}
		}
	}
}
