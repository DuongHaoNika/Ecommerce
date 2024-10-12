package com.project.shopapp.controller;

import com.project.shopapp.dtos.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @GetMapping("")
    public ResponseEntity<String> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        return ResponseEntity.status(200).body("OK");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable long id){
        return ResponseEntity.status(200).body(String.valueOf(id));
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @Valid @ModelAttribute ProductDTO productDTO,
            BindingResult bindingResult
    ){
        try {
            if(bindingResult.hasErrors()){
                return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
            }
            List<MultipartFile> files = productDTO.getFiles();
            files = files == null ? new ArrayList<MultipartFile>() : files;
            for(MultipartFile file : files){
                if(file != null && file.getSize() > 0) {
                    if(file.getSize() > 10 * 1024 * 1024){
                        throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "File is too large!");
                    }
                    String contentType = file.getContentType();
                    if(contentType == null || !contentType.startsWith("image")){
                        return ResponseEntity.badRequest().body("File is not an image!");
                    }
                    String fileName = storeFile(file);
                }
            }
            return ResponseEntity.status(200).body(productDTO.toString());
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path uploadDir = Paths.get("uploads");
        if(!Files.exists(uploadDir)) {
            Files.createDirectory(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(), newFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return newFileName;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable long id, @RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productDTO.toString());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id){
        return ResponseEntity.status(200).body("Deleted product with id " + id);
    }
}
