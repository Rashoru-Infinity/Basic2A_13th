# Basic2A_13th
基礎情報演習2A第13回の応用課題です。  
課題の内容は以下の通りでした。  
>硬貨交換問題において，50円玉，x円玉(5<x<50)，5円玉，1円玉だけが使用できるものとする．このとき，教科書のCoin-changeアルゴリズムがn=50+α(0<α<50)である場合に最適解を与えるかどうか，数値的に調べてみよう.また，この結果は定理を用いた場合と一致するだろうか？以下の構成で解答することを勧める：  １．グリーディ法で求めた解  ２．全解探索で求めた最適解  をα=1\~49かつx=6\~49について列挙する（例，2次元の表）．さらに余力があれば，  ３．上記の結果が定理と矛盾していないか（不等式が成立しているか）をチェックする
# コンパイル
jdkのインストールが必要です。  
Windowsで動かす場合はbuild.bat、linuxで動かす場合はbuild.shを実行してください。  
# 実行
Windowsで動かす場合はrun.bat、linuxで動かす場合はrun.shを実行してください。  
# グリーディー法で求めた解
実行のためのバッチファイルや、シェルスクリプトがあるディレクトリにgreedy.csvとcombination.txtが生成されます。  
csvの軸の対応は、横がa(1\~49)、縦がx(6\~49)です。  csvの表の中身は対応する(x, a)のときにグリーディー法で求めた硬貨の枚数です。  
combination.txtにはグリーディー法と全解探索で求めたそれぞれの解のときの硬貨の枚数の組み合わせが書かれています。
# 全解探索で求めた解
実行のためのバッチファイルや、シェルスクリプトがあるディレクトリにbrute_force.csvとcombination.txtが生成されます。  
csvの軸の対応は、横がa(1\~49)、縦がx(6\~49)です。  csvの表の中身は対応する(x, a)のときにグリーディー法で求めた硬貨の枚数です。  
combination.txtにはグリーディー法と全解探索で求めたそれぞれの解のときの硬貨の枚数の組み合わせが書かれています。
# 結果と定理が矛盾していないか
実行のためのバッチファイルや、シェルスクリプトがあるディレクトリにcheck_theorem.txtが生成されます。  
以下はファイルの中身の抜粋です。  
```
(x, a) = (9, 46) : greedy = 7, brute_force = 7 [Protected]
(x, a) = (9, 47) : greedy = 8, brute_force = 8 [Protected]
(x, a) = (9, 48) : greedy = 9, brute_force = 9 [Protected]
(x, a) = (9, 49) : greedy = 10, brute_force = 10 [Protected]
(x, a) = (10, 1) : greedy = 2, brute_force = 2 [OK]
(x, a) = (10, 2) : greedy = 3, brute_force = 3 [OK]
(x, a) = (10, 3) : greedy = 4, brute_force = 4 [OK]
(x, a) = (10, 4) : greedy = 5, brute_force = 5 [OK]
```
greedy = 7はグリーディー法による解では硬貨が7枚必要であることを示しています。  
[Protected]は定理により、グリーディー法の解が最適解になるとは断言できない条件であることを表します。  
[OK]は定理により、グリーディー法の解が最適解になりうることを表します。  
[KO]は定理により、グリーディー法の解が最適解になりうるとされているにも関わらず、グリーディー法の解が最適解ではなかったことを表します。  
実行すると分かるように、a(1\~49)かつx(6\~49)の条件の中で、check_theorem.txtには[KO]が現れなかったので結果と定理に矛盾は生じていないことを確認できました。
