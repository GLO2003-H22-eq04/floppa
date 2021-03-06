package ulaval.glo2003.api.seller;

import ulaval.glo2003.applicatif.dto.buyer.BuyerInfoResponseDto;
import ulaval.glo2003.applicatif.dto.buyer.BuyerItemResponseDto;
import ulaval.glo2003.applicatif.dto.offer.OfferInfoResponseDto;
import ulaval.glo2003.applicatif.dto.offer.OfferResponseDto;
import ulaval.glo2003.applicatif.dto.product.ProductOfSellerDto;
import ulaval.glo2003.applicatif.dto.product.ProductOfferInfoResponseDto;
import ulaval.glo2003.applicatif.dto.seller.CurrentSellerDto;
import ulaval.glo2003.applicatif.dto.seller.SellerInfoResponseDto;
import ulaval.glo2003.domain.offer.Offers;
import ulaval.glo2003.domain.seller.Seller;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SellerAssembler {

    public CurrentSellerDto currentSellerToDto(Seller seller) {
        var currentSeller = new CurrentSellerDto();

        currentSeller.id = seller.getId();
        currentSeller.name = seller.getName();
        currentSeller.createdAt = Instant.now().atOffset(ZoneOffset.UTC);
        currentSeller.bio = seller.getBio();
        currentSeller.birthDate = seller.getBirthDate();
        currentSeller.products = getProductOfferList(seller);

        return currentSeller;
    }

    public SellerInfoResponseDto sellerInfoResponseToDto(Seller seller) {
        var sellerInfo = new SellerInfoResponseDto();

        sellerInfo.id = seller.getId();
        sellerInfo.name = seller.getName();
        sellerInfo.bio = seller.getBio();
        sellerInfo.createdAt = seller.getCreatedAt();
        sellerInfo.products = getSellerProductList(seller);
        sellerInfo.birthDate = seller.getBirthDate();

        return sellerInfo;
    }

    public List<BuyerItemResponseDto> getOfferList(Offers offers) {
        List<BuyerItemResponseDto> buyerList = new ArrayList<>();
        for (var offer : offers.getListOffer())
            buyerList.add(new BuyerItemResponseDto(
                    offer.getOfferId(),
                    offer.getCreatedAt(),
                    offer.getAmount().getValue(),
                    offer.getMessage(),
                    new BuyerInfoResponseDto(offer.getName(), offer.getEmail(), offer.getPhoneNumber())));
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

    private Collection<ProductOfSellerDto> getSellerProductList(Seller seller) {
        Collection<ProductOfSellerDto> productList = new ArrayList<>();

        for (var product : seller.getProducts()) {

            productList.add(new ProductOfSellerDto(
                    product.getProductId(),
                    product.getCreatedAt(),
                    product.getTitle(),
                    product.getDescription(),
                    product.getSuggestedPrice().getValue(),
                    product.getCategories(),
                    new OfferResponseDto(product.getOffers().getMean(),
                            product.getOffers().getCount())));

        }
        return productList;
    }

}
