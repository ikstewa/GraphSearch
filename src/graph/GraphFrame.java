/**
 * 
 */
package graph;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 * @author eclps
 *
 */
public class GraphFrame extends JFrame implements MouseListener, MouseMotionListener, ActionListener {
   
   
   
   /** Flag to indicate we are drawing the graph */
   protected boolean bIsDrawing = false;
   protected boolean bNeedRedraw = false;
   /** List of nodes */
   protected NodeList nodes = new NodeList();
   /** list of edges */
   protected EdgeList edges = new EdgeList();
   
   /** Xor for drawing edges */
   protected LineXor xor = null;
   /** */
   protected NodeTransform nodeTrans;
   
   
   protected JPanel panel;
   
   //---
   // SEARCHES
   //---
   protected BFS bfs;
   protected DFS dfs;
   
   //---
   // BUTTONS
   //---
   protected JButton drawButton;
   protected JButton bfsButton;
   protected JButton dfsButton;
   
   

   /**
    * Default constructor
    */
   public GraphFrame() {
      super("Graph Display");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setSize(800, 600);

      // set layout
      SpringLayout layout = new SpringLayout();
      setLayout(layout);
      
      // Add buttons
      drawButton = new JButton("Draw Graph");
      drawButton.addActionListener(this);
      add(drawButton);
      Container contentPane = this.getContentPane();
      layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, drawButton, 0,
                           SpringLayout.HORIZONTAL_CENTER, contentPane);
      
      bfsButton = new JButton("BFS");
      bfsButton.addActionListener(this);
      add(bfsButton);
      layout.putConstraint(SpringLayout.WEST, bfsButton, 10,
                           SpringLayout.EAST, drawButton);
      
      dfsButton = new JButton("DFS");
      dfsButton.addActionListener(this);
      add(dfsButton);
      layout.putConstraint(SpringLayout.EAST, dfsButton, -10,
                           SpringLayout.WEST, drawButton);
      
      Container c =this.getContentPane();
      panel = new JPanel();
      //panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      
      layout.putConstraint(SpringLayout.NORTH, panel, 10,
                           SpringLayout.SOUTH,drawButton);
      layout.putConstraint(SpringLayout.SOUTH, panel, -10,
            SpringLayout.SOUTH,c);
      layout.putConstraint(SpringLayout.EAST, panel, -10,
            SpringLayout.EAST,c);
      layout.putConstraint(SpringLayout.WEST, panel, 10,
            SpringLayout.WEST,c);
            
      this.add(panel);
      panel.addMouseListener(this);
     
      
      setVisible(true);
   }
   
   public void redraw() {
      Graphics g = panel.getGraphics();
      // clear the pallet
      g.clearRect(0, 0, panel.getWidth(), panel.getHeight());
      
      if(edges != null)
         edges.drawEdges(g);
      if(nodes != null)
         nodes.drawNodes(g);
      
      
   }
   
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
	   
	   
	   
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
	   
	   if(bNeedRedraw) {
	      redraw();
	      bNeedRedraw = !bNeedRedraw;
	   }
	   
	   

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	   bNeedRedraw = true;

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {	   
	   // if we hit a node start an xor
	   Node n = nodes.getNode(arg0.getPoint());
	   if(n != null) {
         xor = new LineXor(panel.getGraphics());
         xor.startXor(n);
	   }
	   

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
	   
	   
		
	   // deal with edge drawing
	   if(xor != null) {
	      // TODO: hit a node
	      Node end = nodes.getNode(arg0.getPoint());
	      // clear xor first to avoid artifacts
	      xor.clearXor();
	      if(end != null) {
	         Node start = xor.getStart();
	       
	         Edge e = (start == end) ? null : new Edge(xor.getStart(), end);
	         
	         // check if it already exists
	         if(e != null && edges.findEdge(e) == null) {
	            edges.addEdge(e);
	            start.adjList.add(end);
	            
	            // Draw the edges than the nodes to place the edges underneath
	            redraw();
	         }
	      }
	      // clear the xor
	      xor = null;
	   }
	   else {
   	   //Graphics g = getGraphics();
         Point p = arg0.getPoint();
         
         if(!nodes.drawCollision(p.x, p.y)) {
            Node n = new Node((int)p.getX(),(int)p.getY());
            nodes.addNode(n);
            //n.drawNode(g);
            redraw();
         }
	   }
      
      
	}

   @Override
   public void actionPerformed(ActionEvent arg0) {
      Component c = panel; //this;
      if(arg0.getSource() == drawButton) { 
         if(bIsDrawing) {
            c.removeMouseListener(this);
            c.removeMouseMotionListener(this);
            nodeTrans = new NodeTransform(this, nodes);
            c.addMouseListener(nodeTrans);
         }
         else {
            c.removeMouseListener(nodeTrans);
            c.addMouseListener(this);
            c.addMouseMotionListener(this);
         }
         bIsDrawing = !bIsDrawing;
      }
      else if(arg0.getSource() == bfsButton) {
         c.removeMouseListener(this);
         c.removeMouseMotionListener(this);
         c.removeMouseListener(nodeTrans);
         if(bIsDrawing) {
            bIsDrawing = !bIsDrawing;
         }
         bfs = new BFS(this, nodes);
         c.addMouseListener(bfs);
      }
      else if(arg0.getSource() == dfsButton) {
         c.removeMouseListener(this);
         c.removeMouseMotionListener(this);
         c.removeMouseListener(nodeTrans);
         if(bIsDrawing) {
            bIsDrawing = !bIsDrawing;
         }
         dfs = new DFS(this, nodes);
         c.addMouseListener(dfs);
      }
      else {
         System.out.println("RANDOM ACTION!");
      }
   }
   
   /**
    * @param args
    */
   public static void main(String[] args) {
      // TODO Auto-generated method stub
      GraphFrame gf = new GraphFrame();
   }

   @Override
   public void mouseDragged(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
      if(xor != null) {
         xor.moveXor(arg0.getPoint());
      }
      

      
      
   }

   @Override
   public void mouseMoved(MouseEvent e) {
      // TODO Auto-generated method stub
      
   }

}
