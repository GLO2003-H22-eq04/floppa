package ulaval.glo2003;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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

    @Inject
    private ProductAssembler productAssembler;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response postCreatingProduct(@HeaderParam(SELLER_ID_HEADER) String sellerId,
                                        @Valid @NotNull(payload = MissingParameterError.class) ProductDTO productDTO) throws ItemNotFoundError {

        if(!sellerRepository.checkIfSellerExist(Integer.parseInt(sellerId))) {
            throw new ItemNotFoundError("L'id fourni n'existe pas.");
        }

        var product = productFactory.createProduct(productDTO, sellerId);
        var productId = productRepository.add(product);
        var url = String.format(PRODUCTS_PATH + "/%d", productId);
        return Response.created(URI.create(url)).build();
    }

    @GET
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductInfoResponseDTO getSeller(@PathParam("productId") int productId) throws ItemNotFoundError {
        var product = productRepository.findById(productId);

        if (product.isPresent()) {
            var productInfo = product.get();

            var seller = sellerRepository.findById(Integer.parseInt(product.get().getSellerId()));
            var sellerInfo = seller.get();
            ProductSellerDTO productSellerDTO = new ProductSellerDTO(productInfo.getSellerId(), sellerInfo.getName());

            OfferDTO offerDTO = new OfferDTO(0,0);

            return productAssembler.toDto(productInfo, productSellerDTO, offerDTO);
        }
        throw new ItemNotFoundError("L'id fourni n'existe pas.");
    }
}
