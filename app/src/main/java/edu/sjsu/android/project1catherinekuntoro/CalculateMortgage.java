package edu.sjsu.android.project1catherinekuntoro;

public class CalculateMortgage {

    private double principle;
    private double interest;
    private int years;

    public CalculateMortgage(double principle, double interest, int years) {
        this.principle = principle;
        this.interest = interest;
        this.years = years;

    }

    double calculateMortgage(boolean addTaxAndInsurance) {
        int monthsOfLoan = this.getYears() * 12; //N variable
        double result = 0;

        if (getInterest() == 0) {
            result = (getPrinciple() / monthsOfLoan); //P/N

        } else {
            double monthlyInterest = (getInterest()/100) / 12; //J variable
            double numerator = getPrinciple()*monthlyInterest;
            double denominator = 1 - (Math.pow( (1+monthlyInterest) , (-monthsOfLoan) ));

            result = numerator/denominator;
        }

        if(addTaxAndInsurance){
            double taxAndInsurance = (0.1/100) * getPrinciple(); //T variable = 0.1*Principle
            return result + taxAndInsurance;
        } else {
            return result;
        }

    }
    //Getters
    public double getPrinciple() {
        return principle;
    }

    public double getInterest() {
        return interest;
    }

    public int getYears() {
        return years;
    }


}
