package vmd;

import java.util.List;

import biz.Job;
import biz.Person;
import dax.JobsAx;
import dax.PeopleAx;

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
}
