package org.charts3d;
import org.charts3d.xmldatabuilder.XMLParse;
//import net.masagroup.jzy3d.maths.Coord2d;
import net.masagroup.jzy3d.maths.Coord3d;

public class Test {
	public static void main(String[] args) {
		Plotdata pdata = new Plotdata();
		XMLParse xmlp = new XMLParse("coords.xml");
		int numof = xmlp.numOfGraphs();
		int numofp = xmlp.numOfPoints(1);
		pdata = xmlp.parse(1);
		Coord3d[] cd3s = null;
		cd3s = pdata.getCoord3d();
		int d = cd3s.length;
		for(int i=0;i<d;i++) {
			System.out.println("coords" + "[" + i + "] = " + cd3s[i].toString());
		}
		System.out.println("x : " + pdata.hm.get("x"));
		System.out.println("y : " + pdata.hm.get("y"));
		System.out.println("z : " + pdata.hm.get("z"));
	}
}