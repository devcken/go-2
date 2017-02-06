package io.devcken.gonotwo.rtree;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

public interface Node<K extends Geometry, V> extends Geom {
  List<Entry<K, V>> search(Envelope searchRectangle);

  List<Node<K, V>> insert(Entry<? extends K, ? extends V> entry);
}
