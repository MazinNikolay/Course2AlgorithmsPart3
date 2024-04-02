package model;

import org.example.exceptions.IndexOutOfRangeException;
import org.example.exceptions.InvalidArgException;
import org.example.exceptions.NotFoundException;
import org.example.interfaces.IntegerList;
import org.example.model.IntegerListImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class IntegerListImplTest {

    private IntegerListImpl out;

    @BeforeEach
    public void initVariable() {
        out = new IntegerListImpl(5);
    }

    @Test
    void addCorrect() {
        Integer expected = 111;
        Integer actual = out.add(111);
        assertEquals(expected, actual);
    }

    @Test
    void checkEmptyArgMethod() {
        assertThrows(InvalidArgException.class, () -> out.add(null));
    }


    @Test
    void addToPositionCorrect() {
        Integer expectedAdded = 111;
        Integer expectedMoved = 222;
        out.add(222);
        Integer actualAdded = out.add(0, 111);
        Integer actualMoved = out.get(1);
        assertEquals(expectedAdded, actualAdded);
        assertEquals(expectedMoved, actualMoved);
    }

    @Test
    void checkIndexOutOfRangeMethod() {
        out.add(111);
        assertThrows(IndexOutOfRangeException.class, () -> out.add(2, 666));
    }

    @Test
    void setCorrect() {
        out.add(111);
        Integer expected = 222;
        Integer actual = out.set(0, 222);
        assertEquals(expected, actual);
    }

    @Test
    void checkNotFoundItemMethod() {
        out.add(111);
        Integer item = 2;
        assertThrows(NotFoundException.class, () -> out.remove(item));
    }

    @Test
    void removeWithItemCorrect() {
        out.add(1);
        out.add(2);
        out.add(3);
        Integer expectedRemoved = 2;
        Integer expectedMoved = 3;
        Integer actualRemoved = out.remove(expectedRemoved);
        Integer actualMoved = out.get(1);
        assertEquals(expectedRemoved, actualRemoved);
        assertEquals(expectedMoved, actualMoved);
    }

    @Test
    void containsCorrect() {
        out.add(111);
        boolean expected = true;
        boolean actual = out.contains(111);
        assertEquals(expected, actual);
    }

    @Test
    void indexOfCorrect() {
        out.add(111);
        int expected = 0;
        int actual = out.indexOf(111);
        assertEquals(expected, actual);
    }

    @Test
    void lastIndexOfCorrect() {
        out.add(111);
        out.add(222);
        out.add(111);
        int expected = 2;
        int actual = out.lastIndexOf(111);
        assertEquals(expected, actual);
    }

    @Test
    void getCorrect() {
        out.add(111);
        out.add(222);
        out.add(333);
        Integer expected = 222;
        Integer actual = out.get(1);
        assertEquals(expected, actual);
    }

    @Test
    void EqualsCorrect() {
        out.add(111);
        out.add(222);
        out.add(333);
        IntegerList otherList = new IntegerListImpl(4);
        IntegerList copyList = out;
        boolean expectedCopy = true;
        boolean expectedOther = false;
        boolean actualCopy = out.equals(copyList);
        boolean actualOther = out.equals(otherList);
        assertEquals(expectedCopy, actualCopy);
        assertEquals(expectedOther, actualOther);
    }

    @Test
    void sizeCorrect() {
        out.add(111);
        out.add(222);
        out.add(111);
        int expected = 3;
        int actual = out.size();
        assertEquals(expected, actual);
    }

    @Test
    void isEmptyCorrect() {
        boolean expected = true;
        boolean actual = out.isEmpty();
        assertEquals(expected, actual);
    }

    @Test
    void clearCorrect() {
        out.add(111);
        out.add(222);
        out.add(333);
        out.clear();
        boolean expected = true;
        boolean actual = out.isEmpty();
        assertEquals(expected, actual);
    }

    @Test
    void toArrayCorrect() {
        out.add(111);
        out.add(222);
        out.add(333);
        Integer[] expected = {111, 222, 333};
        Integer[] actual = out.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    void sortArray() {
        out.add(3);
        out.add(1);
        out.add(2);
        Integer[] actualArr = out.toArray();
        out.sortArray(actualArr);
        System.out.println(Arrays.toString(actualArr));
        Integer[] expectedArr = {1, 2, 3};
        assertArrayEquals(expectedArr, actualArr);
    }

    @Test
    void binarySearch() {
        out.add(3);
        out.add(1);
        out.add(2);
        Integer[] actualArr = out.toArray();
        out.sortArray(actualArr);
        boolean actual = out.binarySearch(actualArr, 2);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    void grow() {
        out.add(111);
        out.add(222);
        out.add(111);
        out.add(111);
        out.grow();
        int expected = 6;
        int actual = out.getArrayList().length;
        assertEquals(expected, actual);
    }
}