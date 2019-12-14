package biz;

public class Job {
	public static Job createJob(String companyName,String title,String description,String requiredQualifications[],String requiredAbilities[]) {
		Job newJob=new Job();
		newJob.setCompanyName(companyName);
		newJob.setTitle(title);
		newJob.setDescription(description);
		newJob.setRequiredQualifications(requiredQualifications);
		newJob.setRequiredAbilities(requiredAbilities);
		return newJob;
	}
	public String[] getRequiredQualifications() {
		return requiredQualifications;
	}
	public void setRequiredQualifications(String requiredQualifications[]) {
		this.requiredQualifications = requiredQualifications;
	}
	private static int nextId=1;
	private int id;
	private String companyName,title,description,requiredQualifications[],requiredAbilities[];
	private Job() {
		super();
		this.id=nextId++;
	}
	public static int getNextId() {
		return nextId;
	}
	public static void setNextId(int nextId) {
		Job.nextId = nextId;
	}
	public int getId() {
		return id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String[] getRequiredAbilities() {
		return requiredAbilities;
	}
	public void setRequiredAbilities(String[] requiredAbilities) {
		this.requiredAbilities = requiredAbilities;
	}
}
