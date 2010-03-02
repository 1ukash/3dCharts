package org.charts3d.scatter;
import java.awt.event.MouseEvent;

import net.masagroup.jzy3d.chart.controllers.mouse.ScatterMouseSelector;
import net.masagroup.jzy3d.maths.Coord3d;
import net.masagroup.jzy3d.plot3d.primitives.selectables.SelectableScatter;
import net.masagroup.jzy3d.plot3d.rendering.Scene;
import net.masagroup.jzy3d.plot3d.rendering.View;


public class ScatterSelector extends ScatterMouseSelector{

	public ScatterSelector(SelectableScatter scatter) {
		super(scatter);
	}
	@Override
	protected void processSelection(Scene scene, View view, int width,
			int height) {
		Coord3d[] projection = view.toScreen(scatter.getData());
		for (int i = 0; i < projection.length; i++)
			if (matchRectangleSelection(in, out, projection[i], width, height))
				scatter.setHighlighted(i, !scatter.getHighlighted(i));
	}
	
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(dragging)
			releaseSelection(e);
		dragging = false;
		chart.getCanvas().forceRepaint(); // repaint all
	}

}
