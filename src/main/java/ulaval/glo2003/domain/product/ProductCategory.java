package ulaval.glo2003.domain.product;

import dev.morphia.annotations.Entity;

@Entity("ProductCategory")
public enum ProductCategory {
    sports,
    electronics,
    apparel,
    beauty,
    housing,
    other;

    public static ProductCategory findByName(String name) {
        for (var category : values()) if (category.toString().equalsIgnoreCase(name)) return category;
        return other;
    }
}
