package com.sistechnology.aurorapos2.core.fp_comm.legacy.utils;

import android.graphics.drawable.Drawable;
import android.view.View;



import com.sistechnology.aurorapos2.R;
import com.sistechnology.aurorapos2.App;


/**
 * Created by MARIELA on 3.10.2016 г..
 */
public class ClientDataProvider extends IdNameData {
    public ClientDataProvider(String name) {
        this.setName(name);

    }

    public ClientDataProvider(long id, String name) {
        super(id, name);
    }

    public ClientDataProvider()
    {
        setName(App.context.getString(R.string.service));
        setEikType(0);
        setRegNo("999999999");
    }

    public String posCode;
    public String regNo;
    public String address;
    public String phone;
    public String email;
    public String note;
    public String fax;
    public String gln;
    public String externalCode;
    public String VATNumber;
    public boolean flagVATRegistered;
    public boolean flagInternalCustomer;
    public int eikType; // 0 - ЕИК. 1 - ЕГН, 2 - ЛНЧ, 3 - други

 
 


  

    public boolean expanded = false;

 

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Drawable icon;

    

    public void Blank() {
        setId(0);
        setName("");

        posCode = "";
        regNo = "";
        address = "";
        phone = "";
        email = "";
        note = "";
        fax = "";
        gln = "";
        externalCode = "";
        VATNumber = "";
        flagVATRegistered = false;
        flagInternalCustomer = false;
        eikType = 0; // ЕИК
        
    }

    public void CopyFrom(ClientDataProvider client) {
        if (client == null)
            return;

        super.CopyFrom(client);
        setPosCode(client.posCode);
        setRegNo(client.regNo);
        setEmail(client.email);
        setPhone(client.phone);
        setAddress(client.address);
        setExternalCode(client.externalCode);
        setFax(client.fax);
        setGln(client.gln);
        setVATNumber(client.getVATNumber());
        this.flagInternalCustomer = client.flagInternalCustomer;
        this.flagVATRegistered = client.flagVATRegistered;
        this.eikType = client.eikType;
        setNote(client.note);
    }


    public int getEikType() {
        return eikType;
    }

    public void setEikType(int eikType) {
        this.eikType = eikType;
    }

    public void setEikType0(View v) {
        this.eikType = 0;
    }

    public void setEikType1(View v) {
        this.eikType = 1;
    }

    public void setEikType2(View v) {
        this.eikType = 2;
    }

    public void setEikType3(View v) {
        this.eikType = 3;
    }

    public String getEikTypeForLegal() {
        return (eikType == 0 ? "true" : "false");
    }

    public void setEikTypeFromLegal(String legal) {
        this.eikType = (legal.equals("false") ? 1 : 0);
    }


    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }
    
    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    
    public String getGln() {
        return gln;
    }

    public void setGln(String gln) {
        this.gln = gln;
    }

    
    public String getExternalCode() {
        return externalCode;
    }

    public void setExternalCode(String externalCode) {
        this.externalCode = externalCode;
    }

    
    public String getVATNumber() {
        return VATNumber;
    }

    public void setVATNumber(String VATNumber) {
        this.VATNumber = VATNumber;
    }


    
    public boolean getCurrent() {
        return current;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public boolean current;










    
    public boolean isShowButton() {
        return showEditButton;
    }

    public void setShowEditButton(boolean showEditButton) {
        this.showEditButton = showEditButton;
    }

    public boolean showEditButton = false;

    
    public boolean isShowChooseButton() {
        return showChooseButton;
    }

    public void setShowChooseButton(boolean showChooseButton) {
        this.showChooseButton = showChooseButton;
    }

    public boolean showChooseButton = false;

    public void setShowOKButton(boolean showOKButton) {
        this.showOKButton = showOKButton;
    }

    
    public boolean isShowOKButton() {
        return showOKButton;
    }

    public boolean showOKButton = false;



}