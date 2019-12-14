package biz;

public class Person {
	public enum Status {
		AVAILABLE,BUSY,OPEN
	}
	private static int nextId=1;
	private int id=nextId++;
	private String userName,email,password,firstName,lastName;
	private Status status;
	public static Person createPerson(String userName,String email,String password,String firstName,String lastName,Status state) {
		Person newPerson=new Person();
		newPerson.setUserName(userName);
		newPerson.setEmail(email);
		newPerson.setPassword(password);
		newPerson.setFirstName(firstName);
		newPerson.setLastName(lastName);
		newPerson.setStatus(state);
		return newPerson;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status state) {
		this.status = state;
	}
	public static int getNextId() {
		return nextId;
	}
	public int getId() {
		return id;
	}
	private Person() {
		super();
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
