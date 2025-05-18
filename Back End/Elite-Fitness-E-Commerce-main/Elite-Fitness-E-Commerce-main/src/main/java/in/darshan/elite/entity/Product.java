package in.darshan.elite.entity;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
@SQLDelete(sql = "UPDATE product SET deleted = true WHERE product_id = ?")
@Where(clause = "deleted = false")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String name;
    private Integer available;
    private String category; 
    private String description;
    private Integer discount;
    private String image;
    private Integer price;
    private Double rating;
    private Integer buyCounts;

    private boolean deleted = false; // <- this controls soft delete

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Cart> cartItems;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
}
