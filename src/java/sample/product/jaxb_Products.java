/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.product;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author MinhNBHSE61805
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    
})
@XmlRootElement(name = "Products", namespace = "www.Products.com")
public class jaxb_Products {
    @XmlElement(name = "Product", namespace = "www.ProductSchema.com", required = true)
    private List<Product> product;

    public List<Product> getProduct() {
        if (product == null) {
            product = new ArrayList<Product>();
        }
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
    
    
}
