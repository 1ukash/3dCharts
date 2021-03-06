package org.charts3d.scatter.Listeners;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.charts3d.scatter.ExtendedSelectableScatter;

import net.masagroup.jzy3d.chart.Chart;

public class SplitChangeListener implements ChangeListener{
  
  private Chart chart=null;
  private ExtendedSelectableScatter scatter = null;
  
  public SplitChangeListener(Chart chart, ExtendedSelectableScatter scatter){
    this.chart=chart;
    this.scatter=scatter;
  }
  
  @Override
  public void stateChanged(ChangeEvent e) {
    JSlider js = (JSlider) e.getSource();
    int k = js.getValue();
    scatter.setWidth(k);
    chart.getView().shoot();
  }

}
