package ru.aston.service.strategy.impl;

import ru.aston.service.strategy.SortStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

public class OddEvenSortDecorator<T> implements SortStrategy<T> {

    private final SortStrategy<T> delegate;
    private final ToIntFunction<T> keyExtractor;
    private final Comparator<T> comparator;

    public OddEvenSortDecorator(SortStrategy<T> delegate,
                                ToIntFunction<T> keyExtractor,
                                Comparator<T> comparator) {
        this.delegate = delegate;
        this.keyExtractor = keyExtractor;
        this.comparator = comparator;
    }

    @Override
    public void sort(List<T> list, Comparator<T> comparatorIgnored) {
        List<T> result = new ArrayList<>(list);

        List<Integer> evenIndices = new ArrayList<>();
        List<T> evenElements = new ArrayList<>();

        for (int i = 0; i < result.size(); i++) {
            T element = result.get(i);
            int keyValue = keyExtractor.applyAsInt(element);
            if (keyValue % 2 == 0) {
                evenIndices.add(i);
                evenElements.add(element);
            }
        }

        delegate.sort(evenElements, comparator);

        for (int i = 0; i < evenIndices.size(); i++) {
            int originalIndex = evenIndices.get(i);
            result.set(originalIndex, evenElements.get(i));
        }

        list.clear();
        list.addAll(result);
    }

    @Override
    public String getName() {
        return "Четная-нечетная сортировка (" + delegate.getName() + ")";
    }
}
