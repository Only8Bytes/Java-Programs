package employee;

public class Employee {
    
    private String ssn;
    private String fname;
    private String lname;
    
    /**
     *
     * @param fname
     * @param lname
     * @param ssn
     */
    public Employee(String fname, String lname, String ssn){
        this.fname = fname;
        this.lname = lname;
        this.ssn = ssn;
    }
    
    protected String getFName(){return this.fname;}
    protected String getLName(){return this.lname;}
    protected String getSSN(){return this.ssn;}
    
    public static void main(String[] args) {
        CommissionEmployee employee1 = new CommissionEmployee("Fred", "Jones", "111-11-1111", 2000.0, .05);
        BasePlusCommissionEmployee employee2 = new BasePlusCommissionEmployee("Sue", "Smith", "222-22-2222", 3000.0, .05, 300);
        SalariedEmployee employee3 = new SalariedEmployee("Shan", "Yang", "333-33-3333", 1150.0);
        HourlyEmployee employee4 = new HourlyEmployee("Ian", "Tanning", "444-44-4444", 15.0, 50);
        HourlyEmployee employee5 = new HourlyEmployee("Angela", "Domchek", "555-55-5555", 20.0, 40);
        System.out.printf("%s%s%s%s%s", employee1, employee2, employee3, employee4, employee5);
    }
}