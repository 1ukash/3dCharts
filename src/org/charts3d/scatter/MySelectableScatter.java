package org.charts3d.scatter;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import net.masagroup.jzy3d.colors.Color;
import net.masagroup.jzy3d.maths.Coord3d;
import net.masagroup.jzy3d.plot3d.primitives.selectables.SelectableScatter;
import net.masagroup.jzy3d.plot3d.rendering.Camera;

public class MySelectableScatter extends SelectableScatter {

  private boolean[] showPoints = null;

  public MySelectableScatter(Coord3d[] coordinates, Color[] colors) {
    super(coordinates, colors);
    showPoints = new boolean[coordinates.length];
    for (int i = 0; i < showPoints.length; i++)
      showPoints[i] = true;
  }

  public boolean[] getHighlighted() {
    return isHighlighted;
  }
  public void setShowPoints(boolean[] showPoints){
    for(int i=0;i<this.showPoints.length;i++)
      this.showPoints[i]=showPoints[i];
  }

  public void setHighlighted(boolean[] isHighlighted) {
    this.isHighlighted = isHighlighted;
  }

  public void setNewData(Coord3d[] coordinates) {

    bbox.reset();
    for (Coord3d c : coordinates)
      bbox.add(c);
  }

  public void setData(Coord3d[] coordinates, boolean[] flag) {
    this.coordinates = coordinates;
    this.isHighlighted = flag;

    bbox.reset();
    for (Coord3d c : coordinates)
      bbox.add(c);
  }

  public void draw(GL gl, GLU glu, Camera cam) {
    if (transform != null)
      transform.execute(gl);

    gl.glPointSize(width);

    gl.glBegin(GL.GL_POINTS);
    if (colors == null)
      gl.glColor4f(rgb.r, rgb.g, rgb.b, rgb.a);
    if (coordinates != null) {
      int k = 0;
      for (Coord3d c : coordinates) {
        if (colors != null) {
            if (isHighlighted[k]) // Selection coloring goes here
              gl.glColor4f(highlightColor.r, highlightColor.g,
                  highlightColor.b, highlightColor.a);
            else
              gl.glColor4f(colors[k].r, colors[k].g, colors[k].b, colors[k].a);
        }
        
        if (showPoints[k])
          gl.glVertex3f(c.x, c.y, c.z);
        k++;
      }
    }
    gl.glEnd();
  }
}
