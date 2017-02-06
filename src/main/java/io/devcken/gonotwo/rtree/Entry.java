package io.devcken.gonotwo.rtree;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class Entry<K extends Geometry, V> implements Geom {
  private final K geometry;
  private final V value;

  public Entry(K geometry, V value) {
    this.geometry = geometry;
    this.value = value;
  }

  @SuppressWarnings("unchecked")
  public static <K extends Geometry, V> Entry<K, V> newEntry(Double longitude, Double latitude, V value) {
    Point point = new GeometryFactory().createPoint(new Coordinate(longitude, latitude));

    return new Entry(point, value);
  }

  @Override
  public K geometry() { return this.geometry; }

  public V value() {
    return this.value;
  }
}
