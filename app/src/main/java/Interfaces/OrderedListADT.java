package Interfaces;
/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */

public interface OrderedListADT<T> extends ListADT<T> {
	/**
	 * Adds the specified element to this list at
	 * the proper location
	 *
	 * @param element the element to be added to this list
	 */
	public void add (T element);

}
