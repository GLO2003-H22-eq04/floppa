package ulaval.glo2003;

import jakarta.inject.Inject;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import ulaval.glo2003.Criteria.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;


public class FilterTest extends JerseyTest {

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
    private Response sellerID1;
    private Response sellerID2;
    private Response sellerID3;
    private ArrayList<String> categories = new ArrayList<>();
    List<Product> productRepository = new ArrayList<>();


    @Override
    protected Application configure() {
        return Main.getRessourceConfig();
    }

    @Before
    public void setup(){
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
        productDTO1.suggestedPrice = 10;
        categories.clear();
        categories.add("other");
        productDTO1.categories = categories;

        productDTO2 = new ProductDTO();
        productDTO2.title = "Vodka";
        productDTO2.description = "Boisson Alcoolisé";
        productDTO2.suggestedPrice = 20;
        categories.clear();
        categories.add("other");
        productDTO2.categories = categories;

        productDTO3 = new ProductDTO();
        productDTO3.title = "Shirts";
        productDTO3.description = "Morceau de vêtement sports";
        productDTO3.suggestedPrice = 15.50;
        categories.clear();
        categories.add("sports");
        categories.add("apparel");
        productDTO3.categories = categories;

        productDTO4 = new ProductDTO();
        productDTO4.title = "Pants";
        productDTO4.description = "Morceau de vêtement aussi";
        categories.clear();
        categories.add("apparel");
        productDTO4.suggestedPrice = 10;

        var productFactory = new ProductFactory();
        product1 = productFactory.createProduct(productDTO1, "2");
        productRepository.add(product1);
        product2 = productFactory.createProduct(productDTO2, "2");
        productRepository.add(product2);
        product3 = productFactory.createProduct(productDTO3, "1");
        productRepository.add(product3);
        product4 = productFactory.createProduct(productDTO4, "0");
        productRepository.add(product4);
    }

    @Test
    public void canCreateNewCriteria(){
        Criteria criteriaTitle = new CriteriaTitle(product1.getTitle());
        Criteria criteriaMaxPrice = new CriteriaMaxPrice(product1.getSuggestedPrice().getValue());
        Criteria criteriaMinPrice = new CriteriaMinPrice(product1.getSuggestedPrice().getValue());
        Criteria criteriaSellerID = new CriteriaSellerID(Integer.parseInt(product1.getSellerId()));
        Criteria criteriaCategories = new CriteriaCategories(product1.getCategories());

        assertThat(criteriaTitle).isNotNull();
        assertThat(criteriaMinPrice).isNotNull();
        assertThat(criteriaMaxPrice).isNotNull();
        assertThat(criteriaSellerID).isNotNull();
        assertThat(criteriaCategories).isNotNull();
    }
}
