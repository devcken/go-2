package io.devcken.gonotwo.pubtoilet;

import com.vividsolutions.jts.geom.Point;
import io.devcken.gonotwo.rtree.Entry;
import io.devcken.gonotwo.rtree.RTree;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PubToiletService {
  private final PubToiletRepository pubToiletRepository;

  @Inject
  public PubToiletService(PubToiletRepository pubToiletRepository) {
    this.pubToiletRepository = pubToiletRepository;
  }

  List<PubToilet> findToilets(Double xLong, Double xLat, Double yLong, Double yLat) {
    final List<PubToilet> pubToilets = this.pubToiletRepository.findToilets();

    if (pubToilets.size() == 0) return null;

    RTree<Point, PubToilet> tree = RTree.newTree();

    List<Entry<Point, PubToilet>> entries = pubToilets
      .stream()
      .map(t -> Entry.<Point, PubToilet>newEntry(t.getLongitude(), t.getLatitude(), t))
      .collect(Collectors.toList());

    tree = tree.insert(entries);

    return tree.search(xLong, xLat, yLong, yLat)
      .stream()
      .map(Entry::value)
      .collect(Collectors.toList());


//    RTree<PubToilet, Point> tree = RTree.star().create();
//
//    List<Entry<PubToilet, Point>> entries = pubToilets
//      .stream()
//      .map(t -> Entries.entry(t, Geometries.pointGeographic(t.getLongitude(), t.getLatitude())))
//      .collect(Collectors.toList());
//
//    tree = tree.add(entries);
//
//    return tree
//      .search(Geometries.rectangleGeographic(xLong, xLat, yLong, yLat))
//      .map(Entry::value)
//      .toList().toBlocking().single();
  }
}
