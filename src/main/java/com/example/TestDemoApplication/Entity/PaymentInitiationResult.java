package com.example.TestDemoApplication.Entity;

public class PaymentInitiationResult {
    private boolean success;
    private String razorpayOrderId;
    private String message;

    public PaymentInitiationResult() {}

    public PaymentInitiationResult(boolean success, String razorpayOrderId, String message) {
        this.success = success;
        this.razorpayOrderId = razorpayOrderId;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getRazorpayOrderId() { return razorpayOrderId; }
    public void setRazorpayOrderId(String razorpayOrderId) { this.razorpayOrderId = razorpayOrderId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

