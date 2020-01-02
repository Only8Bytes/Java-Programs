package employee;

public class CommissionEmployee extends Employee {
    private double sales;
    private double commissionRate;
    
    public CommissionEmployee(String fname, String lname, String ssn, double sales, double commissionRate){
        super(fname, lname, ssn);
        setGrossSales(sales);
        setCommissionRate(commissionRate);
    }
    
    protected void setGrossSales(double newSales){
        if (newSales >= 0){
            this.sales = newSales;
        }
    }
    
    protected void setCommissionRate(double newRate){
        if (newRate >= 0){
            this.commissionRate = newRate;
        }
    }
    
    public double getCommissionRate(){return this.commissionRate;}
    public double getGrossSales(){return this.sales;}
    
    private double calculateEarnings(){
        return this.sales * this.commissionRate;
    }
    
    @Override
    public String toString(){
        return String.format("Commissioned Employee: %s %s with ssn: %s\n    Gross Sales: %.2f\n    Commission Rate: %.2f\n    Earnings: $%.2f\n", super.getFName(), super.getLName(), super.getSSN(), getGrossSales(), getCommissionRate(), calculateEarnings());
    }
}
