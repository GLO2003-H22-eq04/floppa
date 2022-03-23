package ulaval.glo2003.applicatif.product;

import java.util.List;

public class ProductFilterResponsesCollectionDTO {
    public List<ProductFilteredResponseDTO> products;

    public ProductFilterResponsesCollectionDTO(List<ProductFilteredResponseDTO> products){
        this.products = products;
    }
}
