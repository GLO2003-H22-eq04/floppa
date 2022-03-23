package ulaval.glo2003.api.product;

import ulaval.glo2003.applicatif.offer.OffersResponseDTO;
import ulaval.glo2003.applicatif.product.ProductInfoResponseDTO;
import ulaval.glo2003.applicatif.product.ProductSellerDTO;
import ulaval.glo2003.domain.product.Product;

public class ProductAssembler {

    public ProductInfoResponseDTO toDto(Product product, ProductSellerDTO productSellerDTO, OffersResponseDTO offersDTO){
        var productDTO = new ProductInfoResponseDTO();

        productDTO.id = product.getProductId();
        productDTO.createdAt = product.getCreatedAt();
        productDTO.title = product.getTitle();
        productDTO.description = product.getDescription();
        productDTO.suggestedPrice = product.getSuggestedPrice();
        productDTO.categories = product.getCategoriesName();
        productDTO.seller = productSellerDTO;
        productDTO.offers = offersDTO;

        return productDTO;
    }
}
