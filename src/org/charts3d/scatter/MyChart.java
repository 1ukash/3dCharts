package org.charts3d.scatter;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.masagroup.jzy3d.chart.Chart;
import net.masagroup.jzy3d.chart.controllers.ChartThreadController;
import net.masagroup.jzy3d.chart.controllers.mouse.ChartMouseController;
import net.masagroup.jzy3d.plot3d.rendering.Renderer2d;
import net.masagroup.jzy3d.plot3d.rendering.canvas.Quality;

public class MyChart {
  private MySelectableScatter coord = null;
  private ChartThreadController threadCamera = null;
  private ChartMouseController mouseCamera;
  //private ChartMouseSelectController mouseCameraSelect;
  private MyScatterSelector mouseSelection;
  private Renderer2d messageRenderer;
  private Chart chart = null;

  private boolean isShiftPressed = false;

  public MyChart(MySelectableScatter coord) {
    this.coord = coord;
    this.coord.setWidth(MyJzy3d.POINTSIZE);
  }

  public Chart getChart() {

    chart = new Chart(Quality.Intermediate, "swing");

    chart.getScene().add(coord);
    threadCamera = new ChartThreadController(chart);
    mouseCamera = new ChartMouseController(chart);
    mouseCamera.addSlaveThreadController(threadCamera);
    mouseSelection = new MyScatterSelector(coord);
    //mouseCameraSelect=new ChartMouseSelectController(chart, mouseSelection);

    messageRenderer = new Renderer2d() {
      public void paint(Graphics g) {
        g.setColor(java.awt.Color.RED);
      }
    };
    
    //chart.removeController(mouseCamera);
    //mouseSelection.attachChart(chart);
    
    
    chart.addRenderer(messageRenderer);

    chart.getCanvas().addKeyListener(new KeyListener() {

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
    });
    

    return chart;

  }
  
}
