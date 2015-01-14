package garage;

import java.io.Serializable;

public class Bicycle implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5624437238662626785L;
	private String barcode;
	private User owner;
	private boolean paymentStatus;
	private boolean checkedIn;
    
	/**
 	* Constructs a bicycle object.
 	*/
	public Bicycle(String barcode, User owner){
    	this.barcode = barcode;
    	this.owner = owner;
    	paymentStatus = false;
    	checkedIn = false;
	}
    
	/**
 	* Returns the barcode of the bicycle.
 	*
 	* @return the barcode of the bicycle
 	*/
	public String getBarcode(){
    	return barcode;
	}
    
	/**
 	* Returns the owner of the bicycle.
 	*
 	* @return the owner of the bicycle
 	*/
	public User getOwner(){
    	return owner;
	}
    
	/**
 	* Returns the payment status of the bicycle.
 	*	 
 	* @return the payment status of the bicycle
 	*/
	public boolean getPaymentStatus(){
    	return paymentStatus;
	}
    
	/**
 	* Returns the garage status of the bicycle.
 	*
 	* @return the garage status of the bicycle
 	*/
	public boolean getGarageStatus(){
   	return checkedIn;
	}
    
	/**
 	* Sets the barcode of the bicycle to barcode.
 	* post: the barcode of the bicycle is set to barcode
 	*
 	* @param barcode the new barcode of the bicycle
 	*/
	public void setBarcode(String barcode){
    	this.barcode = barcode;
	}
    
	/**
 	* Sets the owner of the bicycle to u.
 	* post: the owner of the bicycle is set to u
 	*
 	* @param u the new owner of the bicycle
 	*/
	public void setOwner(User u){
    	owner = u;
	}
    
	/**
 	* Sets the payment status to status.
 	* post: the payment status is set to status
 	*
 	* @param status the new payment status of the bicycle
 	*/
	public void setPaymentStatus(boolean status){
    	paymentStatus = status;
	}

	/**
 	* Sets the garage status of the bicycle to status
 	* post: the garage status of the bicycle is set to status
 	*
 	* @param status the new garage status of the bicycle
 	*/
 	public void setGarageStatus(boolean status){
  	checkedIn = status;
	}
	 
/**
     * Compares the specified element with the bicycle,
     *
     * @return true if the barcode of the specified element equals the barcode of the bicycle else
     *     	false
     */
    @Override
    public boolean equals(Object o) {
   	 if (barcode.equals(((Bicycle) o).getBarcode())) {
   		 return true;
   	 }
   	 return false;
    }

   @Override
    public String toString() {
    	return String.valueOf(barcode);
    }

}





