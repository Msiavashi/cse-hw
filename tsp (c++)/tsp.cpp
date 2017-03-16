#include<iostream>
using namespace std;
#define min(a,b) ((a) < (b) ? (a) : (b))
#define rep(i,f,t) for(int i = (f), end = (t); i < t; ++i)

const int INF = 500 * 1000 * 1000;
const int MAX_N = 20;
int costs[MAX_N][MAX_N];
int history[1 << MAX_N][MAX_N];
int v, e;

int backtrack(int all, int last) {
	if (history[all][last] != -1)
		return history[all][last];

	int res = INF;
	int rest = all & ~(1 << last);
	rep(i, 0, v)
		if (rest & (1 << i))
			res = min(res, backtrack(rest, i) + costs[i][last]);

	history[all][last] = res;
	return res;
}

int main() {
	rep(i, 0, MAX_N)
		rep(j, 0, MAX_N)
			costs[i][j] = INF;
	cin >> v >> e;
	rep(i, 0, e) {
		int from, to;
		cin >> from >> to;
		cin >> costs[from - 1][to - 1];
		costs[to - 1][from - 1] = costs[from - 1][to - 1];
	}
	rep(i, 0, (1 << e))
		rep(j, 0, MAX_N)
			history[i][j] = -1;
	
	int all = 0;
	rep(i, 0, v - 1)
		history[1 << i][i] = costs[i][v - 1];
	rep(i, 0, v)
		all |= (1 << i);
	int res = backtrack(all, v - 1);
	cout << "result : " << res << endl;
	return 0;
}