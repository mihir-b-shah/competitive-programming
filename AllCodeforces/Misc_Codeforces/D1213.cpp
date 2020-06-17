
#include <iostream>
#include <vector>
#include <queue>
#include <functional>

using namespace std;

static const int INF = 100000;

static inline int min(int a, int b) {
	return a<b?a:b;
}

static inline int max(int a, int b) {
	return a>b?a:b;
}

int main() {
	int N,K;
	cin >> N >> K;
	
	vector<int> arr(N);	
	int maxNum = 0;
	for(int i = 0; i<N; ++i) {
		cin >> arr[i];
		maxNum = max(maxNum, arr[i]);
	}

	// threshold iteration
	int min_across = INF;
	for(int T = 1; T<=maxNum; ++T) {
		priority_queue<int, vector<int>, greater<int>> pq;
		for(int i = 0; i<N; ++i) {
			int aux = arr[i];
			int ctr = 0;
			while(aux > T) {
				aux >>= 1;
				++ctr;
			}
			pq.push(aux == T ? ctr : INF);
		}

		long long total = 0;
		//cout << "T: " << T << '\n';
		for(int i = 0; i<K; ++i) {
			total += pq.top();
			//cout << pq.top() << ' ';
			pq.pop();
		}
		//cout << '\n';
		min_across = min(min_across, total);
	}

	cout << min_across << endl;
}
