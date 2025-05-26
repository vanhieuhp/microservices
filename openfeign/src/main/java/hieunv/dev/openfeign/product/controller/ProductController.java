package hieunv.dev.openfeign.product.controller;

import hieunv.dev.openfeign.product.entity.Product;
import hieunv.dev.openfeign.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        List<Product> products = productService.getProducts(minPrice, maxPrice);

        return ResponseEntity
                .status(200)
                .body(products);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Product>> getProductsWithCategoryName(@RequestParam("category") String categoryName) {
        List<Product> products = productService.getProductsWithCategoryName(categoryName);

        return ResponseEntity
                .status(200)
                .body(products);
    }

    @GetMapping("/above-average")
    public ResponseEntity<List<Product>> getProductsAboveAveragePrice() {
        List<Product> products = productService.getProductsAboveAveragePrice();
        return ResponseEntity
                .status(200)
                .body(products);
    }

    @GetMapping("/category-page-size")
    public ResponseEntity<Page<Product>> getPaginatedProducts(@RequestParam("category") String categoryName,
                                                              @RequestParam("page") int page,
                                                              @RequestParam("size") int size) {
        Page<Product> products = productService.getPaginatedProducts(categoryName, page, size);
        return ResponseEntity
                .status(200)
                .body(products);
    }
}
