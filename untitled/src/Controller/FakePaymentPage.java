package Controller;

import Vue.PaymentVue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

public  class FakePaymentPage extends JFrame{
    private JTextField cardNumberField;
    private JPasswordField cvvField;
    private JTextField expiryDateField;
    private double prixAPayer;
    private boolean paymentSuccessful;

    public FakePaymentPage(double prixAPayer) {
        this.prixAPayer = prixAPayer;

        new PaymentVue();
    }



    public boolean isPaymentSuccessful() {
        return paymentSuccessful;
    }



}