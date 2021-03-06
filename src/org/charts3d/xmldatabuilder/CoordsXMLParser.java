package org.charts3d.xmldatabuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.charts3d.PlotStorage;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class CoordsXMLParser {
	
	ArrayList<Double>[] arrayCoords = null;
	Collection<PlotStorage> plotStorage = null;
	
	public Collection<PlotStorage> parse(InputStream is) throws IOException {
		XPathFactory xpfactory = XPathFactory.newInstance();
		XPath path = xpfactory.newXPath();
		double val = 0;
		
		int len = is.available();
		byte[] buf = new byte[len];
		is.read(buf);
		String s = new String(buf);
		
		try {
			NodeList data = (NodeList) path.evaluate("/plotdata/data", new InputSource(new StringReader(s)), XPathConstants.NODESET);
			int lenData = data.getLength();
			plotStorage = new ArrayList<PlotStorage>();
			for (int i = 0; i < lenData; i++) {
				NodeList values = (NodeList) path.evaluate("/plotdata/data" + "[" + (i + 1) +"]" + "/values", new InputSource(new StringReader(s)), XPathConstants.NODESET);
				int lenValues = values.getLength();
				arrayCoords = new ArrayList[lenValues];
				plotStorage.add(new PlotStorage());
				for(int j = 0; j < lenValues; j++) {
					arrayCoords[j] = new ArrayList<Double>();
					String axis = (String) path.evaluate("/plotdata/data" + "[" + (i + 1) +"]" + "/values" + "[" + (j + 1) + "]" + "/@axis", new InputSource(new StringReader(s)));
					String name = (String) path.evaluate("/plotdata/data" + "[" + (i + 1) +"]" + "/values" + "[" + (j + 1) + "]" + "/@name", new InputSource(new StringReader(s)));
					((ArrayList<PlotStorage>)plotStorage).get(i).addAttributeToMap(axis,name);
					NodeList value = (NodeList) path.evaluate("/plotdata/data" + "[" + (i + 1) +"]" + "/values" + "[" + (j + 1) + "]" + "/value", new InputSource(new StringReader(s)), XPathConstants.NODESET);
					int lenValue = value.getLength();
					for(int k = 0; k < lenValue; k++) {
						val = ((Number) path.evaluate("/plotdata/data" + "[" + (i + 1) +"]" + "/values" + "[" + (j + 1) + "]" + "/value" + "[" + (k + 1) +"]", new InputSource(new StringReader(s)), XPathConstants.NUMBER)).doubleValue();
						addCoordsToArray(j,val);
						}
					}
				((ArrayList<PlotStorage>)plotStorage).get(i).addCooordsToStorage(arrayCoords);
				//addCoordsToStorage(i);
				}
			}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return plotStorage;
	}
	
	public void addCoordsToArray(int j, double val){
		arrayCoords[j].add(val);
	}

}
