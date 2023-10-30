package com.sistechnology.aurorapos2.core.fp_comm.legacy.Datecs;

public class ErrorCodes
{
    public static String getErrorTextV1(int errorCode)
    {
        switch(errorCode)
        {
            case -150820:
                return "Firmware not for this device";
            case -150819:
                return "This is an invoice";
            case -150818:
                return "XML line too long";
            case -150817:
                return "End command 'E' needed";
            case -150816:
                return "Start command 'B' needed";
            case -150815:
                return "Firmware too big";
            case -150814:
                return "Not crypted firmware";
            case -150813:
                return "No starting data command";
            case -150812:
                return "Outdated petrol station info";
            case -150811:
                return "SIM-card different from registered one";
            case -150810:
                return "No SIM-card in modem";
            case -150809:
                return "GPRS-modem busy";
            case -150808:
                return "GPRS-modem not responding";
            case -150807:
                return "Tax terminal error";
            case -150806:
                return "Not an invoice";
            case -150805:
                return "Customer data already entered";
            case -150804:
                return "No customer data entered";
            case -150803:
                return "No invoice numbers interval programmed";
            case -150802:
                return "Invoice number outside interval";
            case -150801:
                return "No invoice number";
            case -150800:
                return "Printer is not registered";
            case -150434:
                return "Payment transaction denied";
            case -150433:
                return "Already paid non-cash";
            case -150432:
                return "Too many fuel sales in receipt";
            case -150431:
                return "Zero price";
            case -150430:
                return "Invalid storno date and time";
            case -150429:
                return "Fuel storno not allowed";
            case -150428:
                return "No data for receipt copy";
            case -150427:
                return "More than 200 departments with sales";
            case -150426:
                return "Not gas station";
            case -150425:
                return "Fuels may be sold using PLU only";
            case -150424:
                return "Invalid fuel PLU";
            case -150423:
                return "PLU is sold in this receipt";
            case -150422:
                return "Non-zero sum for this payment type";
            case -150421:
                return "No receipt copy data";
            case -150420:
                return "Too many lines in receipt";
            case -150419:
                return "No free PLUs for programming";
            case -150418:
                return "Non-zero sum for this department";
            case -150417:
                return "Non-zero sum for this PLU";
            case -150416:
                return "Non-zero sum for this operator";
            case -150415:
                return "Change not allowed";
            case -150414:
                return "Cash payment only";
            case -150413:
                return "Not enough cash available";
            case -150412:
                return "Total is not entirely paid";
            case -150411:
                return "Total is already paid";
            case -150410:
                return "Payment type not programmed";
            case -150409:
                return "Payment is started";
            case -150408:
                return "Void of non-existing sale";
            case -150407:
                return "Discount larger than price";
            case -150406:
                return "Negative sum or quantity";
            case -150405:
                return "Payment or discount/mark up done";
            case -150404:
                return "Too many sales in receipt";
            case -150403:
                return "VAT group is disabled";
            case -150402:
                return "Invalid VAT group";
            case -150401:
                return "Department does not exist";
            case -150400:
                return "PLU does not exist";
            case -150313:
                return "Fiscal memory read error";
            case -150312:
                return "Fiscal memory not found";
            case -150311:
                return "Fiscal memory is read only";
            case -150310:
                return "Serial number already set";
            case -150309:
                return "Too many records in fiscal memory";
            case -150308:
                return "Fiscal memory not responding";
            case -150307:
                return "No records in fiscal memory";
            case -150306:
                return "Printer is fiscalized";
            case -150305:
                return "Printer is not fiscalized";
            case -150304:
                return "All VAT rates disabled";
            case -150303:
                return "VAT rates not set";
            case -150302:
                return "VAT number not set";
            case -150301:
                return "Serial number not set";
            case -150300:
                return "Fiscal memory not formatted";
            case -150231:
                return "Journal buffer full";
            case -150218:
                return "Journal paper width different";
            case -150217:
                return "No data for document found";
            case -150216:
                return "El. journal number too big";
            case -150215:
                return "Cannot format valid el. journal";
            case -150214:
                return "Journal near end";
            case -150213:
                return "No access to external display";
            case -150212:
                return "Cannot open el. journal document";
            case -150211:
                return "Cannot format el. journal";
            case -150210:
                return "Cannot write to el. journal";
            case -150209:
                return "Cannot read from el. journal";
            case -150208:
                return "Not in service mode";
            case -150207:
                return "Probably wrong date and time";
            case -150206:
                return "Date and time before last JNL record";
            case -150205:
                return "Date and time before last FM record";
            case -150204:
                return "More than 24 hours without Z-report";
            case -150203:
                return "No receipts after Z-report";
            case -150202:
                return "Receipts after Z-report";
            case -150201:
                return "No receipt is open";
            case -150200:
                return "Receipt is open";
            case -150115:
                return "UNP must increase";
            case -150114:
                return "UNP counter overflow";
            case -150113:
                return "UNP missing";
            case -150112:
                return "Wrong invoice number";
            case -150111:
                return "Data contain 'FISCAL'";
            case -150110:
                return "Presenter error";
            case -150109:
                return "Cutter error";
            case -150108:
                return "Invalid request";
            case -150107:
                return "Invalid bar code data";
            case -150106:
                return "Invalid serial number";
            case -150105:
                return "Less than 2 lines in header";
            case -150104:
                return "Invalid password";
            case -150103:
                return "Arithmetic overflow";
            case -150102:
                return "Journal error";
            case -150101:
                return "Out of paper";
            case -150100:
                return "Clock not set";
            case -150010:
                return "Printer is blocked";
            case -150003:
                return "Not permited";
            case -150002:
                return "Invalid command";
            case -150001:
                return "Syntax error";
            case -100000:
                return "Function ''OPEN_PORT'' вЂ“ the serial key is wrong";
            case -33062:
                return "You have not enough rights to do this operation.";
            case -33061:
                return "The error item is not found. Please check the value of ID!";
            case -33060:
                return "The errors list is empty.";
            case -33059:
                return "The header item is not found.";
            case -33058:
                return "The script item is not found.";
            case -33057:
                return "The transaction queue engine can not delete an old transaction.";
            case -33056:
                return "The transaction queue engine reject the command. The script can not be deleted because it is currently executing.";
            case -33055:
                return "Common logical error: answer -F";
            case -33054:
                return "One or more of the script items contain a wrong value.";
            case -33053:
                return "The script item status is not in proper state for this operation.";
            case -33052:
                return "The status of the transaction is still -executing";
            case -33051:
                return "Headers list is empty.";
            case -33050:
                return "Not all items for this transaction are prepared.";
            case -33049:
                return "The header status for this transaction is not in proper state for this operation.";
            case -33048:
                return "Transaction Header not found.";
            case -33047:
                return "File: Access denied";
            case -33046:
                return "Connection is not active";
            case -33045:
                return "Sniffer component not assigned.";
            case -33044:
                return "Unsupported document type. Use other method for this type!";
            case -33043:
                return "The COM Server not assigned.";
            case -33042:
                return "No output params - general error.";
            case -33041:
                return "Param not found.";
            case -33040:
                return "No input params for this command.";
            case -33039:
                return "Invalid enumerate list index.";
            case -33038:
                return "Command not found.";
            case -33037:
                return "Invalid param name.";
            case -33036:
                return "Invalid param index.";
            case -33035:
                return "Invalid command name.";
            case -33034:
                return "The stamp name length must be <= 12 symbols.";
            case -33033:
                return "Empty stamp name.";
            case -33032:
                return "Incorrect checksum";
            case -33031:
                return "The operation is canceled by user.";
            case -33030:
                return "Delete file - access denied.";
            case -33029:
                return "No files for download";
            case -33028:
                return "Device answer is not by its protocol.";
            case -33027:
                return "Not supported script engine";
            case -33026:
                return "Transport protocol not supported.";
            case -33025:
                return "Unknown device model";
            case -33024:
                return "Invalid file name.";
            case -33023:
                return "Device is connected - you can not change these values until the device is still connected.";
            case -33022:
                return "Device not connected";
            case -33021:
                return "Fiscal device component not assigned";
            case -33020:
                return "Device depended method is not prepared.";
            case -33019:
                return "Connector component not assigned.";
            case -33018:
                return "Description container component not assigned.";
            case -33017:
                return "Error container component not assigned.";
            case -33016:
                return "Misc component not assigned.";
            case -33015:
                return "The index is out of range. The value is greater than numbers of the fields in the answer.";
            case -33014:
                return "Misc component is not assigned to scrypt engine.";
            case -33013:
                return "FiscalDevice component is not assigned to scrypt engine.";
            case -33012:
                return "Invalid parameter value";
            case -33011:
                return "Duplicate label";
            case -33010:
                return "The script execution was interrupted by user.";
            case -33009:
                return "The script is empty.";
            case -33008:
                return "Error due parsing. Something is wrong in the script line.";
            case -33007:
                return "Wrong protocol - readByte method:(missing ETX byte)";
            case -33006:
                return "Wrong protocol - readByte method:(missing BCC byte)";
            case -33005:
                return "Wrong protocol - readByte method:(missing ENQ byte)";
            case -33004:
                return "Wrong protocol - readByte method:(missing EOT byte)";
            case -33003:
                return "Wrong protocol - readByte method:(missing SEQ byte).";
            case -33002:
                return "Wrong protocol - readByte method:(missing SOH byte)";
            case -33001:
                return "Save to file failed";
            case -33000:
                return "Load from to failed.";
            case -23007:
                return "Client can not execute update method (2).";
            case -23006:
                return "Client can not broadcast the message.";
            case -23005:
                return "Client can not clear old transactions from the server.";
            case -23004:
                return "Client can not get user table from the server.";
            case -23003:
                return "Client can not disconnect properly from the server.";
            case -23002:
                return "Client can not register calback.";
            case -23001:
                return "Client can not connect to the server.";
            case -23000:
                return "No internet connection.";
            case -16517:
                return "Internal error - cannot find the transaction.";
            case -16516:
                return "Internal error - cannot make a transaction.";
            case -16515:
                return "Wrong protocol - readByte method:(missing status byte).";
            case -16514:
                return "Wrong protocol - readByte method:(missing byte for answer type determination).";
            case -16513:
                return "Wrong protocol - readByte method:(missing event delimiter [$00]  byte).";
            case -16512:
                return "Wrong protocol - readByte method:(missing  $3E  byte).";
            case -16511:
                return "The tags list is empty.";
            case -16510:
                return "The program can't connect to server.";
            case -16509:
                return "Unsupported socket value for TYPE. Wrong protocol.";
            case -16508:
                return "Unsupported socket ID. Wrong protocol.";
            case -16507:
                return "Received an unknown event value. Unknown or wrong protocol.";
            case -16506:
                return "Received an unknown sub event value (after a network event from the device). Unknown or wrong protocol.";
            case -16505:
                return "Unknown status - general error.";
            case -16504:
                return "The communication thread is busy - communication with a Borika server.";
            case -16503:
                return "The communication thread is busy - waiting for pinpad event.";
            case -16502:
                return "The communication thread is busy - waiting for answer.";
            case -16501:
                return "The communication thread is busy - execution of the command.";
            case -16500:
                return "The communication thread is not started yet.";
            case -10530:
                return "This is a header row.";
            case -10529:
                return "The CSV file is not in format ";
            case -10528:
                return "Successfully canceled PinPad transaction (automatically  - from settings)";
            case -10527:
                return "Command not found.(By values)";
            case -10526:
                return "Command not found.(By name)";
            case -10525:
                return "Timeout error.";
            case -10524:
                return "Missing output parameter. [OUTPUT]";
            case -10523:
                return "Check for exceeding the lower limit of the parameter (INT value). [OUTPUT]";
            case -10522:
                return "Check for exceeding the upper limit of the parameter (INT value). [OUTPUT]";
            case -10521:
                return "Check whether the parameter is an integer value. [OUTPUT]";
            case -10520:
                return "Check for exceeding the lower limit of the parameter (QTY value). [OUTPUT]";
            case -10519:
                return "Check for exceeding the upper limit of the parameter (QTY value). [OUTPUT]";
            case -10518:
                return "Check for exceeding the lower limit of the parameter (Currency value). [OUTPUT]";
            case -10517:
                return "Check for exceeding the upper limit of the parameter (Currency value). [OUTPUT]";
            case -10516:
                return "Check whether the parameter is real number. [OUTPTUT]";
            case -10515:
                return "Check for availability in the enumerated list of values. [OUTPUT]";
            case -10514:
                return "Check for exceeding the lower limit of the parameter (length). [OUTPUT]";
            case -10513:
                return "Check for exceeding the upper limit of the parameter (length). [OUTPUT]";
            case -10512:
                return "Check for exceeding the lower limit of the parameter (INT value). [INPUT]";
            case -10511:
                return "Check for exceeding the upper limit of the parameter (INT value). [INPUT]";
            case -10510:
                return "Check whether the parameter is an integer value. [INPUT]";
            case -10509:
                return "Check for exceeding the lower limit of the parameter (QTY value). [INPUT]";
            case -10508:
                return "Check for exceeding the upper limit of the parameter (QTY value). [INPUT]";
            case -10507:
                return "Check for exceeding the lower limit of the parameter (Currency value). [INPUT]";
            case -10506:
                return "Check for exceeding the upper limit of the parameter (Currency value). [INPUT]";
            case -10505:
                return "Check whether the parameter is real number. [INPUT]";
            case -10504:
                return "Check for availability in the enumerated list of values. [INPUT]";
            case -10503:
                return "Check for exceeding the lower limit of the parameter (length). [INPUT]";
            case -10502:
                return "Check for exceeding the upper limit of the parameter (length). [INPUT]";
            case -10500:
                return "Missing input parameter. [INPUT]";
            case -10003:
                return "Function ''OPEN_TCPIP'' - wrong IP address string";
            case -10002:
                return "Function ''EXECUTE_FILE'' - wrong file format. Something is wrong in the string within the file";
            case -10001:
                return "Function ''EXECUTE_FILE'' - execute file does not exist";
            case -10000:
                return "Wrong logical number, check your string or file";
            case -5007:
                return "Exception in CLOSE_TCPIP ";
            case -5006:
                return "Exception in  OPEN_TCPIP ";
            case -5005:
                return "Exception in GET_SERIAL_NUMBER_TCPIP ";
            case -5004:
                return "Exception in GET_SERIAL_NUMBER ";
            case -5003:
                return "Exception in CLOSE_PORT function ";
            case -5002:
                return "Exception in EXECUTE_FILE function - check your parameters";
            case -5001:
                return "Exception in EXECUTE_STRING function - check your parameters";
            case -5000:
                return "Exception in OPEN_PORT function - check your parameters";
            case -229:
                return "Class exceptions due to wrong parameters/bugs";
            case -228:
                return "Class exceptions due to wrong parameters/bugs";
            case -227:
                return "Class exceptions due to wrong parameters/bugs";
            case -226:
                return "Class exceptions due to wrong parameters/bugs";
            case -225:
                return "Class exceptions due to wrong parameters/bugs";
            case -224:
                return "Class exceptions due to wrong parameters/bugs";
            case -223:
                return "Class exceptions due to wrong parameters/bugs";
            case -222:
                return "Class exceptions due to wrong parameters/bugs";
            case -221:
                return "Class exceptions due to wrong parameters/bugs";
            case -220:
                return "Class exceptions due to wrong parameters/bugs";
            case -210:
                return "Class exceptions due to wrong parameters/bugs";
            case -209:
                return "Class exceptions due to wrong parameters/bugs";
            case -208:
                return "Class exceptions due to wrong parameters/bugs";
            case -207:
                return "Class exceptions due to wrong parameters/bugs";
            case -206:
                return "Class exceptions due to wrong parameters/bugs";
            case -205:
                return "Class exceptions due to wrong parameters/bugs";
            case -204:
                return "Class exceptions due to wrong parameters/bugs";
            case -203:
                return "Class exceptions due to wrong parameters/bugs";
            case -202:
                return "Class exceptions due to wrong parameters/bugs";
            case -201:
                return "Class exceptions due to wrong parameters/bugs";
            case -64:
                return "Electronic journal is full";
            case -63:
                return "Tax terminal not responding";
            case -62:
                return "Fiscal memory is in read only mode";
            case -61:
                return "Failure in the printing mechanism";
            case -60:
                return "Incoming data has syntax error or wrong command number";
            case -59:
                return "Ram reset after powering the device";
            case -58:
                return "Command cannot be executed in current fiscal mode ";
            case -57:
                return "Fiscal memory error (read/write failed/corrupt, memory in read-only state or last entry is corrupt)";
            case -56:
                return "Fiscal memory full ";
            case -55:
                return "No opened receipt, command not allowed";
            case -54:
                return "Opened nonfiscal receipt, command not allowed";
            case -53:
                return "Opened fiscal receipt, command not allowed";
            case -52:
                return "Incorrect command number in the string";
            case -51:
                return "General / syntax error ";
            case -50:
                return "The printer is out of paper.";
            case -46:
                return "Device communication exceptions, check if the device is on.";
            case -45:
                return "Device communication exceptions, check if the device is on.";
            case -44:
                return "Device communication exceptions, check if the device is on.";
            case -43:
                return "Device communication exceptions, check if the device is on.";
            case -42:
                return "Device communication exceptions, check if the device is on.";
            case -41:
                return "Device communication exceptions, check if the device is on.";
            case -40:
                return "Device communication exceptions, check if the device is on.";
            case -25:
                return "The functionality of your product is time limited. Please check for a new version or contact the support team!";
            case -24:
                return "Trial period has expired.";
            case -23:
                return "Error which was obtained in GetStatus method. Some bits in the status bytes of the fiscal device was raised.";
            case -22:
                return "No registered";
            case -21:
                return "Invalid command";
            case -20:
                return "Command failed";
            case -19:
                return "No more items or operations";
            case -18:
                return "Not exist object.";
            case -17:
                return "Invalid command.";
            case -16:
                return "The device or resource does not exists.";
            case -15:
                return "The operation is not implemented.";
            case -14:
                return "Device error.";
            case -13:
                return "EEPROM error";
            case -12:
                return "Flash memory error.";
            case -11:
                return "CRC error.";
            case -10:
                return "Input/Output error";
            case -9:
                return "Invalid parameter.";
            case -8:
                return "Memory allocation error.";
            case -7:
                return "The operation is not supported.";
            case -6:
                return "Timeout expired.";
            case -5:
                return "Device or resource busy.";
            case -4:
                return "Close error";
            case -3:
                return "Open error";
            case -2:
                return "Create error.";
            case -1:
                return "General error / Unknown error.";
            case 0:
                return "Operation successful.";
            default:
                return "Datecs FP V1: not recognized error code " + errorCode;
        }
    }

