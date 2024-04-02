package org.example.model;

import org.example.exceptions.*;
import org.example.interfaces.IntegerList;

import java.util.Arrays;
import java.util.Objects;

public class IntegerListImpl implements IntegerList {
    private Integer[] arrayList;
    private int actualSize;

    public IntegerListImpl(int arraySize) {
        if (arraySize < 1) {
            throw new IncorrectSizeInput();
        }
        arrayList = new Integer[arraySize];
        actualSize = 0;
    }

    public int getActualSize() {
        return actualSize;
    }

    public Integer[] getArrayList() {
        return arrayList;
    }

    @Override
    public Integer add(Integer item) {
        isValidArgument(item);
        isInBound();
        arrayList[actualSize++] = item;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        isValidArgument(item);
        isInBound();
        isIndexInRange(index);
        if (index == actualSize) {
            arrayList[actualSize++] = item;
            return item;
        }
        System.arraycopy(arrayList, index, arrayList, index + 1, actualSize - index);
        arrayList[index] = item;
        actualSize++;
        return item;
    }

    @Override
    public Integer set(int index, Integer item) {
        isValidArgument(item);
        isIndexInRange(index);
        arrayList[index] = item;
        return item;
    }

    @Override
    public Integer remove(Integer item) {
        isValidArgument(item);
        isExistElement(item);
        int index = indexOf(item);
        isIndexInRange(index);
        return remove(index);
    }

    @Override
    public Integer remove(int index) {
        isIndexInRange(index);
        Integer item = get(index);
        if (index != actualSize) {
            System.arraycopy(arrayList, index + 1, arrayList, index, actualSize - index);
            return item;
        }
        arrayList[actualSize--] = null;
        return item;
    }

    @Override
    public boolean contains(Integer item) {
        isValidArgument(item);
        Integer[] arrayListCopy = toArray();
        sortArray(arrayListCopy);
        return binarySearch(arrayListCopy, item);
    }

    @Override
    public int indexOf(Integer item) {
        isValidArgument(item);
        for (int i = 0; i < actualSize; i++) {
            if (arrayList[i] == item) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        isValidArgument(item);
        for (int i = actualSize - 1; i >= 0; i--) {
            if (arrayList[i] == item) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        isIndexInRange(index);
        return Integer.valueOf(arrayList[index]);
    }

    @Override
    public boolean equals(IntegerList otherList) {
        isValidArgument(otherList);
        return this == otherList && this.hashCode() == otherList.hashCode() && Arrays.equals(this.toArray(), otherList.toArray());
    }

    @Override
    public int size() {
        return actualSize;
    }

    @Override
    public boolean isEmpty() {
        for (Integer s : arrayList) {
            if (s != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        Arrays.fill(arrayList, null);
        actualSize = 0;
    }

    @Override
    public Integer[] toArray() {
        Integer[] array = Arrays.copyOf(arrayList, actualSize);
        return array;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(actualSize);
        result = 31 * result + Arrays.hashCode(arrayList);
        return result;
    }

    @Override
    public String toString() {
        return "IntegerListImpl{" +
                "arrayList=" + Arrays.toString(arrayList) +
                '}';
    }

    public void sortArray(Integer[] array) {
        quickSort(array, 0, actualSize - 1);
    }

    private void quickSort(Integer[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);
            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);
        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
                swapElements(arr, i, j);
            }
        }
        swapElements(arr, i + 1, end);
        return i + 1;
    }

    private void swapElements(Integer[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    public boolean binarySearch(Integer[] array, Integer arg) {
        int iMin = 0;
        int iMax = array.length;
        while (iMin < iMax) {
            int iMid = (iMax + iMin) / 2;
            if (array[iMid] == arg) {
                return true;
            }
            if (arg > array[iMid]) {
                iMin = iMid + 1;
            } else {
                iMax = iMid - 1;
            }
        }
        return false;
    }

    public void grow() {
        arrayList = Arrays.copyOf(arrayList, actualSize + actualSize / 2);
    }

    private void isValidArgument(Integer arg) {
        if (arg == null) {
            throw new InvalidArgException();
        }
    }

    private void isValidArgument(IntegerList arg) {
        if (arg == null) {
            throw new InvalidArgException();
        }
    }

    private void isIndexInRange(int index) {
        if (index < 0 || index >= actualSize) {
            throw new IndexOutOfRangeException();
        }
    }

    private void isInBound() {
        if (actualSize >= arrayList.length) {
            grow();
        }
    }

    private void isExistElement(Integer arg) {
        if (indexOf(arg) < 0) {
            throw new NotFoundException();
        }
    }
}