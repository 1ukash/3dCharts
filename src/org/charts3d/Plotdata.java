package org.charts3d;
import java.util.ArrayList;
import java.util.HashMap;

import net.masagroup.jzy3d.maths.Coord2d;
import net.masagroup.jzy3d.maths.Coord3d;

public class Plotdata{
	
	Coord3d[] coords;
	Coord2d[] coord2d;
	ArrayList<String> alx = new ArrayList<String>();
	ArrayList<String> aly = new ArrayList<String>();
	ArrayList<String> alz = new ArrayList<String>();
	HashMap<String,String> hm = new HashMap<String,String>();
	
	
	public void addX(String a) {
		alx.add(a);
		//System.out.println("alx = " + alx);
	}
	
	public void addY(String a) {
		aly.add(a);
		//System.out.println("aly = " + aly);
	}

	public void addZ(String a) {
		alz.add(a);
		//System.out.println("alz = " + alz);
	}

	public void alToCd(int flag) {
		if(flag==2) {
			coord2d = new Coord2d[alx.size()];
			for(int i=0;i<alx.size();i++) {
				float fx = Float.valueOf((alx.get(i)).trim());
				float fy = Float.valueOf((aly.get(i)).trim());
				coord2d[i] = new Coord2d(fx,fy);
				System.out.println("coords" + "[" + i + "] = " + coord2d[i].toString());
			}
			
			}
		else {
			coords = new Coord3d[alx.size()];
			for(int i=0;i<alx.size();i++) {
				float fx = Float.valueOf((alx.get(i)).trim());
				float fy = Float.valueOf((aly.get(i)).trim());
				float fz = Float.valueOf((alz.get(i)).trim());
				coords[i] = new Coord3d(fx,fy,fz);
				System.out.println("coords" + "[" + i + "] = " + coords[i].toString());
			}
		}
	}
	
	public void attrToMap(String axis,String name) {
		hm.put(axis, name);
	}

}