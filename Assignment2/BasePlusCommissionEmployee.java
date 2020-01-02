package employee;

public class BasePlusCommissionEmployee extends CommissionEmployee {
    private double baseSalary;
    
    public BasePlusCommissionEmployee(String fname, String lname, String ssn, double sales, double commissionRate, float baseSalary){
        super(fname, lname, ssn, sales, commissionRate);
        super.setGrossSales(sales);
        super.setCommissionRate(commissionRate);
        setBaseSalary(baseSalary);
    }
    
    private void setBaseSalary(float newSalary){
        if (newSalary >= 0){
            this.baseSalary = newSalary;
        }
    }
    
    public double getBaseSalary(){return this.baseSalary;}
    
    private double calculateEarnings(){
        return this.baseSalary + super.getGrossSales() * super.getCommissionRate();
    }
    
    @Override
    public String toString(){
        return String.format("Base Salary Plus Commissioned Employee: %s %s with ssn: %s\n    Gross Sales: %.2f\n    Commission Rate: %.2f\n    with Base Salary of: $%.2f\n    Earnings: $%.2f\n", super.getFName(), super.getLName(), super.getSSN(), super.getGrossSales(), super.getCommissionRate(), this.baseSalary, calculateEarnings());
    }
}