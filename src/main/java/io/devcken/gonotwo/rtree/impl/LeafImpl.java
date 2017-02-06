package io.devcken.gonotwo.rtree.impl;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import io.devcken.gonotwo.rtree.Entry;
import io.devcken.gonotwo.rtree.Node;
import io.devcken.gonotwo.rtree.split.QuadraticSplitter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LeafImpl<K extends Geometry, V> implements Node<K, V> {
  private final List<Entry<K, V>> entries;
  private final Envelope mbr; // Minimal Bounding Rectangle

  LeafImpl(List<Entry<K, V>> entries) {
    this.entries = entries;
    this.mbr = entries
      .stream()
      .map(e -> e.geometry().getEnvelopeInternal())
      .reduce(new Envelope(), (a, b) -> {
        a.expandToInclude(b);
        return a;
      });
  }

  @Override
  public List<Entry<K, V>> search(Envelope searchRectangle) {
    if (!searchRectangle.intersects(this.mbr)) return new ArrayList<>();

    List<Entry<K, V>> result = new ArrayList<>();

    for (Entry<K, V> entry : entries) {
      if (searchRectangle.intersects(entry.geometry().getEnvelopeInternal()))
        result.add(entry);
    }

    return result;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Node<K, V>> insert(Entry<? extends K, ? extends V> entry) {
    final List<Entry<K, V>> result = new ArrayList<>(this.entries.size() + 1);

    result.addAll(this.entries);
    result.add((Entry<K, V>)entry);

    if (result.size() < 4)
      return Collections.singletonList(NodeFactory.leaveOut(result));
    else { // overflow
      Pair<List<Entry<K, V>>, List<Entry<K, V>>> splitedPair = QuadraticSplitter.split((List)result, 2);

      List<Node<K, V>> splitedResult = new ArrayList<>(2);

      splitedResult.add(NodeFactory.leaveOut(splitedPair.getLeft()));
      splitedResult.add(NodeFactory.leaveOut(splitedPair.getRight()));

      return splitedResult;
    }
  }

  @Override
  public Geometry geometry() {
    return new GeometryFactory().toGeometry(this.mbr);
  }

  public List<Entry<K, V>> entries() {
    return this.entries;
  }
}
