package hieunv.dev.products.repository;

import com.querydsl.core.types.dsl.StringPath;
import hieunv.dev.products.entity.QProduct;
import hieunv.dev.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,
        QuerydslPredicateExecutor<Product>, QuerydslBinderCustomizer<QProduct> {
    default void customize(QuerydslBindings bindings, QProduct product) {
        bindings.bind(product.name).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}