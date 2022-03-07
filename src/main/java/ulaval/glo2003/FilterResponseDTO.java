package ulaval.glo2003;

import ulaval.glo2003.ProductFilteredResponseDTO;

import java.util.List;

public class FilterResponseDTO {
    public List<ProductFilteredResponseDTO> products;

    public FilterResponseDTO(List<ProductFilteredResponseDTO> products){
        this.products = products;
    }
}
