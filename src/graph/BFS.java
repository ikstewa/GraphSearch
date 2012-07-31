package graph;

import graph.Node.State;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class BFS implements MouseListener {
   static final int DELAY = 1000;
   
   /** Pointer to the frame so we can redraw */
   protected GraphFrame gf;
   /** Pointer to the node list */
   protected NodeList nodes;
   
   public BFS(GraphFrame gf, NodeList nodes) {
      this.gf = gf;
      this.nodes = nodes;
   }
   
   public void run(Node root) throws Exception {
      LinkedList<Node> queue = new LinkedList<Node>();
      
      for(Node n : nodes.nodes) {
         n.state = State.UNTOUCHED;
         n.depth = -1;
         n.predecessor = null;
         
         // clear DFS and BFS values
         n.depth = -1;
         n.disc = -1;
         n.finish = -1;
      }

      root.depth = 0;
      root.predecessor = null;
      changeState(root,State.TOUCHED);
      queue.add(root);
      
      while(!queue.isEmpty()) {
         Node u = queue.pop();
         
         for(Node v : u.adjList) {
            if(v.state == State.UNTOUCHED) {
               v.depth = u.depth + 1;
               v.predecessor = u;
               
               changeState(v,State.TOUCHED);
               
               queue.add(v);
            }
         }
         changeState(u,State.DONE);
      }
      
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