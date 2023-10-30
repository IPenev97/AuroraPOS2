package com.sistechnology.aurorapos2.core.fp_comm.legacy.Datecs;

import com.sistechnology.aurorapos2.core.fp_comm.legacy.StatusFP;


/**
 * Created by MARIELA on 5.5.2017 г..
 */

public class DatecsStatusFP extends StatusFP
{
    @Override
    public boolean FP_ERROR              (){ return false;}
    @Override
    public boolean WRONG_PASSWORD        (){ return false;}
    @Override
    public boolean ERROR_CUTTER          (){ return false;}
    @Override
    public boolean RAM_NULL              (){ return false;}
    @Override
    public boolean ERROR_TT              (){ return getBit(1, 6);}

    public boolean ERROR_TT_UNSENT       (){ return getBit(1, 5);}

    @Override
    public boolean PRINT_READY           (){ return true;}
    @Override
    public boolean LOW_PAPER             (){ return false;}

    @Override
    public boolean ERROR6                (){ return false;}
    @Override
    public boolean ERROR5                (){ return false;}
    @Override
    public boolean ERROR4                (){ return false;}
    @Override
    public boolean ERROR3                (){ return false;}
    @Override
    public boolean ERROR2                (){ return false;}
    @Override
    public boolean ERROR1                (){ return false;}
    @Override
    public boolean ERROR0                (){ return false;}

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
