package com.sistechnology.aurorapos2.core.fp_comm.legacy.Datecs;

/**
 * Created by MARIELA on 5.5.2017 г..
 */

public class DatecsDP25xStatusFP extends DatecsStatusFP
{
    @Override
    public boolean ERROR_TT              (){ return false;}

    @Override
    public boolean ERROR_TT_UNSENT       (){ return false;}

    @Override
    public boolean WRONG_RECORD_IN_MEMORY(){ return false;}

    @Override
    public boolean MEMORY_READY          (){ return true;}
    @Override
    public boolean SERIAL_PROG           (){ return getBit(4, 2);}
    @Override
    public boolean READONLY_MEMORY       (){ return false;}

    @Override
    public boolean getError() {
        return super.getError() || ERROR_TT_UNSENT() ;
    }

    @Override
    public String getStatusText() {
        String error = "";

        if(ERROR_TT_UNSENT()) error += "ERROR_TT_UNSENT ";
        error += super.getStatusText();

        return error;
    }
}
