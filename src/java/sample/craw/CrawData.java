/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.craw;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import sample.category.Category;
import sample.page.Page;
import sample.parser.Internet;
import sample.product.Product;
import sample.utils.XMLUtils;

/**
 *
 * @author MinhNBHSE61805
 */
public class CrawData {

    public static void crawDataLeQuan() {
        try {
            Internet internet = new Internet();
            internet.parseCategoryLeQuan(Page.prefixFilePath + "categoryList.html", Page.prefixLeQuanUrl + "/danh-muc/dien-thoai/");
            List<Category> categoryList = new ArrayList<>();
            XMLUtils.getLeQuanCategory(Page.prefixFilePath + "categoryList.html", categoryList);
            for (int i = 0; i < categoryList.size(); i++) {
                System.out.println(categoryList.get(i).getCategoryName());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrawData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(CrawData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void crawDataGearVN() {
        try {
            Internet internet = new Internet();
            List<Product> productList = new ArrayList<Product>();
            List<Category> categoryList = new ArrayList<Category>();

            List<String> listFilePath = new ArrayList<>();
            listFilePath.add(Page.prefixFilePath + "homepage.html");

            List<String> listUri = new ArrayList<>();
            listUri.add(Page.prefixGearVNUrl);

            // get list category in home page
            internet.parseListCategory(listFilePath, listUri);

            //declare list of category list slugify
            List<String> slugifyCategoryList = new ArrayList<>();
            XMLUtils.getCategoriesSlugify(Page.prefixFilePath + "homepage.html", slugifyCategoryList, categoryList);
            System.out.println("--Done--\nGot a categories slugify list\n");
            System.out.println(slugifyCategoryList.size());
            //declare list of product list slugify
            List<String> slugifyProductList = new ArrayList<>();

            for (int i = 0; i < slugifyCategoryList.size(); i++) {
                listFilePath = new ArrayList<String>();
                listUri = new ArrayList<>();
                internet.parseProductPageGearVN(Page.prefixFilePath + "page-" + slugifyCategoryList.get(i).toString().replace("/collections/", "") + ".html",
                        Page.prefixGearVNUrl + slugifyCategoryList.get(i).toString());
                int page = XMLUtils.getPageOfCategory(Page.prefixFilePath + "page-" + slugifyCategoryList.get(i).toString().replace("/collections/", "") + ".html");
                for (int j = 0; j < page; j++) {
                    String categoryPage = Page.prefixGearVNUrl + slugifyCategoryList.get(i).toString() + "?page=" + (j + 1);
                    String categoryFilePath = Page.prefixFilePath + "product-" + slugifyCategoryList.get(i).toString().replace("/collections/", "") + "-" + (j + 1) + ".html";
                    listFilePath.add(categoryFilePath);
                    listUri.add(categoryPage);
                    internet.parseProductGearVN(listFilePath, listUri);
                    System.out.println("Done: " + (j + 1) + "/" + page + "\n");
                }
                System.out.println("-*-Done-*-\n");
                XMLUtils.getProductSlugify(listFilePath, slugifyProductList, productList);
            }

            listFilePath = new ArrayList<String>();
            listUri = new ArrayList<>();
            for (int i = 0; i < slugifyProductList.size(); i++) {
                String productFilePath = Page.prefixFilePath + "product\\" + slugifyProductList.get(i).toString().replace("/products/", "") + ".html";
                String productUri = Page.prefixGearVNUrl + slugifyProductList.get(i).toString();
                listFilePath.add(productFilePath.toString());
                listUri.add(productUri.toString());
            }
//            internet.parseProductDetailGearVN(listFilePath, listUri);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrawData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(CrawData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}