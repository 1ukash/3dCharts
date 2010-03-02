package org.charts3d.scatter.Listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JList;

import org.charts3d.scatter.ExtendedSelectableScatter;

import net.masagroup.jzy3d.chart.Chart;

public class PointsListMouseListener implements MouseListener{
  
  private Chart chart=null;
  private ExtendedSelectableScatter scatter = null;
  private ArrayList<Integer> selectedPoints=null;
  private ArrayList<Integer> pointsNumber=null;
  
  public PointsListMouseListener(Chart chart,
                                 ArrayList<Integer> selectedPoints,
                                 ArrayList<Integer> pointsNumber,
                                 ExtendedSelectableScatter scatter){
    this.chart=chart;
    this.scatter=scatter;
    this.selectedPoints=selectedPoints;
    this.pointsNumber=pointsNumber;
  }
  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseClicked(MouseEvent e) {
    JList list = (JList) e.getSource();
    if (e.isShiftDown()) {
      selectedPoints.clear();
      int min = list.getMinSelectionIndex();
      int max = list.getMaxSelectionIndex();

      for (int i = min; i <= max; i++) {
        if(!selectedPoints.contains(pointsNumber.get(i)))
          selectedPoints.add(pointsNumber.get(i));
      }
    } else if (e.isControlDown()) {
      int anchorSelectedIndex = list.getAnchorSelectionIndex();
      findPoints:
      {
        for (int i = 0; i < selectedPoints.size(); i++) {
          if (selectedPoints.get(i).intValue() == pointsNumber.get(anchorSelectedIndex).intValue()){
            selectedPoints.remove(i);
            break findPoints;
          }
        }
        selectedPoints.add(pointsNumber.get(anchorSelectedIndex));
      }
    } else {
      selectedPoints.clear();
      selectedPoints.add(pointsNumber.get(list.getSelectedIndex()));
    }
    scatter.setAllocatedPoint(selectedPoints);
    chart.getView().shoot();
  }
}
