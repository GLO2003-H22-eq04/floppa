package ulaval.glo2003;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ulaval.glo2003.Validation.Errors.ItemNotFoundError;
import ulaval.glo2003.Validation.Errors.MissingParameterError;

import java.net.URI;

@Path(SellerController.SELLERS_PATH)
public class SellerController {

    protected static final String SELLERS_PATH = "/sellers";

    @Inject
    private SellerRepository sellerRepository;

    @Inject
    private ProductRepository productRepository;

    @POST
    public Response postCreatingSeller(@Valid @NotNull(payload = MissingParameterError.class) SellerDTO seller) {
        var sellerId = sellerRepository.add(new Seller(seller));
        var url = String.format(SELLERS_PATH + "/%d", sellerId);
        return Response.created(URI.create(url)).build();
    }

    @GET
    @Path("/{sellerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public SellerInfoResponseDTO getSeller(@PathParam("sellerId") int sellerId) throws ItemNotFoundError {
        var seller = sellerRepository.findById(sellerId);
        if (seller.isPresent()) {
            var sellerInfos = seller.get();
            return new SellerInfoResponseDTO(
                    sellerId,
                    sellerInfos.getName(),
                    sellerInfos.getCreatedAt(),
                    sellerInfos.getBio(),
                    productRepository.productOf(String.valueOf(sellerId))
            );
        }

        throw new ItemNotFoundError("L'id fourni n'existe pas.");
    }
}
