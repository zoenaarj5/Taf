package dax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import biz.Person;

public class PeopleAx implements DataAx<Person> {
	private static PeopleAx uniqueInstance=new PeopleAx();
	public static PeopleAx getUniqueInstance() {
		return uniqueInstance;
	}
	public List<Person> getPeopleList() {
		return peopleList;
	}
	private List<Person> peopleList;
	private PeopleAx() {
		super();
		String[][] peopleData=new String[][]{
			{"bgates","billgates@microsoft.com","BillGates1","Bill","Gates","BUSY"},
			{"mjordan","mj@chicagobulls.com","MichaelJordan1","Michael","Jordan","OPEN"},
			{"ltorvalds","lt@linux.com","LinusTorvalds1","Linus","Torvalds","BUSY"},
			{"bobama","barackobama@democraticparty.us","BarackObama1","Barack","Obama","AVAILABLE"},
			{"emurphy","eddiemurphy@comedy.us","EddieMurphy1","Eddie","Murphy","OPEN"},
			{"cmgregor","cmgregor@fight.com","ConnorMcGregor1","Connor","McGregor","OPEN"},
			{"edirupo","eliodirupo@ps.be","ElioDiRupo1","Elio","Di Rupo","BUSY"},
			{"bbardot","brigittebardot@cannes.fr","BrigitteBardot1","Brigitte","Bardot","AVAILABLE"}
		};
		peopleList=new ArrayList<>();
		for(String personData[]:peopleData) {
			Person p=Person.createPerson(personData[0], personData[1], personData[2], personData[3], personData[4], Person.Status.valueOf(personData[5]));
			peopleList.add(p);
		}
	}
	@Override
	public List<Person> fetchAll() {
		return peopleList;
	}

	@Override
	public List<Person> search(Map<String, Object> criteria) {
		final List<Person> pList=peopleList;
		peopleList.stream().forEach((Person p)->{if(match(p,criteria))pList.add(p);});
		return pList;
	}

	@Override
	public boolean delete(Person p) {
		for(Person pers:peopleList) {
			if(pers.getId()==p.getId()) {
				peopleList.remove(pers);
				return true;
			}
		}
		return false;
	}

	@Override
	public int delete(Map<String, Object> criteria) {
		int total=0;
		for(Person pers:peopleList) {
			if(match(pers,criteria)) {
				peopleList.remove(pers);
				total++;
			}
		}
		return total;
	}

	@Override
	public boolean update(Person p) {
		for(Person pers:peopleList) {
			if(pers.getId()==p.getId()) {
				pers.setEmail(p.getEmail());
				pers.setFirstName(p.getFirstName());
				pers.setPassword(p.getPassword());
				pers.setLastName(p.getLastName());
				pers.setUserName(p.getUserName());
				pers.setStatus(p.getStatus());
				return true;
			}
		}
		return false;
	}

	@Override
	public int update(Person p, String[] attributeNames) {
		int total=0;
		for(Person pers:peopleList) {
			if(match(pers,p,attributeNames) && change(pers,p,attributeNames)) {
				total++;
			}
		}
		return total;
	}
	@Override
	public boolean match(Person p, Map<String, Object> criteria) {
		for(Entry<String,Object> es:criteria.entrySet()) {
			Object value=es.getValue();

			switch(es.getKey()) {
			
				case "id":
					if(!Integer.valueOf(p.getId()).equals((Integer)value)) {
						return false;
					}
					break;
				case "userName":
					if(!p.getUserName().equals(value)) {
						return false;
					}
					break;
				case "email":
					if(!p.getEmail().equals(value)) {
						return false;
					}
					break;
				case "password":
					if(!p.getPassword().equals(value)) {
						return false;
					}
					break;
				case "firstName":
					if(!p.getFirstName().equals(value)) {
						return false;
					}
					break;
				case "lastName":
					if(!p.getLastName().equals(value)) {
						return false;
					}
					break;
				case "status":
					if(!p.getStatus().equals((Person.Status.valueOf(value.toString())))){
						return false;
					}
					break;
			}
		}
		return true;
	}
	@Override
	public boolean match(Person p1, Person p2, String[] attributeNames) {
		for(String attNam:attributeNames) {
			switch(attNam) {
			case "id":
				if(p1.getId()!=p2.getId()) {
					return false;
				}
				break;
			case "userName":
				if(!p1.getUserName().equals(p2.getUserName())) {
					return false;
				}
				break;
			case "email":
				if(!p1.getEmail().equals(p2.getEmail())) {
					return false;
				}
				break;
			case "password":
				if(!p1.getPassword().equals(p2.getPassword())) {
					return false;
				}
				break;
			case "firstName":
				if(!p1.getFirstName().equals(p2.getFirstName())) {
					return false;
				}
				break;
			case "lastName":
				if(!p1.getLastName().equals(p2.getLastName())) {
					return false;
				}
				break;
			case "status":
				if(!p1.getStatus().equals(p2.getStatus())){
					return false;
				}
				break;
			}
		}
		return true;
	}
	@Override
	public boolean change(Person target, Person model,String[] attributeNames) {		
		int total=0;
		for(String attNam:attributeNames) {
			switch(attNam) {
			case "userName":
				target.setUserName(model.getUserName());
				total++;
				break;
			case "email":
				target.setEmail(model.getEmail());
				total++;
				break;
			case "password":
				target.setPassword(model.getPassword());
				total++;
				break;
			case "firstName":
				target.setFirstName(model.getFirstName());
				total++;
				break;
			case "lastName":
				target.setLastName(model.getLastName());
				total++;
				break;
			case "status":
				target.setStatus(model.getStatus());
				total++;
				break;
			}
		}
		return total==attributeNames.length;
	}
	@Override
	public boolean add(Person p) {
		Map<String,Object> crit=new HashMap<>();
		crit.put("email", p.getEmail());
		crit.put("userName", p.getUserName());
		List<Person> duplis=search(crit);
		if(duplis.isEmpty()) {
			peopleList.add(p);
			return true;
		}
		return false;
	}
}
