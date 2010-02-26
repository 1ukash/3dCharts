package org.charts3d.scatter;

import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.masagroup.jzy3d.chart.Chart;
import net.masagroup.jzy3d.colors.Color;
import net.masagroup.jzy3d.maths.Coord3d;
import net.masagroup.jzy3d.plot3d.rendering.Camera;

import org.charts3d.PlotStorage;
import org.charts3d.xmldatabuilder.CoordsXMLParser;

public class MyJzy3d extends JApplet {

  private static final long serialVersionUID = -3961560252163844174L;

  private CoordsXMLParser XMLparser = new CoordsXMLParser();
  private MySelectableScatter sc = null;
  private Chart chart = null;
  private Coord3d coord[] = null;
  private JButton button = null;
  private JPanel infoPanel = null;
  private JScrollPane pointsScrollPane = null;
  private JList pointsList = null;
  private Color color[] = null;

  private ArrayList<Integer> pointsNumber = new ArrayList<Integer>();
  private ArrayList<Integer> selectedPoints = new ArrayList<Integer>();
  private int oldSelectedIndex = 0;
 // private int selectedIndexes[] = null;

  private boolean hide = false;

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

  public void init() {
    try {
      ArrayList<PlotStorage> coordsArray = (ArrayList<PlotStorage>) XMLparser
          .parse(new FileInputStream("d:\\workspace\\3dCharts\\coords.xml"));
      ArrayList<Double>[] dCoord = coordsArray.get(0).getCoords();
      int kol = dCoord[0].size();
      coord = new Coord3d[kol];
      for (int i = 0; i < kol; i++)
        coord[i] = new Coord3d(dCoord[0].get(i), dCoord[1].get(i), dCoord[2]
            .get(i));
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
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

    button = new JButton("Hide");
    button.setBounds((CHARTWIDTH - BUTTONWIDTH) / 2, CHARTHEIGHT + GAP,
        BUTTONWIDTH, BUTTONHEIGHT);

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
    pointsList.addMouseListener(new MouseListener() {

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
        JList list = (JList) e.getSource();
        if (e.isShiftDown()) {
          selectedPoints.clear();
          int min = list.getMinSelectionIndex();
          int max = list.getMaxSelectionIndex();

          for (int i = min; i <= max; i++) {
            if(!selectedPoints.contains(pointsNumber.get(i)))
              selectedPoints.add(pointsNumber.get(i));
          }
        } else if (e.isControlDown()) {
          int anchorSelectedIndex = list.getAnchorSelectionIndex();
          findPoints:
          {
            for (int i = 0; i < selectedPoints.size(); i++) {
              if (selectedPoints.get(i).intValue() == pointsNumber.get(anchorSelectedIndex).intValue()){
                selectedPoints.remove(i);
                break findPoints;
              }
            }
            selectedPoints.add(pointsNumber.get(anchorSelectedIndex));
          }
        } else {
          selectedPoints.clear();
          selectedPoints.add(pointsNumber.get(list.getSelectedIndex()));
        }
        sc.setAllocatedPoint(selectedPoints);
        chart.getView().shoot();
      }
    });

    /*
     * pointsScrollPane settings
     */
    pointsScrollPane = new JScrollPane(pointsList);
    pointsScrollPane.setBounds(0, 28, INFOPANELWIDTH - 20, 250);

    /*
     * slider settings
     */
    JSlider slider = new JSlider(0, 10);
    slider.setValue(POINTSIZE);
    slider.setBounds(CHARTWIDTH + GAP, INFOPANELHEIGHT + GAP, SLIDERWIDTH,
        SLIDERHEIGHT);
    slider.setMajorTickSpacing(5);
    slider.setMinorTickSpacing(1);
    slider.setPaintLabels(true);
    slider.setPaintTicks(true);
    slider.setFocusable(false);
    slider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        JSlider js = (JSlider) e.getSource();
        int k = js.getValue();
        sc.setWidth(k);
        chart.getView().shoot();
      }
    });

    infoPanel.add(pointsScrollPane);
    add(component, 0);
    add(button, 1);
    add(infoPanel, 2);
    add(slider, 3);
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
          button.setText("Hide");

          boolean[] showAll = new boolean[sc.getData().length];
          for (int i = 0; i < showAll.length; i++)
            showAll[i] = true;
          sc.setShowPoints(showAll);
          sc.remoteAllocation();
          pointsList.setListData(new String[] {});
          pointsList.transferFocus();
          pointsNumber.clear();
          chart.getView().shoot();
        } else {

          button.setText("Show");

          boolean[] showInfo = sc.getHighlighted();
          Coord3d coordbuf[] = sc.getData();
          ArrayList<String> pointsInfoArray = new ArrayList<String>();
          for (int i = 0; i < showInfo.length; i++) {
            if (showInfo[i]) {

              pointsInfoArray.add(coordbuf[i].toString());
              // new String("x: " + coordbuf[i].x + " y: "
              // + coordbuf[i].y + " z: " + coordbuf[i].z));
              pointsNumber.add(new Integer(i));
            }
            pointsList.setListData(pointsInfoArray.toArray());
          }

          sc.setShowPoints(sc.getHighlighted());
          chart.getView().shoot();

        }
        hide ^= true;

      }
    });
  }
}
