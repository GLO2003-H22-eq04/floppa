package ulaval.glo2003.api.product;

import ulaval.glo2003.api.offer.dto.OfferDTO;
import ulaval.glo2003.api.product.dto.ProductInfoResponseDTO;
import ulaval.glo2003.api.product.dto.ProductSellerDTO;
import ulaval.glo2003.domain.product.Product;

public class ProductAssembler {

    public ProductInfoResponseDTO toDto(Product product, ProductSellerDTO productSellerDTO, OfferDTO offerDTO){
        var productDTO = new ProductInfoResponseDTO();

        productDTO.id = product.getProductId();
        productDTO.createdAt = product.getCreatedAt();
        productDTO.title = product.getTitle();
        productDTO.description = product.getDescription();
        productDTO.suggestedPrice = product.getSuggestedPrice();
        productDTO.categories = product.getCategoriesName();
        productDTO.seller = productSellerDTO;
        productDTO.offers = offerDTO;

        return productDTO;
    }
}
