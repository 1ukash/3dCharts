package org.charts3d.scatter;

import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import net.masagroup.jzy3d.colors.Color;
import net.masagroup.jzy3d.maths.Coord3d;
import net.masagroup.jzy3d.plot3d.primitives.selectables.SelectableScatter;
import net.masagroup.jzy3d.plot3d.rendering.Camera;

public class MySelectableScatter extends SelectableScatter {

  private boolean[] showPoints = null;
  private boolean[] allocatedPoints = null;
  private ArrayList<Integer> oldAllocatedPoint = null;

  public MySelectableScatter(Coord3d[] coordinates, Color[] colors) {
    super(coordinates, colors);
    showPoints = new boolean[coordinates.length];
    allocatedPoints = new boolean[coordinates.length];
    for (int i = 0; i < showPoints.length; i++)
      showPoints[i] = true;
  }

  public boolean[] getHighlighted() {
    return isHighlighted;
  }

  public void setAllocatedPoint(ArrayList<Integer> index) {
    if (oldAllocatedPoint != null) {
      for (Integer i : oldAllocatedPoint)
        allocatedPoints[i] = false;
    }
    for (Integer i : index) {
      allocatedPoints[i] = true;
    }
    oldAllocatedPoint = (ArrayList<Integer>) index.clone();
  }

  public void remoteAllocation() {
    for (int i=0;i<allocatedPoints.length;i++)
      allocatedPoints[i] = false;
  }
  
  public void showAll(){
    for (int i = 0; i < this.showPoints.length; i++)
      this.showPoints[i] = true;
  }

  public void setShowPoints(boolean[] showPoints) {
    for (int i = 0; i < this.showPoints.length; i++)
      this.showPoints[i] = showPoints[i];
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
            if (allocatedPoints[k]) {
              gl.glColor4f(0, 0, (float) 1.0, (float) 1.0);
            } else {
              gl.glColor4f(highlightColor.r, highlightColor.g,
                  highlightColor.b, highlightColor.a);
            }

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
