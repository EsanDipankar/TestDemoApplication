package com.example.TestDemoApplication.Service.Payment;

import com.example.TestDemoApplication.Entity.PaymentInitiationResult;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.logging.ErrorManager;

@Service

public class PaymentService {

    private RazorpayClient client;

    public PaymentService() throws Exception {
        client = new RazorpayClient(
                "rzp_test_RpEJi4FEDdwZ2L",
                "u5fxU7438dc6fubnquaUKU1M"
        );
    }

    public PaymentInitiationResult initiatePayment(String orderId, String userId, long amountPaise) {

        try {
            JSONObject paymentRequest = new JSONObject();
            paymentRequest.put("amount", amountPaise);      // e.g. 50000 = ₹500
            paymentRequest.put("currency", "INR");
            paymentRequest.put("receipt", orderId);
            paymentRequest.put("payment_capture", 1);

            // Create order on Razorpay
            Order razorpayOrder = client.orders.create(paymentRequest);

            // Return success object
            return new PaymentInitiationResult(true, razorpayOrder.get("id"), "Order Created");

        } catch (Exception e) {
            return new PaymentInitiationResult(false, null, "Error creating order: " + e.getMessage());
        }
    }

}
