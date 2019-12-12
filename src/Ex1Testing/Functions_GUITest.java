package Ex1Testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Ex1.ComplexFunction;
import Ex1.Functions_GUI;
import Ex1.Monom;
import Ex1.Operation;
import Ex1.Polynom;
import Ex1.function;
import Ex1.functions;

/**
 * Note: minor changes (thanks to Amichai!!)
 * The use of "get" was replaced by iterator!
 * 
 * Partial JUnit + main test for the GUI_Functions class, expected output from the main:
 * 0) java.awt.Color[r=0,g=0,b=255]  f(x)= plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0)
1) java.awt.Color[r=0,g=255,b=255]  f(x)= plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)
2) java.awt.Color[r=255,g=0,b=255]  f(x)= div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1)
3) java.awt.Color[r=255,g=200,b=0]  f(x)= -1.0x^4 +2.4x^2 +3.1
4) java.awt.Color[r=255,g=0,b=0]  f(x)= +0.1x^5 -1.2999999999999998x +5.0
5) java.awt.Color[r=0,g=255,b=0]  f(x)= max(max(max(max(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)),div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1)),-1.0x^4 +2.4x^2 +3.1),+0.1x^5 -1.2999999999999998x +5.0)
6) java.awt.Color[r=255,g=175,b=175]  f(x)= min(min(min(min(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)),div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1)),-1.0x^4 +2.4x^2 +3.1),+0.1x^5 -1.2999999999999998x +5.0)

 * @author boaz_benmoshe 
 *
 */
class Functions_GUITest {
	private functions _data=null;
	static int i=1;
	
	@BeforeClass
	public static void bfrClass() {
		System.out.println("Functions_GUI Test");
	}
	
	@AfterClass
	public static void aftClass() {
		System.out.println("Finished Test");
	}

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("Test number "+i);
		i++;
		_data = FunctionsFactory();
	}
	
	@Test
	void testException() {
		Functions_GUI s= new Functions_GUI();
		s.addAll(_data);
		assertThrows(NumberFormatException.class, () -> s.add(new Polynom("4x^5+2s")));
	}

	@Test
	void testInitFromFile() {
		String file="function_file.txt";
		try {
			_data.clear();//remove all the elements
			_data.initFromFile(file);//initialize new function colection from file
			_data.saveToFile("function_file2.txt");//save this collection to another file
			functions tmp1=FunctionsFactory();
			functions tmp2=FunctionsFactory();
			tmp1.initFromFile(file);//create a new collection from the old file - function_file
			tmp2.initFromFile("function_file2.txt");//create a new collection from the new file - function_file2
			Functions_GUI tmp3 = (Functions_GUI) tmp1;
			Functions_GUI tmp4 = (Functions_GUI) tmp2;
			for (int i = 0; i < tmp3.size(); i++) {
				assertEquals(tmp3.c.get(i), tmp4.c.get(i));//comparing the two collections
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testSaveToFile() {
		/*
		 * same idea like the test above, the diffence is using buffered reader,
		 *  inorder to test the initfromstring.
		 */
		String file="function_file.txt";
		try {
			ComplexFunction cf = new ComplexFunction();
			Functions_GUI tmp = new Functions_GUI();
			tmp.initFromFile(file);
			tmp.saveToFile("function_file1.txt");
			String line1="",line2="";
			BufferedReader bf = new BufferedReader(new FileReader(file));
			BufferedReader bf2 = new BufferedReader(new FileReader("function_file1.txt"));
			line2=bf2.readLine(); line1=bf.readLine();
			while((line1!=null)||(line2!=null)) {
				assertEquals(cf.initFromString(line1),cf.initFromString(line2));
				line2=bf2.readLine(); line1=bf.readLine();
			}
			if(line1!=null||line2!=null) fail();
			bf.close();
			bf2.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	//General tests to the functionGUI
	@Test
	void test1() {
		assertEquals(7, _data.size());
		for (int i = 1; i < 10; i++) {
			_data.add(new Monom("4x"));
			assertEquals(7+i, _data.size());
		}
		_data.clear();
		assertEquals(0, _data.size());
	}

	@Test
	void test2() {
		assertFalse(_data.isEmpty());	
		_data.removeAll(_data);
		assertTrue(_data.isEmpty());
		_data.add(new Polynom("4x^2+5x+2"));
		assertTrue(_data.contains(new Polynom("4x^2+5x+2")));
	}
	
	@Test
	void test3() {
		Object []arr=_data.toArray();
		for (int i = 0; i < arr.length; i++) {
			assertTrue(arr[i]==((Functions_GUI)_data).c.get(i));
		}
	}

	public static functions FunctionsFactory() {
		functions ans = new Functions_GUI();
		String s1 = "3.1+2.4x^2-x^4";
		String s2 = "5+2x-3.3x+0.1x^5";
		String[] s3 = {"x+3","x-2", "x-4"};
		Polynom p1 = new Polynom(s1);
		Polynom p2 = new Polynom(s2);
		Polynom p3 = new Polynom(s3[0]);
		ComplexFunction cf3 = new ComplexFunction(p3);
		for(int i=1;i<s3.length;i++) {
			cf3.mul(new Polynom(s3[i]));
		}
		ComplexFunction cf = new ComplexFunction( p1,p2,Operation.Plus);
		ComplexFunction cf4 = new ComplexFunction( new Polynom("x+1"),cf3,Operation.Divid);
		cf4.plus(new Monom("2"));
		ans.add(cf.copy());
		ans.add(cf4.copy());
		cf.div(p1);
		ans.add(cf.copy());
		function cf5 = cf4.initFromString(s1);
		function cf6 = cf4.initFromString(s2);
		ans.add(cf5.copy());
		ans.add(cf6.copy());
		Iterator<function> iter = ans.iterator();
		function f = iter.next();
		ComplexFunction max = new ComplexFunction(f);
		ComplexFunction min = new ComplexFunction(f);
		while(iter.hasNext()) {
			f = iter.next();
			max.max(f);
			min.min(f);
		}
		ans.add(max);
		ans.add(min);
		return ans;
	}
}
