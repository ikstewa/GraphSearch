package graph;

import graph.Node.State;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DFS implements MouseListener {
   static final int DELAY = 1000;
   
   /** Pointer to the frame so we can redraw */
   protected GraphFrame gf;
   /** Pointer to the node list */
   protected NodeList nodes;
   
   protected int time;
   
   public DFS(GraphFrame gf, NodeList nodes) {
      this.gf = gf;
      this.nodes = nodes;
   }
   
   public void run(Node root) throws Exception {
      for(Node n : nodes.nodes) {
         n.state = State.UNTOUCHED;
         n.predecessor = null;
         
         // clear DFS and BFS values
         n.depth = -1;
         n.disc = -1;
         n.finish = -1;
      }
      time = 0;
      visit(root);
      
      
      /*
      for(Node n : nodes.nodes) {
         if(n.state == State.UNTOUCHED) {
            visit(n);
         }
      }
      */
   }
   
   protected void visit(Node n) {
      
      time++;
      n.disc = time;
      changeState(n, State.TOUCHED);
      
      for(Node adj : n.adjList) {
         if(adj.state == State.UNTOUCHED) {
            adj.predecessor = n;
            visit(adj);
         }
      }
      time++;
      n.finish = time;
      changeState(n,State.DONE);
   }
   
   protected void changeState(Node n, State s) {
      n.state = s;
      gf.redraw();
      try {
         Thread.sleep(DELAY);
      } catch(Exception e) {e.printStackTrace();}
   }

   @Override
   public void mouseReleased(MouseEvent arg0) {
      Node n = nodes.getNode(arg0.getPoint());
      if(n != null) {
         gf.panel.removeMouseListener(this);
         try {
            run(n);
         } catch(Exception e) {e.printStackTrace();}
      }
      gf.nodeTrans = new NodeTransform(gf, nodes);
      gf.panel.addMouseListener(gf.nodeTrans);
   }

   @Override
   public void mouseClicked(MouseEvent arg0) {}

   @Override
   public void mouseEntered(MouseEvent arg0) {}

   @Override
   public void mouseExited(MouseEvent arg0) {}

   @Override
   public void mousePressed(MouseEvent arg0) {}
}