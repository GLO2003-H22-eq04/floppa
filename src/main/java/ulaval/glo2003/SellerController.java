package ulaval.glo2003;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ulaval.glo2003.Validation.Errors.InvalidParameterError;
import ulaval.glo2003.Validation.Errors.ItemNotFoundError;
import ulaval.glo2003.Validation.Errors.MissingParameterError;

import java.net.URI;

@Path("/sellers")
public class SellerController {

    @Inject
    private SellerRepository sellerRepository;

    @POST
    public Response postCreatingSeller(@Valid @NotNull(payload = MissingParameterError.class) SellerDTO seller) {
        var id = sellerRepository.add(new Seller(seller));
        return Response.created(URI.create(Integer.toString(id))).build();
    }

    @GET
    @Path("/{sellerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public SellerInfoResponseDTO getSeller(@PathParam("sellerId") int sellerId) throws ItemNotFoundError {
        var seller = sellerRepository.findById(sellerId);
        if (seller.isPresent()) {
            var sellerInfos = seller.get();
            return new SellerInfoResponseDTO(sellerId, sellerInfos.getName(), sellerInfos.getCreatedAt(), sellerInfos.getBio(), sellerInfos.getProducts());
        }

        throw new ItemNotFoundError("L'id fourni n'existe pas.");
    }
}
