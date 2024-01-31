/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassImplementation;
/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */
public class HeapNode<T> extends BinaryTreeNode<T>{
   protected HeapNode<T> parent;
   /**
    * Creates a new heap node with the specified data.
    * 
    * @param obj  the data to be contained within 
    *             the new heap nodes
    */
   HeapNode(T obj) {
      super(obj);
      parent = null;
   }
}
