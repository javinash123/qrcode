package com.simprosys.scan.qrcode.barcode.reader.utility.barcodeUtils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;
import com.simprosys.scan.qrcode.barcode.reader.utility.barcodeUtils.barcode.BarcodeCommon;
import com.simprosys.scan.qrcode.barcode.reader.utility.barcodeUtils.barcode.IBarcode;

import java.util.HashMap;

import static com.simprosys.scan.qrcode.barcode.reader.utility.Utility.getChar;

/**
 * Code 39 encoding
 */
public class Code39Barcode extends BarcodeCommon implements IBarcode {
    private HashMap<Character, String> C39_Code = new HashMap<>();
    private HashMap<String, String> ExtC39_Translation = new HashMap<>();
    private boolean _AllowExtended = false;
    private boolean _EnableChecksum = false;
    private Context context;

    public Code39Barcode(Context context) {
        this.context = context;
    }

    public Code39Barcode(String input) {
        setRawData(input);
    }

    public Code39Barcode(String input, boolean AllowExtended) {
        setRawData(input);
        _AllowExtended = AllowExtended;
    }

    public Code39Barcode(String input, boolean AllowExtended, boolean EnableChecksum) {
        setRawData(input);
        _AllowExtended = AllowExtended;
        _EnableChecksum = EnableChecksum;
    }

    private String encodeCode39() {
        this.init_Code39();
        this.init_ExtendedCode39();

        String strNoAstr = getRawData().replace("*", "");
        String strFormattedData = "*" + strNoAstr + (_EnableChecksum ? getChecksumChar(strNoAstr) : "") + "*";

        if (_AllowExtended) {
            InsertExtendedCharsIfNeeded(strFormattedData);
        }

        StringBuilder result = new StringBuilder();
        for (char c : strFormattedData.toCharArray()) {
            try {
                result.append(C39_Code.get(c));
                result.append("0");
            } catch (Exception ex) {
                if (_AllowExtended) {
                    //error(context.getResources().getString(R.string.msgCode39InValid));
                } else {
                    //error(context.getResources().getString(R.string.msgCode39InValid));
                }
            }
        }

        result = new StringBuilder(result.substring(0, result.length() - 1));

        this.C39_Code.clear();

        return result.toString();
    }

    private void init_Code39() {
        C39_Code.clear();
        C39_Code.put('0', "101001101101");
        C39_Code.put('1', "110100101011");
        C39_Code.put('2', "101100101011");
        C39_Code.put('3', "110110010101");
        C39_Code.put('4', "101001101011");
        C39_Code.put('5', "110100110101");
        C39_Code.put('6', "101100110101");
        C39_Code.put('7', "101001011011");
        C39_Code.put('8', "110100101101");
        C39_Code.put('9', "101100101101");
        C39_Code.put('A', "110101001011");
        C39_Code.put('B', "101101001011");
        C39_Code.put('C', "110110100101");
        C39_Code.put('D', "101011001011");
        C39_Code.put('E', "110101100101");
        C39_Code.put('F', "101101100101");
        C39_Code.put('G', "101010011011");
        C39_Code.put('H', "110101001101");
        C39_Code.put('I', "101101001101");
        C39_Code.put('J', "101011001101");
        C39_Code.put('K', "110101010011");
        C39_Code.put('L', "101101010011");
        C39_Code.put('M', "110110101001");
        C39_Code.put('N', "101011010011");
        C39_Code.put('O', "110101101001");
        C39_Code.put('P', "101101101001");
        C39_Code.put('Q', "101010110011");
        C39_Code.put('R', "110101011001");
        C39_Code.put('S', "101101011001");
        C39_Code.put('T', "101011011001");
        C39_Code.put('U', "110010101011");
        C39_Code.put('V', "100110101011");
        C39_Code.put('W', "110011010101");
        C39_Code.put('X', "100101101011");
        C39_Code.put('Y', "110010110101");
        C39_Code.put('Z', "100110110101");
        C39_Code.put('-', "100101011011");
        C39_Code.put('.', "110010101101");
        C39_Code.put(' ', "100110101101");
        C39_Code.put('$', "100100100101");
        C39_Code.put('/', "100100101001");
        C39_Code.put('+', "100101001001");
        C39_Code.put('%', "101001001001");
        C39_Code.put('*', "100101101101");
    }

