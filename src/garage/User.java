package garage;

import java.io.Serializable;

import java.util.ArrayList;

public class User implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -710580701442977605L;
	private String surname;
    private String lastName;
    private String personNumber;
    private String phoneNumber;
    private String mailAddress;
    private boolean checkedIn;
    private String pinCode;
    private ArrayList<Bicycle> bicycleList;
    private static int MAX_B = 2;

    /**
     * Constructs a user object.
     */
    public User(String surname, String lastName, String personNumber,
   		 String phoneNumber, String mailAddress) {
   	 this.surname = surname;
   	 this.lastName = lastName;
   	 this.personNumber = personNumber;
   	 this.phoneNumber = phoneNumber;
   	 this.mailAddress = mailAddress;

   	 bicycleList = new ArrayList<Bicycle>();
    }

    /**
     * Returns the surname of the user.
     *
     * @return the surname of the user
     */
    public String getSurname() {
   	 return surname;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the last name of the user
     */
    public String getLastName() {
   	 return lastName;
    }

    /**
     * Returns the person number of the user.
     *
     * @return the person number of the user
     */
    public String getPersonNumber() {
   	 return personNumber;
    }

    /**
     * Returns the phone number of the user.
     *
     * @return the phone number of the user
     */
    public String getPhoneNumber() {
   	 return phoneNumber;
    }

    /**
     * Returns the mail address of the user.
     *
     * @return the mail address of the user
     */
    public String getMailAddress() {
   	 return mailAddress;
    }

    /**
     * Returns the garage status of the user.
     *
     * @return the garage status of the user
     */
    public boolean getGarageStatus() {
   	 return checkedIn;
    }

    /**
     * Returns the PIN-code of the user.
     *
     * @return the PIN-code of the user
     */
    public String getPinCode() {
   	 return pinCode;
    }

    /**
     * Return the bicycle with the specified index number from the list.
     *
     * @param num
     *        	the index number of the bicycle to be returned
     * @return the specified bicycle if it exists, else null
     */
    public Bicycle getBicycle(int num) {
   	 if (bicycleList.isEmpty() || num < 0 || num > MAX_B - 1
   			 || num > bicycleList.size() - 1) {
   		 return null;
   	 }

   	 return bicycleList.get(num);
    }

    /**
     * Returns the amount of bicycles of the user.
     *
     * @return the amount of bicycles of the user
     */
    public int getBicycleAmount() {
   	 return bicycleList.size();
    }

    /**
     * Adds the specified bicycle to the list if the list is not full.
     * post: the
     * specified bicycle is added to the list if it is not full
     *
     * @param b
     *        	the bicycle to be added
     * @return true if it was possible to add the bicycle to the list, else
     *     	false
     */
    public boolean addBicycle(Bicycle b) {
   	 if (bicycleList.size() < MAX_B) {
   		 return bicycleList.add(b);
   	 }
   	 return false;
    }

    /**
     * Removes the specified bicycle from the list.
     * post: the specified bicycle
     * is removed from the list
     *
     * @param b
     *        	the bicycle to be removed
     * @return true if it was possible to remove the bicycle, else false
     */
    public boolean removeBicycle(Bicycle b) {
   	 for (int i = 0; i < bicycleList.size(); i++) {
   		 if (bicycleList.get(i).equals(b)) {
   			 return bicycleList.remove(b);
   		 }
   	 }
   	 return false;
    }

    /**
     * Sets the surname of the user to name.
     * post: sets the surname of the user
     * to name
     *
     * @param name
     *        	the new surname of the user
     */
    public void setSurname(String name) {
   	 surname = name;
    }

    /**
     * Sets the last name of the user to name.
     * post: the last name of the user
     * is set to name
     *
     * @param name
     *        	the new last name of the user
     */
    public void setLastName(String name) {
   	 lastName = name;
    }

    /**
     * Sets the person number of the user to number.
     * post: the person number of
     * the user is set to number
     *
     * @param number
     *        	the new person number of the user
     */
    public void setPersonNumber(String number) {
   	 personNumber = number;
    }

    /**
     * Sets the phone number of the user to number.
     * post: the phone number of
     * the user is set to number
     *
     * @param number
     *        	the new phone number of the user
     */
    public void setPhoneNumber(String number) {
   	 phoneNumber = number;
    }

    /**
     * Sets the mail address of the user to address.
     * post: the mail address of
     * the user is set to address
     *
     * @param address
     *        	the new mail address of the user
     */
    public void setMailAddress(String address) {
   	 mailAddress = address;
    }

    /**
     * Sets the garage status of the user to status.
     * post: the garage status of
     * the user is set to status
     *
     * @param status
     *        	the new garage status of the user
     */
    public void setGarageStatus(boolean status) {
   	 checkedIn = status;
    }

    /**
     * Sets the PIN-code of the user to code.
     * post: the PIN-code of the user is
     * set to code
     *
     * @param code
     *        	the new PIN-code of the user
     */
    public void setPinCode(String code) {
   	 pinCode = code;
    }

    /**
     * Compares the specified element with the user.
     *
     * @return true if the person number of the specified element equals the
     *     	person number of the user else false
     */
    @Override
    public boolean equals(Object o) {
   	 if (personNumber.equals(((User) o).getPersonNumber())) {
   		 return true;
   	 }
   	 return false;
    }

    /**
     * Returns a String object containing the surname and the last name of the user.
     *
     * @return the surname and the last name of the user
     */
    @Override
    public String toString() {
   	 return surname + " " + lastName;
    }
}



