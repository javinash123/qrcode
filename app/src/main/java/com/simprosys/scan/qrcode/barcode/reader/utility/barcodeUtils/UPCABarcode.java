package com.simprosys.scan.qrcode.barcode.reader.utility.barcodeUtils;

import android.content.Context;

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.barCodeFragment.UPCAFragment;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;
import com.simprosys.scan.qrcode.barcode.reader.utility.barcodeUtils.barcode.BarcodeCommon;
import com.simprosys.scan.qrcode.barcode.reader.utility.barcodeUtils.barcode.IBarcode;

import java.util.HashMap;

/**
 * Created by simprosys on 8/3/19.
 */

public class UPCABarcode extends BarcodeCommon implements IBarcode {
    private String[] UPC_CodeA = {"0001101", "0011001", "0010011", "0111101", "0100011", "0110001", "0101111", "0111011", "0110111", "0001011"};
    private String[] UPC_CodeB = {"1110010", "1100110", "1101100", "1000010", "1011100", "1001110", "1010000", "1000100", "1001000", "1110100"};

    private String countryAssigningManufacturerCode = "N/A";

    private HashMap<String, String> countryCodes = new HashMap<>();

    private Class TAG = UPCAFragment.class;

    private Context context;

    public UPCABarcode(String input, Context context) {
        setRawData(input);
        this.context = context;
    }

    private String encodeUPCA() {
        if (getRawData().length() != 11 && getRawData().length() != 12) {
            error(context.getResources().getString(R.string.msgUPCABarcode1));
        }

        if (!checkNumericOnly(getRawData())) {
            Utility.logE(TAG, "EUPCA-2: Numeric Data Only");
            error(context.getResources().getString(R.string.msgUPCABarcode2));
        }

        calculateCheckDigit();

        StringBuilder result = new StringBuilder("101");
        result.append(UPC_CodeA[Integer.parseInt(String.valueOf(getRawData().toCharArray()[0]))]);

        int pos = 0;
        while (pos < 5) {
            result.append(UPC_CodeA[Integer.parseInt(String.valueOf(getRawData().toCharArray()[pos + 1]))]);
            pos++;
        }

        result.append("01010");

        pos = 0;
        while (pos < 5) {
            result.append(UPC_CodeB[Integer.parseInt(String.valueOf(getRawData().toCharArray()[(pos++) + 6]))]);
        }

        result.append(UPC_CodeB[Integer.parseInt(String.valueOf(getRawData().toCharArray()[getRawData().length() - 1]))]);

        result.append("101");

        this.init_CountryCodes();

        String twoDigitCode = "0" + getRawData().substring(0, 1);
        try {
            countryAssigningManufacturerCode = countryCodes.get(twoDigitCode);
        } catch (Exception ex) {
            error(context.getResources().getString(R.string.msgUPCABarcode3));
        } finally {
            countryCodes.clear();
        }
        return result.toString();
    }

