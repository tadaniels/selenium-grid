package com.thoughtworks.selenium.grid;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JoinTest {

    @Test
    public void toStringOnAnEmptyCollectionReturnsAnEmptyString() {
        assertEquals("", new Join(new ArrayList(), "does not matter").toString());
    }

    @Test
    public void toStringOnACollectionWithASingleElementReturnThisElementConvertedToAString() {
        final List<Object> collection;

        collection = new LinkedList<Object>();
        collection.add(new Object() {
            public String toString() {
                return "string conversion of single element";
            }
        });
        assertEquals("string conversion of single element",
                     new Join(collection, "does not matter").toString());
    }

    @Test
    public void toStringOnACollectionWithAMultipleElementReturnTheseElementsConvertedToAStringSeparatedByTheSepatorGivenInTheConstructor() {
        final List<Object> collection;

        collection = new LinkedList<Object>();
        collection.add(new Object() {
            public String toString() {
                return "string conversion of first element";
            }
        });
        collection.add(new Object() {
            public String toString() {
                return "string conversion of second element";
            }
        });
        assertEquals("string conversion of first element|the separator|string conversion of second element",
                     new Join(collection, "|the separator|").toString());
    }

}
