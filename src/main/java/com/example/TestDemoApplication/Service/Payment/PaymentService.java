package com.example.TestDemoApplication.Service.Payment;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private RazorpayClient client;

    public PaymentService() throws Exception {
        client = new RazorpayClient(
                "rzp_test_RpEJi4FEDdwZ2L",
                "u5fxU7438dc6fubnquaUKU1M"
        );
    }

    public String initiatePayment(String orderId, String userId,double amount) {
        try {
            // Create Payment Order body
            JSONObject paymentRequest = new JSONObject();
            paymentRequest.put("amount", amount*100);   // ₹500.00 (amount must be in paise)
            paymentRequest.put("currency", "INR");
            paymentRequest.put("receipt", orderId);
            paymentRequest.put("payment_capture", 1); // auto capture

            // Calling Razorpay to create order
            Order razorpayOrder = client.orders.create(paymentRequest);

            // Extract Razorpay orderId
            String razorpayOrderId = razorpayOrder.get("id");

            System.out.println("Razorpay Order Created: " + razorpayOrderId);

            // In real project → save this razorpayOrderId in DB with your orderId

            return "SUCCESS";  // if order creation is successful

        } catch (Exception e) {
            e.printStackTrace();
            return "FAILED";
        }
    }
}
