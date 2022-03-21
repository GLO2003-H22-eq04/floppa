package ulaval.glo2003;

import org.junit.Before;
import org.junit.Test;
import ulaval.glo2003.api.product.dto.ProductDTO;
import ulaval.glo2003.api.seller.dto.SellerDTO;
import ulaval.glo2003.domain.product.Product;
import ulaval.glo2003.domain.product.ProductCategory;
import ulaval.glo2003.domain.product.ProductFactory;
import ulaval.glo2003.domain.product.criteria.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;


public class FilterTest{

    private static final int TITLE_WITH_FULL_NAME_COUNT = 1;
    private static final int TITLE_WITH_E_COUNT = 1;
    private static final int TITLE_WITH_A_COUNT = 2;
    private static final int MAX_PRICE_7_COUNT = 1;
    private static final int MAX_PRICE_100_COUNT = 4;
    private static final int MIN_PRICE_20_COUNT = 1;
    private static final int MIN_PRICE_1_COUNT = 4;
    private static final int SELLER_ID_1_COUNT = 1;
    private static final int SELLER_ID_2_COUNT = 2;
    private static final int CATEGORIES_BEAUTY_COUNT = 1;
    private static final int CATEGORIES_OTHER_COUNT = 2;
    private SellerDTO aSellerDTO1;
    private SellerDTO aSellerDTO2;
    private SellerDTO aSellerDTO3;
    private ProductDTO productDTO1;
    private ProductDTO productDTO2;
    private ProductDTO productDTO3;
    private ProductDTO productDTO4;
    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;
    private List<Product> products;

    private UUID id1;
    private UUID id2;
    private UUID id3;
    private UUID unusedId;

    @Before
    public void setup(){
        id1 = UUID.fromString("63cc6672-431e-472e-845c-6d965903c7b9");
        id2 = UUID.fromString("0fabd7c8-fd84-40e2-8338-7da189f4fae6");
        id3 = UUID.fromString("d8903391-1713-40c0-9fd9-d0ce0f454cd2");
        unusedId = UUID.fromString("cd1dfaf0-a9b3-46fd-9fe9-77371a83ca55");


        aSellerDTO1 = new SellerDTO();
        aSellerDTO1.name = "Joe Blo";
        aSellerDTO1.bio = "Test de bio";
        aSellerDTO1.birthDate = LocalDate.now().minusYears(20);

        aSellerDTO2 = new SellerDTO();
        aSellerDTO2.name = "Bloe Jo";
        aSellerDTO2.bio = "Je ne sais pas quoi écrire ici";
        aSellerDTO2.birthDate = LocalDate.now().minusYears(30);

        aSellerDTO3 = new SellerDTO();
        aSellerDTO3.name = "Joe Moes";
        aSellerDTO3.bio = "Lui-même";
        aSellerDTO3.birthDate = LocalDate.now().minusYears(40);

        productDTO1 = new ProductDTO();
        productDTO1.title = "Beer";
        productDTO1.description = "Boisson Alcoolisé";
        productDTO1.suggestedPrice = 7;
        productDTO1.categories = List.of("others");

        productDTO2 = new ProductDTO();
        productDTO2.title = "Vodka";
        productDTO2.description = "Boisson Alcoolisé";
        productDTO2.suggestedPrice = 20;
        productDTO2.categories = List.of("others");

        productDTO3 = new ProductDTO();
        productDTO3.title = "Shirts";
        productDTO3.description = "Morceau de vêtement sports";
        productDTO3.suggestedPrice = 15.50;
        productDTO3.categories = List.of("beauty");

        productDTO4 = new ProductDTO();
        productDTO4.title = "Pants";
        productDTO4.description = "Morceau de vêtement aussi";
        productDTO4.suggestedPrice = 10;
        productDTO4.categories = List.of("apparel","sports");

        var productFactory = new ProductFactory();
        product1 = productFactory.createProduct(productDTO1, id3);
        product2 = productFactory.createProduct(productDTO2, id3);
        product3 = productFactory.createProduct(productDTO3, id2);
        product4 = productFactory.createProduct(productDTO4, id1);

        products = new ArrayList<>();
        products.addAll(List.of(product1,product2,product3,product4));

    }

    @Test
    public void canCreateNewCriteria(){
        Criteria criteriaTitle = new CriteriaTitle(product1.getTitle());
        Criteria criteriaMaxPrice = new CriteriaMaxPrice(product1.getSuggestedPrice().getValue());
        Criteria criteriaMinPrice = new CriteriaMinPrice(product1.getSuggestedPrice().getValue());
        Criteria criteriaSellerID = new CriteriaSellerID(product1.getSellerId());
        Criteria criteriaCategories = new CriteriaCategories(product1.getCategories());

        assertThat(criteriaTitle).isNotNull();
        assertThat(criteriaMinPrice).isNotNull();
        assertThat(criteriaMaxPrice).isNotNull();
        assertThat(criteriaSellerID).isNotNull();
        assertThat(criteriaCategories).isNotNull();
    }

