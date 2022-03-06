package ulaval.glo2003;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ulaval.glo2003.Validation.Errors.InvalidParameterError;
import ulaval.glo2003.Validation.Errors.ItemNotFoundError;
import ulaval.glo2003.Validation.Errors.MissingParameterError;

import java.net.URI;

@Path(ProductController.PRODUCTS_PATH)
public class ProductController {

    public static final String SELLER_ID_HEADER = "X-Seller-Id";

    protected static final String PRODUCTS_PATH = "/products";

    @Inject
    private SellerRepository sellerRepository;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ProductFactory productFactory;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response postCreatingProduct(@HeaderParam(SELLER_ID_HEADER) String sellerId,
                                        @Valid @NotNull(payload = MissingParameterError.class) ProductDTO productDTO) throws ItemNotFoundError, InvalidParameterError {
        try {
            if (!sellerRepository.existById(Integer.parseInt(sellerId))) {
                throw new ItemNotFoundError("L'id fourni n'existe pas.");
            }
        }
        catch (NumberFormatException e){
            throw new InvalidParameterError("L'id n'est pas bien formaté.");
        }

        var product = productFactory.createProduct(productDTO, sellerId);
        var productId = productRepository.add(product);
        var url = String.format(PRODUCTS_PATH + "/%d", productId);
        return Response.created(URI.create(url)).build();
    }
}
