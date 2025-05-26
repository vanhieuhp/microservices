package hieunv.dev.openfeign.product.repository;

import com.querydsl.core.types.dsl.StringPath;
import hieunv.dev.openfeign.product.entity.Category;
import hieunv.dev.openfeign.product.entity.QCategory;
import hieunv.dev.openfeign.product.entity.QProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface CategoryRepository extends JpaRepository<Category, Long>,
        QuerydslPredicateExecutor<Category>, QuerydslBinderCustomizer<QCategory> {

    default void customize(QuerydslBindings bindings, QCategory category) {
        bindings.bind(category.name).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}