    private void init_ExtendedCode39() {
        ExtC39_Translation.clear();
        ExtC39_Translation.put(getChar(0), "%U");
        ExtC39_Translation.put(getChar(1), "$A");
        ExtC39_Translation.put(getChar(2), "$B");
        ExtC39_Translation.put(getChar(3), "$C");
        ExtC39_Translation.put(getChar(4), "$D");
        ExtC39_Translation.put(getChar(5), "$E");
        ExtC39_Translation.put(getChar(6), "$F");
        ExtC39_Translation.put(getChar(7), "$G");
        ExtC39_Translation.put(getChar(8), "$H");
        ExtC39_Translation.put(getChar(9), "$I");
        ExtC39_Translation.put(getChar(10), "$J");
        ExtC39_Translation.put(getChar(11), "$K");
        ExtC39_Translation.put(getChar(12), "$L");
        ExtC39_Translation.put(getChar(13), "$M");
        ExtC39_Translation.put(getChar(14), "$N");
        ExtC39_Translation.put(getChar(15), "$O");
        ExtC39_Translation.put(getChar(16), "$P");
        ExtC39_Translation.put(getChar(17), "$Q");
        ExtC39_Translation.put(getChar(18), "$R");
        ExtC39_Translation.put(getChar(19), "$S");
        ExtC39_Translation.put(getChar(20), "$T");
        ExtC39_Translation.put(getChar(21), "$U");
        ExtC39_Translation.put(getChar(22), "$V");
        ExtC39_Translation.put(getChar(23), "$W");
        ExtC39_Translation.put(getChar(24), "$X");
        ExtC39_Translation.put(getChar(25), "$Y");
        ExtC39_Translation.put(getChar(26), "$Z");
        ExtC39_Translation.put(getChar(27), "%A");
        ExtC39_Translation.put(getChar(28), "%B");
        ExtC39_Translation.put(getChar(29), "%C");
        ExtC39_Translation.put(getChar(30), "%D");
        ExtC39_Translation.put(getChar(31), "%E");
        ExtC39_Translation.put("!", "/A");
        ExtC39_Translation.put("\"", "/B");
        ExtC39_Translation.put("#", "/C");
        ExtC39_Translation.put("$", "/D");
        ExtC39_Translation.put("%", "/E");
        ExtC39_Translation.put("&", "/F");
        ExtC39_Translation.put("'", "/G");
        ExtC39_Translation.put("(", "/H");
        ExtC39_Translation.put(")", "/I");
        ExtC39_Translation.put("*", "/J");
        ExtC39_Translation.put("+", "/K");
        ExtC39_Translation.put(",", "/L");
        ExtC39_Translation.put("/", "/O");
        ExtC39_Translation.put(":", "/Z");
        ExtC39_Translation.put(";", "%F");
        ExtC39_Translation.put("<", "%G");
        ExtC39_Translation.put("=", "%H");
        ExtC39_Translation.put(">", "%I");
        ExtC39_Translation.put("?", "%J");
        ExtC39_Translation.put("[", "%K");
        ExtC39_Translation.put("\\", "%L");
        ExtC39_Translation.put("]", "%M");
        ExtC39_Translation.put("^", "%N");
        ExtC39_Translation.put("_", "%O");
        ExtC39_Translation.put("{", "%P");
        ExtC39_Translation.put("|", "%Q");
        ExtC39_Translation.put("}", "%R");
        ExtC39_Translation.put("~", "%S");
        ExtC39_Translation.put("`", "%W");
        ExtC39_Translation.put("@", "%V");
        ExtC39_Translation.put("a", "+A");
        ExtC39_Translation.put("b", "+B");
        ExtC39_Translation.put("c", "+C");
        ExtC39_Translation.put("d", "+D");
        ExtC39_Translation.put("e", "+E");
        ExtC39_Translation.put("f", "+F");
        ExtC39_Translation.put("g", "+G");
        ExtC39_Translation.put("h", "+H");
        ExtC39_Translation.put("i", "+I");
        ExtC39_Translation.put("j", "+J");
        ExtC39_Translation.put("k", "+K");
        ExtC39_Translation.put("l", "+L");
        ExtC39_Translation.put("m", "+M");
        ExtC39_Translation.put("n", "+N");
        ExtC39_Translation.put("o", "+O");
        ExtC39_Translation.put("p", "+P");
        ExtC39_Translation.put("q", "+Q");
        ExtC39_Translation.put("r", "+R");
        ExtC39_Translation.put("s", "+S");
        ExtC39_Translation.put("t", "+T");
        ExtC39_Translation.put("u", "+U");
        ExtC39_Translation.put("v", "+V");
        ExtC39_Translation.put("w", "+W");
        ExtC39_Translation.put("x", "+X");
        ExtC39_Translation.put("y", "+Y");
        ExtC39_Translation.put("z", "+Z");
        ExtC39_Translation.put(getChar(127), "%T");
    }

    private void InsertExtendedCharsIfNeeded(String FormattedData) {
        StringBuilder output = new StringBuilder();
        for (char c : FormattedData.toCharArray()) {
            try {
                String s = C39_Code.get(c);
                output.append(c);
            } catch (Exception ex) {
                Object oTrans = ExtC39_Translation.get(String.valueOf(c));
                output.append(oTrans.toString());
            }
        }

        FormattedData = output.toString();
    }

    private char getChecksumChar(String strNoAstr) {
        String Code39_Charset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%";
        InsertExtendedCharsIfNeeded(strNoAstr);
        int sum = 0;

        for (int i = 0; i < strNoAstr.length(); ++i) {
            sum = sum + Code39_Charset.indexOf(strNoAstr.toCharArray()[i]);
        }

        return Code39_Charset.toCharArray()[sum % 43];
    }

    @Override
    public String getEncodedValue() {
        return encodeCode39();
    }
}
