package io.devcken.gonotwo.rtree.impl;

import com.google.common.collect.Lists;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import io.devcken.gonotwo.rtree.Entry;
import io.devcken.gonotwo.rtree.Node;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class LeafAndNonLeafTest {
  private Node<Point, String> root;

  @Before
  public void before() {
    Entry<Point, String> entry = Entry.newEntry(1.0, 1.0, "A");

    this.root = NodeFactory.leaveOut(Lists.newArrayList(entry));
  }

  @Test
  public void insertTest() { // with leaveOut
    Entry<Point, String> entry = Entry.newEntry(1.0, 1.1, "B");

    List<Node<Point, String>> nodes = this.root.insert(entry);

    assertTrue("nodes counts", nodes.size() == 1);
    assertTrue("node's class", nodes.get(0) instanceof LeafImpl);
    assertTrue("node entry counts", ((LeafImpl)nodes.get(0)).entries().size() == 2);
  }

  @Test
  public void branchOutTest() {
    Node<Point, String> newRoot = NodeFactory.branchOut(this.root.insert(Entry.newEntry(1.0, 1.1, "B")));

    assertTrue("branched out geometry", newRoot.geometry().getEnvelope().equalsExact(new GeometryFactory().toGeometry(new Envelope(1.0, 1.0, 1.0,1.1))));
  }

  @Test
  public void searchTest() {
    Node<Point, String> newRoot = NodeFactory.branchOut(this.root.insert(Entry.newEntry(1.0, 1.1, "B")));
    newRoot = NodeFactory.branchOut(newRoot.insert(Entry.newEntry(1.3, 1.7, "C")));
    newRoot = NodeFactory.branchOut(newRoot.insert(Entry.newEntry(2.1, 2.5, "D")));
    newRoot = NodeFactory.branchOut(newRoot.insert(Entry.newEntry(11.7, 12.1, "E")));
    newRoot = NodeFactory.branchOut(newRoot.insert(Entry.newEntry(4.5, 7.2, "F")));
    newRoot = NodeFactory.branchOut(newRoot.insert(Entry.newEntry(1.5, 42.1, "G")));
    newRoot = NodeFactory.branchOut(newRoot.insert(Entry.newEntry(37.5, 4.1, "H")));
    newRoot = NodeFactory.branchOut(newRoot.insert(Entry.newEntry(2.4, 8.1, "I")));
    newRoot = NodeFactory.branchOut(newRoot.insert(Entry.newEntry(1.99, 4.2, "J")));

    List<String> result = newRoot.search(new Envelope(2.0, 8.0, 2.0, 8.0)).stream().map(e -> e.value()).collect(Collectors.toList());

    assertTrue("search result", result.stream().sorted().collect(Collectors.toList()).equals(Lists.asList("D", "F", new String[0])));
  }
}
