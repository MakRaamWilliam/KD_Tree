import edu.princeton.cs.algs4.Point2D;

public class testing {

	public static void main(String[] args) {
		KdTree tree = new KdTree();
		tree.insert(new Point2D(.7, .2));
		tree.insert(new Point2D(.5, .4));
		tree.insert(new Point2D(.2, .3));
		tree.insert(new Point2D(.4, .7));
		tree.insert(new Point2D(.9, .6));
		tree.insert(new Point2D(.7, .3));
		
		System.out.println(tree.contains(new Point2D(.7, .3)));
		System.out.println(tree.size());

		
    }

	
}
