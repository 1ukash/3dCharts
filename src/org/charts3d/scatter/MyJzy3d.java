package org.charts3d.scatter;

import java.awt.Label;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
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

public class MyJzy3d extends JApplet {

  private static final long serialVersionUID = -3961560252163844174L;

  private CoordsXMLParser XMLparser = new CoordsXMLParser();
  private MySelectableScatter sc = null;
  private Chart chart = null;
  private Coord3d coord[] = null;
  // private JButton button = null;
  private JPanel infoPanel = null;
  private JList pointsList = null;
  private Color color[] = null;

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
  private final int MAINWINDOWWIDTH = 700;
  private final int MAINWINDOWHEIGHT = 550;
  private final int SLIDERWIDTH = 100;
  private final int SLIDERHEIGHT = 50;

  public static int POINTSIZE = 5;

  public MyJzy3d() {
    if (false) {
      try {
        coordsArray = (ArrayList<PlotStorage>) XMLparser.parse(new FileInputStream("d:\\workspace\\3dCharts\\coords.xml"));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      String xmlCode = getParameter("xmlString");
      ByteArrayInputStream bais = new ByteArrayInputStream(xmlCode.getBytes());
    }
  }

  public void init() {

    ArrayList<Double>[] dCoord = coordsArray.get(0).getCoords();
    int kol = dCoord[0].size();
    coord = new Coord3d[kol];
    for (int i = 0; i < kol; i++)
      coord[i] = new Coord3d(dCoord[0].get(i), dCoord[1].get(i), dCoord[2].get(i));
    color = new Color[coord.length];
    for (int i = 0; i < coord.length; i++)
      color[i] = new Color(0, (float) 1, 0);
    sc = new MySelectableScatter(coord, color) {
      public void draw(GL gl, GLU glu, Camera cam) {
        gl.glEnable(GL.GL_POINT_SMOOTH); // should be in init
        super.draw(gl, glu, cam);
      }
    };
    MyChart m = new MyChart(sc);
    chart = m.getChart();
    setSize(MAINWINDOWWIDTH, MAINWINDOWHEIGHT);
    setLayout(null);

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

  }
}
