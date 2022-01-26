package ulaval.glo2003;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/sellers")
public class SellerController {

    private final List<SellerDTO> sellerList = new ArrayList<>();

    @POST
    public Response postCreatingSeller(@Valid @NotNull SellerDTO seller) {
        sellerList.add(seller);
        return Response.created(URI.create(Integer.toString(sellerList.size() - 1))).build();
    }
}
