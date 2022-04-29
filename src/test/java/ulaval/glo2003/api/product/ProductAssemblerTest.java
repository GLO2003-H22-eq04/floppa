package ulaval.glo2003.api.product;

import org.junit.Before;
import org.junit.Test;
import ulaval.glo2003.api.product.ProductAssembler;
import ulaval.glo2003.applicatif.dto.offer.OffersResponseDto;
import ulaval.glo2003.applicatif.dto.product.ProductSellerDto;
import ulaval.glo2003.applicatif.dto.product.ProductVisitsDto;
import ulaval.glo2003.domain.product.Amount;
import ulaval.glo2003.domain.product.Product;
import ulaval.glo2003.domain.product.ProductCategory;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;

public class ProductAssemblerTest {

    private static final String EXPECTED_TITLE = "A cool hairbrush";
    private static final String EXPECTED_DESCRIPTION = "Pink and all";
    private static final double EXPECTED_SUGGESTED_PRICE = 5.01;
    private static final List<String> EXPECTED_CATEGORIES = List.of("beauty", "apparel");
    private static final OffsetDateTime EXPECTED_CREATED_AT = Instant.now().atOffset(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS);

    private static final UUID VALID_PRODUCT_ID1 = UUID.fromString("7b2e4fe9-aeaf-482d-b57d-98bac0183470");

    private ProductAssembler productAssembler;
    private Product product;
    private ProductSellerDto productSellerDTO;
    private OffersResponseDto offersDTO;
    private ProductVisitsDto productVisitsDTO;


    @Before
    public void setup() {
        product = new Product(EXPECTED_CREATED_AT);
        product.setSellerId(VALID_PRODUCT_ID1);
        product.setProductId(VALID_PRODUCT_ID1);
        product.setCreatedAt(EXPECTED_CREATED_AT);
        product.setTitle("A cool hairbrush");
        product.setDescription("Pink and all");
        product.setSuggestedPrice(new Amount(5.01));
        product.addCategory(ProductCategory.BEAUTY);
        product.addCategory(ProductCategory.APPAREL);

        productSellerDTO = new ProductSellerDto(VALID_PRODUCT_ID1, "John Doe");
        offersDTO = OffersResponseDto.empty();
        productVisitsDTO = new ProductVisitsDto(0);

        productAssembler = new ProductAssembler();
    }

    private void checkForNull(Product product) {
        assertThat(product).isNotNull();
        assertThat(product.getProductId()).isNotNull();
        assertThat(product.getTitle()).isNotNull();
        assertThat(product.getDescription()).isNotNull();
        assertThat(product.getSuggestedPrice()).isNotNull();
        assertThat(product.getCategories()).isNotNull();
        assertThat(product.getSellerId()).isNotNull();
    }

    @Test
    public void canGetExpectedId() {
        var productInfoResponseDTO = productAssembler.toDto(product, productSellerDTO, offersDTO, productVisitsDTO);

        checkForNull(product);
        assertThat(productInfoResponseDTO.id).isEqualTo(VALID_PRODUCT_ID1);
    }

    @Test
    public void canGetExpectedCreationDate() {
        var productInfoResponseDTO = productAssembler.toDto(product, productSellerDTO, offersDTO, productVisitsDTO);

        checkForNull(product);
        assertThat(productInfoResponseDTO.createdAt).isEqualTo(EXPECTED_CREATED_AT);
    }

    @Test
    public void canGetExpectedTitle() {
        var productInfoResponseDTO = productAssembler.toDto(product, productSellerDTO, offersDTO, productVisitsDTO);

        checkForNull(product);
        assertThat(productInfoResponseDTO.title).isEqualTo(EXPECTED_TITLE);
    }

    @Test
    public void canGetExpectedDescription() {
        var productInfoResponseDTO = productAssembler.toDto(product, productSellerDTO, offersDTO, productVisitsDTO);

        checkForNull(product);
        assertThat(productInfoResponseDTO.description).isEqualTo(EXPECTED_DESCRIPTION);
    }

    @Test
    public void canGetExpectedSuggestedPrice() {
        var productInfoResponseDTO = productAssembler.toDto(product, productSellerDTO, offersDTO, productVisitsDTO);

        checkForNull(product);
        assertThat(productInfoResponseDTO.suggestedPrice).isEqualTo(EXPECTED_SUGGESTED_PRICE);
    }

    @Test
    public void canGetExpectedCategories() {
        var productInfoResponseDTO = productAssembler.toDto(product, productSellerDTO, offersDTO, productVisitsDTO);

        checkForNull(product);
        assertThat(productInfoResponseDTO.categories).isEqualTo(EXPECTED_CATEGORIES);
    }
}
