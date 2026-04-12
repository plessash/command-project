package ru.aston.service.strategy.impl;

import ru.aston.service.strategy.SortStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.ToIntFunction;

public class EvenFieldSortDecorator<T> implements SortStrategy<T> {

    private final SortStrategy<T> delegate;
    private final ToIntFunction<T> keyExtractor;

    public EvenFieldSortDecorator(SortStrategy<T> delegate,
                                  ToIntFunction<T> keyExtractor) {
        this.delegate = Objects.requireNonNull(delegate, "Стратегия сортировки не должна быть null");
        this.keyExtractor = Objects.requireNonNull(keyExtractor, "Функция извлечения поля не должна быть null");
    }

    @Override
    public void sort(List<T> list, Comparator<T> comparator) {
        Objects.requireNonNull(list, "Список не должен быть null");
        Objects.requireNonNull(comparator, "Компаратор не должен быть null");

        List<Integer> evenIndices = new ArrayList<>();
        List<T> evenElements = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);
            int keyValue = keyExtractor.applyAsInt(element);

            if (keyValue % 2 == 0) {
                evenIndices.add(i);
                evenElements.add(element);
            }
        }

        delegate.sort(evenElements, comparator);

        for (int i = 0; i < evenIndices.size(); i++) {
            list.set(evenIndices.get(i), evenElements.get(i));
        }
    }

    @Override
    public String getName() {
        return "Сортировка элементов с четным значением (" + delegate.getName() + ")";
    }
}