package org.charts3d;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import net.masagroup.jzy3d.colors.Color;
import net.masagroup.jzy3d.maths.Coord3d;
import net.masagroup.jzy3d.plot3d.primitives.Point;


public class PointsXYZ {
	private String x[]=null;
	private String y[]=null;
	private String z[]=null;
	private Coord3d[] coord3d=null;
	private Color[] color=null;
	public PointsXYZ() {
		String bufx=null;
		String bufy=null;
		String bufz=null;
		try {
		    //TODO: i haven't such file in my system!
			BufferedReader br=new BufferedReader(
					new InputStreamReader(
					new FileInputStream("D:\\workspace\\myjzy3d\\points.txt")));
			bufx=br.readLine();
			bufy=br.readLine();
			bufz=br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		x=bufx.split(":");
		y=bufy.split(":");
		z=bufz.split(":");
		coord3d=new Coord3d[x.length];
		color=new Color[x.length];
		for(int i=0;i<x.length;i++){
			Coord3d p=new Coord3d(
					Double.parseDouble(x[i]),
					Double.parseDouble(y[i]),
					Double.parseDouble(z[i]));
			//p.setWidth(5);
			//p.setColor(new Color(255, 0, 0));
			coord3d[i]=p;
			color[i]=new Color(0, 0, 0);
		}
			
	}
	public String[] getX() {
		return x;
	}
	public Color[] getColor(){
		return color;
	}
	public Coord3d[] getCoord3d() {
		return coord3d;
	}
	public String[] getY() {
		return y;
	}
	public String[] getZ() {
		return z;
	}
}
