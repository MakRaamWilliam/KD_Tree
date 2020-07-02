import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
public class PointSET {

	private Set<Point2D> points;
	public PointSET() {
		points = new TreeSet<Point2D>();
	}
	
	   public boolean isEmpty() {
		return points.isEmpty();
		}                     
	   public int size() {
		return points.size();
		}                         
	   public    void insert(Point2D p) {
		   if(p == null)   throw new IllegalArgumentException("point can't be null");
		   points.add(p);
	   }              
	   public  boolean contains(Point2D p) {
		   if(p == null)   throw new IllegalArgumentException("point can't be null");
		return points.contains(p);
		}            
	   public  void draw() {
		   for (Point2D p : points) {
	            p.draw();
	        }
	   }                         
	   public Iterable<Point2D> range(RectHV rect){
		   if(rect == null)   throw new IllegalArgumentException("point can't be null");
		   ArrayList<Point2D> rec = new ArrayList<>();
	        for (Point2D point : points) {
	            if (point.x() >= rect.xmin() && point.x() <= rect.xmax() &&
	                point.y() >= rect.ymin() && point.y() <= rect.ymax()) {
	                rec.add(point);
	            }
	        }
	        return new Iterable<Point2D>() {
	            @Override
	            public Iterator<Point2D> iterator() {
	                return rec.iterator();
	            }
	        };
		}              
	   public  Point2D nearest(Point2D p) {
		   if(p == null)   throw new IllegalArgumentException("point can't be null");
		   if(isEmpty()) return null;
	     double min =Double.POSITIVE_INFINITY;
	     Point2D ner=null ;
	     for(Point2D point : points) {
	    	 if(point.distanceSquaredTo(p) < min) {
	    		 min=point.distanceSquaredTo(p);
	         	 ner=point;  
	    	 }
	     }
		   return ner;
		}
	   public static void main(String[] args) {
		   
	   }
	
}
