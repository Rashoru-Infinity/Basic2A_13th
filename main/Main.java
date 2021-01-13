package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	private static ArrayList<Integer>coinList;
	private static ArrayList<Point> bestCombination;
	private static int minCoinCount;
	private static ArrayList<ArrayList<Integer>>greedyTable = new ArrayList<>();	//result of greedy algorithm
	private static ArrayList<ArrayList<Integer>>bfTable = new ArrayList<>();	//result of brute force search
	private static final String OUTFILE1 = "./greedy.csv";
	private static final String OUTFILE2 = "./brute_force.csv";
	public static void main(String[] args) throws IOException {
		HashSet<Integer> coinSet = new HashSet<Integer>();
		File file1 = new File(OUTFILE1);
		FileWriter fw1 = new FileWriter(file1);
		File file2 = new File(OUTFILE2);
		FileWriter fw2 = new FileWriter(file2);
		coinSet.add(1);
		coinSet.add(5);
		coinSet.add(50);
		for (int x = 6;x < 50;++x) {
			ArrayList<Integer> gdList = new ArrayList<>();
			ArrayList<Integer> bfList = new ArrayList<>();
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
				gdList.add(greedy(a, x));
				bruteForceHandler(a, x);
				bfList.add(minCoinCount);
			}
			greedyTable.add(gdList);
			bfTable.add(bfList);
		}
		fw1.write("x/a,");
		for (int a = 1;a < 50;++a) {
			fw1.write(a + ",");
		}
		fw1.write("\n");
		for (int x = 0;x < greedyTable.size();++x) {
			ArrayList<Integer> list = greedyTable.get(x);
			fw1.write(x + ",");
			for (Integer i : list) {
				fw1.write(i + ",");
			}
			fw1.write("\n");
		}
		fw2.write("x/a,");
		for (int a = 1;a < 50;++a) {
			fw2.write(a + ",");
		}
		fw2.write("\n");
		for (int x = 0;x < bfTable.size();++x) {
			ArrayList<Integer> list = bfTable.get(x);
			fw2.write(x + ",");
			for (Integer i : list) {
				fw2.write(i + ",");
			}
			fw2.write("\n");
		}
		fw1.close();
		fw2.close();
		System.out.println("\n\nInstruction for csv file format is as follows.\nhttps://github.com/Rashoru-Infinity/Basic2A_13th");
	}
	private static int greedy(int a, int x) {
		int coinCount = 0;
		int remain = a + 50;
		System.out.println("<Greedy Algorithm>" + "a = " + remain + ",x = " + x);
		for (int i : coinList) {
			System.out.println(i + " x " + remain / i);
			coinCount += remain / i;
			remain %= i;
		}
		System.out.println(coinCount + "coins");
		return coinCount;
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
