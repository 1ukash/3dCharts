package org.charts3d;
import java.awt.Graphics;


import net.masagroup.jzy3d.chart.Chart;
import net.masagroup.jzy3d.chart.controllers.ChartThreadController;
import net.masagroup.jzy3d.chart.controllers.mouse.ChartMouseController;
import net.masagroup.jzy3d.chart.controllers.mouse.ScatterMouseSelector;
import net.masagroup.jzy3d.plot3d.primitives.Point;
import net.masagroup.jzy3d.plot3d.primitives.selectables.SelectableScatter;
import net.masagroup.jzy3d.plot3d.rendering.Renderer2d;
import net.masagroup.jzy3d.plot3d.rendering.Scene;
import net.masagroup.jzy3d.plot3d.rendering.canvas.CanvasAWT;
import net.masagroup.jzy3d.plot3d.rendering.canvas.CanvasSwing;
import net.masagroup.jzy3d.plot3d.rendering.canvas.ICanvas;
import net.masagroup.jzy3d.plot3d.rendering.canvas.Quality;


public class MyChart {
	MySelectableScatter coord=null;
	CanvasAWT canvas=null;
	MyScene myscene=null;
	private ChartThreadController threadCamera=null;
	private ChartMouseController mouseCamera;
	private MyScatterSelector mouseSelection;
	private Renderer2d messageRenderer;
	public MyChart(MySelectableScatter coord){
		this.coord=coord;
		this.coord.setWidth(5);
	}
	public Chart getChart(){
		//myscene=new MyScene();
		//myscene.addPoints(coord);
		Chart chart=new Chart(Quality.Intermediate,"swing");/*{
			public ICanvas initializeCanvas(Scene scene, Quality quality) {
				canvas=new CanvasSwing(scene, quality);
				return canvas;
			}
			public MyScene initializeScene(){
				return myscene;
			}
		};*/
		chart.getScene().add(coord);
		threadCamera=new ChartThreadController(chart);
		mouseCamera=new ChartMouseController(chart);
		mouseCamera.addSlaveThreadController(threadCamera);
		mouseSelection=new MyScatterSelector(coord);
		
		messageRenderer = new Renderer2d(){
        	public void paint(Graphics g){
        			g.setColor(java.awt.Color.RED);
        	}
        };
        chart.removeController(mouseCamera);
        mouseSelection.attachChart(chart);
        chart.addRenderer(messageRenderer);
        return chart;	    
		
	}
}
