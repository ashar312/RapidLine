package com.project.rapidline.Activities.SaeedSons.Forms;
import java.util.Locale;

public class AddBailNumberGenrator {
    private static final String TAG = "AddBailNumberGenrator";
    private String previousNo;
    private char alpha;
    private int alphaPos;
    private String number;


    public AddBailNumberGenrator(String previousNo) {
        this.previousNo = previousNo;


    }

    private String charRemoveAt(String str, int p) {
        return str.substring(0, p) + str.substring(p + 1);
    }


    private void splitNumberAndAlpha() {
        //split alpha
        for (char alphabet : previousNo.toUpperCase().toCharArray()) {
            if (alphabet >= 'A' && alphabet <= 'Z') {
                alpha = alphabet;
                alphaPos = previousNo.indexOf(alpha);
                break;
            }
        }

        number = charRemoveAt(previousNo, alphaPos);
    }

    private void incrementNumber() {

        if (number.equals(generateMaxNumber(number.length()))) {

            if (alpha == 'Z' && alphaPos == 0) {
                String formatPattern = "%0" + (number.length() + 1) + "d";
                number = String.format(Locale.ENGLISH, formatPattern, 0);
            } else {
                String formatPattern = "%0" + (number.length()) + "d";
                number = String.format(Locale.ENGLISH, formatPattern, 0);
            }

        } else {

            System.out.println("length num"+number.length());
            //format number
            String formatPattern = "%0" + (number.length()) + "d";
            number = String.format(formatPattern, Integer.parseInt(this.number)+1);


        }
        System.out.println("increment number:" + number);

    }

    private String generateMaxNumber(int length) {
        String number = "";
        for (int i = 0; i < length; i++) {
            number += "9";
        }
        return number;
    }

    private void incrementAlpha() {
        if (alpha == 'Z') {
            if (alphaPos == 0) {
                alpha = 'A';
                alphaPos = number.length();
            } else {
                alpha = 'A';
                alphaPos = alphaPos - 1;
            }
        } else {
            //regex for all zeros
            if (number.matches("^[0]+$"))
                alpha++;
        }
        System.out.println("increment alpha:" + alpha);
    }

    private String addCharAt(String str, char ch, int position) {
        return str.substring(0, position) + ch + str.substring(position);
    }

    public String generateBailNumber() {
        System.out.println("input number:"+previousNo);
        splitNumberAndAlpha();
        incrementNumber();
        incrementAlpha();

        return addCharAt(number, alpha, alphaPos);
    }

//    public static void main(String[] args) {
//        AddBailNumberGenrator numberGenrator = new AddBailNumberGenrator("000A");
//
//        System.out.println(numberGenrator.generateBailNumber());
//
//    }

}

