package hieunv.dev.openfeign.product.repository;

import com.querydsl.core.types.dsl.StringPath;
import hieunv.dev.openfeign.product.entity.QProduct;
import hieunv.dev.openfeign.product.entity.Product;
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