package ulaval.glo2003;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private ArrayList<String> categories = new ArrayList<>();

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

        var sellerID1 = target("/sellers").request().post(Entity.entity(aSellerDTO1, MediaType.APPLICATION_JSON_TYPE));
        var sellerID2 = target("/sellers").request().post(Entity.entity(aSellerDTO1, MediaType.APPLICATION_JSON_TYPE));
        var sellerID3 = target("/sellers").request().post(Entity.entity(aSellerDTO1, MediaType.APPLICATION_JSON_TYPE));

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
        product1 = productFactory.createProduct(productDTO1, (String) sellerID3.getHeaders().getFirst("Location"));
        product2 = productFactory.createProduct(productDTO2, (String) sellerID3.getHeaders().getFirst("Location"));
        product3 = productFactory.createProduct(productDTO3, (String) sellerID2.getHeaders().getFirst("Location"));
        product4 = productFactory.createProduct(productDTO4, (String) sellerID1.getHeaders().getFirst("Location"));
    }
}
