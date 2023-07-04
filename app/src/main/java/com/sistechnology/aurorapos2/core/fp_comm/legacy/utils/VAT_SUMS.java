package com.sistechnology.aurorapos2.core.fp_comm.legacy.utils;


import com.sistechnology.aurorapos2.R;

/**
 * Created by MARIELA on 12.5.2017 г..
 */

public class VAT_SUMS
{
    public static int numVats = 8;
    public static int[] vatSym = {R.string.VAT_A, R.string.VAT_B, R.string.VAT_C, R.string.VAT_D, R.string.VAT_E, R.string.VAT_F, R.string.VAT_G, R.string.VAT_H};
    public double[] sum = new double[numVats];
    public double[] rate = new double[numVats];
    public double total;
    public VAT_SUMS() { Clear(); }
    public void Clear() { total = 0; for(int i=0; i<numVats; i++) sum[i] = 0.0;}
    public double Total() { total = 0; for(int i=0; i<numVats; i++) total += sum[i]; return total; }
}
