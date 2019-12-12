package Ex1Testing;


import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Ex1.ComplexFunction;
import Ex1.Monom;
import Ex1.Operation;
import Ex1.Polynom;

public class JUnitComplexFunctionTest {
	static int i=1;
	ComplexFunction t1=new ComplexFunction();
	ComplexFunction t2=new ComplexFunction(new Polynom("7x-5"));
	ComplexFunction t3=new ComplexFunction(new Polynom("-x^4+4x"), new Polynom("2x^2-8x+1"), Operation.Times);
	
	
	@BeforeClass
	public static void bfrClass() {
		System.out.println("ComplexFunction Test");
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
	public void testInitFromString() {
		assertEquals(null,t1.initFromString(t1.toString()));
		assertEquals(t2,t2.initFromString(t2.toString()));
		assertEquals(t3,t3.initFromString(t3.toString()));
		
		ComplexFunction test=new ComplexFunction(new ComplexFunction(new Polynom("3x-4"), new Monom("5"), Operation.Divid),
				new Polynom("-x^5-6"), Operation.Comp);
		test.mul(new Polynom("7x^3-x"));
		
		assertEquals(test,test.initFromString("mul(comp(div(3x-4,5),-x^5-6),7x^3-x)"));
		
		
	}
	
	@Test
	public void testCopy() {
		ComplexFunction test1=(ComplexFunction) t1.copy();
		ComplexFunction test2=(ComplexFunction) t2.copy();
		ComplexFunction test3=(ComplexFunction) t3.copy();
		assertEquals(null,test1);
		assertEquals("7.0x-5.0",test2.toString());
		assertEquals("mul(-x^4+4.0x,2.0x^2-8.0x+1.0)",test3.toString());
		
	}
	@Test
	public void testPlus() {
		t1.plus(new Polynom("2x-2"));
		t2.plus(new Monom("x"));
		t3.plus(new ComplexFunction(new Monom("3"),new Polynom("x^2+4"),Operation.Divid));
		
		assertEquals("plus(,2.0x-2.0)",t1.toString());
		assertEquals("plus(7.0x-5.0,x)",t2.toString());
		assertEquals("plus(mul(-x^4+4.0x,2.0x^2-8.0x+1.0),div(3.0,x^2+4.0))",t3.toString());
	}
	
	@Test
	public void testMul() {
		t1.mul(new Polynom("-14x^3+7x+5"));
		t2.mul(new Monom("2x^2"));
		t3.mul(new Polynom("12x-6"));
		assertEquals("mul(,-14.0x^3+7.0x+5.0)",t1.toString());
		assertEquals("mul(7.0x-5.0,2.0x^2)",t2.toString());
		assertEquals("mul(mul(-x^4+4.0x,2.0x^2-8.0x+1.0),12.0x-6.0)",t3.toString());

	}
	
	@Test
	public void testDiv() {
		t1.div(new Polynom("x-3"));
		t2.div(new Monom("2x"));
		t3.div(new ComplexFunction(new Polynom("12x-6"),new Monom("2"),Operation.Max));
		assertEquals("div(,x-3.0)",t1.toString());
		assertEquals("div(7.0x-5.0,2.0x)",t2.toString());
		assertEquals("div(mul(-x^4+4.0x,2.0x^2-8.0x+1.0),max(12.0x-6.0,2.0))",t3.toString());	
	}
	
	@Test
	public void testMinMax() {
		t1.min(new Polynom("x^2-9x"));
		t2.max(new Monom("15"));
		t3.min(new ComplexFunction(new Polynom("-x^2-x-1"),new Polynom("2x^3+5x^2"),Operation.Max));
		assertEquals("min(,x^2-9.0x)",t1.toString());
		assertEquals("max(7.0x-5.0,15.0)",t2.toString());
		assertEquals("min(mul(-x^4+4.0x,2.0x^2-8.0x+1.0),max(-x^2-x-1.0,2.0x^3+5.0x^2))",t3.toString());
		
	}
	
	@Test
	public void testComp() {
		t1.comp(new Polynom("17x^4-2x"));
		t2.comp(new Polynom("9x-12"));
		t3.comp(new ComplexFunction(new Polynom("-10x-5"),new Monom("-15x^2"),Operation.Max));
		assertEquals("comp(,17.0x^4-2.0x)",t1.toString());
		assertEquals("comp(7.0x-5.0,9.0x-12.0)",t2.toString());
		assertEquals("comp(mul(-x^4+4.0x,2.0x^2-8.0x+1.0),max(-10.0x-5.0,-15.0x^2))",t3.toString());
	}
	
	@Test
	public void testLeft() {
		assertEquals(null,t1.left());
		assertEquals("7.0x-5.0",t2.left().toString());
		assertEquals("-x^4+4.0x",t3.left().toString());
		
		t2.div(new Polynom("5x-4"));
		t3.comp(new ComplexFunction(new Monom("8x"),new Polynom("-9x-3"),Operation.Min));
		assertEquals("7.0x-5.0",t2.left().toString());
		assertEquals("mul(-x^4+4.0x,2.0x^2-8.0x+1.0)",t3.left().toString());
	}
	
	@Test
	public void testRight() {
		assertEquals(null,t1.right());
		assertEquals(null,t2.right());
		assertEquals(new ComplexFunction(t3.initFromString("2.0x^2-8.0x+1.0")),t3.right());
	}
	
	@Test
	public void testOp() {
		assertEquals(null,t1.getOp());
		assertEquals(Operation.None,t2.getOp());
		assertEquals(Operation.Times,t3.getOp());
	}
	
	@Test
	public void testToString() {
		assertEquals("",t1.toString());
		assertEquals("7.0x-5.0",t2.toString());
		assertEquals("mul(-x^4+4.0x,2.0x^2-8.0x+1.0)",t3.toString());
	}
	
	@Test
	public void testEquals() {
		assertFalse(t1.equals(new ComplexFunction(new Monom("5x"))));
		assertTrue(t2.equals(new ComplexFunction(new Polynom("7x-5"))));
		assertTrue(t3.equals(new ComplexFunction(new Polynom("-x^4+4x"), new Polynom("2x^2-8x+1"), Operation.Times)));
		
		ComplexFunction eq=new ComplexFunction(new Polynom("2x^2-8x+1"), new Polynom("-x^4+4x"), Operation.Times);
		assertTrue(eq.equals(t3));
		
		Polynom test=new Polynom("7x-5");
		assertEquals(t2,test);
		
		
		
	}
	
	@Test(expected=ArithmeticException.class)
	public void testError1() {
		t3.initFromString("comp(7x-5,div(x,2x-1))))))");
		
	}
	
	@Test(expected=NumberFormatException.class)
	public void testError2() {
		t3.initFromString("comp(7x-5,div(x,2rx-1))");
		
	}
	
	@Test
	public void testFx() {
		assertEquals(9.0,t2.f(2),0);
		assertEquals(-15.0,t3.f(1),0);
		t2.max(new Polynom("-x-4"));
		t3.div(new Polynom("-x^2+3x"));
		assertEquals(16.0,t2.f(3),0);
		assertEquals(-7.5,t3.f(1),0);
		
	}
}
