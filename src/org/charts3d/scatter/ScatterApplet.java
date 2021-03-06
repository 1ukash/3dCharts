package org.charts3d.scatter;

import java.awt.Label;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

import net.masagroup.jzy3d.chart.Chart;
import net.masagroup.jzy3d.colors.Color;
import net.masagroup.jzy3d.maths.Coord3d;
import net.masagroup.jzy3d.plot3d.rendering.Camera;

import org.charts3d.PlotStorage;
import org.charts3d.scatter.Listeners.PointsListMouseListener;
import org.charts3d.scatter.Listeners.ShowHideButtonListener;
import org.charts3d.scatter.Listeners.SplitChangeListener;
import org.charts3d.xmldatabuilder.CoordsXMLParser;

public class ScatterApplet extends JApplet {

  private static final long serialVersionUID = -3961560252163844174L;

  private CoordsXMLParser XMLparser = new CoordsXMLParser();
  private ExtendedSelectableScatter sc = null;
  private Chart chart = null;
  private Coord3d coord[] = null;
  private JPanel infoPanel = null;
  private JList pointsList = null;
  private Color color[] = null;
  private JLabel greeting=null;

  private ArrayList<Integer> pointsNumber = new ArrayList<Integer>();
  private ArrayList<Integer> selectedPoints = new ArrayList<Integer>();

  ArrayList<PlotStorage> coordsArray = null;

  private final int CHARTWIDTH = 500;
  private final int CHARTHEIGHT = 500;
  private final int GAP = 3;
  private final int BUTTONWIDTH = 70;
  private final int BUTTONHEIGHT = 25;
  private final int INFOPANELWIDTH = 150;
  private final int INFOPANELHEIGHT = 300;
  private static final int MAINWINDOWWIDTH = 700;
  private static final int MAINWINDOWHEIGHT = 550;
  private final int SLIDERWIDTH = 100;
  private final int SLIDERHEIGHT = 50;

  public static int POINTSIZE = 5;

  public void init() {
    
    setSize(MAINWINDOWWIDTH, MAINWINDOWHEIGHT);
    setLayout(null);
    
    greeting=new JLabel("Please, click button");
    greeting.setBounds(100, 100, 150, 25);
    
    add(greeting);
    
    /*addNewChart("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+

        "<plotdata xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""+
          " xsi:noNamespaceSchemaLocation=\"coords.xsd\">"+

          "<data id='1'>"+
            "<values axis='x' name='XCOORDS' type='int'>"+
              "<value>1</value>"+
              "<value>4</value>"+
              "<value>1</value>"+
              "<value>3</value>"+
              "<value>10</value>"+
              "<value>5</value>"+
            "</values>"+

            "<values axis='y' name='YCOORDS' type='double'>"+
              "<value>2.0</value>"+
              "<value>1.0</value>"+
              "<value>19.2</value>"+
              "<value>45.1</value>"+
              "<value>1.28</value>"+
              "<value>5.55</value>"+
            "</values>"+

            "<values axis='z' name='ZCOORDS' type='double'>"+
              "<value>5.0</value>"+
              "<value>4.0</value>"+
              "<value>13.2</value>"+
              "<value>15.1</value>"+
              "<value>1.28</value>"+
              "<value>5.0</value>"+
            "</values>"+
          "</data>"+

        "</plotdata>");//*/


  }
  public void addNewChart(String xmlString){
    
    ByteArrayInputStream bais = new ByteArrayInputStream(xmlString.getBytes());
    try {
      coordsArray = (ArrayList<PlotStorage>) XMLparser.parse(bais);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    ArrayList<Double>[] dCoord = coordsArray.get(0).getCoords();
    if(dCoord==null){
      greeting.setText("Error: wrong data file");
      return;
    }
    int kol = dCoord[0].size();
    coord = new Coord3d[kol];
    color = new Color[coord.length];
    for (int i = 0; i < kol; i++){
      coord[i] = new Coord3d(dCoord[0].get(i), dCoord[1].get(i), dCoord[2].get(i));
      color[i] = new Color(0,0 , (float)1);
    }
    
    sc = new ExtendedSelectableScatter(coord, color) {
      public void draw(GL gl, GLU glu, Camera cam) {
        gl.glEnable(GL.GL_POINT_SMOOTH); // should be in init
        super.draw(gl, glu, cam);
      }
    };
    ScatterChart m = new ScatterChart(sc);
    chart = m.getChart();

    JComponent component = (JComponent) chart.getCanvas();
    component.setBounds(0, 0, CHARTWIDTH, CHARTHEIGHT);
    
    /*
     * infoPanel settings
     */
    Label infoLabel = new Label("Points info");
    infoLabel.setAlignment(Label.CENTER);
    infoLabel.setBounds(0, 0, 100, 25);

    infoPanel = new JPanel();
    infoPanel.setLayout(null);
    infoPanel.add(infoLabel);
    infoPanel.setBounds(CHARTWIDTH + GAP, 0, INFOPANELWIDTH, INFOPANELHEIGHT);

    /*
     * pointsList settings
     */
    pointsList = new JList();
    pointsList.addMouseListener(new PointsListMouseListener(chart, selectedPoints, pointsNumber, sc));

    /*
     * pointsScrollPane settings
     */
    JScrollPane pointsScrollPane = new JScrollPane(pointsList);
    pointsScrollPane.setBounds(0, 28, INFOPANELWIDTH - 20, 250);

    /*
     * slider settings
     */
    JSlider slider = new JSlider(0, 10);
    slider.setValue(POINTSIZE);
    slider.setBounds(CHARTWIDTH + GAP, INFOPANELHEIGHT + GAP, SLIDERWIDTH, SLIDERHEIGHT);
    slider.setMajorTickSpacing(5);
    slider.setMinorTickSpacing(1);
    slider.setPaintLabels(true);
    slider.setPaintTicks(true);
    slider.setFocusable(false);
    slider.addChangeListener(new SplitChangeListener(chart, sc));

    /*
     * ShowHideButton settings
     */
    JButton button = new JButton("Hide");
    button.setBounds((CHARTWIDTH - BUTTONWIDTH) / 2, CHARTHEIGHT + GAP, BUTTONWIDTH, BUTTONHEIGHT);
    button.setFocusable(false);
    button.addMouseListener(new ShowHideButtonListener(chart, pointsList, pointsNumber, sc));

    infoPanel.add(pointsScrollPane);

    add(component, 0);
    add(button, 1);
    add(infoPanel, 2);
    add(slider, 3);
    
    //chart.getView().shoot();
    repaint();
    
  }
}
