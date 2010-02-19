package org.charts3d;
import net.masagroup.jzy3d.colors.Color;
import net.masagroup.jzy3d.maths.Coord3d;
import net.masagroup.jzy3d.plot3d.primitives.selectables.SelectableScatter;


public class MySelectableScatter extends SelectableScatter{

	public MySelectableScatter(Coord3d[] coordinates, Color[] colors) {
		super(coordinates, colors);
	}
	
	public boolean[] getHighlighted(){
		return isHighlighted;
	}
	public void setHighlighted(boolean[] isHighlighted){
		this.isHighlighted=isHighlighted;
	}
	public void setNewData(Coord3d[] coordinates){
		
		bbox.reset();
		for(Coord3d c: coordinates)
			bbox.add(c);
	}
	public void setData(Coord3d[] coordinates,boolean[] flag){
		this.coordinates = coordinates;
		this.isHighlighted = flag;
		
		bbox.reset();
		for(Coord3d c: coordinates)
			bbox.add(c);
	}
}
