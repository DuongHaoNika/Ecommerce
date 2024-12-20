package com.project.shopapp.responses;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductListResponse {
    private List<ProductResponse> products;
    private int totalPages;
}
