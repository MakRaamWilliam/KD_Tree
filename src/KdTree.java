import java.util.ArrayList;
import java.util.Iterator;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

   private	Node2d root;
   private	int size;
   private Point2D comparison;
   private Point2D champion;
	public KdTree() {
		root=null;
		size=0;
	}
	public boolean isEmpty() {
		return root==null;
	}
	public int size() {
		return size;
	}
	

  public void insert(Point2D p) {
	if(p == null)   throw new IllegalArgumentException("point can't be null");
//	if(contains(p)) return;
	size++;
	if(root == null) {
	 Node2d node= new Node2d(p);
		node.horizontal=false;
		root=node;
		return ;
	}
	Node2d newnode= new Node2d(p);
	add(p,root,newnode);
}


	private void add(Point2D p, Node2d node, Node2d newnode) {
	if(node.point.equals(p)) {size--; return ;}	
	if(node.horizontal) {
		if(node.point.y()> p.y() &&node.getLeft()!=null ) add(p,node.getLeft(),newnode);
		else if(node.point.y()> p.y() && node.getLeft()==null ) {
		//	newnode.parent=node;
			newnode.horizontal=false;
			node.left=newnode;
		//	System.out.println(node.point);
			return ;
		}
		else if(node.right != null ) add(p, node.right, newnode);
		else {
		//	newnode.parent=node; 
			newnode.horizontal=false;
			node.right=newnode;
			return ;
		}
	}else { 
		if(node.point.x()>p.x() && node.getLeft()!=null ) add(p,node.getLeft(),newnode);
		else if(node.point.x()>p.x() &&node.getLeft()==null) {
		//	newnode.parent=node;
			newnode.horizontal=true;
			node.left=newnode;
		//	System.out.println("ll");
			return ;
		}
		else if(node.right !=null ) add(p, node.right, newnode);
		else {
		//	newnode.parent=node;
			newnode.horizontal=true;
			node.right=newnode;
		//	System.out.println(root.getLeft());
			return ;
		}
	}
}
	
