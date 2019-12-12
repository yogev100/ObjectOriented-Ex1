package Ex1;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.BufferUnderflowException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;


public class Functions_GUI implements functions{

	public static void main(String[] args) throws IOException {
		String file="C:\\Users\\Yogev\\Desktop\\.metadata\\.metadata\\ObjectOrientedEx2\\functions.json";
		Functions_GUI s=new Functions_GUI();
		s.add(new ComplexFunction(new Polynom("x"), new Polynom("2x"), Operation.Plus));
		s.add(new Polynom("x^2"));
		s.add(new Polynom("x^3"));
		s.add(new Polynom("x^4"));
		s.add(new Polynom("x^5"));
		s.add(new Polynom("x^6"));
		s.add(new Polynom("x^7"));
		s.add(new Polynom("x"));
		//		s.saveToFile(file);
		//s.initFromFile("C:\\Users\\Yogev\\Desktop\\function_file.txt");
		//		Range x= new Range(-5, 5);
		//		Range y= new Range(-5, 5);
		//s.drawFunctions(500,500, x, y, 100);
		//s.drawFunctions("C:\\\\Users\\\\Yogev\\\\Desktop\\\\.metadata\\\\.metadata\\\\ObjectOrientedEx2\\\\GUI_params.json");
		//s.saveToFile("C:\\Users\\Yogev\\Desktop\\newfunction.csv");
		Functions_GUI g=new Functions_GUI();
		g.initFromFile("function_file.txt");
		g.drawFunctions("GUI_params.txt");
	}


	public LinkedList <function> c=new LinkedList<function>();// the object that we store the functions.


	@Override
	public void initFromFile(String file) throws IOException {
		String line="";
		ComplexFunction cf=new ComplexFunction();
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(file));//for read from file
			while((line=br.readLine())!=null) {
				function t=(function) cf.initFromString(line);
				if(t!=null) {
					this.add(cf.initFromString(line));//add the function to the collection
				}
			}
		} 
		catch (IOException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
	}

	@Override
	public void saveToFile(String file) throws IOException {
		try {
			PrintWriter pw = new PrintWriter(new File(file));//for write to a file
			StringBuilder sb=new StringBuilder();

			for (int i = 0; i < this.c.size(); i++) {
				sb.append(c.get(i).toString());//write each function
				sb.append("\n");
			}
			pw.write(sb.toString());//write to the print writer
			pw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
			return;

		}


	}


	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		Color[] Colors = {Color.blue, Color.cyan,
				Color.MAGENTA, Color.ORANGE, Color.red, Color.GREEN, Color.PINK};
		//set the window size in pixels
		StdDraw.setCanvasSize(width, height);

		//rescale the coordinate system
		StdDraw.setXscale(rx.get_min(),rx.get_max());
		StdDraw.setYscale(ry.get_min(),ry.get_max());

		//vertical lines
		StdDraw.setPenColor(Color.LIGHT_GRAY);
		for (int i = (int)rx.get_min(); i <=rx.get_max(); i++) {
			StdDraw.line(i, ry.get_min(), i, ry.get_max());
		}

		//horizontal lines
		for (double i =ry.get_min(); i <=ry.get_max(); i++) {
			StdDraw.line(rx.get_min(), i, rx.get_max(), i);
		}

		//////// x axis
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.005);
		StdDraw.line(rx.get_min(),0,rx.get_max(),0);
		StdDraw.setFont(new Font("TimesRoman", Font.BOLD, 15));
		for (int i = (int)rx.get_min(); i <= rx.get_max(); i++) {
			StdDraw.text(i-0.07,-0.2, Integer.toString(i));
		}

		//////// y axis
		StdDraw.line(0,ry.get_min(),0,ry.get_max());
		for (int i = (int)ry.get_min(); i <= ry.get_max(); i++) {
			StdDraw.text(-0.2, i-0.3, Integer.toString(i));
		}
		int c=0;//color index 
		// plot the approximation to the function
		for (int j = 0; j < this.size(); j++) {
			StdDraw.setPenColor(Colors[c++]);
			if(c==6)c=0;
			function tmp=this.c.get(j).copy();
			for (double i = rx.get_min(); i < rx.get_max(); i=i+((rx.get_max()-rx.get_min())/resolution)) {
				double xi=i;
				double yi=tmp.f(xi);
				double xip=i+((rx.get_max()-rx.get_min())/resolution);
				double yip=tmp.f(xip);
				StdDraw.line(xi,yi,xip,yip);
			}
		}
	}


	@Override
	public void drawFunctions(String json_file) {
		// parsing file "GUI_params.json" 
		Object obj = null;
		try {
			JSONParser jp = new JSONParser();
			FileReader fr = new FileReader(json_file);
			obj = jp.parse(fr);
			//obj = new JSONParser().parse(new FileReader(json_file));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} 

		// type casting obj to JSONObject 
		JSONObject jo = (JSONObject) obj; 

		// getting width, height,resolution,rx and ry  
		long width = (long) jo.get("Width"); 
		long height = (long) jo.get("Height"); 
		long resolution =(long) jo.get("Resolution");

		JSONArray arrx=(JSONArray) jo.get("Range_X");
		JSONArray arry=(JSONArray) jo.get("Range_Y");

		long minx=(long)arrx.get(0);
		long maxx=(long)arrx.get(1);
		long miny=(long)arry.get(0);
		long maxy=(long)arry.get(1);

		Range rx=new Range(minx,maxx);
		Range ry=new Range(miny,maxy);

		this.drawFunctions((int)width, (int)height, rx, ry,(int) resolution);
	}

	@Override
	public int size() {
		return c.size();
	}

	@Override
	public boolean isEmpty() {
		return c.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		if(o instanceof function) {
			function n=(function)o;
			Iterator<function> it=this.c.iterator();
			while(it.hasNext()) {
				function temp=it.next();
				if(temp.equals(n)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	@Override
	public Iterator<function> iterator() {
		Iterator<function> it=c.iterator();
		return it;
	}

	@Override
	public Object[] toArray() {
		return c.toArray();
	}

	@Override//@@@@@@@@@@@@@@@@@@@@
	public <T> T[] toArray(T[] a) {
		return c.toArray(a);
	}

	@Override
	public boolean add(function e) {
		c.addLast(e);
		return true;
	}

	@Override
	public boolean remove(Object o) {
		return c.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return c.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends function> c) {
		return this.c.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return this.c.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.c.retainAll(c);
	}

	@Override
	public void clear() {
		c.clear();
	}




}
