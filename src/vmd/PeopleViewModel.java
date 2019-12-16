package vmd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;

import biz.Job;
import biz.Person;
import dax.JobsAx;
import dax.PeopleAx;
import dax.SearchField;
import dax.SearchMode;

public class PeopleViewModel {
	public static void main(String args[]) {
		List<Person> people=PeopleAx.getUniqueInstance().fetchAll();
		for(Person p:people) {
			System.out.println(p.getId());
			System.out.println(p.getEmail());
			System.out.println(p.getFirstName()+" "+p.getLastName().toUpperCase());
			System.out.println(p.getUserName());
			System.out.println(p.getPassword());
			System.out.println(p.getStatus());
			System.out.println("---------");
		}
		List<Job> jobs=JobsAx.getUniqueInstance().fetchAll();
		for(Job j:jobs) {
			System.out.println("JOB");
			System.out.println(j.getId());
			System.out.println(j.getCompanyName());
			System.out.println(j.getTitle());
			System.out.println(j.getDescription());
			System.out.println("REQUIRED ABILITIES");
			for(String ra:j.getRequiredAbilities()) {
				System.out.println(ra);
			}
			System.out.println("---------");
		}
	}
	public PeopleViewModel() {
		this.setPeopleList(peopleList=myPeopleAx.fetchAll());
	}
	private PeopleAx myPeopleAx=PeopleAx.getUniqueInstance();
	private List<Person> peopleList;
	private Person chosenPerson;
	private String searchTerm;
	public String getSearchTerm() {
		return searchTerm;
	}
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	public Person getChosenPerson() {
		return chosenPerson;
	}
	public void setChosenPerson(Person chosenPerson) {
		this.chosenPerson = chosenPerson;
	}
	public PeopleAx getMyPeopleAx() {
		return myPeopleAx;
	}
	public void setMyPeopleAx(PeopleAx myPeopleAx) {
		this.myPeopleAx = myPeopleAx;
	}
	public List<Person> getPeopleList() {
		return peopleList;
	}
	public void setPeopleList(List<Person> peopleList) {
		this.peopleList = peopleList;
	}
	@Command
	public void search() {
		Map<SearchField,Object> criteria=new HashMap<>();
		criteria.put(SearchField.PERSON_FIRST_NAME, this.searchTerm);
		criteria.put(SearchField.PERSON_EMAIL, this.searchTerm);
		criteria.put(SearchField.PERSON_LAST_NAME, this.searchTerm);
		criteria.put(SearchField.PERSON_STATUS, this.searchTerm);
		criteria.put(SearchField.PERSON_USER_NAME, this.searchTerm);
		this.setPeopleList(myPeopleAx.search(criteria,SearchMode.OR));
	}
}
