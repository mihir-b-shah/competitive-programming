
#include <iostream>
#include <vector>
#include <algorithm>

struct Reward {
	int a;
	int b;

	Reward(){}

	Reward(int _a, int _b){
		a = _a;
		b = _b;
	}
};

using namespace std;

int N;
inline bool cmp2(const Reward& r1, const Reward& r2){
	return r2.a + (N-1)*r2.b < r1.a + (N-1)*r1.b;
}

int main(){
	int T;
	cin >> T;

	for(int i = 0; i<T; ++i){
		int M;
		cin >> N >> M;
		vector<Reward> flowers(M);

		for(int j = 0; j<M; ++j){
			cin >> flowers[j].a >> flowers[j].b;
		}

		sort(flowers.begin(), flowers.end(), cmp2);

		cout << '\n';
		for(int j = 0; j<M; ++j){
			cout << flowers[j].a << ' ' << flowers[j].b << '\n';
		} 
	}

	return 0;
}

