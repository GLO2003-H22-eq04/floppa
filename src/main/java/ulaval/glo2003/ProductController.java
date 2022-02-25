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

    @Inject
    private SellerFactory sellerFactory;

    @POST
    public Response postCreatingProduct(@HeaderParam("X-Seller-Id") String sellerId,
                                        @Valid @NotNull(payload = MissingParameterError.class) ProductDTO productDTO) throws ItemNotFoundError {

        var seller = sellerRepository.findById(Integer.parseInt(sellerId));
        if(seller.isPresent()) {
            var sellerDTO = sellerFactory.createSeller(sellerId, seller.get().getName());
            var product = productFactory.createProduct(productDTO, sellerDTO);
            var id = productRepository.add(product);
            var url = String.format(PRODUCTS_PATH + "/%d", id);
            return Response.created(URI.create(url)).build();
        }

        throw new ItemNotFoundError("L'id fourni n'existe pas.");
    }
}
