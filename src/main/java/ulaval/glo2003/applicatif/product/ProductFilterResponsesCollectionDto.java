package ulaval.glo2003.applicatif.product;

import java.util.List;

public class ProductFilterResponsesCollectionDto {
    public List<ProductFilteredResponseDto> products;

    public ProductFilterResponsesCollectionDto(List<ProductFilteredResponseDto> products) {
        this.products = products;
    }
}