//	public void insert(Point2D p) {
//        if (p == null) {
//            throw new IllegalArgumentException("point can't be null");
//        }
//        if (root == null || !contains(p)) {
//            size++;
//            root = insert(root, p, true);
//        }
//    }
//
//
//    private Node2d insert(Node2d node, Point2D p, boolean horizontalSplit) {
//        if (node == null) {
//            return new Node2d(p, null, null, horizontalSplit);
//        }
//
//        if (node.horizontal) {
//            if (p.x() < node.point.x()) {
//                node.left = insert(node.left, p, !horizontalSplit);
//            } else {
//                node.right = insert(node.right, p, !horizontalSplit);
//            }
//        } else {
//            if (p.y() < node.point.y()) {
//                node.left = insert(node.left, p, !horizontalSplit);
//            } else {
//                node.right = insert(node.right, p, !horizontalSplit);
//            }
//        }
//
//        return node;
//    }
//	
	
	
	public boolean contains(Point2D p) {
		if(p == null)   throw new IllegalArgumentException("point can't be null");
		if(root ==null) return false;
		return (search(p,root)  );
	}
			
	private boolean search(Point2D p, Node2d root2) {
		if(root2== null ) return false;
		if(p.equals(root2.point) ) return true;
		if(root2.horizontal && p.y()<root2.point.y() ) return search(p, root2.left);
		if(root2.horizontal && p.y()>=root2.point.y() ) return search(p, root2.right);
		
		if(!root2.horizontal && p.x()<root2.point.x() ) return search(p, root2.left);
		if(!root2.horizontal && p.x()>=root2.point.x() ) return search(p, root2.right);

		return false;
	}
	public void draw() {
        if (!isEmpty()) {
            root.draw();
        }
    }
	
	
	
	public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("");
        }
        ArrayList<Point2D> inrange = new ArrayList<>();
        if (!isEmpty()) {
            range(root, rect, inrange);
        }
        return inrange;
    }



	 private void range(Node2d node, RectHV rect, ArrayList<Point2D> inRangePoints) {

		 if (node == null) {
	            return;
	        }

	        Point2D p = node.point;
	        if (rect.contains(p)) {
	            inRangePoints.add(p);
	           
	        } 
	        if ((!node.horizontal && node.point.x() > rect.xmax()) || (node.horizontal && node.point.y() > rect.ymax())) {
                range(node.left, rect, inRangePoints);
            } else if( (!node.horizontal && node.point.x() < rect.xmin()) || (node.horizontal && node.point.y() < rect.ymin())  ) {
                range(node.right, rect, inRangePoints);
            }
	        
            else {
	            range(node.left, rect, inRangePoints);
	            range(node.right, rect, inRangePoints);
	        }        
	}
	 public Point2D nearest(Point2D p) {
			champion = null;
			comparison = p;
			
			checkNearest(root);
			
			return champion;
		}
		
		private void checkNearest(Node2d node) {
			if (node == null) return;
			
			if (champion == null) {
				champion = node.point;
			} else if (comparison.distanceTo(champion) > comparison.distanceTo(node.point)) {
				champion = node.point;
			}
			
			if (!node.horizontal) {
				//If this point is closer than champion, explore both subtrees
				if (comparison.distanceTo(champion) > comparison.distanceTo(node.point)) {
					if(node.point.x() >= comparison.x()) {
						checkNearest(node.left);
						checkNearest(node.right);
					
					} else {
						checkNearest(node.right);
						checkNearest(node.left);
					}
					
				//Otherwise explore the subtree closer to the comparison point
				} else {
					if(node.point.x() > comparison.x()) {
						checkNearest(node.left);
					
					} else if(node.point.x() < comparison.x()) {
						checkNearest(node.right);
					
					} else {
						checkNearest(node.left);
						checkNearest(node.right);
					}
				}
				
			} else {
				//If this point is closer than champion, explore both subtrees
				if (comparison.distanceTo(champion) > comparison.distanceTo(node.point)) {
					if(node.point.y() >= comparison.y()) {
						checkNearest(node.left);
						checkNearest(node.right);
					
					} else {
						checkNearest(node.right);
						checkNearest(node.left);
					}
					
				//Otherwise explore the subtree closer to the comparison point
				} else {
					if(node.point.y() > comparison.y()) {
						checkNearest(node.left);
					
					} else if(node.point.y() < comparison.y()) {
						checkNearest(node.right);
					
					} else {
						checkNearest(node.left);
						checkNearest(node.right);
					}
				}
			}
		}


 private	static class Node2d {
        private  Point2D point =null;
        private  boolean horizontal;
        private Node2d left;
        private Node2d right;
//	    private Node2d parent;
	    public Node2d(Point2D point) {
	    	this.point=point;
	    //	setLeft(setRight(setParent(null)));
	    	setHorizontal(false);
		}
//	    private Node2d(Point2D point, Node2d left, Node2d right, boolean horizontalSplit) {
//            this.point = point;
//            this.left = left;
//            this.right = right;
//            this.horizontal = horizontalSplit;
//        }
//	 
		private Node2d getLeft() {
			return left;
		}
		
		
		
		
//		public Node2d setParent(Node2d parent) {
//			this.parent = parent;
//			return parent;
//		}
	
		private void setHorizontal(boolean horizontal) {
			this.horizontal = horizontal;
		}
		private void draw() {
            if (left != null) {
                left.draw();
            }
            point.draw();
            if (right != null) {
                right.draw();
            }
        }
 }	
	
	 /** unit testing of the methods (optional) */
	public static void main(String[] args) {
//    	   StdOut.println("hello world");
//	        KdTree kdtree = new KdTree();
//	        assert kdtree.size() == 0;
//	        kdtree.insert(new Point2D(.7, .2));
//	        assert kdtree.size() == 1;
//	        kdtree.insert(new Point2D(.5, .4));
//	        kdtree.insert(new Point2D(.2, .3));
//	        kdtree.insert(new Point2D(.4, .7));
//	        kdtree.insert(new Point2D(.9, .6));
//	        assert kdtree.size() == 5;
//	        //StdOut.println(kdtree);
//
//	        kdtree = new KdTree();
//	        kdtree.insert(new Point2D(0.206107, 0.095492));
//	        kdtree.insert(new Point2D(0.975528, 0.654508));
//	        kdtree.insert(new Point2D(0.024472, 0.345492));
//	        kdtree.insert(new Point2D(0.793893, 0.095492));
//	        kdtree.insert(new Point2D(0.793893, 0.904508));
//	        kdtree.insert(new Point2D(0.975528, 0.345492));
//	        assert kdtree.size() == 6;
//	        kdtree.insert(new Point2D(0.206107, 0.904508));
//	        StdOut.println(kdtree);
//	        assert kdtree.size() == 7;
//
//	        StdOut.println("size: " + kdtree.size());
//
//	        
//	        StdDraw.show(0);
//	        StdDraw.setPenRadius(.02);
//	        kdtree.draw();
//          StdDraw.show(0);
	         


	        
//	        String filename;
//	        In in;
//	        // Test 1a: Insert N distinct points and check size() after each insertion
//	        // 100000 random distinct points in 100000-by-100000 grid
//	        filename = "kdtree/input100K.txt";
//	        in = new In(filename);
//	        kdtree = new KdTree();
//	        for (int i = 0; i < 100000; i++) {
//	            double x = in.readDouble();
//	            double y = in.readDouble();
//
//		   
//	    }
	   }
	
}
