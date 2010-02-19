package org.charts3d;
import org.charts3d.xmldatabuilder.XMLParse;
import net.masagroup.jzy3d.maths.Coord2d;
//import net.masagroup.jzy3d.maths.Coord3d;

public class Test {
	public static void main(String[] args) {
		Plotdata pdata = new Plotdata();
		XMLParse xmlp = new XMLParse("coords.xml");
		pdata = xmlp.parse(2);
		//Coord3d[] cds;
		Coord2d[] cd2s;
		//cds = pdata.coords;
		cd2s = pdata.coord2d;
		int d = cd2s.length;
		for(int i=0;i<d;i++) {
			System.out.println("coords" + "[" + i + "] = " + cd2s[i].toString());
		}
		System.out.println("x : " + pdata.hm.get("x"));
		System.out.println("y : " + pdata.hm.get("y"));
		System.out.println("z : " + pdata.hm.get("z"));
	}
}