package ulaval.glo2003.api.seller;

import ulaval.glo2003.applicatif.buyer.BuyerInfoResponseDto;
import ulaval.glo2003.applicatif.buyer.BuyerItemResponseDto;
import ulaval.glo2003.applicatif.offer.OfferInfoResponseDto;
import ulaval.glo2003.applicatif.product.ProductOfferInfoResponseDto;
import ulaval.glo2003.applicatif.seller.CurrentSellerDto;
import ulaval.glo2003.domain.offer.Offers;
import ulaval.glo2003.domain.seller.Seller;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class SellerAssembler {

    public CurrentSellerDto currentSellerToDto(Seller seller) {
        var currentSeller = new CurrentSellerDto();

        currentSeller.id = seller.getSellerId();
        currentSeller.name = seller.getName();
        currentSeller.createdAt = Instant.now().atOffset(ZoneOffset.UTC);
        currentSeller.bio = seller.getBio();
        currentSeller.birthdate = seller.getBirthDate();
        currentSeller.products = getProductOfferList(seller);

        return currentSeller;
    }

    public List<BuyerItemResponseDto> getOfferList(Offers offers) {
        List<BuyerItemResponseDto> buyerList = new ArrayList<>();
        for (var offer : offers.getListOffer()) {
            buyerList.add(new BuyerItemResponseDto(
                    offer.getOfferId(),
                    offer.getCreatedAt(),
                    offer.getAmount().getValue(),
                    offer.getMessage(),
                    new BuyerInfoResponseDto(offer.getName(), offer.getEmail(), offer.getPhoneNumber())));
        }
        return buyerList;
    }

    public List<ProductOfferInfoResponseDto> getProductOfferList(Seller seller) {
        List<ProductOfferInfoResponseDto> productList = new ArrayList<>();
        for (var product : seller.getProducts()) {

            productList.add(new ProductOfferInfoResponseDto(
                    product.getProductId(),
                    product.getTitle(),
                    product.getDescription(),
                    product.getCreatedAt(),
                    product.getSuggestedPrice().getValue(),
                    product.getCategories(),
                    new OfferInfoResponseDto(product.getOffers().getMin(),
                            product.getOffers().getMax(),
                            product.getOffers().getMean(),
                            product.getOffers().getCount(),
                            getOfferList(product.getOffers()))));

        }
        return productList;
    }

}
