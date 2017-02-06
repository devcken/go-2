package io.devcken.gonotwo.rtree.impl;

import com.vividsolutions.jts.geom.Geometry;
import io.devcken.gonotwo.rtree.Entry;
import io.devcken.gonotwo.rtree.Node;

import java.util.List;

public final class NodeFactory {
  public static <K extends Geometry, V> Node<K, V> leaveOut(List<Entry<K, V>> entries) {
    return new LeafImpl<>(entries);
  }

  public static <K extends Geometry, V> Node<K, V> branchOut(List<Node<K, V>> children) {
    return new NonLeafImpl<K, V>(children);
  }
}
