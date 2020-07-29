
#include <iostream>
#include <string>
#include <vector>
#include <utility>
#include <algorithm>

using namespace std;

pair<vector<vector<int>>,vector<int>> suffixArray(string& str){
	str += '$';

	int n = str.size();
	vector<int> p(n);
	vector<vector<int>> c(1, vector<int>(n));

	// some ideas from Codeforces EDU lecture 1: code
	vector<pair<char,int>> a(n);
	for(int i = 0; i<n; ++i){
		a[i] = {str[i], i};
	}
	
	sort(a.begin(), a.end());
	
	for(int i = 0; i<n; ++i){
		p[i] = a[i].second;
	}

	c[0][p[0]] = 0;
	for(int i = 1; i<n; ++i){
		if(a[i].first == a[i-1].first){
			c[0][p[i]] = c[0][p[i-1]];
		} else {
			c[0][p[i]] = c[0][p[i-1]] + 1;
		}
	}

	int idx = 1;
	for(int k = 2; k<n*2; k*=2){
		c.push_back(vector<int>(n));
		copy(c[idx-1].begin(), c[idx-1].end(), c[idx].begin());
		vector<pair<pair<int,int>,int>> b(n);
		for(int j = 0; j<n; ++j){
			b[j] = {{c[idx][j], c[idx][(j+k/2)%n]},j};
		}

		sort(b.begin(), b.end());
	
		for(int i = 0; i<n; ++i){
			p[i] = b[i].second;
		}
		c[idx][p[0]] = 0;
		for(int i = 1; i<n; ++i){
			if(b[i].first == b[i-1].first){
				c[idx][p[i]] = c[idx][p[i-1]];
			} else {
				c[idx][p[i]] = c[idx][p[i-1]] + 1;
			}
		}
		++idx;
	}

	vector<int> logs(n);
	for(int i = 2; i<n; ++i){
		logs[i] = logs[i/2]+1;
	}

	return {c,logs};
}

vector<int> lgs;
vector<vector<int>> look;

inline bool cmp(pair<int,int>& p1, pair<int,int>& p2){
	int len1 = p1.second-p1.first;
	int len2 = p2.second-p2.first;
	
	int len = min(len1, len2);

	if(look[lgs[len]][p1.first] == look[lgs[len]][p2.first]){
		if(look[lgs[len]][p1.first+len-(1<<lgs[len])] == look[lgs[len]][p2.first+len-(1<<lgs[len])]){
			if(len1 == len2) {
				return p1 < p2;
			} else {
				return len1 < len2;
			}
		} else {
			return look[lgs[len]][p1.first+len-(1<<lgs[len])] == look[lgs[len]][p2.first+len-(1<<lgs[len])];
		}
	} else {
		return look[lgs[len]][p1.first] < look[lgs[len]][p2.first];
	}	
}

int main(){
	string str;
	cin >> str;

	int T;
	cin >> T;

	vector<pair<int,int>> substrs(T);

	for(int i = 0; i<T; ++i){
		cin >> substrs[i].first >> substrs[i].second;
		substrs[i].first -= 1;
	}

	auto ret = suffixArray(str);
	lgs = ret.second;
	look = ret.first;

	/*
	for(vector<int>& inner: look){
		for(int i: inner){
			cout << i << ' ';
		} cout << '\n';
	}

	cout << '\n';
	pair<int,int> p1 = {1,3};
	pair<int,int> p2 = {3,5};
	cout << cmp(p1,p2) << '\n';
	cout << '\n';

	cout << '\n';
	for(int i: lgs){
		cout << i << ' ';
	} cout << "\n\n";
	*/
	sort(substrs.begin(), substrs.end(), cmp);
	for(int i = 0; i<T; ++i){
		cout << 1+substrs[i].first << ' ' << substrs[i].second << '\n';
	}
}
