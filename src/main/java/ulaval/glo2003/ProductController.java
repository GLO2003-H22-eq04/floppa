package ulaval.glo2003;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import ulaval.glo2003.Validation.Errors.ItemNotFoundError;
import ulaval.glo2003.Validation.Errors.MissingParameterError;

import java.net.URI;

@Path(ProductController.PRODUCTS_PATH)
public class ProductController {

    protected static final String PRODUCTS_PATH = "/products";

    @Inject
    private SellerRepository sellerRepository;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ProductFactory productFactory;

    @POST
    public Response postCreatingProduct(@HeaderParam("X-Seller-Id") String sellerId,
                                        @Valid @NotNull(payload = MissingParameterError.class) ProductDTO productDTO) throws ItemNotFoundError {

        if(!sellerRepository.checkIfSellerExist(Integer.parseInt(sellerId))) {
            throw new ItemNotFoundError("L'id fourni n'existe pas.");
        }

        var product = productFactory.createProduct(productDTO, sellerId);
        var productId = productRepository.add(product);
        var url = String.format(PRODUCTS_PATH + "/%d", productId);
        return Response.created(URI.create(url)).build();
    }
}
