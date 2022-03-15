package Main.java;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String[] arab = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] rim = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        System.out.println("Введите выражение в виде сложения двух чисел от 0 до 10 арабскими или римскими цифрами:");
        Scanner in = new Scanner(System.in);
        String val = in.nextLine();
        String prim = val.toUpperCase();
        prim = prim.trim();
        ArabRimProverka pr = new ArabRimProverka(prim, arab, rim);
        Oper op = new Oper(pr.getSt());
        if (pr.getV() == 'a'){
            int a1 = Integer.parseInt(op.getS1());
            int a2 = Integer.parseInt(op.getS2());
            Result res = new Result(a1, a2, op.getOp());
            System.out.println(res.getA());
        }else
        if (pr.getV() == 'r'){
            RimToInt a1 = new RimToInt(op.getS1());
            RimToInt a2 = new RimToInt(op.getS2());
            Result res = new Result(a1.getRtI(), a2.getRtI(), op.getOp());
            IntToRim itr = new IntToRim(res.getA());
            System.out.println(itr.getIt());
        }
        in.close();
    }
}
class ArabRimProverka {
    char ch;
    String s;
    String ar;
    ArabRimProverka(String str, String[] a, String[] b) {
        int k;
        Pattern pattern = Pattern.compile("\\+|\\-|\\*|\\/");
        String[] word = pattern.split(str);
        k = word[0].length();
        s = str.substring(k, k+1);
        ar = word[0] + s + word[1];
        if (word.length > 2){
            System.out.println("В выражении больше 2 элементов. Так нельзя");
        }
        for (int i = 0; i < a.length; i++) {
            if (str.substring(0, 1).equals(a[i])) {
                ch = 'a';return;
            }
        }
        for (int i = 0; i < b.length; i++) {
            if (str.substring(0, 1).equals(b[i])) {
                ch = 'r';
            }
        }
    }
    String getSt(){return ar;}
    char getV(){
        return ch;
    }
}
class Oper {
    String s1, s2;
    char ch;
    int a, b;
    Oper(String st) {
        Pattern pattern = Pattern.compile("\\+|\\-|\\*|\\/");
        String[] word = pattern.split(st);
        s1 = word[0];
        s2 = word[1];
        if (st.contains("+")) {
            ch = '+';
        } else if (st.contains("-")) {
            ch = '-';
        } else if (st.contains("*")) {
            ch = '*';
        } else if (st.contains("/")) {
            ch = '/';
        }
    }
    String getS1(){return s1;}
    String getS2(){return s2;}
    char getOp () {return ch;}
}
class Result{
    int res = 0;
    Result(int t1, int t2, char ch) {
        switch (ch) {
            case '+':
                res = t1 + t2; return;
            case '-':
                res = t1 - t2; return;
            case '*':
                res = t1 * t2; return;
            case '/':
                res = t1 / t2;
        }
    }
    int getA () {
        return res;
    }
}
class RimToInt {
    int d;
    RimToInt(String str) {
        char[] x = str.toCharArray();
        int lenx = x.length;
        int[] masInt = new int[lenx];
        for (int i = 0; i < lenx; i++) {
            switch (x[i]) {
                case 'C':
                    d = 100; break;
                case 'L':
                    d = 50; break;
                case 'X':
                    d = 10; break;
                case 'V':
                    d = 5; break;
                case 'I':
                    d = 1; break;
            }
            masInt[i] = d;
        }
        d = masInt[0];
        for (int i = 1; i < lenx; i++) {
            if (masInt[i] <= masInt[i - 1]) {
                d = d + masInt[i];
            }
            else d = d + masInt[i] - masInt[i-1] * 2;
        }
    }
    int getRtI (){
        return d;
    }
}
class IntToRim{
    String[] rim = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    String[] c = new String[]{"I", "V", "X", "L", "C"};
    String it = "";
    IntToRim(int num){
        int nc, nf, nt, nd;
        nc = num / 100;
        nd = num % 100;
        if (nc == 0) {
            nf = num / 50;
            if (nf == 0) {
                nt = num / 10;
                nd = num % 10;
            }
            else {
                nt = num / 10;
                nd = num % 10;
            }
        }
        else {
            nf = nd / 50;
            if (nf == 0) {
                nt = nd / 10;
                nd = nd % 10;
            }
            else {
                nd = nd % 50;
                nt = nd / 10;
                nd = nd % 10;
            }
        }
        for (int i = 0; i < nc; i++){
            it = it + c[4];
        }
        if (nf == 1) {it = it + c[3];}
        if (nt == 4){it = it +  c[2] + c[3];}
        else {
            for (int i = 0; i < nt; i++) {
                it = it + c[2];
            }
        }
        if (nd != 0){it = it + rim[nd-1];}
    }
    String getIt(){return it;}
}