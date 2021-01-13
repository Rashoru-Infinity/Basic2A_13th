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
	private static final String OUTFILE3 = "./check_theorem.txt";
	private static final String OUTFILE4 = "./combination.txt";
	public static void main(String[] args) throws IOException {
		HashSet<Integer> coinSet = new HashSet<Integer>();
		File file1 = new File(OUTFILE1);
		FileWriter fw1 = new FileWriter(file1);
		File file2 = new File(OUTFILE2);
		FileWriter fw2 = new FileWriter(file2);
		File file3 = new File(OUTFILE3);
		FileWriter fw3 = new FileWriter(file3);
		File file4 = new File(OUTFILE4);
		FileWriter fw4 = new FileWriter(file4);
		coinSet.add(1);
		coinSet.add(5);
		coinSet.add(50);
		for (int x = 6;x < 50;++x) {
			ArrayList<Integer> gdList = new ArrayList<>();
			ArrayList<Integer> bfList = new ArrayList<>();
			coinSet.add(x);
			coinList = new ArrayList<>(coinSet);
			Collections.sort(coinList);
			Collections.reverse(coinList);
			for (int a = 1;a < 50;++a) {
				minCoinCount = a + 50;
				if (bestCombination != null) {
					bestCombination.clear();
				}
				gdList.add(greedyHandler(a, x, fw4));
				bruteForceHandler(a, x, fw4);
				bfList.add(minCoinCount);
			}
			greedyTable.add(gdList);
			bfTable.add(bfList);
			if (x != 10) {
				coinSet.remove(x);
			}
		}
		fw1.write("x/a,");
		for (int a = 1;a < 50;++a) {
			fw1.write(a + ",");
		}
		fw1.write("\n");
		for (int x = 0;x < greedyTable.size();++x) {
			ArrayList<Integer> list = greedyTable.get(x);
			fw1.write((x + 6) + ",");
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
			fw2.write((x + 6) + ",");
			for (Integer i : list) {
				fw2.write(i + ",");
			}
			fw2.write("\n");
		}
		for (int x = 0;x < bfTable.size();++x) {
			ArrayList<Integer> gdList = greedyTable.get(x);
			ArrayList<Integer> bfList = bfTable.get(x);
			for (int a = 0;a < gdList.size();++a) {
				coinSet.add(x + 6);
				coinList = new ArrayList<>(coinSet);
				if (x + 6 != 10) {
					coinSet.remove(x + 6);
				}
				Collections.sort(coinList);
				Collections.reverse(coinList);
				int next = coinList.get(coinList.indexOf(x + 6) + 1);
				int prev = coinList.get(coinList.indexOf(x + 6) - 1);
				boolean meetCondition = false;
				int p1 = (x + 6) / next;
				if ((x + 6) % next != 0) {
					++p1;
				}
				int p2 = prev / (x + 6);
				if (prev % (x + 6) != 0) {
					++p2;
				}
				meetCondition = 1 + greedy(p1 * next - (x + 6), x + 6, null) <= p1
						&& 1 + greedy(p2 * (x + 6) - prev, prev, null) <= p2;
				fw3.write("(x, a) = (" + (x + 6) + ", " + (a + 1) + ") : ");
				fw3.write("greedy = " + gdList.get(a) + ", brute_force = " + bfList.get(a));
				if ((meetCondition && gdList.get(a) <= bfList.get(a))) {
					fw3.write(" [OK]\n");
				}else if (!meetCondition) {
					fw3.write(" [Protected]\n");
				}else {
					fw3.write(" [KO]\n");
				}
			}
		}
		fw1.close();
		fw2.close();
		fw3.close();
		fw4.close();
		System.out.println("Instructions for this program is as follows.\nhttps://github.com/Rashoru-Infinity/Basic2A_13th");
	}
	private static int greedyHandler(int a, int x, FileWriter fw) throws IOException {
		ArrayList<Point>combination = new ArrayList<>();
		int coinCount;
		int remain = a + 50;
		fw.write("<Greedy Algorithm>" + "a = " + (remain - 50) + ",x = " + x + "\n");
		coinCount = greedy(remain, x, combination);
		for (Point p : combination) {
			fw.write(p.x + " x " + p.y + "\n");
		}
		fw.write(coinCount + "coins\n");
		return coinCount;
	}
	private static int greedy(int a, int x, ArrayList<Point>combination) {
		int coinCount = 0;
		int remain = a;
		for (int i : coinList) {
			if (combination != null ) {
				combination.add(new Point(i, remain / i));
			}
			coinCount += remain / i;
			remain %= i;
		}
		return coinCount;
	}
	private static void bruteForceHandler(int a, int x, FileWriter fw) throws IOException {
		int remain = a + 50;
		fw.write("<Brute Force Search>" + "a = " + (remain - 50) + ",x = " + x + "\n");
		bruteForce(null, 0, remain, 0);
		for (Point p : bestCombination) {
			fw.write(p.x + " x " + p.y + "\n");
		}
		fw.write(minCoinCount + "coins\n");
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
