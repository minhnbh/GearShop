/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.product;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

/**
 *
 * @author MinhNBHSE61805
 */
@Entity
@Table(name = "Product", catalog = "GearShop", schema = "dbo")
@XmlRootElement(name = "Product", namespace = "www.ProductSchema.com")
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
    , @NamedQuery(name = "Product.findByProductID", query = "SELECT p FROM Product p WHERE p.productID = :productID")
    , @NamedQuery(name = "Product.findByProductName", query = "SELECT p FROM Product p WHERE p.productName = :productName")
    , @NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price")
    , @NamedQuery(name = "Product.findByDateCreated", query = "SELECT p FROM Product p WHERE p.dateCreated = :dateCreated")
    , @NamedQuery(name = "Product.findByLastModified", query = "SELECT p FROM Product p WHERE p.lastModified = :lastModified")
    , @NamedQuery(name = "Product.findByImg", query = "SELECT p FROM Product p WHERE p.img = :img")
    , @NamedQuery(name = "Product.findBySku", query = "SELECT p FROM Product p WHERE p.sku = :sku")
    , @NamedQuery(name = "Product.findByQuantity", query = "SELECT p FROM Product p WHERE p.quantity = :quantity")
    , @NamedQuery(name = "Product.findBySlugify", query = "SELECT p FROM Product p WHERE p.slugify = :slugify")})
public class Product implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ProductID", nullable = false)
    @XmlAttribute(name = "ProductID", required = true)
    private Integer productID;
    
    @Basic(optional = false)
    @Column(name = "ProductName", nullable = false, length = 255)
    @XmlElement(name = "ProductName", namespace = "www.ProductSchema.com", required = true)
    private String productName;
    
    @Basic(optional = false)
    @Lob
    @Column(name = "Description", nullable = false, length = 2147483647)
    @XmlElement(name = "Description", namespace = "www.ProductSchema.com", required = true)
    private String description;
    
    @Basic(optional = false)
    @Column(name = "Price", nullable = false, length = 20)
    @XmlElement(name = "Price", namespace = "www.ProductSchema.com", required = true)
    private String price;
    
    @Basic(optional = false)
    @Column(name = "DateCreated", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @XmlElement(name = "DateCreated", namespace = "www.ProductSchema.com", required = true)
    @XmlSchemaType(name = "dateTime")
    private String dateCreated;
    
    @Basic(optional = false)
    @Column(name = "LastModified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @XmlElement(name = "LastModified", namespace = "www.ProductSchema.com", required = true)
    @XmlSchemaType(name = "dateTime")
    private String lastModified;
    
    @Basic(optional = false)
    @Column(name = "Img", nullable = false, length = 255)
    @XmlElement(name = "Img", namespace = "www.ProductSchema.com", required = true)
    private String img;
    
    @Basic(optional = false)
    @Column(name = "SKU", nullable = false, length = 20)
    @XmlElement(name = "SKU", namespace = "www.ProductSchema.com", required = true)
    private String sku;
    
    @Basic(optional = false)
    @Column(name = "Quantity", nullable = false)
    @XmlElement(name = "Quantity", namespace = "www.ProductSchema.com", required = true)
    private int quantity;
    
    @Basic(optional = false)
    @Column(name = "Slugify", nullable = false, length = 255)
    @XmlElement(name = "Slugify", namespace = "www.ProductSchema.com", required = true)
    private String slugify;

    @Basic(optional = false)
    @Column(name = "IsDelete", nullable = false)
    @XmlAttribute(name = "IsDelete")
    private int isDelete;
    
    @Basic(optional = false)
    @Column(name = "CategoryID", nullable = false)
    @XmlAttribute(name = "CategoryID", required = true)
    private BigInteger categoryID;
    
    public Product() {
    }

    public Product(Integer productID) {
        this.productID = productID;
    }

    public Product(Integer productID, String productName, String description, String price, String dateCreated, String lastModified, String img, String sku, int quantity, String slugify, int isDelete) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.dateCreated = dateCreated;
        this.lastModified = lastModified;
        this.img = img;
        this.sku = sku;
        this.quantity = quantity;
        this.slugify = slugify;
        this.isDelete = isDelete;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSlugify() {
        return slugify;
    }

    public void setSlugify(String slugify) {
        this.slugify = slugify;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public BigInteger getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(BigInteger categoryID) {
        this.categoryID = categoryID;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productID != null ? productID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productID == null && other.productID != null) || (this.productID != null && !this.productID.equals(other.productID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sample.product.Product[ productID=" + productID + " ]";
    }
    
}
