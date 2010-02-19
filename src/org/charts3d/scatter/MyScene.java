package org.charts3d.scatter;

import net.masagroup.jzy3d.chart.ChartScene;
import net.masagroup.jzy3d.plot3d.primitives.Point;
import net.masagroup.jzy3d.plot3d.rendering.Graph;


public class MyScene extends ChartScene{
	private Point[] points=null;
	public void addPoints(Point[] coord){
		points=coord;
		for(int i=0;i<coord.length;i++){
			add(coord[i]);
		}
		
	}

	public Point[] getPoints() {
		return points;
	}
	
	public Graph getGraph(){
		return graph;
	}
	
}
