package io.devcken.gonotwo.rtree.split;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.vividsolutions.jts.geom.Envelope;
import io.devcken.gonotwo.rtree.Geom;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class QuadraticSplitter {
  @SuppressWarnings("unchecked")
  public static <T extends Geom> Pair<List<T>, List<T>> split(List<T> items, int min) {
    Preconditions.checkArgument(items.size() >= 2 && items.size() > min);

    final Pair<T, T> sortedMostWaistful = sortByMostWasteful(items);

    final List<T> group1 = Lists.newArrayList(sortedMostWaistful.getLeft());
    final List<T> group2 = Lists.newArrayList(sortedMostWaistful.getRight());

    final List<T> remaining = new ArrayList<T>(items);
    remaining.remove(sortedMostWaistful.getLeft());
    remaining.remove(sortedMostWaistful.getRight());

    final int minGroupSize = items.size() / 2;

    while (remaining.size() > 0) {
      assignRemained(group1, group2, remaining, minGroupSize);
    }

    return new ImmutablePair<>(group1, group2);
  }

  private static <T extends Geom> Pair<T, T> sortByMostWasteful(List<T> items) {
    Optional<T> e1 = Optional.empty();
    Optional<T> e2 = Optional.empty();

    Optional<Double> maxArea = Optional.empty();

    for (int i = 0; i < items.size(); i++) {
      for (int j = i + 1; j < items.size(); j++) {
        T entry1 = items.get(i);
        T entry2 = items.get(j);

        Envelope mbr = new Envelope(entry1.geometry().getEnvelopeInternal());
        mbr.expandToInclude(entry2.geometry().getEnvelopeInternal());

        final double area = mbr.getArea();

        if (!maxArea.isPresent() || area > maxArea.get()) {
          e1 = Optional.of(entry1);
          e2 = Optional.of(entry2);

          maxArea = Optional.of(area);
        }
      }
    }

    if (e1.isPresent())
      return new ImmutablePair<>(e1.get(), e2.get());
    else
      return new ImmutablePair<>(items.get(0), items.get(1));
  }

  private static <T extends Geom> void assignRemained(final List<T> group1, final List<T> group2, final List<T> remaining, final int minGroupSize) {
    final Envelope group1Mbr = group1
      .stream()
      .map(n -> n.geometry().getEnvelopeInternal())
      .reduce(new Envelope(), (a, b) -> {
        a.expandToInclude(b);
        return a;
      });

    final Envelope group2Mbr = group2
      .stream()
      .map(n -> n.geometry().getEnvelopeInternal())
      .reduce(new Envelope(), (a, b) -> {
        a.expandToInclude(b);
        return a;
      });

    final T candidatedEntry1 = getFirstPriorityEntityForGroup(remaining, group1Mbr);
    final T candidatedEntry2 = getFirstPriorityEntityForGroup(remaining, group2Mbr);

    candidatedEntry1.geometry().getEnvelopeInternal().expandToInclude(group1Mbr);
    candidatedEntry2.geometry().getEnvelopeInternal().expandToInclude(group2Mbr);

    final boolean area1LessThanArea2 = candidatedEntry1.geometry().getEnvelopeInternal().getArea() <= candidatedEntry2.geometry().getEnvelopeInternal().getArea();

    if (area1LessThanArea2 && group2.size() + remaining.size() - 1 >= minGroupSize
      || !area1LessThanArea2 && (group1.size() + remaining.size() == minGroupSize)) {
      group1.add(candidatedEntry1);
      remaining.remove(candidatedEntry1);
    } else {
      group2.add(candidatedEntry2);
      remaining.remove(candidatedEntry2);
    }
  }

  private static <T extends Geom> T getFirstPriorityEntityForGroup(List<T> items, Envelope groupMbr) {
    return items.stream()
      .map(t -> {
        Envelope mbr = new Envelope(groupMbr);
        mbr.expandToInclude(t.geometry().getEnvelopeInternal());

        return new ImmutablePair<>(t, mbr.getArea());
      })
      .sorted(Comparator.comparingDouble(ImmutablePair::getRight))
      .findFirst().get().getLeft();
  }
}
