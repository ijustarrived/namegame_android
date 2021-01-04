package com.willowtreeapps.namegame.core;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ListRandomizer {

    @NonNull
    private final Random random;

    public ListRandomizer(@NonNull Random random) {
        this.random = random;
    }

    @NonNull
    public <T> T pickOne(@NonNull List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    @NonNull
    public <T> List<T> pickN(@NonNull List<T> list, int n) {
        if (list.size() == n) return list;
        if (n == 0) return Collections.emptyList();
        List<T> pickFrom = new ArrayList<>(list);
        List<T> picks = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            picks.add(pickFrom.remove(random.nextInt(pickFrom.size())));
        }
        return picks;
    }

    public <T> List<T> GenerateRandomListFromList(List<T> randomSeed, int maxSize) throws Exception
    {
        if(maxSize >= randomSeed.size())
            throw new Exception("Max size cannot be greater than the list seed");

        final List<T> NEW_LIST = new ArrayList<>();

        final Set<Integer> RANDOM_INDEXES = new LinkedHashSet<>();

        while (RANDOM_INDEXES.size() != maxSize)
        {
            RANDOM_INDEXES.add(random.nextInt(randomSeed.size()));
        }

        for (int i: RANDOM_INDEXES)
            NEW_LIST.add(randomSeed.get(i));

        return NEW_LIST;
    }
}
