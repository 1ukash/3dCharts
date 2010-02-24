package org.charts3d;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.charts3d.xmldatabuilder.CoordsXMLParser;

public class Test {
	public static void main(String[] args) {
		FileInputStream is = null;
		try {
			is = new FileInputStream("coords.xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		CoordsXMLParser xmlp = new CoordsXMLParser();
		try {
			xmlp.parse(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}