    public static String getErrorTextV2(int errorCode)
    {
        switch (errorCode) {
            case -100001:
                return "General error in fiscal device: In - out error( cannot read or write )";
            case -100002:
                return "General error in fiscal device: Wrong checksum";
            case -100003:
                return "General error in fiscal device: No more data";
            case -100004:
                return "General error in fiscal device: The element is not found";
            case -100005:
                return "General error in fiscal device: There are no records found";
            case -100006:
                return "General error in fiscal device: The operation is aborted";
            case -100007:
                return "Wrong mode( standart training...)  is selected.";
            case -100008:
                return "General error in fiscal device: Device is not ready";
            case -100100:
                return "Fiscal memory error: Fiscal memory is busy";
            case -100101:
                return "Fiscal memory error: Fiscal memory failure. Could not read or write";
            case -100102:
                return "Fiscal memory error: Forbidden write in fiscal memory";
            case -100103:
                return "Fiscal memory error: Wrong address in fiscal memory";
            case -100104:
                return "Fiscal memory error: Wrong size in fiscal memory";
            case -100105:
                return "Fiscal memory error: Fiscal memory is not connected";
            case -100106:
                return "Fiscal memory error: Wrong checksum in fiscal memory( invalid data )";
            case -100107:
                return "Fiscal memory error: Empty block in fiscal memory";
            case -100108:
                return "Fiscal memory error: Maximum number of block  in fiscal memory";
            case -100109:
                return "Fiscal memory error: Wrong range in fiscal memory";
            case -100110:
                return "Fiscal memory error: Empty range in fiscal memory";
            case -100111:
                return "Fiscal memory error: New module in fiscal memory";
            case -100112:
                return "Fiscal memory error: Fiscal memory is not empty";
            case -100113:
                return "Fiscal memory error: Fiscal memory is not equal";
            case -100114:
                return "Fiscal memory error: Fiscal memory is full";
            case -100115:
                return "Fiscal memory error: Fiscal memory needs update";
            case -100116:
                return "Fiscal memory error: Fiscal memory is blocked";
            case -100400:
                return "Line thermal printer mechanism error: Power supply error ( 3V )";
            case -100401:
                return "Line thermal printer mechanism error: Power supply error ( 24V or 8V )";
            case -100402:
                return "Line thermal printer mechanism error: Head overheating";
            case -100403:
                return "Line thermal printer mechanism error: Paper end";
            case -100404:
                return "Line thermal printer mechanism error: Cover is openFiscalReceipt";
            case -100405:
                return "Line thermal printer mechanism error: Near paper end";
            case -100406:
                return "Line thermal printer mechanism error: Mark sensor - not used";
            case -100407:
                return "Line thermal printer mechanism error: Cutter error";
            case -100408:
                return "Line thermal printer mechanism error: Not used  ";
            case -100409:
                return "Line thermal printer mechanism error: Not used";
            case -100410:
                return "Line thermal printer mechanism error: Not used";
            case -100411:
                return "Line thermal printer mechanism error: Not used";
            case -100412:
                return "Line thermal printer mechanism error: Not used";
            case -100413:
                return "Line thermal printer mechanism error: Not used";
            case -100414:
                return "Printer on time is overrun.";
            case -100500:
                return "System error: Memory structure error";
            case -100501:
                return "System error: Error in RAM";
            case -100502:
                return "System error: Flash memory error";
            case -100503:
                return "System error: SD card error";
            case -100504:
                return "System error: Invalid message file";
            case -100505:
                return "System error: Fiscal memory error( could not write or read )";
            case -100506:
                return "System error: No RAM battery";
            case -100507:
                return "System error: SAM module error";
            case -100508:
                return "System error: Real time clock error";
            case -100509:
                return "System error: Memory error";
            case -100510:
                return "System error: The size of SD card is wrong.";
            case -101000:
                return "Common logical error: No heap memory( cannot allocate memory for operation )";
            case -101001:
                return "Common logical error: File manipulate error";
            case -101003:
                return "Common logical error: Operation is rejected";
            case -101004:
                return "Common logical error: Bad input. Some of the data or parameters are incorrect";
            case -101005:
                return "Common logical error: In Application Programming error";
            case -101006:
                return "Common logical error: The execution of the operation is not possible";
            case -101007:
                return "Common logical error: Timeout. The time for waiting execution is out";
            case -101008:
                return "Common logical error: Invalid time";
            case -101009:
                return "Common logical error: The operation is cancelled";
            case -101010:
                return "Common logical error: Invalid format";
            case -101011:
                return "Common logical error: Invalid data";
            case -101012:
                return "Common logical error: Data parsing error";
            case -101013:
                return "Common logical error: Hardware configuration error";
            case -101014:
                return "ERR_ACCESS_DENIED";
            case -101015:
                return "Wrong data length";
            case -101500:
                return "Update error: No update. The device is up to date";
            case -102000:
                return "Battery error: Low battery";
            case -102001:
                return "Battery error: Low battery warning";
            case -102002:
                return "Operator error: Wrong operator password";
            case -102003:
                return "ECR error: ID number is empty";
            case -102004:
                return "Bluetooth error: Bluetooth is not found";
            case -102005:
                return "Display error: Display is not connected";
            case -102006:
                return "Printer error: Printer is not connected";
            case -102007:
                return "SD card error: SD card not present";
            case -102008:
                return "SD card error: SD2 card not present";
            case -102009:
                return "ECR error: VAT rates is not set.";
            case -102010:
                return "ECR error: Header lines are empty.";
            case -102011:
                return "User is registered by VAT, but number of the user is not entered.";
            case -102012:
                return "ECR error: FM number is empty";
            case -103000:
                return "PLU database error: PLU database is not found";
            case -103001:
                return "PLU database error: PLU code already exists";
            case -103002:
                return "PLU database error: Barcode already exists";
            case -103003:
                return "PLU database error: PLU database is full";
            case -103004:
                return "PLU database error: PLU has turnover";
            case -103005:
                return "PLU database error: In the PLU base has an article with same name.";
            case -103006:
                return "PLU database error: PLU name is not unique.";
            case -103007:
                return "PLU database error: Database format is not compatible.";
            case -103008:
                return "Can't openFiscalReceipt the PLU database file";
            case -104000:
                return "Service operation error: Z report is needed for this operation";
            case -104001:
                return "Service operation error: Service jumper is needed for this operation";
            case -104002:
                return "Service operation error: Service password is needed for this operation";
            case -104003:
                return "Service operation error: The operation is forbidden";
            case -104004:
                return "Service operation error: Service intervention is needed";
            case -104005:
                return "Service operation error: All clearing report is needed.";
            case -104006:
                return "Service operation error: Z report closed.";
            case -104007:
                return "Service operation error: Montly report needed.";
            case -104008:
                return "Service operation error: Year report needed.";
            case -104009:
                return "Service operation error: Backup needed.";
            case -104010:
                return "ERR_NEED_ALL_PAIDOUT";
            case -105000:
                return "EJ error: No records in EJ";
            case -105001:
                return "EJ error: Cannot add to EJ";
            case -105002:
                return "EJ error: SAM module signature error";
            case -105003:
                return "EJ error: Signature key version is changed -> impossible check";
            case -105004:
                return "EJ error: Bad record in EJ";
            case -105005:
                return "EJ error: Generate signature error( cannoct generate signature )";
            case -105006:
                return "EJ error: Wrong type of document to sign";
            case -105007:
                return "EJ error: Document is already signed";
            case -105008:
                return "EJ error: EJ is not from this device";
            case -105009:
                return "EJ error: EJ is almost full";
            case -105010:
                return "EJ error: EJ is full";
            case -105011:
                return "EJ error: Wrong format of EJ";
            case -105012:
                return "The electronic journal is not ready.";
            case -106000:
                return "Client database error: Firm does not exist";
            case -106001:
                return "Client database error: Firmcode already exists";
            case -106002:
                return "Client database error: EIK already exists";
            case -106003:
                return "Client database error: Firm database is full";
            case -106004:
                return "Client database error: Firm database is not found";
            case -107001:
                return "Invalid certificate.";
            case -107002:
                return "Certificate exist.";
            case -107003:
                return "Certificate unpack failed.";
            case -107004:
                return "Wrong certificate password.";
            case -107005:
                return "File write error.";
            case -107006:
                return "File read error.";
            case -107007:
                return "Certificate not found.";
            case -107500:
                return "?he current account has expired!";
            case -107501:
                return "Invalid profile file!";
            case -107502:
                return "Profile start date is invalid!";
            case -107503:
                return "Profile verification fail!";
            case -107504:
                return "Wrong profile struct format";
            case -108000:
                return "Discount card database error: Discount card does not exist";
            case -108001:
                return "Discount card database error: Discount card already exists";
            case -108002:
                return "Discount card database error: Barcode already exists";
            case -108003:
                return "Discount card database error: Discount card database is full";
            case -108004:
                return "Discount card database error: Discount card not found";
            case -109981:
                return "Smartcard error: No card in the holder.";
            case -109982:
                return "Smartcard error: Configuration failed";
            case -109983:
                return "Smartcard error: SmartCard communication error.";
            case -109984:
                return "Smartcard error: Supply voltage drop a VCC over-current detection or overheating.";
            case -109985:
                return "Smartcard error: Unexpected response from the applet.";
            case -109986:
                return "The ID of the smart card does not match the ID stored in the fiscal memory.";
            case -110000:
                return "SAM module error: SAM init error";
            case -110001:
                return "SAM module error: Error while trying to openFiscalReceipt SAM module with programmed serial number";
            case -110002:
                return "SAM module error: Opening SAM with empty ID";
            case -110003:
                return "SAM module error: SAM select file error";
            case -110004:
                return "SAM module error: SAM init telegram error";
            case -110005:
                return "SAM module error: SAM is already openFiscalReceipt";
            case -110006:
                return "SAM module error: SAM communication error";
            case -110010:
                return "SAM module error: Operation execution in SAM module is unsuccessful";
            case -110011:
                return "SAM module error: Cannot get last transaction from SAM";
            case -110012:
                return "SAM module error: Cannot register current transaction in SAM";
            case -110013:
                return "SAM module error: Cannot get Z-report from SAM";
            case -110014:
                return "SAM module error: Cannot register Z-report in SAM";
            case -110021:
                return "SAM module error: Transaction is closed in SAM -> 'closing needed'";
            case -110022:
                return "SAM module error: Z-report already closed in SAM -> 'Z needed'";
            case -110023:
                return "SAM module error: Unsent Z-reports -> 'communication needed'";
            case -110024:
                return "SAM module error: Overflow in reg -> 'Z needed'";
            case -110025:
                return "SAM module error: Not opened( empty ) Z - report -> 'Z impossible'";
            case -110100:
                return "Device error: Communication error";
            case -110101:
                return "Device error: Wrong struct format";
            case -110102:
                return "Device error: ST flag is active";
            case -110103:
                return "Device error: Invalid data";
            case -110104:
                return "Device error: Device is not fiscalized";
            case -110105:
                return "Device error: Device is already fiscalized";
            case -110106:
                return "Device error: Device is in service mode";
            case -110107:
                return "Device error: Service date is passed";
            case -110108:
                return "Device error: Day( shift ) is openFiscalReceipt";
            case -110109:
                return "Device error: Day( shift ) is closed";
            case -110110:
                return "Device error: Z-report number and shift number are not equal";
            case -110111:
                return "Device error: Only admin has permition";
            case -110200:
                return "NAP server error: Error openFiscalReceipt session";
            case -110201:
                return "NAP server error: Error preparing data for server";
            case -110202:
                return "NAP server error: There is unsent data";
            case -110203:
                return "NAP server error: Receiving data error";
            case -110204:
                return "NAP server error: Empty data";
            case -110205:
                return "NAP server error: Server negative answer";
            case -110206:
                return "NAP server error: Wrong answer format";
            case -110207:
                return "NAP server error: Server HOSTDI is zerro";
            case -110208:
                return "NAP server error: Server exception";
            case -110209:
                return "NAP server error: Not registered on server";
            case -110210:
                return "NAP server error: Communication with NAP server is blocked";
            case -110211:
                return "NAP server error: Modem error";
            case -110212:
                return "NAP server error: NAP is busy";
            case -110213:
                return "NAP server error: Already registered";
            case -110214:
                return "NAP server error: Wrong PS type";
            case -110215:
                return "NAP server error: Deregistered in NAP";
            case -110216:
                return "NAP server error: Wrong IMSI number";
            case -110217:
                return "NAP server error: Device is blocked( maximum Z-reports )";
            case -110218:
                return "NAP server error: Wrong FD( Fiscal device ) type";
            case -110219:
                return "NAP server error: The ECR is blocked by server";
            case -110220:
                return "NAP server error: The ECR is blocked - server error";
            case -110221:
                return "NAP server error: No server address";
            case -110222:
                return "NAP server error: Max. registrations reached.";
            case -110223:
                return "Invalid INN of the cashier";
            case -110224:
                return "Invalid INN of the server";
            case -110300:
                return "Working error: Invalid file";
            case -110301:
                return "Working error: Invalid parameters";
            case -110400:
                return "Connection error: Connection init error";
            case -110401:
                return "NRA connection error: Wrong parameteres";
            case -110402:
                return "NRA connection error: No GPRS";
            case -110403:
                return "Connection error: Failed to initialize connection with NRA Repository Server";
            case -110404:
                return "Connection error: Wrong answer format";
            case -110405:
                return "NRA server returns error";
            case -110481:
                return "Error in answer from NRA server on parameter 1";
            case -110482:
                return "Error in answer from NRA server on parameter 2";
            case -110483:
                return "Error in answer from NRA server on parameter 3";
            case -110484:
                return "Error in answer from NRA server on parameter 4";
            case -110485:
                return "Error in answer from NRA server on parameter 5";
            case -110486:
                return "Error in answer from NRA server on parameter 6";
            case -110487:
                return "Error in answer from NRA server on parameter 7";
            case -110488:
                return "Error in answer from NRA server on parameter 8";
            case -110489:
                return "Error in answer from NRA server on parameter 9";
            case -110490:
                return "Error in answer from NRA server on parameter 10";
            case -110491:
                return "Error in answer from NRA server on parameter 11";
            case -110492:
                return "Error in answer from NRA server on parameter 12";
            case -110493:
                return "Error in answer from NRA server on parameter 13";
            case -110494:
                return "Error in answer from NRA server on parameter 14";
            case -110495:
                return "Error in answer from NRA server on parameter 15";
            case -110496:
                return "Error in answer from NRA server on parameter 16";
            case -110500:
                return "Modem error: error in communication between device and modem";
            case -110501:
                return "Modem error: No SIM card";
            case -110502:
                return "Modem error: Wrong PIN of SIM";
            case -110503:
                return "Modem error: Cannot register to mobile network";
            case -110504:
                return "Modem error: No PPP connection( cannot connect )";
            case -110505:
                return "Modem error: Wrong modem configuration( for example - no programmed apn )";
            case -110506:
                return "Modem error: Modem initializing";
            case -110507:
                return "Modem error: Modem is not ready";
            case -110508:
                return "Modem error: Remove SIM card";
            case -110509:
                return "Modem error: Modem found a cell";
            case -110510:
                return "Modem error: Modem does not find a cell";
            case -110511:
                return "Modem error: Failed lot days";
            case -110601:
                return "Modem error: Device is not connected to AP( access point )";
            case -110700:
                return "Network error: Cannot resolve address";
            case -110701:
                return "Network error: Cannot openFiscalReceipt socket for communication with server";
            case -110702:
                return "Network error: Connection error( cannot connect to a server )";
            case -110703:
                return "Network error: Config error( for example: no server address )";
            case -110704:
                return "Network error: Connection socket is already opened";
            case -110705:
                return "Network error: SSL communication error( something went wrong in cryptographic protocol )";
            case -110706:
                return "Network error: HTTP communication error( something went wrong in http protocol )";
            case -110800:
                return "Tax terminal error: No error";
            case -110801:
                return "Tax terminal error: Unknown ID";
            case -110802:
                return "Tax terminal error: Invalid token( key from the server )";
            case -110803:
                return "Tax terminal error: Protocol error";
            case -110804:
                return "Tax terminal error: The command is unknown";
            case -110805:
                return "Tax terminal error: The command is not supported";
            case -110806:
                return "Tax terminal error: Invalid configuration";
            case -110807:
                return "Tax terminal error: SSL is not allowed";
            case -110808:
                return "Tax terminal error: Invalid request number";
            case -110809:
                return "Tax terminal error: Invalid retry request";
            case -110810:
                return "Tax terminal error: Cannot cancel ticket";
            case -110811:
                return "Tax terminal error: More than 24 hours from shift opening";
            case -110812:
                return "Tax terminal error: Invalid login name or password";
            case -110813:
                return "Tax terminal error: Incorrect request data";
            case -110814:
                return "Tax terminal error: Not enough cash";
            case -110815:
                return "Tax terminal error: Blocked from server";
            case -110854:
                return "Tax terminal error: Service temporarily unavailable";
            case -110855:
                return "Tax terminal error: Unknown error";
            case -111000:
                return "Registration mode error: Common error followed by deliting all data for the command";
            case -111001:
                return "Registration mode error: Common error followed by partly deliting data for the command";
            case -111002:
                return "Registration mode error: Syntax error. Check the parameters of the command";
            case -111003:
                return "Registration mode error: Cannot do operation";
            case -111004:
                return "Registration mode error: PLU code was not found";
            case -111005:
                return "Registration mode error: Forbidden VAT";
            case -111006:
                return "Registration mode error: Overflow in multiplication of quantity and price";
            case -111007:
                return "Registration mode error: PLU has no price";
            case -111008:
                return "Registration mode error: Group is not in range";
            case -111009:
                return "Registration mode error: Department is not in range";
            case -111010:
                return "Registration mode error: BAR code does not exist";
            case -111011:
                return "Registration mode error: Overflow of the PLU turnover";
            case -111012:
                return "Registration mode error: Overflow of the PLU quantity";
            case -111013:
                return "Registration mode error: ECR daily registers overflow";
            case -111014:
                return "Registration mode error: Bill total register overflow";
            case -111015:
                return "Registration mode error: Receipt is opened";
            case -111016:
                return "Registration mode error: Receipt is closed";
            case -111017:
                return "Registration mode error: No cash in ECR";
            case -111018:
                return "Registration mode error: Payment is initiated";
            case -111019:
                return "Registration mode error: Maximum number of sales in receipt";
            case -111020:
                return "Registration mode error: No transactions";
            case -111021:
                return "Registration mode error: Possible negative turnover";
            case -111022:
                return "Registration mode error: Foreign payment has change";
            case -111023:
                return "Registration mode error: Transaction is not found in the receipt";
            case -111024:
                return "Registration mode error: End of 24 hour blocking";
            case -111025:
                return "Registration mode error: Invalid invoice range";
            case -111026:
                return "Registration mode error: Operation is cancelled by operator";
            case -111027:
                return "Registration mode error: Operation approved by POS";
            case -111028:
                return "Registration mode error: Operation is not approved by POS";
            case -111029:
                return "Registration mode error: POS terminal communication error";
            case -111030:
                return "Registration mode error: Multiplication of quantity and price is 0";
            case -111031:
                return "Registration mode error: Value is too big";
            case -111032:
                return "Registration mode error: Value is bad";
            case -111033:
                return "Registration mode error: Price is too big";
            case -111034:
                return "Registration mode error: Price is bad";
            case -111035:
                return "Registration mode error: Operation all void is selected to be executed";
            case -111036:
                return "Registration mode error: Only all void operation is permitted";
            case -111040:
                return "Registration mode error: Restaurant: There is no free space for other purchases";
            case -111041:
                return "Registration mode error: Restaurant: There is no free space for new acount";
            case -111042:
                return "Registration mode error: Restaurant: Account is already opened";
            case -111043:
                return "Registration mode error: Restaurant: Wrong index";
            case -111044:
                return "Registration mode error: Restaurant: Account is not found";
            case -111045:
                return "Registration mode error: Restaurant: Not permitted( only for admins )";
            case -111046:
                return "Registration mode error: non-fiscal receipt is opened";
            case -111047:
                return "Registration mode error: fiscal receipt is opened";
            case -111048:
                return "Registration mode error: Buyers TIN is already entered";
            case -111049:
                return "Registration mode error: Buyers TIN is not entered";
            case -111050:
                return "Registration mode error: Payment is not initiated";
            case -111051:
                return "Registration mode error: Reeipt type mismatch";
            case -111052:
                return "Registration mode error: Receipt total limit is reached";
            case -111053:
                return "Registration mode error: Sum cannot be divided by the minimum coin";
            case -111054:
                return "Registration mode error: Sum must be <= payment amount";
            case -111055:
                return "Registration mode error: Sum of voucher must be entered when paying with voucher";
            case -111056:
                return "Registration mode error: Value surcharge of the difference between voucher sum and total must be done when paying with voucher and sum > total";
            case -111057:
                return "Registration mode error: Payment with foreign currency is disabled";
            case -111058:
                return "Registration mode error: Payment with foreign currency is impossible";
            case -111059:
                return "Registration mode error: Sum must be bigger or equal to payment amount";
            case -111060:
                return "Registration mode error: Safe opening is disabled";
            case -111061:
                return "Registration mode error: Forbidden payment";
            case -111062:
                return "Registration mode error: Forbidden key for surcharge/discount";
            case -111063:
                return "Registration mode error: Entered sum is bigger than receipt sum";
            case -111064:
                return "Registration mode error: Entered sum is smaller than receipt sum";
            case -111065:
                return "Registration mode error: Fiscal printer: Sum of receipt is 0. Operation 'void' is needed";
            case -111066:
                return "Registration mode error: Fiscal printer: Operation 'void' is executed. Close receipt is needed";
            case -111067:
                return "Registration mode error: Storno receipt is opened";
            case -111068:
                return "Registration mode error: Sum is not entered";
            case -111069:
                return "Registration mode error: Price type is invalid";
            case -111070:
                return "Registration mode error: Linked surcharge is forbidden";
            case -111071:
                return "Registration mode error: Negative price is forbidden";
            case -111072:
                return "Registration mode error: More than 1 VAT in one receipt is not allowed";
            case -111073:
                return "Registration mode error: Pinpad error";
            case -111074:
                return "Registration mode error: Buyer data is wrong";
            case -111075:
                return "Registration mode error: Vat system disable.";
            case -111076:
                return "Operator not logged in.";
            case -111080:
                return "Registration mode error: Registration mode error: Out of stock";
            case -111081:
                return "Registration mode error: Must pushing of the STL before TL.";
            case -111082:
                return "Package does not exist";
            case -111083:
                return "Measuring unit not found";
            case -111084:
                return "Category not found in the data base";
            case -111085:
                return "Invalid department name";
            case -111086:
                return "Bank terminal not configured";
            case -111089:
                return "Entered price is bigger than the programmed";
            case -111090:
                return "Fix PLU's price";
            case -111091:
                return "Incorect sign agent.";
            case -111092:
                return "Voucher payment cannot have change";
            case -111093:
                return "Sum for advance payment is bigger than the sum of article";
            case -111094:
                return "Payment in storno can not have change";
            case -111095:
                return "Invalid parameter - PLU is not defined as excise PLU";
            case -111096:
                return "Excise stamp of an excise PLU is not entered";
            case -111097:
                return "SALE FORBIDDEN (excise stamp is not valid)";
            case -111500:
                return "Pinpad error: No error from pinpad";
            case -111501:
                return "Pinpad error: General unicreditbulbank icon error";
            case -111502:
                return "Pinpad error: Not valid command or sub command code";
            case -111503:
                return "Pinpad error: Invalid parameter";
            case -111504:
                return "Pinpad error: The address is outside limits";
            case -111505:
                return "Pinpad error: The value is outside limits";
            case -111506:
                return "Pinpad error: The length is outside limits";
            case -111507:
                return "Pinpad error: The action is not permited in current state";
            case -111508:
                return "Pinpad error: There is no data to be returned";
            case -111509:
                return "Pinpad error: Timeout occurs";
            case -111510:
                return "Pinpad error: Invalid key number";
            case -111511:
                return "Pinpad error: Invalid key attributes(usage)";
            case -111512:
                return "Pinpad error: Calling of non-existing device";
            case -111513:
                return "Pinpad error: (Not used in this FW version)";
            case -111514:
                return "Pinpad error: Pin entering limit exceed";
            case -111515:
                return "Pinpad error: General error in flash commands";
            case -111516:
                return "Pinpad error: General hardware unicreditbulbank error";
            case -111517:
                return "Pinpad error: Invalid code check (Not used in this FW version)";
            case -111518:
                return "Pinpad error: The button 'CANCEL' is pressed";
            case -111519:
                return "Pinpad error: Invalid signature";
            case -111520:
                return "Pinpad error: Invalid data in header";
            case -111521:
                return "Pinpad error: Incorrect password";
            case -111522:
                return "Pinpad error: Invalid key format";
            case -111523:
                return "Pinpad error: General unicreditbulbank error in smart card reader";
            case -111524:
                return "Pinpad error: Error code returned from HAL functions";
            case -111525:
                return "Pinpad error: Invalid key (may not be present)";
            case -111526:
                return "Pinpad error: The PIN length is less than 4 or bigger than 12";
            case -111527:
                return "Pinpad error: Issuer or ICC key invalid remainder length";
            case -111528:
                return "Pinpad error: Not initialized (Not used in this FW version)";
            case -111529:
                return "Pinpad error: Limit is reached (Not used in this FW version)";
            case -111530:
                return "Pinpad error: Invalid sequence (Not used in this FW version)";
            case -111531:
                return "Pinpad error: The action is not permitted";
            case -111532:
                return "Pinpad error: TMK is not loaded. The action cannot be executed";
            case -111533:
                return "Pinpad error: Wrong key format";
            case -111534:
                return "Pinpad error: Duplicated key";
            case -111535:
                return "Pinpad error: General keyboard error";
            case -111536:
                return "Pinpad error: The keyboard is no calibrated.";
            case -111537:
                return "Pinpad error: Keyboard bug detected.";
            case -111538:
                return "Pinpad error: The device is busy try again";
            case -111539:
                return "Pinpad error: Device is tampered";
            case -111540:
                return "Pinpad error: Error in encrypted head";
            case -111541:
                return "Pinpad error: The button 'OK' is pressed";
            case -111542:
                return "Pinpad error: Wrong PAN";
            case -111543:
                return "Pinpad error: Out of memory";
            case -111544:
                return "Pinpad error: EMV error";
            case -111545:
                return "Pinpad error: Cryptographic error";
            case -111546:
                return "Pinpad error: Communication error";
            case -111547:
                return "Pinpad error: Invalid firmware version";
            case -111548:
                return "Pinpad error: Printer is out of paper";
            case -111549:
                return "Pinpad error: Printer is overheated";
            case -111550:
                return "Pinpad error: Device is not connected";
            case -111551:
                return "Pinpad error: Use the chip reader";
            case -111552:
                return "Pinpad error: End the day first";
            case -111554:
                return "Pinpad error: Error from Borica";
            case -111555:
                return "Pinpad error: No connection with pinpad";
            case -111556:
                return "Pinpad error: Success in pinpad unsuccess in ECR";
            case -111557:
                return "Pinpad error: Not configured connection between fiscal device and PinPad";
            case -111558:
                return "Pinpad error: The last transactions are equals or connection is interrupted - try again.";
            case -111559:
                return "Pinpad error: Payment type: debit/credit card via PinPad. In the fiscal receipt is allowed only one payment with such type.";
            case -111560:
                return "Pinpad error: Unknown result of the transaction between fiscal device and PinPad";
            case -111561:
                return "Pinpad error: Pinpad type not configured";
            case -111700:
                return "Pinpad error: Invalid ammount.";
            case -111701:
                return "Pinpad error: Transaction not found.";
            case -111702:
                return "Pinpad error: The file is empty.";
            case -111800:
                return "ERR_SCALE_NOT_RESPOND";
            case -111801:
                return "ERR_SCALE_NOT_CALCULATED";
            case -111802:
                return "ERR_SCALE_WRONG_RESPONSE";
            case -111803:
                return "ERR_SCALE_ZERO_WEIGHT";
            case -111804:
                return "ERR_SCALE_NEGATIVE_WEIGHT";
            case -111805:
                return "ERR_SCALE_T_WRONG_INTF";
            case -111806:
                return "ERR_SCALE_T_CONNECT";
            case -111807:
                return "ERR_SCALE_SEND";
            case -111808:
                return "ERR_SCALE_RECEIVE";
            case -111809:
                return "ERR_SCALE_FILE_GENERATE";
            case -111810:
                return "ERR_SCALE_NOT_CONFIG";
            case -111900:
                return "Communication error wtih NTP server: Cannot make communication";
            case -111901:
                return "Communication error wtih NTP server: The date and time is earlier than the last saved in the fiscal memory";
            case -111902:
                return "Communication error wtih NTP server: Wrong IP address";
            case -112000:
                return "Fiscal printer error: Fiscal printer invalid command";
            case -112001:
                return "Fiscal printer error: Fiscal printer command invalid syntax";
            case -112002:
                return "Fiscal printer error: Command is not permitted";
            case -112003:
                return "Fiscal printer error: Register overflow";
            case -112004:
                return "Fiscal printer error: Wrong date/time";
            case -112005:
                return "Fiscal printer error: PC mode is needed";
            case -112006:
                return "Fiscal printer error: No paper";
            case -112007:
                return "Fiscal printer error: Cover is open";
            case -112008:
                return "Fiscal printer error: Printing mechanism error";
            case -112100:
                return "_ERR_FP_SYNTAX_PARAM_BEGIN";
            case -112101:
                return "Invalid syntax of parameter 1.";
            case -112102:
                return "Invalid syntax of parameter 2.";
            case -112103:
                return "Invalid syntax of parameter 3.";
            case -112104:
                return "Invalid syntax of parameter 4.";
            case -112105:
                return "Invalid syntax of parameter 5.";
            case -112106:
                return "Invalid syntax of parameter 6.";
            case -112107:
                return "Invalid syntax of parameter 7.";
            case -112108:
                return "Invalid syntax of parameter 8.";
            case -112109:
                return "Invalid syntax of parameter 9.";
            case -112110:
                return "Invalid syntax of parameter 10.";
            case -112111:
                return "Invalid syntax of parameter 11.";
            case -112112:
                return "Invalid syntax of parameter 12.";
            case -112113:
                return "Invalid syntax of parameter 13.";
            case -112114:
                return "Invalid syntax of parameter 14.";
            case -112115:
                return "Invalid syntax of parameter 15.";
            case -112116:
                return "Invalid syntax of parameter 16.";
            case -112199:
                return "_ERR_FP_SYNTAX_PARAM_END";
            case -112200:
                return "_ERR_FP_BAD_PARAM_BEGIN";
            case -112201:
                return "Bad value of parameter 1.";
            case -112202:
                return "Bad value of parameter 2.";
            case -112203:
                return "Bad value of parameter 3.";
            case -112204:
                return "Bad value of parameter 4.";
            case -112205:
                return "Bad value of parameter 5.";
            case -112206:
                return "Bad value of parameter 6.";
            case -112207:
                return "Bad value of parameter 7.";
            case -112208:
                return "Bad value of parameter 8.";
            case -112209:
                return "Bad value of parameter 9.";
            case -112210:
                return "Bad value of parameter 10.";
            case -112211:
                return "Bad value of parameter 11.";
            case -112212:
                return "Bad value of parameter 12.";
            case -112213:
                return "Bad value of parameter 13.";
            case -112214:
                return "Bad value of parameter 14.";
            case -112215:
                return "Bad value of parameter 15.";
            case -112216:
                return "Bad value of parameter 16.";
            case -112299:
                return "_ERR_FP_BAD_PARAM_END";
            case -112900:
                return "_ERR_RANGE_EM_BEGIN";
            case -112999:
                return "_ERR_RANGE_EM_END";
            case -113000:
                return "Flash memory error: Reading ID error";
            case -113001:
                return "Flash memory error: Sector size error";
            case -114000:
                return "POS- terminal error: Communication channel is closed";
            case -114998:
                return "Crypto module error: Wrong ID numbers";
            case -114999:
                return "Crypto module ?????: Unexpected response";
            case -115000:
                return "Crypto module ?????: CM is disconnected";
            case -115001:
                return "Crypto module ?????: Communication error";
            case -115002:
                return "Crypto module ?????: Timeout";
            case -116000:
                return "Crypto module ?????:Crypto module errors range begin.";
            case -117999:
                return "Crypto module ?????:Crypto module errors range end.";
            case -118000:
                return "ECR server error: The connection socket is not openFiscalReceipt";
            case -118001:
                return "ECR server error: The set for this command is not opened";
            case -118002:
                return "ECR server error: Wrong parameter";
            case -118003:
                return "ECR server error: Socket send error. Could not send data to server";
            case -118004:
                return "ECR server error: Receiving timeout. No data is receivec on time";
            case -118005:
                return "ECR server error: Socket is closed";
            case -118006:
                return "ECR server error: Unknown state";
            case -118007:
                return "ECR server error: Forbidden operation";
            case -170000:
                return "USB error: Host init error";
            case -170001:
                return "USB error: No device";
            case -170002:
                return "USB error: No filesystem";
            case -170003:
                return "USB error: File openFiscalReceipt error";
            case -170004:
                return "USB error: File copy error";
            case -170005:
                return "USB error: File unpack error";
            case -110225:
                return "NAP server error: Device is blocked( unsent sales documents )";
            case -110226:
                return "NAP server error: Communication with NAP server is blocked. More than 24 hours from last sent receipt.";
                /******/
            case -111077:
                return "The receipt date is early on last date in fiscal memory.";
            case -111078:
                return "Correction receipt data is not entered!";
            case -111079:
                return "Fractional quantity!";
            case -111087:
                return "Disallowed 'признак расчета' (Russia)";
            case -111088:
                return "Forbidden признак товара";
            case -111703:
                return "Entered cashback is bigger than cashback limit.";
            case -120000:
                return "Programming: Name is not unique!";
            case -120001:
                return "Programming: Operator password is not unique!";
            case -120002:
                return "Programming: Date and time is under the range.";
            case -121000:
                return "Barcode scanner reading error!";
            case -121001:
                return "Invalid EIK/EGN number!";
            case -171000:
                return "Rental database: Not found";
            case -171001:
                return "Rental database: Full";
            case -171002:
                return "Rental database: Position is occupied";
            case -171003:
                return "Rental database: Position is free";
            case -171004:
                return "Rental database: Subscription is active";
            case 0:
                return "Operation successful.";
            default:
                return "Datecs FP V2: not recognized error code " + errorCode;
        }
    }
}
