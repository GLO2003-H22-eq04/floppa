package ulaval.glo2003;

import org.junit.Before;
import org.junit.Test;
import ulaval.glo2003.applicatif.offer.OfferItemDto;
import ulaval.glo2003.domain.offer.OfferFactory;
import ulaval.glo2003.domain.offer.OfferItem;

import java.util.List;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;

public class OfferFactoryTests {
    private OfferFactory offerFactory;
    private OfferItemDto offerItemDto1;
    private OfferItemDto offerItemDto2;

    @Before
    public void setUp(){
        offerItemDto1 = new OfferItemDto();
        offerItemDto1.name = "John Smith";
        offerItemDto1.phoneNumber = "18191234567";
        offerItemDto1.email = "john.smith@gmail.com";
        offerItemDto1.message = "Donec porttitor interdum lacus sed finibus. Nam pulvinar facilisis posuere. Maecenas vel lorem amet.";
        offerItemDto1.amount = 30;

        offerItemDto2 = new OfferItemDto();
        offerItemDto2.name = "Pat Rat";
        offerItemDto2.phoneNumber = "18191237654";
        offerItemDto2.email = "patRat1997@gmail.com";
        offerItemDto2.message = "Nam pulvinar facilisis posuere. Maecenas vel lorem amet. Donec porttitor interdum lacus sed finibus. ";
        offerItemDto2.amount = 40;

        offerFactory = new OfferFactory();
    }

    private void checkForNull(OfferItem offer){
        assertThat(offer).isNotNull();
        assertThat(offer.getName()).isNotNull();
        assertThat(offer.getAmount()).isNotNull();
        assertThat(offer.getEmail()).isNotNull();
        assertThat(offer.getMessage()).isNotNull();
        assertThat(offer.getPhoneNumber()).isNotNull();
    }

    private void checkForSameObject(OfferItem offer, OfferItem offer2){
        assertThat(offer).isNotEqualTo(offer2);
        assertThat(offer.getName()).isNotEqualTo(offer2.getName());
        assertThat(offer.getAmount()).isNotEqualTo(offer2.getAmount());
        assertThat(offer.getMessage()).isNotEqualTo(offer2.getMessage());
        assertThat(offer.getPhoneNumber()).isNotEqualTo(offer2.getPhoneNumber());
    }

    @Test
    public void canCreateSingleOfferNormal(){
        var offer = offerFactory.createNewOffer(offerItemDto1);
        checkForNull(offer);
    }

    @Test
    public void canCreateMultipleOfferNormal(){
        var offer = offerFactory.createNewOffer(offerItemDto1);
        checkForNull(offer);
        var offer2 = offerFactory.createNewOffer(offerItemDto2);
        checkForNull(offer2);
        checkForSameObject(offer, offer2);
    }
}
