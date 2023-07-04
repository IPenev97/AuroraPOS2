package com.sistechnology.aurorapos2.core.fp_comm.legacy.utils;

import android.util.Log;

public class CommonFn {
    public static int ParseInt(String value) {
        if (value == null || value.equals("") || value.equals("null")) {
            return 0;
        }

        value = value.trim();
        for(int i = 0; i < value.length(); i++)
        {
            if(i == 0 && (value.charAt(i) == '-' || value.charAt(i) == '+'))
                continue;
            if (!Character.isDigit(value.charAt(i)))
                return 0;
        }
        if(value.isEmpty())
            return 0;
        int output = 0;
        try {
            output = Integer.parseInt(value);
        } catch (Exception e){
            Log.e("CORE", e.getMessage());
            return output;
        }
        return output;
    }
    public static long ParseLong(String value) {
        if (value == null || value.equals("") || value.equals("null")) {
            return 0;
        }

        if(value.contains("."))
            return (long)ParseDouble(value);

        return Long.parseLong(value);
    }
    public static double ParseDouble(String value) {
        if (value == null || value.equals("null")) {
            //Log.e("CORE", "Null value!");
            return 0.0;
        }
        if (value.equals("")) {
            Log.e("CORE", "Empty value!");
            return 0.0;
        }

        if(value.endsWith("%")) // ако стойността е 20.00%, например
            value = value.substring(0, value.length()-1);
        try {
            double d = Double.parseDouble(value);
            if(Math.abs(d)<0.0001d && !value.substring(0,1).equals("0"))
            {
                String valueWithDot = value.replaceAll(",", ".");
                d = Double.parseDouble(valueWithDot);
            }
            return d;
        }
        catch(Exception e) {
            try {
                String valueWithDot = value.replaceAll(",", ".");
                return Double.parseDouble(valueWithDot);
            }
            catch(Exception e2)
            {
                return 0.0d;
            }
        }

        /*
            double dTempToCheck = 0;
            Locale theLocale = Locale.getDefault();
            NumberFormat numberFormat = DecimalFormat.getInstance(theLocale);
            Number theNumber;
            try {
                theNumber = numberFormat.parse(value);

                dTempToCheck = theNumber.doubleValue();

                // sometimes 0.135 comes as 135
                if (dTempToCheck > 0.0001 && value.length() > 2)
                    if (value.substring(0, 2).equals("0.")) {
                        String valueNoDot = value.replace(".", ",");
                        theNumber = numberFormat.parse(valueNoDot);

                        dTempToCheck = theNumber.doubleValue();
                    }
                return dTempToCheck;
            } catch (ParseException e3) {
                // The string value might be either 99.99 or 99,99, depending on Locale.
                // We can deal with this safely, by forcing to be a point for the decimal separator, and then using Double.valueOf ...
                //http://stackoverflow.com/questions/4323599/best-way-to-parsedouble-with-comma-as-decimal-separator
                String valueWithDot = value.replaceAll(",", ".");

                try {
                    return Double.valueOf(valueWithDot);
                } catch (NumberFormatException e2) {
                    // This happens if we're trying (say) to parse a string that isn't a number, as though it were a number!
                    // If this happens, it should only be due to application logic problems.
                    // In this case, the safest thing to do is return 0, having first fired-off a log warning.
                    Log.w("CORE", "Warning: Value is not a number" + value);
                    return 0.0;
                }

            }
        */

    }
    public static byte getCyrByte(char charTmp)
    {
        byte byteCyr;
        if(charTmp>='а' && charTmp<='я')
        {
            byteCyr=(byte)(0xE0 + (int)charTmp - (int)'а');
        }
        else
        if(charTmp>='А' && charTmp<='Я')
        {
            byteCyr=(byte)(0xC0 + (int)charTmp - (int)'А');
        }
        else
        if(charTmp=='№')
            byteCyr = (byte)0xB9;
        else
            byteCyr = (byte)charTmp;

        return byteCyr;
    }

    public static byte getCyrByte856(char charTmp)
    {
        byte byteCyr;

        if(charTmp>='а' && charTmp<='ъ')
            byteCyr=(byte)(0xA0 + (int)charTmp - (int)'а');
        else if(charTmp=='ь')
            byteCyr=(byte)0xBC;
        else if(charTmp=='ю')
            byteCyr=(byte)0xBE;
        else if(charTmp=='я')
            byteCyr=(byte)0xBF;
        else if(charTmp>='А' && charTmp<='Ъ')
            byteCyr=(byte)(0x80 + (int)charTmp - (int)'А');
        else if(charTmp=='Ю')
            byteCyr=(byte)0x9E;
        else if(charTmp=='Я')
            byteCyr=(byte)0x9F;
        else if(charTmp=='№')
            byteCyr = (byte)0xD5;
        else
            byteCyr = (byte)charTmp;

        return byteCyr;
    }
}
