import java.util.Scanner;

public class Solution {
    public static void main(String[] args) throws Exception{
        Lineclass newline = new Lineclass();
        try {
            newline.getByConsole();
            newline.getAnswer();
        }catch(Exception ex){
            throw ex;
        }
    }
}
// Класс для получения начальных значений
class StartData{
    String methodArr[] = new String[] {"+", "-", "/", "*"};
    String romeArr[] = new String[] {"I", "V", "X", "L", "C"};
    int arabArr[] = new int[] {1, 5, 10, 50, 100};
}

// Класс для работы с числами (распознавание, обработка)
class Number{
    String newLine;
    String romeN;
    int arabicN;
    String numberType;
    Number(String num) throws Exception{
        newLine = num;
        try {
            digit(num);
        }catch(Exception ex){
            throw ex;
        }
    }
    Number(int num){
        arabicN = num;
    }
    // Проверка чисел (являются или не являются арабскими)
    void digit(String num) throws Exception{
        newLine = num;
        try{
            arabicN = Integer.parseInt(num);
            numberType = "arabic";
            if(arabicN > 10) throw new Exception("Too big number");
        }catch (NumberFormatException e){
            if(newLine.matches("[IVXLC]+")) {
                romeN = num;
                numberType = "rome";
            }else{
                throw new Exception("Unknown symbols");
            }
        }
    }
    // Перевод римского числа в арабское
    void transforming(String strToTransform) throws Exception{
        String romes[] = new StartData().romeArr;
        int arabics[] = new StartData().arabArr;
        String transformString = new String(strToTransform);
        int lenght = transformString.length();
        if (lenght > 0) {
            for (int i = 4; i >= 0; i--){
                String str = romes[i];
                int index = transformString.indexOf(str);
                if (index != -1){
                    int arg;
                    // При расширении перечня возможных чисел нужно добавить циклы
                    switch(index){
                        case 0:
                            arg = arabics[i];
                            break;
                        case 1:
                            arg = arabics[i] - 1;
                            break;
                        default:
                            throw new Exception("Error in rome syntax");
                    }
                    arabicN = arabicN + arg;
                    if (arabicN > 10) throw new Exception("Too big number");
                    if (index < (transformString.length() - 1)){
                        transformString = transformString.substring(index + 1);
                        transforming(transformString);
                        transformString = "";
                    }else{
                        transformString = "";
                        break;
                    }
                }
            }
        }
    }
    void transformToArabic() throws Exception{
        arabicN = 0;
        try {
            transforming(romeN);
        }catch (Exception ex){
            throw ex;
        }
    }
    // Перевод арабского числа в римское
    void transformToRome(){
        int arabicdata[] = new StartData().arabArr;
        String romedata [] = new StartData().romeArr;
        String newanswer = "";
        int degree10;
        for (int i = 4; i >= 0; i = i-2){
            if (arabicN >= arabicdata[i]){
                degree10 = i / 2;
                int arr[] = new int[degree10 + 1];
                int remainder = arabicN;
                for (int a = arr.length - 1; a >= 0 ; a--){
                    arr[a] = remainder / (int)(Math.pow(10, a));
                    remainder = remainder - (arr[a] * (int)(Math.pow(10, a)));
                    if (remainder >= 0) {
                        switch (arr[a]) {
                            case 1, 2, 3:
                                for (int с = 0; с < arr[a]; с++) {
                                    newanswer = newanswer + romedata[a * 2];
                                }
                                break;
                            case 4:
                                newanswer = newanswer + romedata[(a * 2)] + romedata[(a * 2) + 1];
                                break;
                            case 5:
                                newanswer = newanswer + romedata[(a * 2) + 1];
                                break;
                            case 6, 7, 8:
                                newanswer = newanswer + romedata[(a * 2) + 1];
                                for (int с = 0; с < arr[a] - 5; с++) {
                                    newanswer = newanswer + romedata[(a * 2)];
                                }
                                break;
                            case 9:
                                newanswer = newanswer + romedata[(a * 2)] + romedata[(a * 2) + 2];
                                break;
                        }
                    }else{
                        System.out.println(newanswer);
                        break;
                    }
                }
                break;
            }
        }
        romeN = newanswer;
    }
}

// Класс для работы со строкой (Получение, разбор и сборка строки)
class Lineclass {
    String line;
    String method;
    String linetype;
    int num1;
    int num2;
    int arabicanswer;
    String romeanswer;
    // Получение ввода с консоли и приведение к единому виду
    void getByConsole() throws Exception{
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()){
            line = scanner.nextLine();
            line = line.toUpperCase();
            line = line.replace(" ", "");
        }else
            throw new Exception("Unreadable string");
    }
    // Определение применяемого математического метода по массиву
    void methodDetection() throws Exception{
        String methods[] = new StartData().methodArr;
        for (String a : methods) {
            int indexOfMethodChar = line.indexOf(a);
            if (indexOfMethodChar != -1 && method==null) {
                method = a;
                if (line.length() > indexOfMethodChar + 1) {
                    String subline = line.substring(indexOfMethodChar + 1);
                    indexOfMethodChar = subline.indexOf(a);
                    if (indexOfMethodChar != -1){
                        throw new Exception("Too many methods");
                    }
                }
            }else if(indexOfMethodChar != -1){
                throw new Exception("Too many methods");
            }
        }
        if (method == null) throw new Exception("Unknown method");
    }
    // Парсинг строки, распознавание значений
    void lineParsing() throws Exception{
        try {
            this.methodDetection();
        }catch(Exception ex){
            throw ex;
        }
        String sep = String.format("\\%s", method);
        String numbers[] = line.split(sep);
        if (numbers.length != 2) throw new Exception("The number of numbers is not enough");
        try {
            Number newNumber1 = new Number(numbers[0]), newNumber2 = new Number(numbers[1]);
            if (newNumber1.numberType.equals("arabic") || newNumber2.numberType.equals("arabic")) {
                if (newNumber1.numberType.equals("arabic") && newNumber2.numberType.equals("arabic")){
                    linetype = "arabic";
                    num1 = newNumber1.arabicN;
                    num2 = newNumber2.arabicN;
                }else{
                    throw new Exception("Different types of numbers are not allowed");
                }
            }else {
                linetype = "rome";
                newNumber1.transformToArabic();
                newNumber2.transformToArabic();
                num1 = newNumber1.arabicN;
                num2 = newNumber2.arabicN;
            }
        } catch(Exception ex){
            throw ex;
        }
    }
    // Получение математического ответа
    void calc() throws Exception{
        try {
            lineParsing();
        }catch (Exception ex){
            throw ex;
        }
        if (method.equals("*")){
            arabicanswer = num1 * num2;
        }else if (method.equals("/")){
            arabicanswer = num1 / num2;
        }else if (method.equals("+")){
            arabicanswer = num1 + num2;
        }else if (method.equals("-")){
            arabicanswer = num1 - num2;
        }else{
            throw new Exception("Unknown method");
        }
    }
    // Перевод ответа к нужной форме
    void getAnswer() throws Exception{
        try {
            calc();
        }catch(Exception ex){
            throw ex;
        }
        if (linetype.equals("arabic")){
            System.out.println(arabicanswer);
        }else{
            if (arabicanswer > 0){
                Number answer = new Number(arabicanswer);
                answer.transformToRome();
                romeanswer = answer.romeN;
                System.out.println(romeanswer);
            }else{
                throw new Exception("Answer <= 0 not allowed for rome numbers");
            }
        }
    }
}