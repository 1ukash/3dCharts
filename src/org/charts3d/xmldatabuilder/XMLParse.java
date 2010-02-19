package org.charts3d.xmldatabuilder;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;

import org.charts3d.Plotdata;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParse {
	File xmlfile;
	Plotdata cdata = new Plotdata();

	public XMLParse(String f) {
		xmlfile = new File(f);
	}

	public Plotdata parse(int num) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document = null;
		try {
			DocumentBuilder db = factory.newDocumentBuilder();
			document = db.parse(xmlfile);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Element root_elem = (Element) document.getDocumentElement();

		NodeList child_lst = root_elem.getChildNodes();

		int cg=0;
		int cv=0;
		int cf=0;

		for (int i = 0; i < child_lst.getLength(); i++) {
			if (child_lst.item(i).getNodeType() == Node.ELEMENT_NODE
					&& child_lst.item(i).getNodeName() == "data") {
				cg++;
				if (cg!=num) continue;
				NodeList child_lst_1 = child_lst.item(i).getChildNodes();
				for (int j = 0; j < child_lst_1.getLength(); j++) {
					if (child_lst_1.item(j).getNodeType() == Node.ELEMENT_NODE
							&& child_lst_1.item(j).getNodeName() == "values") {
						Element node_1 = (Element) child_lst_1.item(j);
						String axis = node_1.getAttribute("axis");
						String name = node_1.getAttribute("name");
						cdata.attrToMap(axis,name);
						NodeList child_lst_2 = child_lst_1.item(j)
								.getChildNodes();
							cv++;
							cf=0;
							for (int k = 0; k < child_lst_2.getLength(); k++) {
								if (child_lst_2.item(k).getNodeType() == Node.ELEMENT_NODE
										&& child_lst_2.item(k).getNodeName() == "value") {
									cf++;
									if(cv==1) cdata.addX(child_lst_2.item(k).getTextContent());
									if(cv==2) cdata.addY(child_lst_2.item(k).getTextContent());
									if(cv==3) cdata.addZ(child_lst_2.item(k).getTextContent());
								}
							}
						}

					}
				
				cdata.alToCd(cf);
				}

			}
	return cdata;
	}
}