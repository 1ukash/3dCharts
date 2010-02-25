package org.charts3d;

import java.awt.List;
import java.util.HashMap;
import java.util.Map;

import net.masagroup.jzy3d.maths.Coord2d;
import net.masagroup.jzy3d.maths.Coord3d;

public class PlotStorage {
	private Map<String,String> axisNames = new HashMap<String,String>();
	private Coord3d[] coords3d;
	private Coord2d[] coords2d;
	

	public void addAttributeToMap(String axis,String name) {
		axisNames.put(axis, name);
	}
	
	
	public void addCoords(Coord2d[] coords) {
		coords2d = coords;
		
	}
	
	public void addCoords(Coord3d[] coords) {
		coords3d = coords;
	}
	
}
