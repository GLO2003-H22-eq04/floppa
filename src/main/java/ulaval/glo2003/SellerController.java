package ulaval.glo2003;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/sellers")
public class SellerController {

    @Inject
    private SellerRepository sellerRepository;

    @POST
    public Response postCreatingSeller(@Valid @NotNull SellerDTO seller) {
        var id = sellerRepository.add(new Seller(seller));
        return Response.created(URI.create(Integer.toString(id))).build();
    }

    @GET
    @Path("/{sellerId}")
    @Produces("application/json")
    public SellerInfoResponseDTO getSeller(@PathParam("sellerId") int sellerId){
        var seller = sellerRepository.findById(sellerId);
        return new SellerInfoResponseDTO(sellerId, seller.getName(), seller.getCreatedAt(), seller.getBio(), seller.getProducts());
    }
}
