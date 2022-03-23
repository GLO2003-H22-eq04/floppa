package ulaval.glo2003.api.seller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ulaval.glo2003.applicatif.seller.SellerDTO;
import ulaval.glo2003.applicatif.seller.SellerInfoResponseDTO;
import ulaval.glo2003.api.validation.errors.ItemNotFoundError;
import ulaval.glo2003.api.validation.errors.MissingParameterError;
import ulaval.glo2003.domain.product.repository.ProductRepository;
import ulaval.glo2003.domain.seller.Seller;
import ulaval.glo2003.domain.seller.repository.SellerRepository;

import java.net.URI;
import java.util.UUID;

@Path(SellerController.SELLERS_PATH)
public class SellerController {

    public static final String SELLERS_PATH = "/sellers";
    public static final String GET_SELLER_PATH = "/{sellerId}";
    public static final String PARAM_SELLER_ID = "sellerId";

    @Inject
    private SellerRepository sellerRepository;

    @Inject
    private ProductRepository productRepository;

    @POST
    public Response postCreatingSeller(@Valid @NotNull(payload = MissingParameterError.class) SellerDTO seller) {
        var sellerId = sellerRepository.add(new Seller(seller));
        var url = SELLERS_PATH + "/" + sellerId;
        return Response.created(URI.create(url)).build();
    }

    @GET
    @Path(GET_SELLER_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    public SellerInfoResponseDTO getSeller(@PathParam(PARAM_SELLER_ID) UUID sellerId) throws ItemNotFoundError {
        var seller = sellerRepository.findById(sellerId);
        if (seller.isPresent()) {
            var sellerInfos = seller.get();
            return new SellerInfoResponseDTO(
                    sellerId,
                    sellerInfos.getName(),
                    sellerInfos.getCreatedAt(),
                    sellerInfos.getBio(),
                    productRepository.productOf(sellerId)
            );
        }

        throw new ItemNotFoundError("L'id fourni n'existe pas.");
    }
}
