package ulaval.glo2003;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.ZoneOffset;

import static com.google.common.truth.Truth.assertThat;

public class ProductAssemblerTest {

    private ProductAssembler productAssembler;
    private Product product;
    private ProductSellerDTO productSellerDTO;
    private OfferDTO offerDTO;

    @Before
    public void setup(){
        product = new Product();
        product.setSellerId("123");
        product.setProductId(0);
        product.setCreatedAt(Instant.now().atOffset(ZoneOffset.UTC));
        product.setTitle("A cool haribrush");
        product.setDescription("Pink and all");
        product.setSuggestedPrice(new Amount(5.01));
        product.addCategory(ProductCategory.BEAUTY);
        product.addCategory(ProductCategory.APPAREL);

        productSellerDTO = new ProductSellerDTO("123", "John Doe");
        offerDTO = new OfferDTO(0,0);

        productAssembler = new ProductAssembler();
    }

    private void checkForNull(Product product){
        assertThat(product).isNotNull();
        assertThat(product.getProductId()).isNotNull();
        assertThat(product.getTitle()).isNotNull();
        assertThat(product.getDescription()).isNotNull();
        assertThat(product.getSuggestedPrice()).isNotNull();
        assertThat(product.getCategories()).isNotNull();
        assertThat(product.getSellerId()).isNotNull();
    }

    @Test
    public void canGetExpectedId(){
        var productInfoResponseDTO = productAssembler.toDto(product, productSellerDTO, offerDTO);
        var expectedId = String.valueOf(product.getProductId());

        checkForNull(product);
        assertThat(productInfoResponseDTO.id).isEqualTo(expectedId);
    }

    @Test
    public void canGetExpectedCreationDate(){
        var productInfoResponseDTO = productAssembler.toDto(product, productSellerDTO, offerDTO);

        checkForNull(product);
        assertThat(productInfoResponseDTO.createdAt).isEqualTo(product.getCreatedAt());
    }

    @Test
    public void canGetExpectedTitle(){
        var productInfoResponseDTO = productAssembler.toDto(product, productSellerDTO, offerDTO);

        checkForNull(product);
        assertThat(productInfoResponseDTO.title).isEqualTo(product.getTitle());
    }

    @Test
    public void canGetExpectedDescription(){
        var productInfoResponseDTO = productAssembler.toDto(product, productSellerDTO, offerDTO);

        checkForNull(product);
        assertThat(productInfoResponseDTO.description).isEqualTo(product.getDescription());
    }

    @Test
    public void canGetExpectedSuggestedPrice(){
        var productInfoResponseDTO = productAssembler.toDto(product, productSellerDTO, offerDTO);

        checkForNull(product);
        assertThat(productInfoResponseDTO.suggestedPrice).isEqualTo(product.getSuggestedPrice());
    }

    @Test
    public void canGetExpectedCategories(){
        var productInfoResponseDTO = productAssembler.toDto(product, productSellerDTO, offerDTO);

        checkForNull(product);
        assertThat(productInfoResponseDTO.categories).isEqualTo(product.getCategoriesName(product.getCategories()));
    }
}
