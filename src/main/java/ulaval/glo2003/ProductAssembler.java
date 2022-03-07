package ulaval.glo2003;

public class ProductAssembler {

    public ProductInfoResponseDTO toDto(Product product, ProductSellerDTO productSellerDTO, OfferDTO offerDTO){
        var productDTO = new ProductInfoResponseDTO();

        productDTO.id = String.valueOf(product.getProductId());
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
