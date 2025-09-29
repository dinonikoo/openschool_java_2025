package banking.utils;
import banking.model.PaymentRegistry;
import banking.model.ProductRegistry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreditScheduleCalculator {

    public static List<PaymentRegistry> buildSchedule(
            Double creditAmount,
            Double annualRate,
            Integer months,
            ProductRegistry registry
    ) {
        List<PaymentRegistry> schedule = new ArrayList<>();

        double monthlyRate = annualRate / 12 / 100.0;
        double annuityPayment = creditAmount * (
                (monthlyRate * Math.pow(1 + monthlyRate, months)) /
                        (Math.pow(1 + monthlyRate, months) - 1)
        );

        double remainingDebt = creditAmount;

        for (int m = 1; m <= months; m++) {
            double interest = remainingDebt * monthlyRate;
            double principal = annuityPayment - interest;
            remainingDebt -= principal;

            PaymentRegistry payment = new PaymentRegistry();
            payment.setProductRegistry(registry);
            payment.setPaymendDate(LocalDate.now().plusMonths(m));
            payment.setAmount(annuityPayment);
            payment.setInterestRateAmount(interest);
            payment.setDebtAmount(principal);
            payment.setExpired(false);
            payment.setPaymentExpirationDate(LocalDate.now().plusMonths(m));
            schedule.add(payment);
        }

        return schedule;
    }
}
