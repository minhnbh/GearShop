/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.category;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import sample.product.Product;

/**
 *
 * @author MinhNBHSE61805
 */
@Entity
@Table(name = "Category", catalog = "GearShop", schema = "dbo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Category", namespace = "www.CategorySchema.com")
@NamedQueries({
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
    , @NamedQuery(name = "Category.findByCategoryID", query = "SELECT c FROM Category c WHERE c.categoryID = :categoryID")
    , @NamedQuery(name = "Category.findByCategoryName", query = "SELECT c FROM Category c WHERE c.categoryName = :categoryName")
    , @NamedQuery(name = "Category.findByDescription", query = "SELECT c FROM Category c WHERE c.description = :description")
    , @NamedQuery(name = "Category.findBySlugify", query = "SELECT c FROM Category c WHERE c.slugify = :slugify")
    , @NamedQuery(name = "Category.findByParentID", query = "SELECT c FROM Category c WHERE c.parentID = :parentID")
    , @NamedQuery(name = "Category.findByIsDelete", query = "SELECT c FROM Category c WHERE c.isDelete = :isDelete")})
public class Category implements Serializable {

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoryID")
//    private Collection<Product> productCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CategoryID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlAttribute(name = "CategoryID", required = true)
    private Integer categoryID;
    
    @Basic(optional = false)
    @Column(name = "CategoryName", nullable = false, length = 255)
    @XmlElement(name = "CategoryName", namespace = "www.CategorySchema.com", required = true)
    private String categoryName;
    
    @Basic(optional = false)
    @Lob
    @Column(name = "Description", nullable = false, length = 1000)
    @XmlElement(name = "Description", namespace = "www.CategorySchema.com")
    private String description;

    @Basic(optional = false)
    @Column(name = "Slugify", nullable = false, length = 255)
    @XmlElement(name = "Slugify", namespace = "www.CategorySchema.com")
    private String slugify;
    
    @Basic(optional = false)
    @Column(name = "ParentID", nullable = false)
    @XmlAttribute(name = "ParentID", required = true)
    private int parentID;
    
    @Basic(optional = false)
    @Column(name = "IsDelete", nullable = false)
    @XmlAttribute(name = "IsDelete", required = true)
    private int isDelete;

    public Category() {
    }

    public Category(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public Category(Integer categoryID, String categoryName, String description, String slugify, int parentID, int isDelete) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.description = description;
        this.slugify = slugify;
        this.parentID = parentID;
        this.isDelete = isDelete;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlugify() {
        return slugify;
    }

    public void setSlugify(String slugify) {
        this.slugify = slugify;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryID != null ? categoryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.categoryID == null && other.categoryID != null) || (this.categoryID != null && !this.categoryID.equals(other.categoryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sample.category.Category[ categoryID=" + categoryID + " ]";
    }

//    @XmlTransient
//    public Collection<Product> getProductCollection() {
//        return productCollection;
//    }
//
//    public void setProductCollection(Collection<Product> productCollection) {
//        this.productCollection = productCollection;
//    }
    
}
