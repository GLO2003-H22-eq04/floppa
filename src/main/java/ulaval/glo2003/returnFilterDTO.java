package ulaval.glo2003;

import java.util.List;

public class returnFilterDTO {
    public List<ProductFilteredResonseDTO> products;

    public returnFilterDTO(List<ProductFilteredResonseDTO> products){
        this.products = products;
    }
}
