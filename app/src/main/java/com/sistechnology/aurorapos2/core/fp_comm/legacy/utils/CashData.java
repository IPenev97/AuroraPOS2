package com.sistechnology.aurorapos2.core.fp_comm.legacy.utils;

public class CashData
{
    public String dateTime = "";

    public int closure; // already written
    public VAT_SUMS vatSumsNetto = new VAT_SUMS();
    public VAT_SUMS vatSumsBrutto = new VAT_SUMS();
    public VAT_SUMS vatSumsStorno = new VAT_SUMS();
    public double allBrutto;
    public int clients;
    public int discounts;
    public double discountsSum;
    public int surcharges;
    public double surchargesSum;
    public double paymentsCash;
    public double paymentsCheque;
    public double paymentsAccount;
    public double paymentsCredit;
    public double paymentsCashIn;
    public double paymentsCashOut;
    public double cashAvail;
    public String date;
    public String serial;
    public int sendStatus;                 // 4 - изпратен

    public String time = "";
    public String fiscalMemory = "";
    public int numStorno;

    public void Clear()
    {
        dateTime = "";

        closure = 0;
        vatSumsNetto.Clear();
        vatSumsBrutto.Clear();
        vatSumsStorno.Clear();
        allBrutto = 0;
        clients = 0;
        discounts = 0;
        discountsSum = 0;
        surcharges = 0;
        surchargesSum = 0;
        paymentsCash = 0;
        paymentsCheque = 0;
        paymentsAccount = 0;
        paymentsCredit = 0;
        paymentsCashIn = 0;
        paymentsCashOut = 0;
        cashAvail = 0;
        date = "";
        serial = "";
        sendStatus = 0;                 // 4 - изпратен
        time = "";
        fiscalMemory = "";
        numStorno = 0;
    }
}
