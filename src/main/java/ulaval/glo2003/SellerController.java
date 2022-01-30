package ulaval.glo2003;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ulaval.glo2003.Validation.Errors.InvalidParameterError;
import ulaval.glo2003.Validation.Errors.MissingParameterError;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
    public SellerInfoResponseDTO getSeller(@NotEmpty(payload = MissingParameterError.class) @PathParam("sellerId") int sellerId){
        var seller = sellerRepository.findById(sellerId);
        return new SellerInfoResponseDTO(sellerId, seller.getName(), seller.getCreatedAt(), seller.getBio(), seller.getProducts());
    }
}
