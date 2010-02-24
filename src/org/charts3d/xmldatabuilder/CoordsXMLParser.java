package org.charts3d.xmldatabuilder;


import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class CoordsXMLParser {
	
	public void parse(InputStream is) throws IOException {
		XPathFactory xpfactory = XPathFactory.newInstance();
		XPath path = xpfactory.newXPath();

		int len = is.available();
		byte[] buf = new byte[len];
		is.read(buf);
		String s = new String(buf);

		try {
			NodeList data = (NodeList) path.evaluate("/plotdata/data", new InputSource(new StringReader(s)), XPathConstants.NODESET);
			int len_data = data.getLength();
			for (int i = 0; i < len_data; i++) {
				NodeList values = (NodeList) path.evaluate("/plotdata/data" + "[" + (i + 1) +"]" + "/values", new InputSource(new StringReader(s)), XPathConstants.NODESET);
				int len_values = values.getLength();
				for(int j = 0; j < len_values; j++) {
					NodeList value = (NodeList) path.evaluate("/plotdata/data" + "[" + (i + 1) +"]" + "/values" + "[" + (j + 1) + "]" + "/value", new InputSource(new StringReader(s)), XPathConstants.NODESET);
					int len_value = value.getLength();
					for(int k = 0; k < len_value; k++) {
						double val = ((Number) path.evaluate("/plotdata/data" + "[" + (i + 1) +"]" + "/values" + "[" + (j + 1) + "]" + "/value" + "[" + (k + 1) +"]", new InputSource(new StringReader(s)), XPathConstants.NUMBER)).doubleValue();
						
					}
				}
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		//return null;
	}
	
}
