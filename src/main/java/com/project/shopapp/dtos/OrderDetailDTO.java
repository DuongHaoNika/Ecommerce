package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value = 1, message = "Order ID must be > 0!")
    private long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product ID must be > 0!")
    private long productId;

    @Min(value = 1, message = "Price must be > 0!")
    private float price;
    @Min(value = 1, message = "Quantity must be > 0!")
    private int quantity;

    @Min(value = 0, message = "Total money must be >= 0!")
    @JsonProperty("total_money")
    private long totalMoney;

    private String color;
}
