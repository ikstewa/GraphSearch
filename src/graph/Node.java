package graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Node {
   
   
   /** */
   public static int NODE_RAD = 15;
   /** The diameter of the nodes to draw */
   public static int NODE_DIAMETER = NODE_RAD+NODE_RAD;
   
   /** The default color of the nodes fill */
   public static Color DEFAULT_FILL = Color.WHITE;
   /** The default color of the nodes fill */
   public static Color TOUCHED_FILL = Color.GRAY;
   /** The default color of the nodes fill */
   public static Color DONE_FILL = Color.BLACK;
   /** The default color of the nodes fill */
   public static Color DEFAULT_STROKE = Color.BLACK;
   /** The color of the xor */
   public static Color XOR_COLOR = Color.LIGHT_GRAY;
   
   /** node location */
   int x;
   int y;
   public enum State {
      UNTOUCHED,
      TOUCHED,
      DONE
   }
   /** node color */
   public State state = State.UNTOUCHED; 
   
   /** Adj list */
   public ArrayList<Node> adjList = new ArrayList<Node>();
   
   /** DFS vars */
   public int depth = -1;
   public Node predecessor = null;
   
   /** BFS vars */
   public int disc = -1;
   public int finish = -1;
   
   
   public Node(int x, int y) {
      this.x = x;
      this.y = y;
   }
   
   /**
    * Hit check for the node.
    * 
    * @param x
    * @param y
    * @return
    */
   public boolean hit(int x, int y) {
      return (distSqr(x,y)<(NODE_RAD*NODE_RAD));
   }
   
   /**
    * Square distance from point to the node.
    * 
    * @param x
    * @param y
    * @return
    */
   public int distSqr(int x, int y) {
      int dx = this.x - x;
      int dy = this.y - y;
      
      return dx*dx + dy*dy;
   }
   
   public void drawNode(Graphics g) {
      
      Color c = null;
      switch(state) {
         case UNTOUCHED:
            c = DEFAULT_FILL;
            break;
         case TOUCHED:
            c = TOUCHED_FILL;
            break;
         case DONE:
            c = DONE_FILL;
            break;
      }
      g.setPaintMode();
      g.setColor(c);
      g.fillOval(x-NODE_RAD, y-NODE_RAD, NODE_DIAMETER, NODE_DIAMETER);
      g.setColor(DEFAULT_STROKE);
      g.drawOval(x-NODE_RAD, y-NODE_RAD, NODE_DIAMETER, NODE_DIAMETER);
      
      
      //
      g.setFont(new Font("Verdana", Font.BOLD, 15));
      g.setXORMode(XOR_COLOR);
      
      if(depth != -1) {
         char[] d = Integer.toString(depth).toCharArray();
         g.drawChars(d, 0, d.length, x-NODE_RAD/2, y+NODE_RAD/2);
      }
      
      if(disc != -1) {
         String s = Integer.toString(disc);
         g.setFont(new Font("Verdana", Font.BOLD, 10));
         
         if(finish != -1) {
            s = s.concat("/"+Integer.toString(finish));
         }
         char[] d = s.toCharArray();
         g.drawChars(d, 0, d.length, x-NODE_RAD*7/8, y+NODE_RAD/4);
      }
      
   }
   
   public void moveTo(Point p) {
      this.x = p.x;
      this.y = p.y;
   }

}
