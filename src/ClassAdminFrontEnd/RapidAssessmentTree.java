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

		private double mark;

	}

	public class TreeNode {
		private double x;
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

		private LinkedList<TreeNode> childNodes = new LinkedList<RapidAssessmentTree.TreeNode>();
	}
	
	public class TreeContainerNode extends TreeRectangleNode{

		String image;
		public TreeContainerNode(double x, double y, double w, double h, String image) {
			super(x, y, w, h);
			this.image = image;
			
		}
		
	}

}
