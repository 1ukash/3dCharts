package org.charts3d.scatter;

import javax.swing.JComponent;
import java.awt.Label;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JApplet;
import javax.swing.JButton;

import net.masagroup.jzy3d.chart.Chart;
import net.masagroup.jzy3d.colors.Color;
import net.masagroup.jzy3d.maths.Coord3d;
import net.masagroup.jzy3d.plot3d.rendering.CameraMode;
import net.masagroup.jzy3d.plot3d.rendering.canvas.ICanvas;

import org.charts3d.Plotdata;
import org.charts3d.xmldatabuilder.XMLParse;

public class MyJzy3d extends JApplet {
  /**
	 * 
	 */
  private static final long serialVersionUID = -3961560252163844174L;

  private XMLParse XMLparser = new XMLParse("../coords.xml");
  private Plotdata pdata = new Plotdata();
  private MySelectableScatter sc = null;
  private MySelectableScatter unHidenSc = null;
  private Chart chart = null;
  private Chart bufChart = null;
  private Coord3d coord[] = null;
  private boolean[] b = null;
  private JButton button = null;
  private JPanel chartPanel=null;
  private JPanel infoPanel = null;
  private JPanel pointsPanel = null;
  //private ScatterKeyListener scatterKey=null;

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
    pdata = XMLparser.parse(1);
    coord = pdata.getCoord3d();
    Color color[] = new Color[coord.length];
    for (int i = 0; i < coord.length; i++)
      color[i] = new Color(0, 1, 0);
    sc = new MySelectableScatter(coord, color);
    MyChart m = new MyChart(sc);
    chart = m.getChart();
    setSize(MAINWINDOWWIDTH, MAINWINDOWHEIGHT);
    setLayout(null);
    //scatterKey=new ScatterKeyListener(chart, sc);
    
   // chart.getCanvas().addKeyListener(scatterKey);
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
    //pointsPanel.setLayout(new BoxLayout(pointsPanel, BoxLayout.PAGE_AXIS));
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
