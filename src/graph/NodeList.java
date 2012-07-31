package graph;

import static graph.Node.NODE_DIAMETER;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class NodeList {
   
   /** Array of nodes */
   protected ArrayList<Node> nodes = null;
   
   public NodeList() {
      nodes = new ArrayList<Node>();
   }
   
   public void addNode(Node n) {
      nodes.add(n);
   }
   
   public void removeNode(Node n) {
      nodes.remove(n);
   }
   
   public void drawNodes(Graphics g) {
      // draw them all
      for(Node node : nodes) {
         node.drawNode(g);
      }
   }
   
   public Node getNode(Point p) {
      for(Node n : nodes) {
         if(n.hit(p.x, p.y)) {
            return n;
         }
      }
      return null;
   }
   
   public boolean drawCollision(int x, int y) {
      // check if we have room to add a node
      int minDistSqr = (NODE_DIAMETER*NODE_DIAMETER);
      for(Node n : nodes) {
         int distSqr = n.distSqr(x, y);
         
         if(distSqr < minDistSqr) return true;
      }
      
      return false;
   }
}
