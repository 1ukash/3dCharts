package org.charts3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlotStorage {
	private Map<String,String> axisNames = new HashMap<String,String>();
	private ArrayList<Double>[] arrayStorage = null;
	/*	private Coord3d[] coords3d;
	private Coord2d[] coords2d;*/

	public void addAttributeToMap(String axis,String name) {
		axisNames.put(axis, name);
	}
	

	public void addCooordsToStorage(ArrayList<Double>[] arrayCoords) {
		arrayStorage = new ArrayList[arrayCoords.length];
		arrayStorage = arrayCoords;
	}
	
	public ArrayList<Double>[] getCoords(){
		  return arrayStorage;
		}

/*
	public void addCoords(Coord2d[] coords) {
		coords2d = coords;
		
	}
	
	public void addCoords(Coord3d[] coords) {
		coords3d = coords;
	}

	public Coord3d[] getCoord3d(){
	  return coords3d;
	}
	
	public Coord2d[] getCoord2d(){
    return coords2d;
  }
*/
}
