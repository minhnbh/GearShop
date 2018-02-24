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

                    if (inputLine.contains("script")) {
                        inScript = true;
                    }

                    if ((!inputLine.contains("script") && !inputLine.contains("noscript")
                            && !inputLine.contains("meta") && !inputLine.contains("--")
                            && !inputLine.contains("height=1") && !inputLine.contains("<input type=")
                            && !inputLine.contains("data-interval") && !inputLine.contains("nbsp")
                            && !inputLine.contains("footer") && !inputLine.contains("<link")
                            && !inScript && inputLine.length() > 0 && inputLine.contains("<a") && isValid)) {
                        if (inputLine.contains("&ndash")) {
                            inputLine = inputLine.replace("&ndash", "");
                        }
                        writer.write(inputLine.trim() + "test</a>\n");
                        isValid = false;
                    }

                    if (inputLine.contains("<div class=\"product-row\"")) {
                        isValid = true;
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
                
                if (inputLine.contains("script")) {
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
}
