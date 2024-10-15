package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "order_details")
@Entity
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_money", nullable = false)
    private Float totalMoney;

    @Column(name = "color")
    private String color;
}
