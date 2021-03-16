package dorian.guerrero.lanzone.utils.assertions;

import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ChipValueAssertion  implements ViewAssertion {
    private final String mExpectedText;
    private final int mAtPosition;

    private ChipValueAssertion(int atPosition, String expectedText) {
        mAtPosition = atPosition - 1;
        mExpectedText = expectedText;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        int count = ((ChipGroup) view).getChildCount();

        assertNotEquals(0, count);
        assert(mAtPosition >= 0 && mAtPosition <= count);

        Chip tmpEmail = (Chip) ((ChipGroup) view).getChildAt(mAtPosition);
        String email = tmpEmail.getText().toString();

        assertEquals(mExpectedText, email);

    }

    public static ChipValueAssertion matchesChipTextAtPosition(int atPosition, String expectedText) {
        return new ChipValueAssertion(atPosition, expectedText);
    }
}
