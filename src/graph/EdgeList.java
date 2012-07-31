package graph;

import java.awt.Graphics;
import java.util.ArrayList;

public class EdgeList {
   
   protected ArrayList<Edge> edges = new ArrayList<Edge>();
   
   public EdgeList() {
      
   }
   
   public void addEdge(Edge e) {
      edges.add(e);
   }
   
   public void drawEdges(Graphics g) {
      for(Edge e : edges) {
         e.drawEdge(g);
      }
   }
   
   public Edge findEdge(Edge e) {
      for( Edge edge : edges ) {
         if(edge.equals(e)) {
            return edge;
         }
      }
      
      return null;
   }
}