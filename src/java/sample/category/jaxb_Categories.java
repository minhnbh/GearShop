/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.category;

import java.io.Serializable;
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
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "Categories", namespace = "www.Categories.com")
public class jaxb_Categories implements Serializable {

    @XmlElement(name = "Category", namespace = "www.CategorySchema.com", required = true)
    private List<Category> category;

    public List<Category> getCategory() {
        if (category == null) {
            category = new ArrayList<>();
        }
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

}
