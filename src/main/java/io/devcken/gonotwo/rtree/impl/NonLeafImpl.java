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
import java.util.stream.Collectors;

public final class NonLeafImpl<K extends Geometry, V> implements Node<K, V> {
  private final List<Node<K, V>> children;
  private final Envelope mbr;

  NonLeafImpl(List<Node<K, V>> children) {
    this.children = children;
    this.mbr = this.children
      .stream()
      .map(n -> n.geometry().getEnvelopeInternal())
      .reduce(new Envelope(), (a, b) -> {
        a.expandToInclude(b);
        return a;
      });
  }

  @Override
  public List<Entry<K, V>> search(Envelope searchRectangle) {
    if (!searchRectangle.intersects(this.mbr)) return new ArrayList<>();

    List<Entry<K, V>> result = new ArrayList<>();

    for (Node<K, V> child : this.children) {
      result.addAll(child.search(searchRectangle));
    }

    return result;
  }

  @Override
  public List<Node<K, V>> insert(Entry<? extends K, ? extends V> entry) {
    // 새로운 엔트리가 속하게 될 노드를 선택한다.
    final Node<K, V> selectedNode = select(entry.geometry().getEnvelopeInternal(), this.children);

    List<Node<K, V>> inserted = selectedNode.insert(entry);

    List<Node<K, V>> newChildren = new ArrayList<>(this.children.size() + inserted.size());
    newChildren.addAll(this.children.stream().filter(n -> n != selectedNode).collect(Collectors.toList()));
    newChildren.addAll(inserted);

    if (newChildren.size() < 4) // <- M
      return Collections.singletonList(NodeFactory.branchOut(newChildren));
    else { // overflow
      Pair<List<Node<K, V>>, List<Node<K, V>>> splited = QuadraticSplitter.split(newChildren, 2); // <- m

      List<Node<K, V>> result = new ArrayList<>();

      result.add(NodeFactory.branchOut(splited.getLeft()));
      result.add(NodeFactory.branchOut(splited.getRight()));

      return result;
    }
  }

  private static <K extends Geometry, V> Node<K, V> select(Envelope mbr, List<Node<K, V>> nodes) {
    return nodes.stream().min((n1, n2) -> {
      Envelope mbr1 = new Envelope(n1.geometry().getEnvelopeInternal());
      Envelope mbr2 = new Envelope(n2.geometry().getEnvelopeInternal());

      mbr1.expandToInclude(mbr);
      mbr2.expandToInclude(mbr);

      int value = Double.compare(mbr1.getArea() - mbr.getArea(), mbr2.getArea() - mbr.getArea());

      if (value == 0) {
        value = Double.compare(mbr1.getArea(), mbr2.getArea());
      }

      return value;
    }).get();
  }

  @Override
  public Geometry geometry() {
    return new GeometryFactory().toGeometry(this.mbr);
  }
}
