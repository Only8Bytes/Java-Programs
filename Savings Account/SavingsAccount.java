package savingsaccount;

public class SavingsAccount {
    public static float annualInterestRate;
    private float savingsBalance;
    
    public float calculateMonthlyInterest(){
        float monthlyInterest = this.savingsBalance * annualInterestRate/12;
        savingsBalance += monthlyInterest;
        return savingsBalance;
    }
    
    public static void setInterestRate(float rate){
        annualInterestRate = rate;
    }
    
    private void setSavingsBalance(float savings){
        this.savingsBalance = savings;
    }
    
    private float getSavingsBalance(){
        return this.savingsBalance;
    }
    
    public static void main(String[] args) {
        SavingsAccount saver1 = new SavingsAccount();
        SavingsAccount saver2 = new SavingsAccount();
        saver1.setSavingsBalance((float) 2000);
        saver2.setSavingsBalance((float) 3000);
        SavingsAccount.setInterestRate((float) 0.04);
        System.out.printf("%-8s%-8s%-8s", "Month", "Saver1", "Saver2");
        System.out.println();
        for (int i = 1; i <= 12; i++){
            saver1.calculateMonthlyInterest();
            saver2.calculateMonthlyInterest();
            System.out.printf("%-8d%-8.2f%-8.2f", i, saver1.getSavingsBalance(), saver2.getSavingsBalance());
            System.out.println();
        }
        SavingsAccount.setInterestRate((float) 0.05);
        saver1.calculateMonthlyInterest();
        saver2.calculateMonthlyInterest();
        System.out.printf("%-8d%-8.2f%-8.2f", 13, saver1.getSavingsBalance(), saver2.getSavingsBalance());
        System.out.println();
    }
}