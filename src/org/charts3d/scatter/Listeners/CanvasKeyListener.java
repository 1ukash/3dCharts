package org.charts3d.scatter.Listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.charts3d.scatter.ScatterSelector;

import net.masagroup.jzy3d.chart.Chart;
import net.masagroup.jzy3d.chart.controllers.mouse.ChartMouseController;
import net.masagroup.jzy3d.chart.controllers.mouse.ScatterMouseSelector;

public class CanvasKeyListener implements KeyListener{
  private boolean isShiftPressed=false;
  private Chart chart=null;
  private ScatterSelector mouseSelection=null;
  private ChartMouseController mouseCamera=null;
  
  public CanvasKeyListener(Chart chart,ScatterMouseSelector mouseSelection,ChartMouseController mouseCamera){
    this.chart=chart;
    this.mouseSelection=(ScatterSelector)mouseSelection;
    this.mouseCamera=mouseCamera;
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
      isShiftPressed=false;
      mouseSelection.releaseChart();
      chart.addController(mouseCamera);
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (!isShiftPressed && e.isShiftDown()) {
      isShiftPressed=true;
      chart.removeController(mouseCamera);
      mouseSelection.attachChart(chart);

    } 
  }
}
