package ulaval.glo2003.api.product;

import ulaval.glo2003.applicatif.dto.offer.OffersResponseDto;
import ulaval.glo2003.applicatif.dto.product.ProductInfoResponseDto;
import ulaval.glo2003.applicatif.dto.product.ProductSellerDto;
import ulaval.glo2003.domain.product.Product;

public class ProductAssembler {

    public ProductInfoResponseDto toDto(Product product, ProductSellerDto productSellerDto, OffersResponseDto offersDto) {
        var productDto = new ProductInfoResponseDto();

        productDto.id = product.getProductId();
        productDto.createdAt = product.getCreatedAt();
        productDto.title = product.getTitle();
        productDto.description = product.getDescription();
        productDto.suggestedPrice = product.getSuggestedPrice().getValue();
        productDto.categories = product.getCategories();
        productDto.seller = productSellerDto;
        productDto.offers = offersDto;
        productDto.visits = product.getVisits();

        return productDto;
    }
}
