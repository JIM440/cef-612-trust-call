package slee.reporting;

public class ReportingSbb {

    public void onReport(String phoneNumber, String reason) {
        System.out.println("Reporting SBB received report for: " + phoneNumber + " reason: " + reason);
    }
}
