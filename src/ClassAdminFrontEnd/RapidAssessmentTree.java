package ClassAdminFrontEnd;

import java.util.LinkedList;

public class RapidAssessmentTree {
	private TreeContainerNode head;
	public TreeContainerNode getHead() {
		return head;
	}

	public void setHead(TreeContainerNode head) {
		this.head = head;
	}

	public RapidAssessmentTree(TreeContainerNode head){
		this.head = head;
	}
	
	public class TreeMarkNode extends TreeNode {
		public TreeMarkNode(double x, double y, double mark) {
			super(x, y);
			this.mark = mark;
			// TODO Auto-generated constructor stub
		}

		/**
		 * @return the mark
		 */
		public double getMark() {
			return mark;
		}

		/**
		 * @param mark the mark to set
		 */
		public void setMark(double mark) {
			this.mark = mark;
		}

		private double mark;

	}

	public class TreeNode {
		private double x;
		/**
		 * @return the x
		 */
		public double getX() {
			return x;
		}

		/**
		 * @param x the x to set
		 */
		public void setX(double x) {
			this.x = x;
		}

		/**
		 * @return the y
		 */
		public double getY() {
			return y;
		}

		/**
		 * @param y the y to set
		 */
		public void setY(double y) {
			this.y = y;
		}

		private double y;

		public TreeNode(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	public class TreeRectangleNode extends TreeNode {
		private double w;
		private double h;

		public LinkedList<TreeNode> getChildNodes() {
			return childNodes;
		}

		public TreeRectangleNode(double x, double y, double w, double h) {
			super(x, y);
			this.w = w;
			this.h = h;
			// TODO Auto-generated constructor stub
		}

		/**
		 * @return the w
		 */
		public double getW() {
			return w;
		}

		/**
		 * @param w the w to set
		 */
		public void setW(double w) {
			this.w = w;
		}

		/**
		 * @return the h
		 */
		public double getH() {
			return h;
		}

		/**
		 * @param h the h to set
		 */
		public void setH(double h) {
			this.h = h;
		}

		private LinkedList<TreeNode> childNodes = new LinkedList<RapidAssessmentTree.TreeNode>();
	}
	
	public class TreeContainerNode extends TreeRectangleNode{

		private String image;
		/**
		 * @return the image
		 */
		public String getImage() {
			return image;
		}
		/**
		 * @param image the image to set
		 */
		public void setImage(String image) {
			this.image = image;
		}
		public TreeContainerNode(double x, double y, double w, double h, String image) {
			super(x, y, w, h);
			this.image = image;
			
		}
		
	}

}
