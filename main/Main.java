package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Collections;
import java.awt.Point;

public class Main {
	private static ArrayList<Integer>coinList;
	private static ArrayList<Point> bestCombination;
	private static int minCoinCount;
	public static void main(String[] args) {
		HashSet<Integer> coinSet = new HashSet<Integer>();
		int amount;
		int original;
		Scanner sc = new Scanner(System.in);
		coinSet.add(1);
		coinSet.add(5);
		coinSet.add(50);
		original = sc.nextInt();
		coinSet.add(original);
		amount = sc.nextInt();
		minCoinCount = amount;
		sc.close();
		coinList = new ArrayList<>(coinSet);
		Collections.sort(coinList);
		Collections.reverse(coinList);
		greedy(amount - 50, original);
		bruteForceHandler(amount - 50, original);
	}
	private static void greedy(int a, int x) {
		int coinCount = 0;
		int remain = a + 50;
		System.out.println("<Greedy Algorithm>" + "a = " + remain + ",x = " + x);
		for (int i : coinList) {
			System.out.println(i + " x " + remain / i);
			coinCount += remain / i;
			remain %= i;
		}
		System.out.println(coinCount + "coins");
	}
	
	private static void bruteForceHandler(int a, int x) {
		int remain = a + 50;
		System.out.println("<Brute Force Search>" + "a = " + remain + ",x = " + x);
		bruteForce(null, 0, remain, 0);
		for (Point p : bestCombination) {
			System.out.println(p.x + " x " + p.y);
		}
		System.out.println(minCoinCount + "coins");
	}
	@SuppressWarnings("unchecked")
	private static void bruteForce(ArrayList<Point> combination, int coinListIndex, int remain, int coinCount) {
		ArrayList<Point> nextCombination;
		int currentCoin = coinList.get(coinListIndex);
		if (currentCoin == 1) {
			if (combination != null) {
				nextCombination = (ArrayList<Point>)combination.clone();
			}else {
				nextCombination = new ArrayList<>();
			}
			nextCombination.add(new Point(currentCoin, remain));
			coinCount += remain;
			if (coinCount < minCoinCount) {
				minCoinCount = coinCount;
				bestCombination = nextCombination;
			}
			return ;
		}
		for (int i = remain / currentCoin;i >= 0;--i) {
			if (combination != null) {
				nextCombination = (ArrayList<Point>)combination.clone();
			}else {
				nextCombination = new ArrayList<>();
			}
			nextCombination.add(new Point(currentCoin, i));
			if (coinCount + i < minCoinCount) {
				bruteForce(nextCombination, coinListIndex + 1, remain - currentCoin * i, coinCount + i);
			}
		}
	}
}
