/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import sample.category.Category;
import sample.category.jaxb_Categories;
import sample.page.Page;
import sample.product.Product;

/**
 *
 * @author MinhNBHSE61805
 */
public class XMLUtils implements Serializable {

    public static void getLeQuanCategory(String filePath, List<Category> categoryList, List<String> listUri) throws FileNotFoundException, XMLStreamException {
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
                if (tagName.equals("li")) {
                    String liId = XMLUtils.getAttrOfElement(reader, "", "id");
                    if (liId.equals("menu-item-3213") || liId.equals("menu-item-4410")
                            || liId.equals("menu-item-3218") || liId.equals("menu-item-3222")) {
                        inCategory = true;
                        category = new Category();
                        category.setCategoryID(0);
                        category.setDescription("");
                        pos++;
                    }
                }
                if (tagName.equals("a") && inCategory) {
                    String href = XMLUtils.getAttrOfElement(reader, "", "href");
                    listUri.add(href);
                    String slugify = href.replace(Page.prefixLeQuanUrl + "/danh-muc/", "");
                    category.setSlugify(slugify);
                }
//                
                if (tagName.equals("i") && inCategory) {
                    reader.nextTag();
                    reader.next();
                    category.setCategoryName(reader.getText());
                    inCategory = false;
                    System.out.println(category.getCategoryName() + " | " + category.getSlugify());
                    categoryList.add(category);
                }
            }
        }
        System.out.println("Done get categories!!!");
    }

    public static int getCategoryPageLeQuan(String filePath) throws FileNotFoundException, XMLStreamException {
        int page = 1;
        XMLInputFactory factory = XMLInputFactory.newFactory();
        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, true);
        factory.setProperty(XMLInputFactory.IS_VALIDATING, false);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        factory.setProperty(XMLInputFactory.IS_COALESCING, true);
        System.out.println("file path: " + filePath);
        InputStream is = null;
        XMLStreamReader reader = null;
        is = new FileInputStream(filePath);
        reader = factory.createXMLStreamReader(is);
        String pageStr = "";

        while (reader.hasNext()) {
            int cursor = reader.next();
            if (cursor == XMLStreamConstants.START_ELEMENT) {
                String tagName = reader.getLocalName();
                if (tagName.equals("a")) {
                    if (XMLUtils.getAttrOfElement(reader, "", "class").equals("page-numbers")) {
                        pageStr = reader.getElementText();
                        System.out.println(pageStr);
                        try {
                            int tmpPage = Integer.parseInt(pageStr);
                            if (tmpPage > page) {
                                page = tmpPage;
                            }
                        } catch (NumberFormatException e) {
                            continue;
                        }
                    }
                }
            }
        }

        return page;
    }

    public static void getProductListLeQuan(List<String> listFilePath, List<Product> productList) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, true);
        factory.setProperty(XMLInputFactory.IS_VALIDATING, false);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        factory.setProperty(XMLInputFactory.IS_COALESCING, true);

        InputStream is = null;
        XMLStreamReader reader = null;
        boolean inProduct = false;
        Product product = new Product();

        for (int i = 0; i < listFilePath.size(); i++) {
            is = new FileInputStream(listFilePath.get(i).toString());
            reader = factory.createXMLStreamReader(is);
            while (reader.hasNext()) {
                int cursor = reader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if (tagName.equals("div")) {
                        String divClass = XMLUtils.getAttrOfElement(reader, "", "class");
                        if (divClass.equals("product-inner clearfix")) {
                            product = new Product();
                            inProduct = true;
                        }
                    }

                    if (tagName.equals("a")) {
                        String aClass = XMLUtils.getAttrOfElement(reader, "", "class");
                        if (aClass != null && aClass.equals("woocommerce-LoopProduct-link")) {
                            String aHref = XMLUtils.getAttrOfElement(reader, "", "href");
                            product.setSlugify(aHref.replace(Page.prefixLeQuanUrl + "/san-pham/", ""));
                        }
                    }

                    if (tagName.equals("img") && inProduct) {
                        String imgClass = XMLUtils.getAttrOfElement(reader, "", "class");
                        if (imgClass != null && imgClass.equals("attachment-shop_catalog size-shop_catalog wp-post-image")) {
                            product.setImg(XMLUtils.getAttrOfElement(reader, "", "src"));
                        }
                    }

                    if (tagName.equals("h3") && inProduct) {
                        product.setProductName(reader.getElementText());
                    }

                    if (tagName.equals("span") && inProduct) {
                        String spanClass = XMLUtils.getAttrOfElement(reader, "", "class");
                        if (spanClass != null && spanClass.equals("woocommerce-Price-amount amount")) {
                            reader.next();
                            product.setPrice(reader.getText());
                            productList.add(product);
                        }
                    }
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

    public static <T> String saveToXML(T obj) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            StringWriter sw = new StringWriter();
            Marshaller ms = context.createMarshaller();
            ms.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            ms.marshal(obj, sw);
            System.out.println(sw.toString());
            return sw.toString();
        } catch (JAXBException ex) {
            return null;
        }
    }

    public static boolean validateXMLBeforeSaveToDB(String schemaFilePath, String xmlStr) throws IOException {
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(schemaFilePath));
            InputSource source = new InputSource(new StringReader(xmlStr));
            Validator validator = schema.newValidator();
            validator.validate(new SAXSource(source));
        } catch (SAXException ex) {
            System.out.println(ex.getMessage());
            System.out.println("False");
            return false;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XMLUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("True");
        return true;
    }
}
