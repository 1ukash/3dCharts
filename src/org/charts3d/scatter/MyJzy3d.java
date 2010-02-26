package org.charts3d.scatter;

import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import net.masagroup.jzy3d.chart.Chart;
import net.masagroup.jzy3d.colors.Color;
import net.masagroup.jzy3d.maths.Coord3d;

import org.charts3d.PlotStorage;
import org.charts3d.xmldatabuilder.CoordsXMLParser;

public class MyJzy3d extends JApplet {
  /**
	 * 
	 */
  private static final long serialVersionUID = -3961560252163844174L;

  private CoordsXMLParser XMLparser = new CoordsXMLParser();
  private MySelectableScatter sc = null;
  private Chart chart = null;
  private Coord3d coord[] = null;
  private JButton button = null;
  private JPanel infoPanel = null;
  private JPanel pointsPanel = null;

  private boolean hide = false;
  
  
  private final int CHARTWIDTH=500;
  private final int CHARTHEIGHT=500;
  private final int GAP=3;
  private final int BUTTONWIDTH=70;
  private final int BUTTONHEIGHT=25;
  private final int INFOPANELWIDTH=150;
  private final int INFOPANELHEIGHT=500;
  private final int MAINWINDOWWIDTH=700;
  private final int MAINWINDOWHEIGHT=550;
  

  public void init() {
    try {
      ArrayList<PlotStorage> coordsArray=(ArrayList<PlotStorage>)XMLparser.parse(
          new FileInputStream("d:\\workspace\\3dCharts\\coords.xml"));
      coord=coordsArray.get(0).getCoord3d();
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    Color color[] = new Color[coord.length];
    for (int i = 0; i < coord.length; i++)
      color[i] = new Color(0, 1, 0);
    sc = new MySelectableScatter(coord, color);
    MyChart m = new MyChart(sc);
    chart = m.getChart();
    setSize(MAINWINDOWWIDTH, MAINWINDOWHEIGHT);
    setLayout(null);
    
    JComponent component=(JComponent)chart.getCanvas();
    component.setBounds(0, 0, CHARTWIDTH, CHARTHEIGHT);
    
    button = new JButton("Hide");
    button.setBounds((CHARTWIDTH-BUTTONWIDTH)/2, CHARTHEIGHT+GAP, BUTTONWIDTH, BUTTONHEIGHT);
    
    Label infoLabel=new Label("Points info");
    infoLabel.setAlignment(Label.CENTER);
    infoLabel.setBounds(0, 0, 100, 25);
    
    infoPanel = new JPanel();
    infoPanel.setLayout(null);
    infoPanel.add(infoLabel);
    infoPanel.setBounds(CHARTWIDTH+GAP, 0, INFOPANELWIDTH, INFOPANELHEIGHT);

    
    pointsPanel = new JPanel();
    pointsPanel.setLayout(null);
    pointsPanel.setBounds(0, 28, INFOPANELWIDTH, 450);

    infoPanel.add(pointsPanel);
    add(component, 0);
    add(button, 1);
    add(infoPanel, 2);
    button.setFocusable(false);
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
        if (hide) {
          pointsPanel.removeAll();
          button.setText("Hide");
          
          boolean[] showAll=new boolean[sc.getData().length];
          for(int i=0;i<showAll.length;i++)
            showAll[i]=true;
          sc.setShowPoints(showAll);
          chart.getView().shoot();
        } else {
        
          button.setText("Show");
          
          boolean[] showInfo=sc.getHighlighted();
          Coord3d coordbuf[] = sc.getData();
          Label bufLabel=null;
          for(int i=0,k=0;i<showInfo.length;i++){
            if(showInfo[i]){
              bufLabel=new Label("x: " + coordbuf[i].x + " y: "
                  + coordbuf[i].y + " z: " + coordbuf[i].z); 
              bufLabel.setBounds(5, k*25, 150, 25);
              pointsPanel.add(bufLabel);
              k++;
            }
          }
          
          sc.setShowPoints(sc.getHighlighted());
          chart.getView().shoot();

        }
        hide ^= true;

      }
    });
  }
}
