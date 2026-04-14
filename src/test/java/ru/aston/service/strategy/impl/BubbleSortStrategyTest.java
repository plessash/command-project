package ru.aston.service.strategy.impl;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BubbleSortStrategyTest {

    private final BubbleSortStrategy<Integer> strategy = new BubbleSortStrategy<>();

    @Test
    void sort_shouldSortListInAscendingOrder() {
        List<Integer> list = new ArrayList<>(List.of(5, 2, 4, 6, 1, 3));

        strategy.sort(list, Comparator.naturalOrder());

        assertEquals(List.of(1, 2, 3, 4, 5, 6), list);
    }

    @Test
    void sort_shouldSortListInDescendingOrder() {
        List<Integer> list = new ArrayList<>(List.of(5, 2, 4, 6, 1, 3));

        strategy.sort(list, Comparator.reverseOrder());

        assertEquals(List.of(6, 5, 4, 3, 2, 1), list);
    }

    @Test
    void sort_shouldKeepEmptyListUnchanged() {
        List<Integer> list = new ArrayList<>();

        strategy.sort(list, Comparator.naturalOrder());

        assertEquals(List.of(), list);
    }

    @Test
    void sort_shouldKeepSingleElementListUnchanged() {
        List<Integer> list = new ArrayList<>(List.of(42));

        strategy.sort(list, Comparator.naturalOrder());

        assertEquals(List.of(42), list);
    }

    @Test
    void sort_shouldKeepAlreadySortedListUnchanged() {
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));

        strategy.sort(list, Comparator.naturalOrder());

        assertEquals(List.of(1, 2, 3, 4, 5), list);
    }

    @Test
    void sort_shouldHandleDuplicatesCorrectly() {
        List<Integer> list = new ArrayList<>(List.of(4, 2, 2, 5, 1, 4));

        strategy.sort(list, Comparator.naturalOrder());

        assertEquals(List.of(1, 2, 2, 4, 4, 5), list);
    }

    @Test
    void getName_shouldReturnExpectedName() {
        assertEquals("Пузырьковая сортировка", strategy.getName());
    }
}