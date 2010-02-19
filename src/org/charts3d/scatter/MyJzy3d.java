package org.charts3d.scatter;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.text.html.HTMLEditorKit.Parser;

import org.charts3d.Plotdata;
import org.charts3d.xmldatabuilder.XMLParse;


import net.masagroup.jzy3d.chart.Chart;
import net.masagroup.jzy3d.chart.ChartScene;
import net.masagroup.jzy3d.colors.Color;
import net.masagroup.jzy3d.maths.Coord3d;
import net.masagroup.jzy3d.plot3d.primitives.Scatter;
import net.masagroup.jzy3d.plot3d.primitives.selectables.SelectableScatter;
import net.masagroup.jzy3d.plot3d.rendering.Graph;
import net.masagroup.jzy3d.plot3d.rendering.canvas.ICanvas;


public class MyJzy3d extends JApplet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3961560252163844174L;
	
	XMLParse XMLparser=new XMLParse("../coords.xml");
	Plotdata pdata = new Plotdata();
	MySelectableScatter sc=null;
	MySelectableScatter unHidenSc=null;
	Chart chart=null;
	Chart bufChart=null;
	Coord3d coord[]=null;
	boolean[] b=null;
	JButton button=null;
	//Component comp=null;
	
	boolean hide=false;
	
	public void init(){
		pdata=XMLparser.parse(1);
		coord=pdata.getCoord3d();
		Color color[]=new Color[coord.length];
		for (int i=0;i<coord.length;i++)
			color[i]=new Color(0, 1, 0);
		sc=new MySelectableScatter(coord,color);
		MyChart m=new MyChart(sc);
		chart=m.getChart();
		setLayout(new BorderLayout());
		ICanvas comp = chart.getCanvas();
		comp.forceRepaint();
		button=new JButton("Hide");
//		jp.add(comp);
		add((JComponent)comp, BorderLayout.CENTER,0);
		add(button, BorderLayout.SOUTH,1);
		button.addMouseListener(new MouseListener() {
			
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
				if(hide){
					button.setText("Hide");
					ICanvas canvas = chart.getCanvas();
					canvas.forceRepaint();
					add((JComponent)canvas,0);
				}else{					
					b=sc.getHighlighted();
					int kol=0;
					//считаем сколько точек оставить
					Coord3d coordbuf[]=sc.getData();
					for(int i=0;i<b.length;i++){
						if(b[i]){
							kol++;
						}
					}
					Coord3d buf[]=new Coord3d[kol];
					Color col[]=new Color[kol];
					for(int i=0,n=0;i<b.length;i++){
						if(b[i]){
							buf[n]=coordbuf[i];
							col[n]=new Color(1, 0, 0);
							n++;
						}
					}
					unHidenSc=new MySelectableScatter(buf, col);
					button.setText("Show");
					
					MyChart mb=new MyChart(unHidenSc);
					bufChart=mb.getChart();
					ICanvas canvas = bufChart.getCanvas();
					canvas.forceRepaint();
					add((JComponent)bufChart.getCanvas(), 0);
				}
				hide^=true;
			}
		});
	}
}
