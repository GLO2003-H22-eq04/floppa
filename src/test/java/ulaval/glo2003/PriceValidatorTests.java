package ulaval.glo2003;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ulaval.glo2003.Validation.AgeValidator;
import ulaval.glo2003.Validation.MinimumAge;
import ulaval.glo2003.Validation.MinimumPrice;
import ulaval.glo2003.Validation.PriceValidator;

import java.time.LocalDate;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PriceValidatorTests {

    private PriceValidator validator;
    private MinimumPrice annotationMock;
    private ConstraintValidatorContext contextMock;
    private static final double DEFAULT_ANNOTATION_MOCK_MINIMUM_PRICE = 1;

    @Before
    public void setUp() {
        validator = new PriceValidator();
        contextMock = mock(ConstraintValidatorContext.class);
        annotationMock = mock(MinimumPrice.class);
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