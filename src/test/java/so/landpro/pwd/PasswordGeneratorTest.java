package so.landpro.pwd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PasswordGeneratorTest {

    private PasswordGenerator subject;
    private int passwordLength;

    @BeforeEach
    void setUp() {
        passwordLength = 10;
        subject = new PasswordGenerator(passwordLength);
    }

    @Test
    public void testNextPassword() {
        String password = subject.nextPassword();

        assertThat(password, is(notNullValue()));
        assertThat(password.length(), equalTo(passwordLength));
    }

}
