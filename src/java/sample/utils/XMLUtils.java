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
import java.sql.Timestamp;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import sample.category.Category;
import sample.page.Page;
import sample.product.Product;

/**
 *
 * @author MinhNBHSE61805
 */
public class XMLUtils implements Serializable {

    public static void getLeQuanCategory(String filePath, List<Category> categoryList) throws FileNotFoundException, XMLStreamException {
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
        int pos = -1;
        Category category = new Category();

        while (reader.hasNext()) {
            int cursor = reader.next();
            if (cursor == XMLStreamConstants.START_ELEMENT) {
                String tagName = reader.getLocalName();
                System.out.println(tagName);
                if (tagName.equals("li")) {
                    String liId = XMLUtils.getAttrOfElement(reader, "", "id");
                    if (liId.equals("menu-item-3213") || liId.equals("menu-item-4410")
                            || liId.equals("menu-item-3218") || liId.equals("menu-item-3222")) {
                        System.out.println("id: " + liId);
                        category = new Category();
                        inCategory = true;
                        pos++;
                    }
                }
                if (tagName.equals("a") && inCategory) {
                    String href = XMLUtils.getAttrOfElement(reader, "", "href");
                    String description = href.replace(Page.prefixLeQuanUrl, "");
                    category.setDescription(description);
                }
//                
                if (tagName.equals("i") && inCategory) {
                    reader.nextTag();
                    reader.next();
                    category.setCategoryName(reader.getText());
                    inCategory = false;
                    categoryList.add(category);
                }
            }
        }
    }

    public static void getCategoriesSlugify(String filePath, List<String> slugifyList, List<Category> categoryList) throws FileNotFoundException, XMLStreamException {
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
        int pos = -1;
        Category category = new Category();

        while (reader.hasNext()) {
            int cursor = reader.next();
            if (cursor == XMLStreamConstants.START_ELEMENT) {
                String tagName = reader.getLocalName(); //get html tag
                if (tagName.equals("li")) {
                    String liAttr = XMLUtils.getAttrOfElement(reader, "", "class");
                    if (liAttr != null && liAttr.equals("item-child deeper parent")) {
                        inCategory = true;
                        category = new Category();
                        pos++;
                    }
                }

                if (tagName.equals("a") && inCategory) {
                    String hrefCategory = XMLUtils.getAttrOfElement(reader, "", "href");
                    if (hrefCategory != null) {
                        System.out.println(hrefCategory);
                        if (!hrefCategory.contains("mouse-pad")) {
                            slugifyList.add(hrefCategory);
                            category.setCategoryName(reader.getElementText());
                            category.setDescription("");
                        }
                        inCategory = false;
                        categoryList.add(category);
                    }
                }
            }
        }
    }

    public static void getProductSlugify(List<String> filePath, List<String> slugifyList, List<Product> productList)
            throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, true);
        factory.setProperty(XMLInputFactory.IS_VALIDATING, false);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        factory.setProperty(XMLInputFactory.IS_COALESCING, true);

        InputStream is = null;
        XMLStreamReader reader = null;
        int pos = -1;
        Product product = new Product();

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
                        String divClass = XMLUtils.getAttrOfElement(reader, "", "class");
                        if (divClass.equals("col-sm-4 col-xs-12 padding-none col-fix20")) {
                            product = new Product();
                            product.setLastModified((new Timestamp(System.currentTimeMillis())).toString());
                            pos++;
                        }
                    }
                    
                    if (tagName.equals("a")) {
                        String href = XMLUtils.getAttrOfElement(reader, "", "href");
                        product.setSlugify(href.replace("/products/", ""));
                    }
                    
                    if (tagName.equals("img")) {
                        String imgClass = XMLUtils.getAttrOfElement(reader, "", "class");
                        if (imgClass.equals("product-row-thumbnail")) {
                            product.setImg(XMLUtils.getAttrOfElement(reader, "", "src"));
                        }
                    }
                    
                    if (tagName.equals("h2")) {
                        if (XMLUtils.getAttrOfElement(reader, "", "class").equals("product-row-name")) {
                            product.setProductName(reader.getElementText());
                        }
                    }
                    
                    if (tagName.equals("span")) {
                        if (XMLUtils.getAttrOfElement(reader, "", "class").equals("product-row-sale")) {
                            product.setPrice(reader.getElementText());
                            productList.add(product);
                        }
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
