package ulaval.glo2003;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Path("/")
public class SellerController {

    private List<SellerObject> sellerList = new ArrayList<>();

    @GET
    public Response getMain(){
        return Response.ok().build();
    }

    @POST
    @Path("/sellers/")
    public Response postCreatingSeller(SellerObject seller) throws Exception {


        if(seller.name.isEmpty() || seller.bio.isEmpty()){
            throw new Exception("MISSING_PARAMETER");
        } else if(seller.birthDate.isAfter(LocalDate.now().minusYears(18))){
            throw new Exception("INVALID_PARAMETER");
        }

        sellerList.add(seller);
        return Response.created(URI.create(Integer.toString(sellerList.size() - 1))).build();
    }
}
