package Ex1;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class JUnitPolynomTest {
	
	static int i=1;
	Polynom test1=new Polynom("4x^3+2x^2-8x");
	Polynom test2=new Polynom("-4x^2-3");
	Polynom test3=new Polynom();
	
	@BeforeClass
	public static void bfrClass() {
		System.out.println("Polynom Test");
	}
	@AfterClass
	public static void aftClass() {
		System.out.println("Finished Test");
	}

	@Before
	public void bef() {
		System.out.println("Test number "+i);
		i++;
	}
	
	@Test
	public void testPolConstructor() {
		assertEquals("4.0x^3+2.0x^2-8.0x",test1.toString());
		assertEquals("-4.0x^2-3.0",test2.toString());
		assertEquals("0", test3.toString());
	}
	@Test(expected=NumberFormatException.class)
	public void testPolErr() {
		Polynom e=new Polynom("3x^2+5x-6z");
			
	}
	
	@Test
	public void testPolFx() {
		assertEquals(24, test1.f(2), 0);
		assertEquals(-7, test2.f(1), 0);
	}
	@Test
	public void testAdd() {
		Polynom add=new Polynom("-3x^2+7x+10");
		test1.add(add);
		test2.add(add);
		test3.add(add);
		assertEquals("4.0x^3-x^2-x+10.0", test1.toString());
		assertEquals("-7.0x^2+7.0x+7.0", test2.toString());
		assertEquals("-3.0x^2+7.0x+10.0", test3.toString());

	}
	@Test
	public void testSub() {
		Polynom sub=new Polynom("x^3-5x");
		test1.substract(sub);
		test2.substract(sub);
		test3.substract(sub);
		assertEquals("3.0x^3+2.0x^2-3.0x",test1.toString());
		assertEquals("-x^3-4.0x^2+5.0x-3.0",test2.toString());
		assertEquals("-x^3+5.0x",test3.toString());

	}
	@Test
	public void testMul() {
		Polynom mul=new Polynom("-2x^2+6x");
		test1.multiply(mul);
		test2.multiply(mul);
		test3.multiply(mul);
		assertEquals("-8.0x^5+20.0x^4+28.0x^3-48.0x^2",test1.toString());
		assertEquals("8.0x^4-24.0x^3+6.0x^2-18.0x",test2.toString());
		assertEquals("0",test3.toString());
	}
	
	@Test
	public void testEquals() {
		Polynom t=new Polynom("-4x^2-3");
		assertFalse(test1.equals(t));
		assertTrue(test2.equals(t));
		assertFalse(test3.equals(t));
	}
	
	@Test
	public void testZero() {
		Polynom z=new Polynom("0x^3+0");
		assertFalse(test1.isZero());
		assertFalse(test2.isZero());
		assertTrue(test3.isZero());
		assertTrue(z.isZero());
	}
	
	@Test
	public void testCopy() {
		Polynom c1=(Polynom) test1.copy();
		Polynom c2=(Polynom) test2.copy();
		Polynom c3=(Polynom) test3.copy();
		assertTrue(test1.equals(c1));
		assertTrue(test2.equals(c2));
		assertTrue(test3.equals(c3));

	}
	
	@Test
	public void testDer() {
		Polynom t1=(Polynom) test1.derivative();
		Polynom t2=(Polynom) test2.derivative();
		Polynom t3=(Polynom) test3.derivative();
		assertEquals("12.0x^2+4.0x-8.0",t1.toString());
		assertEquals("-8.0x",t2.toString());
		assertEquals("0",t3.toString());
	}
	
	@Test
	public void testArea() {
		Polynom a1=new Polynom("x^2+1");
		Polynom a2=new Polynom("5x");
		Polynom a3=new Polynom("x^3-6");
		assertEquals(12.045049999999954,a1.area(0, 3, 0.01),0);
		assertEquals(22.57499999999998,a2.area(-2, 3, 0.01),0);
		assertEquals(0.0,a3.area(-3, 0, 0.01),0);
		
	}
	@Test
	public void testRoot() {
		Polynom r1=new Polynom("x^2-3");
		Polynom r2=new Polynom("3x+3");
		Polynom r3=new Polynom("x^3+5");
		assertEquals(1.7414515271246325,r1.root(0, 2, 0.1),0);
		assertEquals(-1.0,r2.root(-2, 0, 0.1),0);
		assertEquals(-1.7101808693816598,r3.root(-2, 1, 0.1),0);
		
	}
}
