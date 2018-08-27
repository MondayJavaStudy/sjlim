package com.woowahan.study.java.sumGame;

import java.io.*;
import java.util.*;

// 20180820 javastudy - testable
public class sumGameTestable {
    public static void main(String[] args) throws Exception {
        doIt(new InputStreamReader(System.in), new OutputStreamWriter(System.out));
    }

    public static void doIt(Reader reader, Writer writer) throws Exception{
        PrintWriter pw = new PrintWriter(writer);
        ArrayList<Integer> numberCardList = new ArrayList<Integer>();    // 숫자카드
        int correctNumber = 0;                                           // 술래가 외친 숫자

        ArrayList<resultNumbers> results;   // (정답하나 셋트의 리스트) 전체 정답리스트

        // 숫자 초기화 (숫자 입력)
        correctNumber = inputNumberListAndCorrectSumNumber(numberCardList, reader);

        // 입력받은 숫자 정렬
        Ascending ascending = new Ascending();
        Collections.sort(numberCardList, ascending);

        // 술래가 외친 숫자보다 큰 숫자는 숫자카드에서 제외함
        removeBigNumberCard(numberCardList, correctNumber);

        // 세 숫자로 원하는 숫자를 만들 수 있는지 확인
        results = equalsNumberAndNumbersSum(numberCardList, correctNumber);
        Set<resultNumbers> set = new HashSet<>(results);

        // 정답 리스트가 있으면 정답 리스트를 출력하고 없으면 NO를 출력
        printResult(results, pw);
    }


    // 표준 입력
    public static int inputNumberListAndCorrectSumNumber(ArrayList<Integer> numbers, Reader reader){
        String strLine1;                            // 첫번째 입력
        String strLine2;                            // 두번째 입력
        Scanner scan = new Scanner(reader);

        System.out.println("숫자 카드 리스트 입력");
        strLine1 = scan.nextLine();

        String[] words = strLine1.split(" ");
        for (String word : words) {
            numbers.add(Integer.parseInt(word));
        }

        System.out.println("술래가 제시한 숫자 입력");
        strLine2 = scan.nextLine();
        return Integer.parseInt(strLine2);
    }

    // 술래가 외친 숫자보다 큰 숫자는 숫자카드에서 제외함
    public static void removeBigNumberCard(ArrayList<Integer> numbers, int correctNumber){
        Iterator<Integer> numbersIterator = numbers.iterator();
        while(numbersIterator.hasNext()) {
            Integer number = numbersIterator.next();
            if (number > correctNumber) {
                numbersIterator.remove();
            }
        }
    }
    // 세 숫자로 원하는 숫자를 만들 수 있는지 확인
    public static ArrayList<resultNumbers> equalsNumberAndNumbersSum(ArrayList<Integer> numbers, int correctNumber) {
        ArrayList<resultNumbers> resultNumbers2 = new ArrayList<resultNumbers>();
        int idx1 = 0, idx2 = 0, idx3 = 0;

        for(idx1=0; idx1 < numbers.size(); idx1++){
            for(idx2=idx1+1; idx2 < numbers.size(); idx2++){
                for(idx3=idx2+1; idx3 < numbers.size(); idx3++){
                    if (correctNumber == (numbers.get(idx1) + numbers.get(idx2) + numbers.get(idx3))) {
                        resultNumbers resultList = new resultNumbers((int) numbers.get(idx1), (int) numbers.get(idx2), (int) numbers.get(idx3));
                        resultNumbers2.add(resultList);
                    }
                }
            }
        }

        return resultNumbers2;
    }


    // 정답 리스트가 있으면 정답 리스트를 출력하고 없으면 NO를 출력
    public static void printResult(Collection<resultNumbers> results, PrintWriter pw){
        if (results.size() <=0 )
            pw.println("NO");
        else
            for (resultNumbers result : results) {
                //printIntegerArrayList(result);
                result.printNumberList(pw);
            }
    }

    // util method
    public static void printIntegerArrayList(ArrayList<Integer> numbers){
        for (Integer number : numbers) {
            System.out.print(number + " ");
        }
        System.out.println("");
    }

    public static class Ascending implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }

    }

    // 정답 하나의 셋트 클래스
    public static class resultNumbers {
        int number1=0, number2=0, number3 = 0;

        public resultNumbers(){
            int number1, number2, number3 = 0;
        }

        public resultNumbers(int number1, int number2, int number3){
            this.number1 = number1;
            this.number2 = number2;
            this.number3 = number3;
        }

        public void printNumberList(PrintWriter pw) {
            pw.println(number1 + " " + number2 + " " + number3);
        }

        @Override
        public int hashCode() {
            return (this.number1 * this.number2 * this.number3);
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof resultNumbers)) {
                return false;
            }

            resultNumbers num = (resultNumbers)obj;

            return (this.number1 == num.number1 && this.number2 == num.number2 && this.number3 == num.number3);
        }
    }
}
