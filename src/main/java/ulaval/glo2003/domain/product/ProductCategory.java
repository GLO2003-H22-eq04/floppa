package ulaval.glo2003.domain.product;

public enum ProductCategory {
    SPORTS("sports"),
    ELECTRONICS("electronics"),
    APPAREL("apparel"),
    BEAUTY("beauty"),
    HOUSING("housing"),
    OTHER("other");

    private final String name;

    ProductCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ProductCategory findByName(String name) {
        for (var category : values()) {
            if (category.name.equals(name)) {
                return category;
            }
        }
        return OTHER;
    }
}
