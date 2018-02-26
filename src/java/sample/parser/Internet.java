/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MinhNBHSE61805
 */
public class Internet {

    public static void parseCategoryLeQuan(String filePath, String uri) {
        Writer writer = null;
        boolean inScript = false, inCategory = false;

        try {
            URL url = new URL(uri);
            URLConnection con = url.openConnection();
            con.addRequestProperty("User-agent", "Chrome/61.0.3163.100 (compatible; MSIE 6.0; Windows NT 5.0)");
            InputStream is = con.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String inputLine;
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath), "UTF-8"));

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains("<script")) {
                    inScript = true;
                }

                if (inputLine.contains("id =\"bn-info-menu\"")) {
                    inCategory = true;
                }

                if (inputLine.contains("id=\"menu-main-menu\"")) {
                    writer.write("</ul>\n</div>\n</div>\n</div>\n</div>\n");
                    if (writer != null) {
                        writer.close();
                    }
                    break;
                }

                if ((!inputLine.contains("script") && !inputLine.contains("noscript")
                        && !inputLine.contains("meta") && !inputLine.contains("--")
                        && !inputLine.contains("height=1") && !inputLine.contains("<input type=")
                        && !inputLine.contains("data-interval") && !inputLine.contains("nbsp")
                        && !inputLine.contains("footer") && !inputLine.contains("<link")
                        && !inScript && inputLine.length() > 0 && inCategory)) {
                    if (inputLine.contains("&ndash")) {
                        inputLine = inputLine.replace("&ndash", "");
                    }
                    writer.write(inputLine.trim() + "\n");
                }

                if (inputLine.contains("</script>")) {
                    inScript = false;
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void parseCategoryPageLeQuan(List<String> listFilePath, List<String> listUri) {
        Writer writer = null;
        boolean inScript = false;
        boolean inPagination = false;

        try {
            for (int i = 0; i < listUri.size(); i++) {
                URL url = new URL(listUri.get(i).toString());
                URLConnection con = url.openConnection();
                con.addRequestProperty("User-agent", "Chrome/61.0.3163.100 (compatible; MSIE 6.0; Windows NT 5.0)");
                InputStream is = con.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String inputLine;
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(listFilePath.get(i).toString()), "UTF-8"));

                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains("<script")) {
                        inScript = true;
                    }

                    if (inputLine.contains("<nav class=\"woocommerce-pagination\"")) {
                        inPagination = true;
                    }

                    if ((!inputLine.contains("script") && !inputLine.contains("noscript")
                            && !inputLine.contains("meta") && !inputLine.contains("--")
                            && !inputLine.contains("height=1") && !inputLine.contains("<input type=")
                            && !inputLine.contains("data-interval") && !inputLine.contains("nbsp")
                            && !inputLine.contains("footer") && !inputLine.contains("<link")
                            && !inScript && inputLine.length() > 0 && inPagination)) {
                        if (inputLine.contains("&ndash")) {
                            inputLine = inputLine.replace("&ndash", "");
                        }
                        writer.write(inputLine.trim() + "\n");
                    }

                    if (inputLine.contains("</nav") && inPagination) {
                        if (writer != null) {
                            writer.close();
                        }
                        break;
                    }

                    if (inputLine.contains("</script")) {
                        inScript = false;
                    }
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void parseCategoryProductLeQuan(String filePath, String uri) {
        Writer writer = null;
        boolean inScript = false, inProduct = false;

        try {
            URL url = new URL(uri);
            URLConnection con = url.openConnection();
            con.addRequestProperty("User-agent", "Chrome/61.0.3163.100 (compatible; MSIE 6.0; Windows NT 5.0)");
            InputStream is = con.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String inputLine;
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath), "UTF-8"));

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains("<script")) {
                    inScript = true;
                }

                if (inputLine.contains("<ul class=\"products\"")) {
                    inProduct = true;
                }

                if (inputLine.contains("<div class=\"shop-toolbar\"") && inProduct) {
                    if (writer != null) {
                        writer.close();
                    }
                    break;
                }

                if ((!inputLine.contains("script") && !inputLine.contains("noscript")
                        && !inputLine.contains("meta") && !inputLine.contains("--")
                        && !inputLine.contains("height=1") && !inputLine.contains("<input type=")
                        && !inputLine.contains("data-interval") && !inputLine.contains("nbsp")
                        && !inputLine.contains("footer") && !inputLine.contains("<link")
                        && !inScript && inputLine.length() > 0 && inProduct)) {
                    if (inputLine.contains("&ndash")) {
                        inputLine = inputLine.replace("&ndash", "");
                    }
                    if (inputLine.contains("&nbsp;")) {
                        inputLine = inputLine.replace("&nbsp;", "");
                    }
                    writer.write(inputLine.trim() + "\n");
                }

                if (inputLine.contains("</script>")) {
                    inScript = false;
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void parseListCategory(List<String> listFilePath, List<String> listUri) {
        Writer writer = null;
        boolean inScript = false;
        boolean inMainMenu = false;

        try {
            for (int i = 0; i < listUri.size(); i++) {
                URL url = new URL(listUri.get(i).toString());
                URLConnection con = url.openConnection();
                con.addRequestProperty("User-agent", "Chrome/61.0.3163.100 (compatible; MSIE 6.0; Windows NT 5.0)");
                InputStream is = con.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String inputLine;
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(listFilePath.get(i).toString()), "UTF-8"));

                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains("script")) {
                        inScript = true;
                    }

                    if (inputLine.contains("<div id=\"mainmenu\">")) {
                        inMainMenu = true;
                    }

                    if (inMainMenu && inputLine.contains("<div class=\"container\" id=\"menu-mobile\">")) {
                        writer.write("</div>");
                        if (writer != null) {
                            writer.close();
                        }
                        break;
                    }

                    if ((!inputLine.contains("script") && !inputLine.contains("noscript")
                            && !inputLine.contains("meta") && !inputLine.contains("--")
                            && !inputLine.contains("height=1") && !inputLine.contains("<input type=")
                            && !inputLine.contains("data-interval") && !inputLine.contains("nbsp")
                            && !inputLine.contains("footer") && !inputLine.contains("<link")
                            && !inScript && inputLine.length() > 0 && inMainMenu)) {
                        if (inputLine.contains("&ndash")) {
                            inputLine = inputLine.replace("&ndash", "");
                        }
                        writer.write(inputLine.trim() + "\n");
                    }

                    if (inputLine.contains("</script>")) {
                        inScript = false;
                    }
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void parseProductGearVN(List<String> listFilePath, List<String> listUri) {
        Writer writer = null;
        boolean inScript = false;
        boolean inProductArea = false;
        boolean isValid = false;

        try {
            for (int i = 0; i < listUri.size(); i++) {
                URL url = new URL(listUri.get(i).toString());
                URLConnection con = url.openConnection();
                con.addRequestProperty("User-agent", "Chrome/61.0.3163.100 (compatible; MSIE 6.0; Windows NT 5.0)");
                InputStream is = con.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String inputLine;
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(listFilePath.get(i).toString()), "UTF-8"));
                writer.write("<div>");
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains("<div id=\"register-email\">")) {
                        writer.write("</div>");
                        if (writer != null) {
                            writer.close();
                        }
                        break;
                    }

                    if (inputLine.contains("<script")) {
                        inScript = true;
                    }

                    if (inputLine.contains("<div class=\"col-md-12 product-list\"")) {
                        inProductArea = true;
                    }

                    if (inputLine.contains("<div class=\"col-md-12 \">")) {
                        inProductArea = false;
                    }

                    if ((!inputLine.contains("script") && !inputLine.contains("noscript")
                            && !inputLine.contains("meta") && !inputLine.contains("--")
                            && !inputLine.contains("height=1") && !inputLine.contains("<input type=")
                            && !inputLine.contains("data-interval")
                            && !inputLine.contains("footer") && !inputLine.contains("<link")
                            && !inScript && inputLine.length() > 0 && inProductArea)) {
                        if (inputLine.contains("<img")) {
                            int screenSize = 22;
                            if (inputLine.contains("24.5\"")) {
                                inputLine = inputLine.replace("24.5\"", "24.5");
                            }
                            if (inputLine.contains("R22\"")) {
                                inputLine = inputLine.replace("R22\"", "R22 \"");
                            }
                            if (inputLine.contains("G433\"")) {
                                inputLine = inputLine.replace("G433\"", "G433 \"");
                            }
                            if (inputLine.contains("G633\"")) {
                                inputLine = inputLine.replace("G633\"", "G633 \"");
                            }
                            if (inputLine.contains("C922\"")) {
                                inputLine = inputLine.replace("C922\"", "C922 \"");
                            }
                            while (screenSize < 40) {
                                if (inputLine.contains(screenSize + "\"")) {
                                    inputLine = inputLine.replace(screenSize + "\"", screenSize + "");
                                    break;
                                }
                                screenSize++;
                            }
                        }
                        if (inputLine.contains("&")) {
                            inputLine = inputLine.replace("&", "");
                        }
                        writer.write(inputLine.trim() + "\n");
                    }

                    if (inputLine.contains("</script>")) {
                        inScript = false;
                    }
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void parseProductPageGearVN(String filePath, String uri) {
        Writer writer = null;
        boolean inScript = false;
        boolean inPaginationArea = false;
        boolean isFound = false;

        try {
            URL url = new URL(uri);
            URLConnection con = url.openConnection();
            con.addRequestProperty("User-agent", "Chrome/61.0.3163.100 (compatible; MSIE 6.0; Windows NT 5.0)");
            InputStream is = con.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String inputLine;
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath), "UTF-8"));

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains("<input type=\"hidden\" name=\"limitstart\"")) {
                    if (writer != null) {
                        writer.close();
                    }
                    break;
                }

                if (inputLine.contains("</html>")) {
                    writer.write("<div></div>");
                    if (writer != null) {
                        writer.close();
                    }
                    break;
                }

                if (inputLine.contains("<script")) {
                    inScript = true;
                }

                if (inputLine.contains("<ul class=\"pagination-list\">")) {
                    inPaginationArea = true;
                }

                if ((!inputLine.contains("script") && !inputLine.contains("noscript")
                        && !inputLine.contains("meta") && !inputLine.contains("--")
                        && !inputLine.contains("height=1") && !inputLine.contains("<input type=")
                        && !inputLine.contains("data-interval") && !inputLine.contains("nbsp")
                        && !inputLine.contains("footer") && !inputLine.contains("<link")
                        && !inScript && inputLine.length() > 0 && inPaginationArea)) {
                    if (inputLine.contains("&ndash")) {
                        inputLine = inputLine.replace("&ndash", "");
                    }
                    if (inputLine.contains("&hellip")) {
                        inputLine = inputLine.replace("&hellip", "");
                    }
                    isFound = true;
                    writer.write(inputLine.trim() + "\n");
                }

                if (inputLine.contains("</script>")) {
                    inScript = false;
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void parseProductDetailGearVN(List<String> listFilePath, List<String> listUri) {
        Writer writer = null;

        try {
            for (int i = 0; i < listUri.size(); i++) {
                boolean inScript = false;
                boolean isValid = false;
                boolean inDescription = false;
                boolean inProductPrice = false, inProductName = false;

                URL url = new URL(listUri.get(i).toString());
                URLConnection con = url.openConnection();
                con.addRequestProperty("User-agent", "Chrome/61.0.3163.100 (compatible; MSIE 6.0; Windows NT 5.0)");
                InputStream is = con.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String inputLine;
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(listFilePath.get(i).toString()), "UTF-8"));
                writer.write("<html>\n");
                while ((inputLine = in.readLine().trim()) != null) {
                    if (inputLine.contains("</html>")) {
                        writer.write(inputLine);
                        if (writer != null) {
                            writer.close();
                        }
                        break;
                    }

                    if (inputLine.contains("<script")) {
                        inScript = true;
                    }

                    //get product info
                    if (inputLine.contains("<h1 class=\"product_name\">")) {
                        isValid = true;
                    }

                    if (inputLine.contains("<div class=\"fb-like pull-right\"")) {
                        isValid = false;
                    }

                    //get product description
                    if (inputLine.contains("id=\"chitiet\"")) {
                        System.out.println("chi tiet");
                        isValid = true;
                    }

                    if (inputLine.contains("id=\"dacdiem\"")) {
                        isValid = false;
                    }

//                    if (inProductName == true || inDescription == true || inProductPrice == true) {
//                        isValid = true;
//                    } else {
//                        isValid = false;
//                    }
                    if ((!inputLine.contains("script") && !inputLine.contains("noscript")
                            && !inputLine.contains("meta") && !inputLine.contains("--")
                            && !inputLine.contains("height=1") && !inputLine.contains("<input type=")
                            && !inputLine.contains("data-interval") && !inputLine.contains("nbsp")
                            && !inputLine.contains("footer") && !inputLine.contains("<link")
                            && !inScript && isValid)) {
                        if (inputLine.contains("&ndash")) {
                            inputLine = inputLine.replace("&ndash", "");
                        }
                        if (inputLine.contains("&hellip")) {
                            inputLine = inputLine.replace("&hellip", "");
                        }
                        System.out.println(inputLine);
                        writer.write(inputLine + "\n");
                    }

                    if (inputLine.contains("</script>")) {
                        inScript = false;
                    }
                }
                System.out.println("Done: " + listUri.get(i).toString());
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
