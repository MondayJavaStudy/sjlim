package com.woowahan.study.java.sumGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.*;

// 20180813 javastudy - homework
// http://wiki.woowa.in/pages/viewpage.action?pageId=65972219

/*
0-20까지의 숫자 중 무작위로 하나가 적힌 종이를 바닥에 여러 장 뿌려놓고 술래가 숫자 하나를 크게 외칩니다.
술래를 제외한 나머지 팀원들은 바닥에 뿌려진 종이 중 3장을 집어 종이에 적힌 숫자의 합이 술래가 외친 숫자와 같다면 게임에서 승리하게 됩니다.

    입력조건
    1. 표준입력으로 첫 번째 줄에는 0에서 20까지의 int형 숫자들이 공백으로 구분되어 최대 20개까지 주어집니다.
    2. 최대 20개의 숫자는 중복되어 주어질 수 있고, 정렬되지 않았습니다.
    3. 표준입력으로 두 번째 줄에는 술래가 외친 숫자 하나가 주어집니다.

    출력조건
    1. 결괏값으로 3개의 숫자는 왼쪽부터 오름차순으로 출력되어야 합니다. 1 3 2가 답이라면 1 2 3으로 출력해야 합니다.
    2. 결괏값으로 3개의 숫자는 아래로 오름차순으로 출력되어야 합니다. 1 5 2, 1 3 4이 결괏값이라면 1 3 4를 먼저 출력해야 합니다.
    3. 결괏값으로 3개 숫자의 조합 중 같은 조합이 여러 개 존재한다면 한 번만 출력해야 합니다. 1 2 3, 1 2 3이 결괏값이라면 1 2 3 한 번만 출력되어야 합니다.
    4. 결괏값이 없다면 NO를 출력합니다.
*/

public class sumGameOrigin {
    public static void main(String[] args) {
        ArrayList<Integer> numberCardList = new ArrayList<Integer>();    // 숫자카드
        int correctNumber = 0;                                           // 술래가 외친 숫자

        ArrayList<resultNumbers> results;   // (정답하나 셋트의 리스트) 전체 정답리스트

        // 숫자 초기화 (숫자 입력)
        correctNumber = inputNumberListAndCorrectSumNumber(numberCardList);

        // 입력받은 숫자 정렬
        Ascending ascending = new Ascending();
        Collections.sort(numberCardList, ascending);

        // 술래가 외친 숫자보다 큰 숫자는 숫자카드에서 제외함
        removeBigNumberCard(numberCardList, correctNumber);

        // 세 숫자로 원하는 숫자를 만들 수 있는지 확인
        results = equalsNumberAndNumbersSum(numberCardList, correctNumber);
        Set<resultNumbers> set = new HashSet<>(results);

        // 정답 리스트가 있으면 정답 리스트를 출력하고 없으면 NO를 출력
        printResult(results);
    }
    // 표준 입력
    public static int inputNumberListAndCorrectSumNumber(ArrayList<Integer> numbers){
        String strLine1;                            // 첫번째 입력
        String strLine2;                            // 두번째 입력
        Scanner scan = new Scanner(System.in);

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
    public static void printResult(Collection<resultNumbers> results){
        if (results.size() <=0 )
            System.out.println("NO");
        else
            for (resultNumbers result : results) {
                //printIntegerArrayList(result);
                result.printNumberList();
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

        public void printNumberList() {
            System.out.println(number1 + " " + number2 + " " + number3);
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
