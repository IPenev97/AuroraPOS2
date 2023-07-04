package com.sistechnology.aurorapos2.core.fp_comm.legacy.utils;



import com.sistechnology.aurorapos2.core.utils.DateUtil;


/**
 * Created by MARIELA on 12.6.2017 г..
 */

public class PrinterFilter 
{
    public PickedDate dateFrom = new PickedDate();
    public PickedDate dateTo = new PickedDate();
    public String numberStart = "";
    public String numberEnd = "";

    public String cashInOut = "";

    
    public String getCashInOut() { return cashInOut; }
    public void setCashInOut(String cashInOut) { this.cashInOut = cashInOut; }

    
    public PickedDate getDateFrom() { return dateFrom; }
    public void setDateFrom(PickedDate dateFrom) { this.dateFrom = dateFrom; }

    
    public PickedDate getDateTo() { return dateTo; }
    public void setDateTo(PickedDate dateTo) { this.dateTo = dateTo; }

    
    public String getNumberStart() { return numberStart; }
    public void setNumberStart(String numberStart) { this.numberStart = numberStart; }
    
    public String getNumberEnd() { return numberEnd; }
    public void setNumberEnd(String numberEnd) { this.numberEnd = numberEnd; }

    public PrinterFilter()
    {
        dateFrom.setDt(DateUtil.Companion.getCurrentDateAsString());
        dateTo.setDt(DateUtil.Companion.getCurrentDateAsString());
        cashInOut = "";
    }
    public void Clear()
    {
        dateFrom.setDt(DateUtil.Companion.getCurrentDateAsString());
        dateTo.setDt(DateUtil.Companion.getCurrentDateAsString());
        cashInOut = "";
    }
}
