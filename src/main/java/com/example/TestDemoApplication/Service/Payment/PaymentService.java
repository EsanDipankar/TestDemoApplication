package com.example.TestDemoApplication.Service.Payment;

import com.example.TestDemoApplication.Entity.PaymentInitiationResult;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private RazorpayClient client;

    public PaymentService() {
        try {
            this.client = new RazorpayClient(
                    "rzp_test_RpEJi4FEDdwZ2L",
                    "u5fxU7438dc6fubnquaUKU1M"
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize RazorpayClient", e);
        }
    }

    public PaymentInitiationResult initiatePayment(String orderId, String userId, Long amountInPaise) {
        try {
            // amountInPaise should be exact paise (e.g. 50000 = ₹500)
            JSONObject request = new JSONObject();
            request.put("amount", amountInPaise); // Already paise
            request.put("currency", "INR");
            request.put("receipt", orderId);
            request.put("payment_capture", 1);

            Order razorpayOrder = client.orders.create(request);

            return new PaymentInitiationResult(
                    true,
                    razorpayOrder.get("id"),
                    "Order Created Successfully"
            );

        } catch (Exception e) {
            return new PaymentInitiationResult(
                    false,
                    null,
                    "Error creating order: " + e.getMessage()
            );
        }
    }
}
