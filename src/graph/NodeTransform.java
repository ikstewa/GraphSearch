package graph;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class NodeTransform implements MouseMotionListener, MouseListener {
   
   public GraphFrame gf;
   public NodeList nodes;
   public Node n;
   
   public NodeTransform(GraphFrame gf, NodeList nodes) {
      this.gf = gf;
      this.nodes = nodes;
   }

   @Override
   public void mouseDragged(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
      n.moveTo(arg0.getPoint());
      gf.redraw();

   }

   @Override
   public void mouseMoved(MouseEvent arg0) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseClicked(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mouseEntered(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mouseExited(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mousePressed(MouseEvent arg0) {
      if((n = nodes.getNode(arg0.getPoint())) != null) {
         gf.panel.addMouseMotionListener(this);
      }
   }

   @Override
   public void mouseReleased(MouseEvent arg0) {
      gf.panel.removeMouseMotionListener(this);
      
   }

}
