<<<<<<< HEAD
package Ex1Testing;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Ex1.Monom;


public class JUnitMonomTest {


	public int i=1;
	Monom test1=new Monom("3x^2");
	Monom test2=new Monom("-4.2x");
	Monom test3=new Monom("0.x");


	@BeforeClass
	public static void bfrClass() {
		System.out.println("Monom Test");
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
	public void testMonomConstructor() {
		assertEquals("3.0x^2",test1.toString());
		assertEquals("-4.2x", test2.toString());
	}
	@Test(expected=NumberFormatException.class)
	public void testMonomErr() {
		Monom test3=new Monom("5f^4");
		
	}
	@Test
	public void testMonomDer() {
		assertEquals("6.0x", test1.derivative().toString());
		assertEquals("-4.2", test2.derivative().toString());

	}
	@Test
	public void testAdd() {
		Monom t=new Monom("-2x^2");
		Monom r=new Monom("-5x");
		test1.add(t);
		test2.add(r);
		assertEquals("x^2", test1.toString());
		assertEquals("-9.2x", test2.toString());

	}

	@Test
	public void testMul() {
		Monom mul=new Monom("8x");
		test1.multipy(mul);
		test2.multipy(mul);
		test3.multipy(mul);
		assertEquals("24.0x^3",test1.toString());
		assertEquals("-33.6x^2", test2.toString());
		assertEquals("0", test3.toString());
	}
	
	@Test
	public void testEquals() {
		Monom t=new Monom("3x^2");
		assertTrue(test1.equals(t));
		Monom r=new Monom("5x^5");
		assertFalse(test1.equals(r));
		Monom z=new Monom("0x^3");
		assertTrue(test3.equals(z));
	}
	@Test
	public void testZero() {
		Monom z=new Monom("0x^4");
		assertTrue(test3.isZero());
		assertTrue(z.isZero());
		assertFalse(test1.isZero());
		assertFalse(test2.isZero());
	}
}




=======
package Ex1Testing;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Ex1.Monom;


public class JUnitMonomTest {


	public int i=1;
	Monom test1=new Monom("3x^2");
	Monom test2=new Monom("-4.2x");
	Monom test3=new Monom("0.x");


	@BeforeClass
	public static void bfrClass() {
		System.out.println("Monom Test");
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
	public void testMonomConstructor() {
		assertEquals("3.0x^2",test1.toString());
		assertEquals("-4.2x", test2.toString());
	}
	
	@Test(expected=ArithmeticException.class)
	public void testMonomErr() {
		Monom test3=new Monom("5f^4");
		
	}
	@Test
	public void testMonomDer() {
		assertEquals("6.0x", test1.derivative().toString());
		assertEquals("-4.2", test2.derivative().toString());

	}
	@Test
	public void testAdd() {
		Monom t=new Monom("-2x^2");
		Monom r=new Monom("-5x");
		test1.add(t);
		test2.add(r);
		assertEquals("x^2", test1.toString());
		assertEquals("-9.2x", test2.toString());

	}

	@Test
	public void testMul() {
		Monom mul=new Monom("8x");
		test1.multipy(mul);
		test2.multipy(mul);
		test3.multipy(mul);
		assertEquals("24.0x^3",test1.toString());
		assertEquals("-33.6x^2", test2.toString());
		assertEquals("0", test3.toString());
	}
	
	@Test
	public void testEquals() {
		Monom t=new Monom("3x^2");
		assertTrue(test1.equals(t));
		Monom r=new Monom("5x^5");
		assertFalse(test1.equals(r));
		Monom z=new Monom("0x^3");
		assertTrue(test3.equals(z));
	}
	@Test
	public void testZero() {
		Monom z=new Monom("0x^4");
		assertTrue(test3.isZero());
		assertTrue(z.isZero());
		assertFalse(test1.isZero());
		assertFalse(test2.isZero());
	}
}



>>>>>>> 7ff7a49d6e31b3832f452495384a34a65fe45e1e
