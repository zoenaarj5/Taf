package dax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import biz.Job;
import search.SearchField;
import search.SearchMode;

public class JobsAx implements DataAx<Job> {
	private static JobsAx uniqueInstance=new JobsAx();
	public static JobsAx getUniqueInstance() {
		return uniqueInstance;
	}

	public List<Job> getJobList() {
		return jobList;
	}

	private JobsAx() {
		super();
		jobList=new ArrayList<Job>();
		jobList.add(Job.createJob(
				"TOYOTA", "Manager", "Experienced manager who can organize and direct more than 300 people in our plant.", 
				new String[]{
					"Master in communication",
					"Master in business administration"
				}, new String[]{
					"5 years of experience on a similar post",
					"Good references"
				})
			);
		jobList.add(Job.createJob(
				"IBM", "Business Analyst", "This is a good opportunity for a trained analyst who can grow with the company", 
				new String[]{
					"Master in Project Management",
					"Bachelor in Computer Science"
				}, new String[]{
					"2 years of experience as analyst / programmer",
					"Fluent English and French / Dutch"
				})
			);
		jobList.add(Job.createJob(
				"Microsoft Belgium", ".NET developer", "We need a really, really good developer who is ready to work in an ever-evolving environment", 
				new String[]{
					"Bachelor in Computer Science"
				}, new String[]{
					"Fluent English, basic French / Dutch",
					"Available immediately"
				})
			);
		jobList.add(Job.createJob(
				"STIB", "Bus Driver", "If you like driving and being in contact with clients, we want you!", 
				new String[]{
					"Driving licence (B) Possible training if no licence for bus",
					"Certificate of good conduct"
				}, new String[]{
					"Experience as a driver (truck, taxi, bus...) is a plus"
				})
			);
	}
	private List<Job> jobList;
	@Override
	public List<Job> fetchAll() {
		// TODO Auto-generated method stub
		return jobList;
	}

	@Override
	public List<Job> search(Map<SearchField, Object> criteria,SearchMode mode) {
		final List<Job> foundList=new ArrayList<>();
		jobList.stream().forEach(
				(Job j)->{
				if(match(j,criteria,mode)) {
					foundList.add(j);
				}
			}
		);
		return foundList;
	}

	@Override
	public boolean delete(Job j) {
		for(Job job:jobList) {
			if(job.getId()==j.getId()) {
				jobList.remove(job);
				return true;
			}
		}
		return false;
	}

	@Override
	public int delete(Map<SearchField, Object> criteria,SearchMode mode) {
		int deleted=0;
		for(Job job:jobList) {
			if(match(job,criteria,mode)) {
				jobList.remove(job);
				deleted++;
			}
		}
		return deleted;
	}

	@Override
	public boolean update(Job j) {
		for(Job job:jobList) {
			if(job.getId()==j.getId()) {
				job.setCompanyName(j.getCompanyName());
				job.setTitle(j.getTitle());
				job.setDescription(j.getDescription());
				job.setRequiredQualifications(j.getRequiredQualifications());
				job.setRequiredAbilities(j.getRequiredAbilities());
				return true;
			}
		}
		return false;
	}

	@Override
	public int update(Job j, SearchField[] attributeNames) {
		int updated=0;
		for(Job job:jobList) {
			if(match(job,j,attributeNames,SearchMode.AND) && change(job,j,attributeNames)) {
				updated++;
			}
		}
		return updated;
	}

