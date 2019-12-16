package dax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import biz.Person;
import search.SearchField;
import search.SearchMode;

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
	public List<Person> search(Map<SearchField, Object> criteria,SearchMode mode) {
		final List<Person> pList=peopleList;
		peopleList.stream().forEach((Person p)->{if(match(p,criteria,mode))pList.add(p);});
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
	public int delete(Map<SearchField, Object> criteria,SearchMode mode) {
		int total=0;
		for(Person pers:peopleList) {
			if(match(pers,criteria,mode)) {
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
	public int update(Person p, SearchField[] attributeNames) {
		int total=0;
		for(Person pers:peopleList) {
			if(match(pers,p,attributeNames,SearchMode.AND) && change(pers,p,attributeNames)) {
				total++;
			}
		}
		return total;
	}
	@Override
	public boolean match(Person p, Map<SearchField, Object> criteria,SearchMode mode) {
		for(Entry<SearchField,Object> es:criteria.entrySet()) {
			Object value=es.getValue();
			switch(es.getKey()) {
				case PERSON_ID:
					if(mode==SearchMode.AND && !Integer.valueOf(p.getId()).equals((Integer)value)) {
						return false;
					}else if (mode==SearchMode.OR && Integer.valueOf(p.getId()).equals((Integer)value)) {
						return true;
					}
					break;
				case PERSON_USER_NAME:
					if(mode==SearchMode.AND && !p.getUserName().equals(value)) {
						return false;
					} else if(mode==SearchMode.OR && p.getUserName().equals(value)) {
						return true;
					}
					break;
				case PERSON_EMAIL:
					if(mode==SearchMode.AND && !p.getEmail().equals(value)) {
						return false;
					}else if(mode==SearchMode.OR && p.getEmail().equals(value)) {
						return true;
					}
					break;
			  case PERSON_PASSWORD:
				  if(mode==SearchMode.AND && !p.getPassword().equals(value)) {
					  return false;
				  } else if(mode==SearchMode.OR && p.getPassword().equals(value)){
					  return true; 
				  }
				  break; 
			 case PERSON_FIRST_NAME: 
				 if(mode==SearchMode.AND && !p.getFirstName().equals(value)) { 
					 return false;
				 }	else if(mode==SearchMode.OR && p.getFirstName().equals(value)) {
					 return true;
				 }
				 break; 
			  case PERSON_LAST_NAME: if(mode==SearchMode.AND && !p.getLastName().equals(value)) {
				  return false;
				  }else if(mode==SearchMode.OR && p.getLastName().equals(value)) {
					  return true;
				  }
				  break;
			  case PERSON_STATUS:
			  if(mode==SearchMode.AND &&
			  !p.getStatus().equals((Person.Status.valueOf(value.toString())))){ return
			  false; }else if(mode==SearchMode.OR &&
			  !p.getStatus().equals((Person.Status.valueOf(value.toString())))){ return
			  true; } break;
			 }
		}
		return true;
	}
	@Override
	public boolean match(Person p1, Person p2, SearchField[] attributeNames,SearchMode mode) {
		for(SearchField attNam:attributeNames) {
			switch(attNam){
			case PERSON_ID:
				if(mode==SearchMode.AND && (p1.getId()!=p2.getId())) {
					return false;
				}else if(mode==SearchMode.OR && p1.getId()==p2.getId()) {
					return true;
				}
				break;
			case PERSON_USER_NAME:
				if(mode==SearchMode.AND && !p1.getUserName().equals(p2.getUserName())) {
					return false;
				}else if(mode==SearchMode.OR && p1.getId()==p2.getId()) {
					return true;
				}
				break;
			case PERSON_EMAIL:
				if(mode==SearchMode.AND && !p1.getEmail().equals(p2.getEmail())) {
					return false;
				}else if(mode==SearchMode.OR && p1.getEmail().equals(p2.getEmail())) {
					return true;
				}
				break;
			case PERSON_PASSWORD:
				if(mode==SearchMode.AND && !p1.getPassword().equals(p2.getPassword())) {
					return false;
				}else if(mode==SearchMode.OR && p1.getPassword().equals(p2.getPassword())) {
					return true;
				}
				break;
			case PERSON_FIRST_NAME:
				if(mode==SearchMode.AND && !p1.getFirstName().equals(p2.getFirstName())) {
					return false;
				} else if(mode==SearchMode.OR && p1.getFirstName().equals(p2.getFirstName())) {
					return true;
				}
				break;
			case PERSON_LAST_NAME:
				if(mode==SearchMode.AND && !p1.getLastName().equals(p2.getLastName())) {
					return false;
				} else if(mode==SearchMode.OR && p1.getLastName().equals(p2.getLastName())) {
					return true;
				}
				break;
			case PERSON_STATUS:
				if(mode==SearchMode.AND && !p1.getStatus().equals(p2.getStatus())){
					return false;
				} else if(mode==SearchMode.OR && p1.getStatus().equals(p2.getStatus())){
					return true;
				}
				break;
			}
		}
		return true;
	}
	@Override
	public boolean change(Person target, Person model,SearchField[] attributeNames) {		
		int total=0;
		for(SearchField attNam:attributeNames) {
			switch(attNam) {
			case PERSON_USER_NAME:
				target.setUserName(model.getUserName());
				total++;
				break;
			case PERSON_EMAIL:
				target.setEmail(model.getEmail());
				total++;
				break;
			case PERSON_PASSWORD:
				target.setPassword(model.getPassword());
				total++;
				break;
			case PERSON_FIRST_NAME:
				target.setFirstName(model.getFirstName());
				total++;
				break;
			case PERSON_LAST_NAME:
				target.setLastName(model.getLastName());
				total++;
				break;
			case PERSON_STATUS:
				target.setStatus(model.getStatus());
				total++;
				break;
			default:
				break;
			}
		}
		return total==attributeNames.length;
	}
	@Override
	public boolean add(Person p) {
		Map<SearchField,Object> crit=new HashMap<>();
		crit.put(SearchField.PERSON_EMAIL, p.getEmail());
		crit.put(SearchField.PERSON_USER_NAME, p.getUserName());
		crit.put(SearchField.PERSON_FIRST_NAME,p.getFirstName());
		crit.put(SearchField.PERSON_LAST_NAME,p.getLastName());
		List<Person> duplis=search(crit,SearchMode.OR);
		if(duplis.isEmpty()) {
			peopleList.add(p);
			return true;
		}
		return false;
	}
	@Override
	public List<Person> search(String term) {
		Map<SearchField,Object> crit=new HashMap<>();
		crit.put(SearchField.PERSON_EMAIL, term);
		crit.put(SearchField.PERSON_USER_NAME, term);
		crit.put(SearchField.PERSON_FIRST_NAME, term);
		crit.put(SearchField.PERSON_LAST_NAME, term);
		return search(crit,SearchMode.OR);
	}
}
