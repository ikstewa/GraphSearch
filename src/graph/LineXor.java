package graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class LineXor {
   
   protected Graphics g;
   protected Point start;
   protected Node startNode;
   protected Point end;
   protected Node endNode;
   protected boolean visible = false;
   
   public LineXor(Graphics g) {
      this.g = g;
   }
   
   public void startXor(Node n) {
      startNode = n;
      start = end = new Point(n.x, n.y);
      
      xor();
   }
   
   public void moveXor(Point p) {
      if(visible)
         xor();
      
      end = p;
      xor();
   }
   
   public void clearXor() {
      if(visible)
         xor();
   }
   
   public Node getStart() {
      return startNode;
   }
   
   protected void xor() {
      g.setXORMode(Color.RED);
      g.drawLine(start.x, start.y, end.x, end.y);
      visible = !visible;
   }
    
}