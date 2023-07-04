package com.sistechnology.aurorapos2.core.fp_comm.legacy;


import android.util.Log;

/**
 * Created by MARIELA on 5.5.2017 г..
 */

public class StatusFP
{
    public byte[] statusBytes;
    private String TAG;

    public void initStatusBytes() { statusBytes = new byte[6]; }

    public StatusFP() { initStatusBytes(); }

    public boolean getBit(int byteNo, int position) {return ((statusBytes[byteNo] >> position) & 1) == 1;}

    public boolean COMMON_ERROR          (){ return getBit(0, 5);}
    public boolean FP_ERROR              (){ return getBit(0, 4);}
    public boolean NO_DISPLAY            (){ return getBit(0, 3);}
    public boolean NO_DATETIME           (){ return getBit(0, 2);}
    public boolean INVALID_CMD           (){ return getBit(0, 1);}
    public boolean SYNTAX_ERROR          (){ return getBit(0, 0);}

    public boolean WRONG_PASSWORD        (){ return getBit(1, 6);}
    public boolean ERROR_CUTTER          (){ return getBit(1, 5);}
    public boolean RAM_NULL              (){ return getBit(1, 2);}
    public boolean WRONG_CMD             (){ return getBit(1, 1);}
    public boolean OVERFLOW              (){ return getBit(1, 0);}

    public boolean PRINT_READY           (){ return getBit(2, 6);}
    public boolean NON_FISCAL_OPENED     (){ return getBit(2, 5);}
    public boolean LOW_PAPER_KL          (){ return getBit(2, 4);}
    public boolean FISCAL_OPENED         (){ return getBit(2, 3);}
    public boolean NO_PAPER_KL           (){ return getBit(2, 2);}
    public boolean LOW_PAPER             (){ return getBit(2, 1);}
    public boolean NO_PAPER              (){ return getBit(2, 0);}

    public boolean ERROR6                (){ return getBit(3, 6);}
    public boolean ERROR5                (){ return getBit(3, 5);}
    public boolean ERROR4                (){ return getBit(3, 4);}
    public boolean ERROR3                (){ return getBit(3, 3);}
    public boolean ERROR2                (){ return getBit(3, 2);}
    public boolean ERROR1                (){ return getBit(3, 1);}
    public boolean ERROR0                (){ return getBit(3, 0);}

    public boolean COMMON_ERROR45        (){ return getBit(4, 5);}
    public boolean MEMORY_FULL           (){ return getBit(4, 4);}
    public boolean MEMORY_ALMOST_FULL    (){ return getBit(4, 3);}
    public boolean WRONG_RECORD_IN_MEMORY(){ return getBit(4, 2);}
    public boolean ERROR_TT              (){ return getBit(4, 1);}
    public boolean ERROR_WRITING_MEMORY  (){ return getBit(4, 0);}

    public boolean MEMORY_READY          (){ return getBit(5, 6);}
    public boolean SERIAL_PROG           (){ return getBit(5, 5);}
    public boolean TAX_PROG              (){ return getBit(5, 4);}
    public boolean FISCALIZED            (){ return getBit(5, 3);}
    public boolean READONLY_MEMORY       (){ return getBit(5, 0);}

    public boolean BATTERY_LOW           (){ return false;} // todo


    public boolean getError() {
        return COMMON_ERROR() || FP_ERROR() || NO_DATETIME() || INVALID_CMD() || SYNTAX_ERROR() || WRONG_PASSWORD() || ERROR_CUTTER() || RAM_NULL() || WRONG_CMD() || OVERFLOW() ||
               LOW_PAPER() || NO_PAPER() || MEMORY_FULL() || WRONG_RECORD_IN_MEMORY() || ERROR_TT() || ERROR_WRITING_MEMORY() || READONLY_MEMORY() ||
                ERROR0() || ERROR1() || ERROR2() || ERROR3() || ERROR4() || ERROR5() || ERROR6() ;
    }

    public String getStatusText() {
        String error = "";

        if(COMMON_ERROR()) error += "COMMON_ERROR ";
        if(FP_ERROR()) error += "FP_ERROR ";
        if(NO_DATETIME()) error+= "NO_DATETIME ";
        if(INVALID_CMD()) error+= "INVALID_CMD ";
        if(SYNTAX_ERROR()) error+= "SYNTAX_ERROR ";
        if(WRONG_PASSWORD()) error+= "WRONG_PASSWORD ";
        if(ERROR_CUTTER()) error+= "ERROR_CUTTER ";
        if(RAM_NULL()) error+= "RAM_NULL ";
        if(WRONG_CMD()) error+= "WRONG_CMD ";
        if(OVERFLOW()) error+= "OVERFLOW ";
        if(LOW_PAPER()) error+= "LOW_PAPER ";
        if(NO_PAPER()) error+= "NO_PAPER ";
        if(MEMORY_FULL()) error+= "MEMORY_FULL ";
        if(WRONG_RECORD_IN_MEMORY()) error+= "WRONG_RECORD_IN_MEMORY ";
        if(ERROR_TT()) error+= "ERROR_TT ";
        if(ERROR_WRITING_MEMORY()) error+= "ERROR_WRITING_MEMORY ";
        if(READONLY_MEMORY()) error+= "READONLY_MEMORY ";
        if(ERROR0()) error+= "ERROR0 ";
        if(ERROR0()) error+= "ERROR1 ";
        if(ERROR2()) error+= "ERROR2 ";
        if(ERROR3()) error+= "ERROR3 ";
        if(ERROR4()) error+= "ERROR4 ";
        if(ERROR5()) error+= "ERROR5 ";
        if(ERROR6()) error+= "ERROR6 ";

        Log.e(TAG, error);

        return error;
    }

    public boolean isFiscalOpened() { return FISCAL_OPENED(); }
    public boolean isReceiptOpened() { return FISCAL_OPENED() || NON_FISCAL_OPENED(); }
    public boolean isPaymentStarted() { return false; }

    public boolean readStatusBytes(){ return true;} // do not set for non N5 FP
}
