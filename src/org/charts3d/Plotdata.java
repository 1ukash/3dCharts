package org.charts3d;
import java.util.ArrayList;
import java.util.HashMap;

import net.masagroup.jzy3d.maths.Coord2d;
import net.masagroup.jzy3d.maths.Coord3d;

public class Plotdata{
	
	private Coord3d[] coord3d;
	private Coord2d[] coord2d;
	//TODO: why aren't fields private?
	ArrayList<String> alx = new ArrayList<String>();
	ArrayList<String> aly = new ArrayList<String>();
	ArrayList<String> alz = new ArrayList<String>();
	HashMap<String,String> hm = new HashMap<String,String>();
	
	
	public void addToAr(String a, int cv) {
	    //TODO: ugly code: what is 1, 2 and 3? constants?
		if(cv==1) alx.add(a);
		else if (cv==2) aly.add(a);
		else if (cv==3) alz.add(a);
	}

	//TODO: please change method name
	public void alToCd(int flag) {
		if(flag==2) {
			coord2d = new Coord2d[alx.size()];
			for(int i=0;i<alx.size();i++) {
				float fx = Float.valueOf((alx.get(i)).trim());
				float fy = Float.valueOf((aly.get(i)).trim());
				coord2d[i] = new Coord2d(fx,fy);
			}	
		}
		else {
			coord3d = new Coord3d[alx.size()];
			for(int i=0;i<alx.size();i++) {
				float fx = Float.valueOf((alx.get(i)).trim());
				float fy = Float.valueOf((aly.get(i)).trim());
				float fz = Float.valueOf((alz.get(i)).trim());
				coord3d[i] = new Coord3d(fx,fy,fz);
			}
		}
	}
	
	public void attrToMap(String axis,String name) {
		hm.put(axis, name);
	}
	
	public Coord3d[] getCoord3d(){
		return coord3d;
	}
	public Coord2d[] getCoord2d(){
		return coord2d;
	}

}
