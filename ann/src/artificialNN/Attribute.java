package artificialNN;

import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

class Attribute {
	String name;
	String[] vals = {};
	boolean cont;
	int index;
	boolean input;

	/**
	 * constructor (receives a file line and creates an attribute)
	 * 
	 * @param line
	 * @param index
	 * @param input
	 */
	public Attribute(String line, int index, boolean input) {
		Scanner sc = new Scanner(line);
		name = sc.next();
		String temp = sc.next();
		if (temp.equals("continuous")) {
			cont = true;
		} else {
			ArrayList<String> tempAL = new ArrayList<String>();
			tempAL.add(temp);
			while (sc.hasNext()) {
				tempAL.add(sc.next());
			}
			vals = tempAL.toArray(vals);
		}
		this.index = index;
		this.input = input;
	}

	/**
	 * prints an attribute
	 */
	public void print() {
		System.out.print(name + " values:" + Arrays.toString(vals));
		System.out.print(" cont:" + cont + " input:" + input);
		System.out.println(" index:" + index);
	}
}
