/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import sample.page.Page;
import sample.parser.Internet;
import sample.utils.XMLUtils;

/**
 *
 * @author MinhNBHSE61805
 */
public class ProcessServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Internet internet = new Internet();

            List<String> listFilePath = new ArrayList<>();
            listFilePath.add(Page.prefixFilePath + "homepage.html");

            List<String> listUri = new ArrayList<>();
            listUri.add(Page.prefixGearVNUrl);

            // get list category in home page
            internet.parseListCategory(listFilePath, listUri);

            //declare list of category list slugify
            List<String> slugifyCategoryList = new ArrayList<>();
            XMLUtils.getCategoriesSlugify(Page.prefixFilePath + "homepage.html", slugifyCategoryList);
            System.out.println("--Done--\nGot a categories slugify list\n");

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
                XMLUtils.getProductSlugify(listFilePath, slugifyProductList);
                System.out.println(slugifyProductList.size());
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(ProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
