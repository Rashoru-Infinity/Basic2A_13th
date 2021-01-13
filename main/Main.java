package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.awt.Point;

public class Main {
	private static ArrayList<Integer>coinList;
	private static ArrayList<Point> bestCombination;
	private static int minCoinCount;
	public static void main(String[] args) {
		HashSet<Integer> coinSet = new HashSet<Integer>();
		coinSet.add(1);
		coinSet.add(5);
		coinSet.add(50);
		for (int x = 6;x < 50;++x) {
			if (x != 6) {
				coinSet.remove(x - 1);
			}
			coinSet.add(x);
			coinList = new ArrayList<>(coinSet);
			Collections.sort(coinList);
			Collections.reverse(coinList);
			for (int a = 1;a < 50;++a) {
				minCoinCount = a + 50;
				if (bestCombination != null) {
					bestCombination.clear();
				}
				greedy(a, x);
				bruteForceHandler(a, x);
				
			}
		}
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
				bestCombination = (ArrayList<Point>) nextCombination.clone();
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