    private void init_CountryCodes() {
        countryCodes.clear();
        countryCodes.put("00", "US / CANADA");
        countryCodes.put("01", "US / CANADA");
        countryCodes.put("02", "US / CANADA");
        countryCodes.put("03", "US / CANADA");
        countryCodes.put("04", "US / CANADA");
        countryCodes.put("05", "US / CANADA");
        countryCodes.put("06", "US / CANADA");
        countryCodes.put("07", "US / CANADA");
        countryCodes.put("08", "US / CANADA");
        countryCodes.put("09", "US / CANADA");
        countryCodes.put("10", "US / CANADA");
        countryCodes.put("11", "US / CANADA");
        countryCodes.put("12", "US / CANADA");
        countryCodes.put("13", "US / CANADA");

        countryCodes.put("20", "IN STORE");
        countryCodes.put("21", "IN STORE");
        countryCodes.put("22", "IN STORE");
        countryCodes.put("23", "IN STORE");
        countryCodes.put("24", "IN STORE");
        countryCodes.put("25", "IN STORE");
        countryCodes.put("26", "IN STORE");
        countryCodes.put("27", "IN STORE");
        countryCodes.put("28", "IN STORE");
        countryCodes.put("29", "IN STORE");

        countryCodes.put("30", "FRANCE");
        countryCodes.put("31", "FRANCE");
        countryCodes.put("32", "FRANCE");
        countryCodes.put("33", "FRANCE");
        countryCodes.put("34", "FRANCE");
        countryCodes.put("35", "FRANCE");
        countryCodes.put("36", "FRANCE");
        countryCodes.put("37", "FRANCE");

        countryCodes.put("40", "GERMANY");
        countryCodes.put("41", "GERMANY");
        countryCodes.put("42", "GERMANY");
        countryCodes.put("43", "GERMANY");
        countryCodes.put("44", "GERMANY");

        countryCodes.put("45", "JAPAN");
        countryCodes.put("46", "RUSSIAN FEDERATION");
        countryCodes.put("49", "JAPAN (JAN-13)");

        countryCodes.put("50", "UNITED KINGDOM");
        countryCodes.put("54", "BELGIUM / LUXEMBOURG");
        countryCodes.put("57", "DENMARK");

        countryCodes.put("64", "FINLAND");

        countryCodes.put("70", "NORWAY");
        countryCodes.put("73", "SWEDEN");
        countryCodes.put("76", "SWITZERLAND");

        countryCodes.put("80", "ITALY");
        countryCodes.put("81", "ITALY");
        countryCodes.put("82", "ITALY");
        countryCodes.put("83", "ITALY");
        countryCodes.put("84", "SPAIN");
        countryCodes.put("87", "NETHERLANDS");

        countryCodes.put("90", "AUSTRIA");
        countryCodes.put("91", "AUSTRIA");
        countryCodes.put("93", "AUSTRALIA");
        countryCodes.put("94", "NEW ZEALAND");
        countryCodes.put("99", "COUPONS");

        countryCodes.put("471", "TAIWAN");
        countryCodes.put("474", "ESTONIA");
        countryCodes.put("475", "LATVIA");
        countryCodes.put("477", "LITHUANIA");
        countryCodes.put("479", "SRI LANKA");
        countryCodes.put("480", "PHILIPPINES");
        countryCodes.put("482", "UKRAINE");
        countryCodes.put("484", "MOLDOVA");
        countryCodes.put("485", "ARMENIA");
        countryCodes.put("486", "GEORGIA");
        countryCodes.put("487", "KAZAKHSTAN");
        countryCodes.put("489", "HONG KONG");

        countryCodes.put("520", "GREECE");
        countryCodes.put("528", "LEBANON");
        countryCodes.put("529", "CYPRUS");
        countryCodes.put("531", "MACEDONIA");
        countryCodes.put("535", "MALTA");
        countryCodes.put("539", "IRELAND");
        countryCodes.put("560", "PORTUGAL");
        countryCodes.put("569", "ICELAND");
        countryCodes.put("590", "POLAND");
        countryCodes.put("594", "ROMANIA");
        countryCodes.put("599", "HUNGARY");

        countryCodes.put("600", "SOUTH AFRICA");
        countryCodes.put("601", "SOUTH AFRICA");
        countryCodes.put("609", "MAURITIUS");
        countryCodes.put("611", "MOROCCO");
        countryCodes.put("613", "ALGERIA");
        countryCodes.put("619", "TUNISIA");
        countryCodes.put("622", "EGYPT");
        countryCodes.put("625", "JORDAN");
        countryCodes.put("626", "IRAN");
        countryCodes.put("690", "CHINA");
        countryCodes.put("691", "CHINA");
        countryCodes.put("692", "CHINA");

        countryCodes.put("729", "ISRAEL");
        countryCodes.put("740", "GUATEMALA");
        countryCodes.put("741", "EL SALVADOR");
        countryCodes.put("742", "HONDURAS");
        countryCodes.put("743", "NICARAGUA");
        countryCodes.put("744", "COSTA RICA");
        countryCodes.put("746", "DOMINICAN REPUBLIC");
        countryCodes.put("750", "MEXICO");
        countryCodes.put("759", "VENEZUELA");
        countryCodes.put("770", "COLOMBIA");
        countryCodes.put("773", "URUGUAY");
        countryCodes.put("775", "PERU");
        countryCodes.put("777", "BOLIVIA");
        countryCodes.put("779", "ARGENTINA");
        countryCodes.put("780", "CHILE");
        countryCodes.put("784", "PARAGUAY");
        countryCodes.put("785", "PERU");
        countryCodes.put("786", "ECUADOR");
        countryCodes.put("789", "BRAZIL");

        countryCodes.put("850", "CUBA");
        countryCodes.put("858", "SLOVAKIA");
        countryCodes.put("859", "CZECH REPUBLIC");
        countryCodes.put("860", "YUGLOSLAVIA");
        countryCodes.put("869", "TURKEY");
        countryCodes.put("880", "SOUTH KOREA");
        countryCodes.put("885", "THAILAND");
        countryCodes.put("888", "SINGAPORE");
        countryCodes.put("890", "INDIA");
        countryCodes.put("893", "VIETNAM");
        countryCodes.put("899", "INDONESIA");

        countryCodes.put("955", "MALAYSIA");
        countryCodes.put("977", "INTERNATIONAL STANDARD SERIAL NUMBER FOR PERIODICALS (ISSN)");
        countryCodes.put("978", "INTERNATIONAL STANDARD BOOK NUMBERING (ISBN)");
        countryCodes.put("979", "INTERNATIONAL STANDARD MUSIC NUMBER (ISMN)");
        countryCodes.put("980", "REFUND RECEIPTS");
        countryCodes.put("981", "COMMON CURRENCY COUPONS");
        countryCodes.put("982", "COMMON CURRENCY COUPONS");
    }

    private void calculateCheckDigit() {
        try {
            String rawDataHolder = getRawData().substring(0, 11);

            int even = 0;
            int odd = 0;

            for (int i = 0; i < rawDataHolder.length(); i++) {
                if (i % 2 == 0) {
                    odd += Integer.parseInt(rawDataHolder.substring(i, i + 1)) * 3;
                } else {
                    even += Integer.parseInt(rawDataHolder.substring(i, i + 1));
                }
            }

            int total = even + odd;
            int cs = total % 10;
            cs = 10 - cs;
            if (cs == 10) {
                cs = 0;
            }

            setRawData(rawDataHolder + String.valueOf(cs).toCharArray()[0]);
        } catch (Exception ex) {
            error(context.getResources().getString(R.string.msgUPCABarcode4));
        }
    }

    @Override
    public String getEncodedValue() {
        return encodeUPCA();
    }
}
