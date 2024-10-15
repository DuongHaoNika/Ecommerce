package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    @NotBlank(message = "Title is not empty!")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters!")
    private String name;

    @Min(value = 0, message = "Price must be >= 0")
    private float price;
    private String url;
    private String description;
    @JsonProperty("category_id")
    private Long categoryId;
}
