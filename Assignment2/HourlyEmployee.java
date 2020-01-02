package employee;

public class HourlyEmployee extends Employee {
    private double hourlyWage;
    private float hoursWorked;
            
    public HourlyEmployee(String fname, String lname, String ssn, double hourlyWage, float hoursWorked){
        super(fname, lname, ssn);
        setHourlyWage(hourlyWage);
        setHoursWorked(hoursWorked);
    }
    
    private void setHoursWorked(float newHours){
        if ((newHours >= 1) && (newHours <= 168)){
            this.hoursWorked = newHours;
        }
    }
    
    private void setHourlyWage(double newWage){
        if (newWage > 0){
            this.hourlyWage = newWage;
        }
    }
    
    public float getHoursWorked(){return this.hoursWorked;}
    public double getHourlyWage(){return this.hourlyWage;}
    
    private double calculateEarnings(){
        double earnings;
        if (this.hoursWorked > 40){
            earnings = 40 * hourlyWage;
            earnings += hourlyWage * 1.5 * (hoursWorked - 40);
        } else {
            earnings = hourlyWage * hoursWorked;
        }
        return earnings;
    }
    
    @Override
    public String toString(){
        return String.format("Hourly Employee: %s %s with ssn: %s\n    Hourly Wage: %.2f\n    Hours Worked: %.2f\n    Earnings: $%.2f\n", super.getFName(), super.getLName(), super.getSSN(), this.hourlyWage, this.hoursWorked, calculateEarnings());
    }
}
