package poly;

import java.io.IOException;
import java.util.Scanner;

// The evaluate, add and multiply methods are my work the other parts of the project were pre made.   

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	
	
	// helper methods
	private static Node tail(Node head) {  
		Node ptr  = head; 
		while(ptr!=null) { 
			if(ptr.next==null) { 
				break;
			}
			ptr= ptr.next;
		} 
		return ptr;
	}
	
	private static Node deleteNode(Node head,float valD) { 
		
		Node ptr = head; 
		if(head.term.degree == valD) { 
			return ptr.next;
		}
		while(ptr!=null) { 
			if(ptr.next!= null && ptr.next.term.degree == valD) { 
				ptr.next= ptr.next.next;
				ptr = head;
				break;}  
			else {
			ptr= ptr.next;}
		} 
		return ptr;
	} 
	
private static Node deleteCoNode(Node head,float valD) { 
		Node ptr = head; 
		if(ptr == head && ptr.term.coeff == valD) { 
			return ptr.next;}
		
		while(ptr!=null) { 
			if(ptr.next!= null && ptr.next.term.coeff == valD) { 
				ptr.next= ptr.next.next;
				ptr = head;
				break;}  
			else {
			ptr= ptr.next;}
		} 
		return ptr;} 
	
	private static int lengthll(Node head) {  
		Node ptr = head;
		int count =0;
		while(ptr!=null) { 
			ptr= ptr.next;
			count++;} 
		return count;}
	
	private static Node copy(Node head) { 
		Node base = new Node(0,0,null); 
		Node ptr= head;
		Node store = base;
		int count = lengthll(head);
		while(count>0) { 
			base.next= new Node(ptr.term.coeff, ptr.term.degree, null); 
			ptr=ptr.next;
			base= base.next; 
			count --;
		} 
		return store.next;
	} 
	
	private static Node merge(Node head1,Node head2) {  // my own implementation of merge but something isn't quiet working yet
    	if(head2==null) { // added this in
    		return head1;
    	}

		Node ptr= head2;
		while(ptr!=null) { 
			head1= insert(head1,new Node(ptr.term.coeff,ptr.term.degree,null)); 
			ptr=ptr.next;}
    	return head1;}
	
	private static Node insert(Node head1, Node insert) { //insert node into ll in degree order
		if(insert== null) { 
			return head1;
		}
		else if(head1==null) { 
			return insert;
		}
		
		if(tail(head1).term.degree<insert.term.degree) { 
			tail(head1).next= insert; 
			return head1;}  
		else if(head1.term.degree>insert.term.degree) { 
			insert.next= head1; 
			head1 = insert;
			return head1;}
		for(Node ptr1 = head1; ptr1!=null; ptr1=ptr1.next) { 
			if(insert.term.degree>ptr1.next.term.degree) { 
					continue;}
				else { 
					insert.next= ptr1.next; 
					ptr1.next = insert; 
					break;}  
		}  
		return head1;} 
		
	
	public static Node add(Node poly1, Node poly2) {
		if (poly1==null) { return poly2;} 
		else if(poly2==null) { return poly1;}
		Node p1,p2; ß
		if(tail(poly1).term.degree>tail(poly2).term.degree) { 
			 p1 = copy(poly2); 
			 p2= copy(poly1);}  
		else { 
			 p1 = copy(poly1); 
			 p2= copy(poly2);}
		for(Node ptr1= p1; ptr1!=null  ;ptr1=ptr1.next) {
			for(Node ptr2 = p2; ptr2!=null; ptr2=ptr2.next){	
				if(ptr1!=null && ptr2!=null) {
					if(ptr1.term.degree==ptr2.term.degree) {
						ptr2.term.coeff = ptr2.term.coeff+ ptr1.term.coeff;
						p1= deleteNode(p1,ptr1.term.degree);
						break;}  }}}    
		Node ptr2 = p2;	
		while(ptr2!=null){
			if(Math.abs(ptr2.term.coeff)-0.0<Math.pow(10, -17)) {
				p2= deleteCoNode(p2,ptr2.term.coeff);} 
			ptr2=ptr2.next;
		}
		p1= merge(p1,p2);
		return p1; 
	} 
		
		
	
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		if(poly1==null|| poly2 ==null) {return null;}
		Node p1; 
		Node p2;
		if(lengthll(poly1)>lengthll(poly2)) {  // the longest ll with be p1 and shorter p2
			 p1 = copy(poly1); 
			 p2= copy(poly2);}  
		else { 
			 p1 = copy(poly2); 
			 p2= copy(poly1);} 
		Node hold  = copy(p1); // keeps a copy of original poly for recursive call
		if(lengthll(p2)==1 ) {  // base case
			for(Node ptr = p1; ptr!=null; ptr=ptr.next) { 
				ptr.term.coeff*= p2.term.coeff; 
				ptr.term.degree +=p2.term.degree;
			}  
			return p1;
		}
		else { 
			for(Node ptr = p1; ptr!=null; ptr=ptr.next) {  
				ptr.term.coeff*= p2.term.coeff; 
				ptr.term.degree +=p2.term.degree;
			}     
			return add(p1,multiply(hold,p2.next )); // recursive call   
		} 
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION 
		float sum =0;
		while(poly!=null) { 
		sum+=	poly.term.coeff*(Math.pow(x, poly.term.degree));
		poly=poly.next;
		}
		
		return sum;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
