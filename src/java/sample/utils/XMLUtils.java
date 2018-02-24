/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author MinhNBHSE61805
 */
public class XMLUtils implements Serializable {

    public static void getCategoriesSlugify(String filePath, List<String> slugifyList) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, true);
        factory.setProperty(XMLInputFactory.IS_VALIDATING, false);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        factory.setProperty(XMLInputFactory.IS_COALESCING, true);

        InputStream is = null;
        XMLStreamReader reader = null;
        is = new FileInputStream(filePath);
        reader = factory.createXMLStreamReader(is);
        boolean inCategory = false;

        while (reader.hasNext()) {
            int cursor = reader.next();
            if (cursor == XMLStreamConstants.START_ELEMENT) {
                String tagName = reader.getLocalName(); //get html tag
                if (tagName.equals("li")) {
                    String liAttr = XMLUtils.getAttrOfElement(reader, "", "class");
                    if (liAttr != null && liAttr.equals("item-child deeper parent")) {
                        inCategory = true;
                    }
                }

                if (tagName.equals("a") && inCategory) {
                    String hrefCategory = XMLUtils.getAttrOfElement(reader, "", "href");
                    if (hrefCategory != null) {
                        hrefCategory.replace("/collections/", "");
                        System.out.println(hrefCategory);
                        slugifyList.add(hrefCategory);
                        inCategory = false;
                    }
                }
            }
        }
    }

    public static void getProductSlugify(List<String> filePath, List<String> slugifyList) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, true);
        factory.setProperty(XMLInputFactory.IS_VALIDATING, false);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        factory.setProperty(XMLInputFactory.IS_COALESCING, true);

        InputStream is = null;
        XMLStreamReader reader = null;
        boolean inProduct = false;

        System.out.println("Begin for loop");
        for (int i = 0; i < filePath.size(); i++) {
            System.out.println(filePath.get(i).toString());
            is = new FileInputStream(filePath.get(i).toString());
            reader = factory.createXMLStreamReader(is);
            while (reader.hasNext()) {
                int cursor = reader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if (tagName.equals("div")) {
                        String sectionClass = XMLUtils.getAttrOfElement(reader, "", "class");
                        if (sectionClass != null && sectionClass.equals("product-row")) {
                            inProduct = true;
                        }
                    }

                    if (tagName.equals("a") && inProduct) {
                        String aHref = XMLUtils.getAttrOfElement(reader, "", "href");
                        slugifyList.add(aHref);
                        inProduct = false;
                    }
                }
            }
        }
    }

    public static int getPageOfCategory(String filePath) throws FileNotFoundException, XMLStreamException {
        int page = 1;
        XMLInputFactory factory = XMLInputFactory.newFactory();
        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, true);
        factory.setProperty(XMLInputFactory.IS_VALIDATING, false);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        factory.setProperty(XMLInputFactory.IS_COALESCING, true);

        InputStream is = null;
        XMLStreamReader reader = null;

        is = new FileInputStream(filePath);
        reader = factory.createXMLStreamReader(is);
        while (reader.hasNext()) {
            int cursor = reader.next();
            if (cursor == XMLStreamConstants.START_ELEMENT) {
                String tagName = reader.getLocalName();
                if (tagName.equals("a")) {
                    String tempPage = XMLUtils.getAttrOfElement(reader, "", "title");
                    tempPage.trim();
                    boolean isValidPage = true;
                    for (int i = 0; i < tempPage.length(); i++) {
                        if (tempPage.charAt(i) < 49 || tempPage.charAt(i) > 57) {
                            isValidPage = false;
                            break;
                        }
                    }
                    if (isValidPage) {
                        page = Integer.parseInt(tempPage);
                    }
                }
            }
        }

        return page;
    }

    public static void staxCursorParser(List<String> filePath, String xmlFilePath) {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, true);
        factory.setProperty(XMLInputFactory.IS_VALIDATING, false);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        factory.setProperty(XMLInputFactory.IS_COALESCING, true);
        //Declare var
        InputStream is = null;
        XMLStreamReader reader = null;
        String productDescription = "";
        String productPrice = "";
    }

    public static String getAttrOfElement(XMLStreamReader reader, String namespaceURI, String attr) {
        if (reader != null) {
            return reader.getAttributeValue(namespaceURI, attr);
        }
        return null;
    }
}
