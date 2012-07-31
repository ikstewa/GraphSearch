package graph;

import java.awt.Color;
import java.awt.Graphics;

import static graph.Node.NODE_RAD;
   
public class Edge {
   protected Node start, end;
   
   public Edge(Node n1, Node n2) {
      start = n1;
      end = n2;
   }
   
   public void drawEdge(Graphics g) {
      g.setPaintMode();
      g.setColor(Color.BLACK);
      g.drawLine(start.x, start.y, end.x, end.y);
      

      
      
      // create an arrowhead at origin
      double points[] = new double[6];
      
      points[0] = 0.0;
      points[1] = 0.0;
      points[2] = -1*NODE_RAD;
      points[3] = -1*NODE_RAD/2;
      points[4] = -1*NODE_RAD;
      points[5] = NODE_RAD/2;
      
      
      // interpolate along the line
      
      //x = (1-t)*start.x + t*(end.x)
      
      double theta1 = Math.atan2(start.y-end.y, start.x-end.x);
      
      
      double rot;
      if(end.y>start.y)
         rot = Math.PI + theta1;
      else
         rot = -Math.PI + theta1;
      
      double x = NODE_RAD*Math.cos(rot);
      double y = NODE_RAD*Math.sin(rot);
      
      
      //double t =
      //double x = start.x + t(end.x-start.x);
      //double y = start.y + t(end.y-start.y);
      
      
      
      // find theta atan2(endY-startY, endX-startX)
      double theta = Math.atan2(end.y-start.y, end.x-start.x);
      // rotate the points
      double txPoints[] = new double[3];
      double tyPoints[] = new double[3];
      
      

      for(int i = 0; i<txPoints.length; i++) {
         txPoints[i] = (points[2*i] * Math.cos(theta)) - (points[2*i+1] * Math.sin(theta));
         tyPoints[i] = (points[2*i] * Math.sin(theta)) + (points[2*i+1] * Math.cos(theta));
         
         // move to the end of the line
         txPoints[i] += end.x-x;
         tyPoints[i] += end.y-y;
      }

      int ix[] = new int[3];
      int iy[] = new int[3];
      
      for(int i = 0; i<ix.length; i++) {
         ix[i] = (int)txPoints[i];
         iy[i] = (int)tyPoints[i];
      }
      g.fillPolygon(ix, iy, ix.length);
   }
   public boolean equals(Edge e) {
      return (start == e.start)&&(end == e.end);
   }
}