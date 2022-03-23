package ulaval.glo2003.api.product;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ulaval.glo2003.api.offer.dto.OfferItemDTO;
import ulaval.glo2003.api.offer.dto.OfferItemResponseDTO;
import ulaval.glo2003.api.offer.dto.OffersResponseDTO;
import ulaval.glo2003.api.product.dto.*;
import ulaval.glo2003.api.validation.errors.InvalidParameterError;
import ulaval.glo2003.api.validation.errors.ItemNotFoundError;
import ulaval.glo2003.api.validation.errors.MissingParameterError;
import ulaval.glo2003.domain.offer.OfferFactory;
import ulaval.glo2003.domain.offer.Offers;
import ulaval.glo2003.domain.product.Product;
import ulaval.glo2003.domain.product.ProductCategory;
import ulaval.glo2003.domain.product.ProductFactory;
import ulaval.glo2003.domain.product.criteria.*;
import ulaval.glo2003.domain.product.repository.ProductRepository;
import ulaval.glo2003.domain.seller.repository.SellerRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path(ProductController.PRODUCTS_PATH)
public class ProductController {

    public static final String SELLER_ID_HEADER = "X-Seller-Id";

    public static final String PRODUCTS_PATH = "/products";

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
    public Response postCreatingProduct(@HeaderParam(SELLER_ID_HEADER) UUID sellerId,
                                        @Valid @NotNull(payload = MissingParameterError.class) ProductDTO productDTO) throws ItemNotFoundError {
        if (!sellerRepository.existById(sellerId))
            throw new ItemNotFoundError("L'id fourni n'existe pas.");

        var product = productFactory.createProduct(productDTO, sellerId);
        UUID productId = productRepository.add(product);
        var url = PRODUCTS_PATH + "/" + productId;
        return Response.created(URI.create(url)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ProductFilterResponsesCollectionDTO getProductsFromFilter(@QueryParam("sellerId") UUID p_sellerId,
                                                                     @QueryParam("title") String p_title,
                                                                     @QueryParam("categories") List<String> p_categories,
                                                                     @QueryParam("minPrice") float p_minPrice,
                                                                     @QueryParam("maxPrice") float p_maxPrice) {

        List<Product> productList = productRepository.findAll();
        List<ProductFilteredResponseDTO> products = new ArrayList<>();

        if (p_sellerId != null) {
            Criteria sellerFilter = new CriteriaSellerID(p_sellerId);
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
            for (Product product : productList) {
                var seller = sellerRepository.findById(product.getSellerId());
                ProductSellerDTO productSeller = null;
                if (seller.isPresent()) {
                    productSeller = new ProductSellerDTO(
                            product.getSellerId(),
                            seller.get().getName()
                    );
                }

                var offers = product.getOffers();
                var offerList = getOfferList(offers);
                products.add(new ProductFilteredResponseDTO(
                        product.getProductId(),
                        product.getCreatedAt(),
                        product.getTitle(),
                        product.getDescription(),
                        product.getSuggestedPrice().getValue(),
                        product.getCategories(),
                        productSeller,
                        new OffersResponseDTO(offers.getMin(),
                                offers.getMax(),offers.getMean(),offers.getCount(),offerList)));
            }
        }

        return new ProductFilterResponsesCollectionDTO(products);
    }

    @GET
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductInfoResponseDTO getSeller(@PathParam("productId") UUID productId) throws ItemNotFoundError {
        var product = productRepository.findById(productId);

        if (product.isEmpty())
            throw new ItemNotFoundError("L'id fourni n'existe pas.");

        var productInfo = product.get();

        var seller = sellerRepository.findById(product.get().getSellerId());

        if (seller.isEmpty())
            throw new ItemNotFoundError("L'id fourni n'existe pas.");

        var sellerInfo = seller.get();
        var productSellerDTO = new ProductSellerDTO(productInfo.getSellerId(), sellerInfo.getName());

        var offers = productInfo.getOffers();
        var offerList = getOfferList(offers);

        return productAssembler.toDto(productInfo, productSellerDTO, new OffersResponseDTO(offers.getMin(),
                offers.getMax(),offers.getMean(),offers.getCount(),offerList));
    }

    @POST
    @Path("/{productId}/offers")
    public Response postOffers(@PathParam("productId") UUID productId,
                               @Valid @NotNull(payload = MissingParameterError.class) OfferItemDTO offerItemDTO) throws ItemNotFoundError, InvalidParameterError {
        OfferFactory offerFactory = new OfferFactory();
        var product = productRepository.findById(productId);

        if (product.isEmpty())
            throw new ItemNotFoundError("L'id fourni n'existe pas.");

        if (product.get().getSuggestedPrice().getValue() >= offerItemDTO.amount)
            throw new InvalidParameterError("Le montant de l'offre doit être égal ou suppérieur à celui demandé");

        var productOffers = product.get().getOffers();
        var newOffer = offerFactory.createNewOffer(offerItemDTO);

        productOffers.addNewOffer(newOffer);
        return Response
                .status(Response.Status.OK)
                .entity("OK")
                .build();
    }

    private List<OfferItemResponseDTO> getOfferList(Offers offers){
        List<OfferItemResponseDTO> offerList = new ArrayList<>();
        for (var offer : offers.getListOffer()){
            offerList.add(new OfferItemResponseDTO(
                    offer.getName(),
                    offer.getMessage(),
                    offer.getEmail(),
                    offer.getPhoneNumber(),
                    offer.getAmount().getValue()));
        }
        return offerList;
    }
}
