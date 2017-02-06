package io.devcken.gonotwo.rtree;

import com.google.common.collect.Lists;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import io.devcken.gonotwo.rtree.impl.NodeFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class RTree<K extends Geometry, V> {
  private final Optional<Node<K, V>> root;

  RTree(Optional<Node<K, V>> root) {
    this.root = root;
  }

  RTree(Node<K, V> root) {
    this.root = Optional.of(root);
  }

  public static <K extends Geometry, V> RTree<K, V> newTree() {
    return new RTree<K, V>(Optional.empty());
  }

  public List<Entry<K, V>> search(final double xLong, final double xLat, final double yLong, final double yLat) {
    if (this.root.isPresent()) {
      return this.root.get().search(new Envelope(xLong, yLong, xLat, yLat));
    } else {
      return new ArrayList<>();
    }
  }

  @SuppressWarnings("unchecked")
  public RTree<K, V> insert(Entry<K, V> entry) {
    if (this.root.isPresent()) { // 루트가 있으므로 현재 루트로부터 삽입을 시작한다.
      List<Node<K, V>> nodes = this.root.get().insert(entry);

      // 루트가 있는데 루트의 하위 노드가 1개 밖에 없다면 1개의 하위 노드를 루트로 만든다.
      return new RTree<K, V>(nodes.size() == 1 ? nodes.get(0) : NodeFactory.branchOut(nodes));
    } else { // 루트가 없으므로 새로운 루트를 만든다. 이 때 루트는 잎 노드다.
      return new RTree<K, V>(NodeFactory.leaveOut(Lists.newArrayList(entry)));
    }
  }

  public RTree<K, V> insert(Iterable<Entry<K, V>> entries) {
    RTree<K, V> tree = this;

    for (Entry<K, V> entry: entries) {
      tree = tree.insert(entry);
    }

    return tree;
  }
}
