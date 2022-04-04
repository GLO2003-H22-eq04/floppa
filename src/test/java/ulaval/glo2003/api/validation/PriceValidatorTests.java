package ulaval.glo2003.api.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ulaval.glo2003.api.validation.MinimumPrice;
import ulaval.glo2003.api.validation.PriceValidator;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PriceValidatorTests {

    private static final double DEFAULT_ANNOTATION_MOCK_MINIMUM_PRICE = 1;

    @Mock
    private MinimumPrice annotationMock;

    @Mock
    private ConstraintValidatorContext contextMock;

    private PriceValidator validator;

    @Before
    public void setUp() {
        validator = new PriceValidator();
        when(annotationMock.price()).thenReturn(DEFAULT_ANNOTATION_MOCK_MINIMUM_PRICE);
    }

    private boolean testValidator(double priceToSend) {
        validator.initialize(annotationMock);

        return validator.isValid(priceToSend, contextMock);
    }

    @Test
    public void canRejectNegativePrice() {
        var price = -42;

        var result = testValidator(price);

        assertThat(result).isFalse();
    }

    @Test
    public void canRejectTooLowPrice() {
        var price = 0.50;

        var result = testValidator(price);

        assertThat(result).isFalse();
    }

    @Test
    public void canAcceptValidPrice() {
        var price = 5.99;

        var result = testValidator(price);

        assertThat(result).isTrue();
    }

    @Test
    public void canAcceptExactPrice() {
        var result = testValidator(DEFAULT_ANNOTATION_MOCK_MINIMUM_PRICE);

        assertThat(result).isTrue();
    }
}