	@Override
	public boolean match(Job j, Map<SearchField, Object> criteria,SearchMode mode) {
		int matchingCrits=0;
		for(Entry<SearchField,Object> es:criteria.entrySet()) {
			Object value=es.getValue();
			switch(es.getKey()) {

				case JOB_ID:
					if(Integer.valueOf(j.getId()).equals((Integer)value)) {
						if(mode==SearchMode.OR) {
							return true;
						}
						matchingCrits++;
					}
					break;
				case JOB_COMPANY_NAME:
					if(j.getCompanyName().contains((String)value)){
						if(mode==SearchMode.OR) {
							return true;
						}
						matchingCrits++;
					}
					break;
				case JOB_TITLE:
					if(j.getTitle().contains((String)value)){
						if(mode==SearchMode.OR) {
							return true;
						}
						matchingCrits++;
					}
					break;
				case JOB_DESCRIPTION:
					String target=j.getDescription(), model=(String)value;
					if(target.contains(model) || model.contentEquals(target)){
						if(mode==SearchMode.OR) {
							return true;
						}
						matchingCrits++;
					}
					break;
				case JOB_REQUIRED_QUALIFICATIONS:
					String[] rqus=j.getRequiredQualifications();
					String[] qus=(String[])value;
					int cptOKs=0;
					for(String rqu:rqus) {
						for(String qu:qus) {
							if(rqu.contains(qu) || qu.contains(rqu)) {
								cptOKs++;
								break;
							}
						}
					}
					if(cptOKs>=rqus.length) {
						if(mode==SearchMode.OR) {
							return true;
						}
						matchingCrits++;
					}
					break;
				case JOB_REQUIRED_ABILITIES:
					String[] rabs=j.getRequiredAbilities();
					String[] abs=(String[])value;
					int oks=0;
					for(String rab:rabs) {
						for(String ab:abs) {
							if(rab.contains(ab) || ab.contains(rab)) {
								oks++;
								break;
							}
						}
					}
					if(oks>=rabs.length) {
						if(mode==SearchMode.OR) {
							return true;
						}
						matchingCrits++;
					}
					break;
			}
		}
		if(matchingCrits>=criteria.entrySet().size()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean match(Job j1, Job j2, SearchField[] attributeNames,SearchMode mode) {
		int matchingCrits=0;
		for(SearchField an:attributeNames) {
			switch(an) {

			case JOB_ID:
				if(j1.getId()==j2.getId()) {
					if(mode==SearchMode.OR) {
						return true;
					}
					matchingCrits++;
				}
				break;
			case JOB_COMPANY_NAME:
				if(j1.getCompanyName().equals(j2.getCompanyName())){
					if(mode==SearchMode.OR) {
						return true;
					}
					matchingCrits++;
				}
				break;
			case JOB_TITLE:
				if(j1.getCompanyName().equals(j2.getCompanyName())){
					if(mode==SearchMode.OR) {
						return true;
					}
					matchingCrits++;
				}
				break;
			case JOB_DESCRIPTION:
				String target=j1.getDescription(), model=j2.getDescription();
				if(target.contains(model) || model.contentEquals(target)){
					if(mode==SearchMode.OR) {
						return true;
					}
					matchingCrits++;
				}
				break;
			case JOB_REQUIRED_QUALIFICATIONS:
				String[] rqus=j1.getRequiredQualifications();
				String[] qus=j2.getRequiredQualifications();
				int cptOKs=0;
				for(String rqu:rqus) {
					for(String qu:qus) {
						if(rqu.contains(qu) || qu.contains(rqu)) {
							cptOKs++;
							break;
						}
					}
				}
				if(cptOKs>=rqus.length) {
					if(mode==SearchMode.OR) {
						return true;
					}
					matchingCrits++;
				}
				break;
			case JOB_REQUIRED_ABILITIES:
				String[] rabs=j1.getRequiredAbilities();
				String[] abs=j2.getRequiredAbilities();
				int oks=0;
				for(String rab:rabs) {
					for(String ab:abs) {
						if(rab.contains(ab) || ab.contains(rab)) {
							oks++;
							break;
						}
					}
				}
				if(oks>=rabs.length) {
					if(mode==SearchMode.OR) {
						return true;
					}
					matchingCrits++;
				}
				break;
			 
			}
		}
		return matchingCrits>=attributeNames.length;
	}

	@Override
	public boolean change(Job j1, Job j2,SearchField[] attributeNames) {
		int matchingCrits=0;
		for(SearchField an:attributeNames) {
			switch(an) {
			case JOB_COMPANY_NAME:
				if(j1.getCompanyName().equals(j2.getCompanyName())){
					matchingCrits++;
				}
				break;
			case JOB_TITLE:
				if(j1.getCompanyName().equals(j2.getCompanyName())){
					matchingCrits++;
				}
				break;
			case JOB_DESCRIPTION:
				String target=j1.getDescription(), model=j2.getDescription();
				if(target.contains(model) || model.contentEquals(target)){
					matchingCrits++;
				}
				break;
			case JOB_REQUIRED_QUALIFICATIONS:
				String[] rqus=j1.getRequiredQualifications();
				String[] qus=j2.getRequiredQualifications();
				int cptOKs=0;
				for(String rqu:rqus) {
					for(String qu:qus) {
						if(rqu.contains(qu) || qu.contains(rqu)) {
							cptOKs++;
							break;
						}
					}
				}
				if(cptOKs>=rqus.length) {
					matchingCrits++;
				}
				break;
			case JOB_REQUIRED_ABILITIES:
				String[] rabs=j1.getRequiredAbilities();
				String[] abs=j2.getRequiredAbilities();
				int oks=0;
				for(String rab:rabs) {
					for(String ab:abs) {
						if(rab.contains(ab) || ab.contains(rab)) {
							oks++;
							break;
						}
					}
				}
				if(oks>=rabs.length) {
					matchingCrits++;
				}
				break;
			}
		}
		return matchingCrits>=attributeNames.length;
	}

	@Override
	public boolean add(Job j) {
		for(Job job:jobList) {
			if(match(job,j,new SearchField[] {SearchField.JOB_ID},SearchMode.AND)) {
				return false;
			}
		}
		jobList.add(j);
		return true;
	}

	@Override
	public List<Job> search(String term) {
		Map<SearchField,Object> crit=new HashMap<>();
		crit.put(SearchField.JOB_COMPANY_NAME,term);
		crit.put(SearchField.JOB_TITLE,term);
		crit.put(SearchField.JOB_DESCRIPTION,term);
		crit.put(SearchField.JOB_REQUIRED_QUALIFICATIONS,term);
		crit.put(SearchField.JOB_REQUIRED_ABILITIES,term);
		return search(crit,SearchMode.OR);
	}
}
