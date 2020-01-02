package employee;

public class SalariedEmployee extends Employee {
    private double salary;
    
    public SalariedEmployee(String fname, String lname, String ssn, double salary){
        super(fname, lname, ssn);
        setSalary(salary);
    }
    
    private void setSalary(double newSalary){
        if (newSalary >= 0){
            this.salary = newSalary;
        }
    }
    
    public double getSalary(){return this.salary;}
    
    private double calculateEarnings(){return this.salary;}
    
    @Override
    public String toString(){
        return String.format("Salaried Employee: %s %s with ssn: %s\n    Salary: %.2f\n    Earnings: $%.2f\n", super.getFName(), super.getLName(), super.getSSN(), this.salary, calculateEarnings());
    }
}
