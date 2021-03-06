package ulaval.glo2003.api.product;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ulaval.glo2003.applicatif.dto.offer.OffersCountDto;
import ulaval.glo2003.applicatif.dto.offer.OfferItemDto;
import ulaval.glo2003.applicatif.dto.offer.OffersResponseDto;
import ulaval.glo2003.applicatif.dto.product.*;
import ulaval.glo2003.applicatif.validation.errors.InvalidParameterError;
import ulaval.glo2003.applicatif.validation.errors.ItemNotFoundError;
import ulaval.glo2003.applicatif.validation.errors.MissingParameterError;
import ulaval.glo2003.domain.offer.OfferFactory;
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
                                        @Valid @NotNull(payload = MissingParameterError.class) ProductDto productDto) throws ItemNotFoundError {
        if (!sellerRepository.existById(sellerId))
            throw new ItemNotFoundError("L'id fourni n'existe pas.");

        var product = productFactory.createProduct(productDto, sellerId);
        var seller = sellerRepository.findById(sellerId).get();
        seller.getProducts().add(product);
        var productId = productRepository.add(product);
        product.setProductId(productId);

        var url = PRODUCTS_PATH + "/" + productId;
        return Response.created(URI.create(url)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ProductFilterResponsesCollectionDto getProductsFromFilter(@QueryParam("sellerId") UUID sellerId,
                                                                     @QueryParam("title") String title,
                                                                     @QueryParam("categories") List<String> categories,
                                                                     @QueryParam("minPrice") float minPrice,
                                                                     @QueryParam("maxPrice") float maxPrice) {

        var productList = productRepository.findAll();
        List<ProductFilteredResponseDto> products = new ArrayList<>();

        if (sellerId != null) {
            Criteria sellerFilter = new CriteriaSellerId(sellerId);
            productList = sellerFilter.meetCriteria(productList);
        }
        if (title != null) {
            Criteria titleFilter = new CriteriaTitle(title);
            productList = titleFilter.meetCriteria(productList);

        }
        if (!categories.isEmpty()) {
            List<ProductCategory> productCategoriesList = new ArrayList<>();
            for (var category : categories) productCategoriesList.add(ProductCategory.findByName(category));

            Criteria categoryFilter = new CriteriaCategories(productCategoriesList);
            productList = categoryFilter.meetCriteria(productList);
        }
        if (minPrice != 0) {
            Criteria minPriceFilter = new CriteriaMinPrice(minPrice);
            productList = minPriceFilter.meetCriteria(productList);
        }
        if (maxPrice != 0) {
            Criteria maxPriceFilter = new CriteriaMaxPrice(maxPrice);
            productList = maxPriceFilter.meetCriteria(productList);
        }

        if (!productList.isEmpty())
            for (var product : productList) {
                var seller = sellerRepository.findById(product.getSellerId());
                ProductSellerDto productSeller = null;
                if (seller.isPresent()) productSeller = new ProductSellerDto(
                        product.getSellerId(),
                        seller.get().getName()
                );

                product.addVisits(1);

                var offers = product.getOffers();
                products.add(new ProductFilteredResponseDto(
                        product.getProductId(),
                        product.getCreatedAt(),
                        product.getTitle(),
                        product.getDescription(),
                        product.getSuggestedPrice().getValue(),
                        product.getCategories(),
                        productSeller,
                        product.getVisits(),
                        new OffersCountDto(offers.getCount())));
            }

        return new ProductFilterResponsesCollectionDto(products);
    }

    @GET
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductInfoResponseDto getSeller(@PathParam("productId") UUID productId) throws ItemNotFoundError {
        var product = productRepository.findById(productId);

        if (product.isEmpty())
            throw new ItemNotFoundError("L'id fourni n'existe pas.");

        var productInfo = product.get();
        productInfo.addVisits(1);

        var seller = sellerRepository.findById(product.get().getSellerId());

        if (seller.isEmpty())
            throw new ItemNotFoundError("L'id fourni n'existe pas.");

        var sellerInfo = seller.get();
        var productSellerDto = new ProductSellerDto(productInfo.getSellerId(), sellerInfo.getName());

        var offers = productInfo.getOffers();

        return productAssembler.toDto(productInfo, productSellerDto, OffersResponseDto.fromOffers(offers));
    }

    @POST
    @Path("/{productId}/offers")
    public Response postOffers(@PathParam("productId") UUID productId,
                               @Valid @NotNull(payload = MissingParameterError.class) OfferItemDto offerItemDto
    ) throws ItemNotFoundError, InvalidParameterError {
        var offerFactory = new OfferFactory();
        var product = productRepository.findById(productId);

        if (product.isEmpty())
            throw new ItemNotFoundError("L'id fourni n'existe pas.");

        if (product.get().getSuggestedPrice().getValue() >= offerItemDto.amount)
            throw new InvalidParameterError("Le montant de l'offre doit ??tre ??gal ou supp??rieur ?? celui demand??");

        var productOffers = product.get().getOffers();
        var newOffer = offerFactory.createNewOffer(offerItemDto);

        productOffers.addNewOffer(newOffer);

        return Response
                .status(Response.Status.OK)
                .entity("OK")
                .build();
    }
}
