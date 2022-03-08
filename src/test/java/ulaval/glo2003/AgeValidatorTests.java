package ulaval.glo2003;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ulaval.glo2003.api.validation.AgeValidator;
import ulaval.glo2003.api.validation.MinimumAge;

import java.time.LocalDate;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AgeValidatorTests {

    AgeValidator validator;
    MinimumAge annotationMock;
    ConstraintValidatorContext contextMock;
    private static final int DEFAULT_ANNOTATION_MOCK_MINIMUM_AGE = 18;

    @Before
    public void setUp() {
        validator = new AgeValidator();
        contextMock = mock(ConstraintValidatorContext.class);
        annotationMock = mock(MinimumAge.class);
        when(annotationMock.age()).thenReturn(DEFAULT_ANNOTATION_MOCK_MINIMUM_AGE);
    }

    private boolean testValidator(LocalDate dateToSend) {
        validator.initialize(annotationMock);
        return validator.isValid(dateToSend, contextMock);
    }

    @Test
    public void canRejectTooYoungBirthdate() {
        assertThat(testValidator(LocalDate.now().minusYears(1))).isFalse();
    }

    @Test
    public void canAcceptValidBirthdate() {
        assertThat(testValidator(LocalDate.now().minusYears(20))).isTrue();
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowOnNullBirthdate() {
        testValidator(null);
    }

    @Test
    public void canAcceptExactAge() {
        Assert.assertTrue(testValidator(LocalDate.now().minusYears(DEFAULT_ANNOTATION_MOCK_MINIMUM_AGE)));
    }

    @Test
    public void canRejectFutureDate(){
        assertThat(testValidator(LocalDate.now().plusYears(5))).isFalse();
    }
}