    @Test
    public void canFilterFromFullTitle(){
        Criteria criteria = new CriteriaTitle(product1.getTitle());
        var actualResult = criteria.meetCriteria(products);

        assertThat(actualResult.size()).isEqualTo(TITLE_WITH_FULL_NAME_COUNT);
    }

    @Test
    public void canFilterFromSmallerTitleButAppearOnce(){
        var testLetter = "e";
        Criteria criteria = new CriteriaTitle(testLetter);
        var actualResult = criteria.meetCriteria(products);

        assertThat(criteria.meetCriteria(products).size()).isEqualTo(TITLE_WITH_E_COUNT);
    }

    @Test
    public void canFilterFromSmallerTitleButAppearMoreThanOnce(){
        var testLetter = "a";
        Criteria criteria = new CriteriaTitle(testLetter);
        var actualResult = criteria.meetCriteria(products);

        assertThat(actualResult.size()).isEqualTo(TITLE_WITH_A_COUNT);
    }

    @Test
    public void canFilterFromSmallerTitleButNeverAppear(){
        var testLetter = "w";
        Criteria criteria = new CriteriaTitle(testLetter);
        var actualResult = criteria.meetCriteria(products);

        assertThat(actualResult).isEmpty();
    }

    @Test
    public void canFilterFromMaxPriceAppearOnce(){
        double amount = 7;
        Criteria criteria = new CriteriaMaxPrice(amount);
        var actualResult = criteria.meetCriteria(products);

        assertThat(actualResult.size()).isEqualTo(MAX_PRICE_7_COUNT);
    }

    @Test
    public void canFilterFromMaxPriceButNotOnce(){
        double amount = 5;
        Criteria criteria = new CriteriaMaxPrice(amount);
        var actualResult = criteria.meetCriteria(products);

        assertThat(actualResult).isEmpty();
    }

    @Test
    public void canFilterFromMaxPriceAppearMoreThanOnce(){
        double amount = 100;
        Criteria criteria = new CriteriaMaxPrice(amount);
        var actualResult = criteria.meetCriteria(products);

        assertThat(actualResult.size()).isEqualTo(MAX_PRICE_100_COUNT);
    }

    @Test
    public void canFilterFromMinPriceAppearOnce(){
        double amount = 20;
        Criteria criteria = new CriteriaMinPrice(amount);
        var actualResult = criteria.meetCriteria(products);

        assertThat(actualResult.size()).isEqualTo(MIN_PRICE_20_COUNT);
    }

    @Test
    public void canFilterFromMinPriceButNotOnce(){
        double amount = 100;
        Criteria criteria = new CriteriaMinPrice(amount);
        var actualResult = criteria.meetCriteria(products);

        assertThat(actualResult).isEmpty();
    }

    @Test
    public void canFilterFromMinPriceAppearMoreThanOnce(){
        double amount = 1;
        Criteria criteria = new CriteriaMinPrice(amount);
        var actualResult = criteria.meetCriteria(products);

        assertThat(actualResult.size()).isEqualTo(MIN_PRICE_1_COUNT);
    }

    @Test
    public void canFilterFromSellerIDAppearOnce(){
        var id = product4.getSellerId();
        Criteria criteria = new CriteriaSellerID(id);
        var actualResult = criteria.meetCriteria(products);

        assertThat(actualResult.size()).isEqualTo(SELLER_ID_1_COUNT);
    }

    @Test
    public void canFilterFromSellerIDAppearMoreThanOnce(){
        var id = product1.getSellerId();
        Criteria criteria = new CriteriaSellerID(id);
        var actualResult = criteria.meetCriteria(products);

        assertThat(actualResult.size()).isEqualTo(SELLER_ID_2_COUNT);
    }

    @Test
    public void canFilterFromSellerIDNeverAppear(){
        Criteria criteria = new CriteriaSellerID(unusedId);
        var actualResult = criteria.meetCriteria(products);

        assertThat(actualResult).isEmpty();
    }

    @Test
    public void canFilterFromCategoriesAppearOnce(){
        var categories = product3.getCategories();
        Criteria criteria = new CriteriaCategories(categories);
        var actualResult = criteria.meetCriteria(products);

        assertThat(actualResult.size()).isEqualTo(CATEGORIES_BEAUTY_COUNT);
    }

    @Test
    public void canFilterFromCategoriesAppearMoreThanOnce(){
        var categories = product1.getCategories();
        Criteria criteria = new CriteriaCategories(categories);
        var actualResult = criteria.meetCriteria(products);

        assertThat(actualResult).isNotEmpty();
        assertThat(actualResult.size()).isEqualTo(CATEGORIES_OTHER_COUNT);
    }

    @Test
    public void canFilterFromCategoriesNeverAppear(){
        var categories = List.of(ProductCategory.ELECTRONICS);
        Criteria criteria = new CriteriaCategories(categories);
        var actualResult = criteria.meetCriteria(products);

        assertThat(actualResult).isEmpty();
    }
}
