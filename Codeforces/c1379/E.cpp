
#include <iostream>
#include <vector>

using namespace std;

bool kI(int l, int r){
	return 2*l < r || 2*r < l;
}

bool T(int n, int k){
	if(k < 0) return false;
	if(n == 1) return k == 0;
	bool ret = 0;
	bool kIg;
	for(int i = 1; i<n; i+=2){
		kIg = kI(i,n-1-i);
		ret |= T(i, k-kIg) && T(n-1-i, k-kIg); 
	}
	return ret;
}

int main(){
	int N,K;
	cin >> N >> K;
	cout << (T(N,K) ? "YES" : "NO") << endl;
	return 0;
}
