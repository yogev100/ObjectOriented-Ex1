package Ex1;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


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
//
//	@Test
//	public void testNumDividers() {
//		assertEquals(1, Functions.numDividers(25));
//		assertEquals(7, Functions.numDividers(36));
//	}
//
//	@Test
//	public void testReverse() {
//		assertEquals("dcba",Functions.reverse("abcd"));
//		assertEquals("a",Functions.reverse("a"));		
//	}
//	@Test(timeout=500)
//	public void testInfinity(){
//		Functions.infinity();
//	}
//		@Test
//	public void testLongestMonotony() {
//		int[]a1 = {1,2,1,5,6,7,0,-1}, a1Ans = {1,5,6,7};
//		int[]a2 = {1,2,5,6,7,0,-1}, a2Ans = {1,2,5,6,7};
//		int[]a3 = {1,2,-1,5,6,7}, a3Ans = {-1,5,6,7};
//		assertTrue(Arrays.equals(a1Ans, Functions.longestMonotony(a1)));
//		assertEquals(Arrays.equals(a2Ans, Functions.longestMonotony(a2)), true);
//		assertEquals(Arrays.equals(a3Ans, Functions.longestMonotony(a3)), true);
//	}



