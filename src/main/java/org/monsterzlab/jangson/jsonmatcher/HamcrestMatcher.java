package org.monsterzlab.jangson.jsonmatcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class HamcrestMatcher extends TypeSafeDiagnosingMatcher<String> {

    private JsonData expected;

    public HamcrestMatcher(JsonData expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(String actualtext, Description description) {
        JsonData actual = JsonMatcher.actual(actualtext);
        return JsonMatcher.match(expected, actual);
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expected);
    }

    public static HamcrestMatcher match(JsonData expected){
        return new HamcrestMatcher(expected);
    }

    public static HamcrestMatcher match(String expectedJson){
        JsonData expected = JsonMatcher.expected(expectedJson);
        return new HamcrestMatcher(expected);
    }
}
