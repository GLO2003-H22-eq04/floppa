package ulaval.glo2003.api.seller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ulaval.glo2003.applicatif.buyer.BuyerInfoResponseDTO;
import ulaval.glo2003.applicatif.buyer.BuyerItemResponseDTO;
import ulaval.glo2003.applicatif.offer.OfferInfoResponseDTO;
import ulaval.glo2003.applicatif.product.ProductOfferInfoResponseDTO;
import ulaval.glo2003.applicatif.seller.CurrentSellerDTO;
import ulaval.glo2003.applicatif.seller.SellerDto;
import ulaval.glo2003.applicatif.seller.SellerInfoResponseDto;
import ulaval.glo2003.api.validation.errors.ItemNotFoundError;
import ulaval.glo2003.api.validation.errors.MissingParameterError;
import ulaval.glo2003.domain.offer.Offers;
import ulaval.glo2003.domain.product.repository.ProductRepository;
import ulaval.glo2003.domain.seller.Seller;
import ulaval.glo2003.domain.seller.repository.SellerRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path(SellerController.SELLERS_PATH)
public class SellerController {

    public static final String SELLERS_PATH = "/sellers";
    public static final String GET_SELLER_PATH = "/{sellerId}";
    public static final String GET_CURRENT_SELLER_PATH = "/@me";
    public static final String PARAM_SELLER_ID = "sellerId";
    public static final String SELLER_ID_HEADER = "X-Seller-Id";

    @Inject
    private SellerRepository sellerRepository;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private SellerAssembler sellerAssembler;

    @POST
    public Response postCreatingSeller(@Valid @NotNull(payload = MissingParameterError.class) SellerDto seller) {
        var sellerId = sellerRepository.add(new Seller(seller));
        var url = SELLERS_PATH + "/" + sellerId;
        return Response.created(URI.create(url)).build();
    }

    @GET
    @Path(GET_SELLER_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    public SellerInfoResponseDto getSeller(@PathParam(PARAM_SELLER_ID) UUID sellerId) throws ItemNotFoundError {
        var seller = sellerRepository.findById(sellerId);
        if (seller.isPresent()) {
            var sellerInfos = seller.get();
            return new SellerInfoResponseDto(
                    sellerId,
                    sellerInfos.getName(),
                    sellerInfos.getCreatedAt(),
                    sellerInfos.getBio(),
                    productRepository.productOf(sellerId)
            );
        }

        throw new ItemNotFoundError("L'id fourni n'existe pas.");
    }

    @GET
    @Path(GET_CURRENT_SELLER_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    public CurrentSellerDTO getCurrentSeller(@HeaderParam(SELLER_ID_HEADER) UUID sellerId){

        var seller = sellerRepository.findById(sellerId).get();
        var offerList = getProductOfferList(seller);

        return sellerAssembler.currentSellerToDTO(seller, sellerId, offerList);
    }

    private List<BuyerItemResponseDTO> getOfferList(Offers offers){
        List<BuyerItemResponseDTO> buyerList = new ArrayList<>();
        for (var offer : offers.getListOffer()){
            buyerList.add(new BuyerItemResponseDTO(
                    offer.getCreatedAt(),
                    offer.getAmount().getValue(),
                    offer.getMessage(),
                    new BuyerInfoResponseDTO(offer.getName(),offer.getEmail(),offer.getPhoneNumber())));
        }
        return buyerList;
    }

    private List<ProductOfferInfoResponseDTO> getProductOfferList(Seller seller){
        List<ProductOfferInfoResponseDTO> productList = new ArrayList<>();
        List<BuyerItemResponseDTO> buyerList = new ArrayList<>();
        for (var product : seller.getProductList()){

            productList.add(new ProductOfferInfoResponseDTO(
                    product.getProductId(),
                    product.getTitle(),
                    product.getDescription(),
                    product.getCreatedAt(),
                    product.getSuggestedPrice().getValue(),
                    product.getCategories(),
                    new OfferInfoResponseDTO(product.getOffers().getMin(),
                            product.getOffers().getMax(),
                            product.getOffers().getMean(),
                            product.getOffers().getCount(),
                            getOfferList(product.getOffers()))));

        }
        return productList;
    }
}
