package ulaval.glo2003;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ulaval.glo2003.Criteria.*;
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
import java.util.ArrayList;
import java.util.List;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public FilterResponseDTO getProductsFromFilter(@QueryParam("sellerId") String p_sellerId,
                                                   @QueryParam("title") String p_title,
                                                   @QueryParam("categories") List<String> p_categories,
                                                   @QueryParam("minPrice") float p_minPrice,
                                                   @QueryParam("maxPrice") float p_maxPrice) {

        List<Product> productList = productRepository.findAll();
        List<ProductFilteredResponseDTO> products = null;

        if (p_sellerId != null) {
            Criteria sellerFilter = new CriteriaSellerID(Integer.parseInt(p_sellerId));
            productList = sellerFilter.meetCriteria(productList);
        }
        if (p_title != null) {
            Criteria titleFilter = new CriteriaTitle(p_title);
            productList = titleFilter.meetCriteria(productList);

        }
        if (!p_categories.isEmpty()) {
            List<ProductCategory> productCategoriesList = new ArrayList<>();
            for (String category : p_categories) {
                productCategoriesList.add(ProductCategory.findByName(category));
            }

            Criteria categoryFilter = new CriteriaCategories(productCategoriesList);
            productList = categoryFilter.meetCriteria(productList);
        }
        if (p_minPrice != 0) {
            Criteria minPriceFilter = new CriteriaMinPrice(p_minPrice);
            productList = minPriceFilter.meetCriteria(productList);
        }
        if (p_maxPrice != 0) {
            Criteria maxPriceFilter = new CriteriaMaxPrice(p_maxPrice);
            productList = maxPriceFilter.meetCriteria(productList);
        }

        if (!productList.isEmpty()) {
            products = new ArrayList<>();
            for (Product product : productList) {
                var seller = sellerRepository.findById(Integer.parseInt(product.getSellerId()));
                ProductSellerDTO productSeller = null;
                if (seller.isPresent()) {
                    productSeller = new ProductSellerDTO(
                            product.getSellerId(),
                            seller.get().getName()
                    );
                }

                products.add(new ProductFilteredResponseDTO(
                        Integer.toString(product.getProductId()),
                        product.getCreatedAt(),
                        product.getTitle(),
                        product.getDescription(),
                        product.getSuggestedPrice().getValue(),
                        product.getCategories(),
                        productSeller,
                        new OffersDTO(new Amount(0.00),0)));
            }
        }

        return new FilterResponseDTO(products);
    }
}
