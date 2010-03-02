package org.charts3d.scatter;

import java.awt.Graphics;

import net.masagroup.jzy3d.chart.Chart;
import net.masagroup.jzy3d.chart.controllers.ChartThreadController;
import net.masagroup.jzy3d.chart.controllers.mouse.ChartMouseController;
import net.masagroup.jzy3d.plot3d.rendering.Renderer2d;
import net.masagroup.jzy3d.plot3d.rendering.canvas.Quality;

import org.charts3d.scatter.Listeners.CanvasKeyListener;

public class MyChart {
  private MySelectableScatter coord = null;
  private ChartThreadController threadCamera = null;
  private ChartMouseController mouseCamera;
  private MyScatterSelector mouseSelection;
  private Renderer2d messageRenderer;
  private Chart chart = null;

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

    messageRenderer = new Renderer2d() {
      public void paint(Graphics g) {
        g.setColor(java.awt.Color.RED);
      }
    };
    
    chart.addRenderer(messageRenderer);

    chart.getCanvas().addKeyListener(new CanvasKeyListener(chart,mouseSelection,mouseCamera));

    return chart;

  }
  
}
