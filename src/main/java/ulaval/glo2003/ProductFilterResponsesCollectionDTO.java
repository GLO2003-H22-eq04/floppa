package ulaval.glo2003;

import java.util.List;

public class ProductFilterResponsesCollectionDTO {
    public List<ProductFilteredResponseDTO> products;

    public ProductFilterResponsesCollectionDTO(List<ProductFilteredResponseDTO> products){
        this.products = products;
    }
}
