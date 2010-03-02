package org.charts3d.scatter.Listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;

import org.charts3d.scatter.ExtendedSelectableScatter;

import net.masagroup.jzy3d.chart.Chart;
import net.masagroup.jzy3d.maths.Coord3d;

public class ShowHideButtonListener implements MouseListener{
  private boolean hide=false;
  
  private Chart chart=null;
  private ExtendedSelectableScatter scatter = null;
  private JList pointsList = null;
  private ArrayList<Integer> pointsNumber=null;
  
  public ShowHideButtonListener(Chart chart, 
                                JList pointsList,
                                ArrayList<Integer> pointsNumber,
                                ExtendedSelectableScatter scatter){
    this.chart=chart;
    this.pointsList=pointsList;
    this.pointsNumber=pointsNumber;
    this.scatter=scatter;
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
    JButton button=(JButton)e.getSource();
    if (hide) {
      button.setText("Hide");

      /*boolean[] showAll = new boolean[scatter.getData().length];
      for (int i = 0; i < showAll.length; i++)
        showAll[i] = true;
      scatter.setShowPoints(showAll);*/
      scatter.showAll();
      scatter.remoteAllocation();
      pointsList.setListData(new String[] {});
      pointsList.transferFocus();
      pointsNumber.clear();
      chart.getView().shoot();
    } else {

      button.setText("Show");

      boolean[] showInfo = scatter.getHighlighted();
      Coord3d coordbuf[] = scatter.getData();
      ArrayList<String> pointsInfoArray = new ArrayList<String>();
      for (int i = 0; i < showInfo.length; i++) {
        if (showInfo[i]) {

          pointsInfoArray.add(coordbuf[i].toString());
          // new String("x: " + coordbuf[i].x + " y: "
          // + coordbuf[i].y + " z: " + coordbuf[i].z));
          pointsNumber.add(new Integer(i));
        }
      }
      pointsList.setListData(pointsInfoArray.toArray());

      scatter.setShowPoints(scatter.getHighlighted());
      chart.getView().shoot();

    }
    hide ^= true;

  }

}
