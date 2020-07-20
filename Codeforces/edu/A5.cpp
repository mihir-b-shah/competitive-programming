
#include <iostream>
#include <string>
#include <vector>
#include <utility>
#include <algorithm>

using namespace std;

pair<vector<int>,vector<int>> suffixArray(string& str){
	str += '$';

	int n = str.size();
	vector<int> p(n);
	vector<int> c(n);

	// some ideas from Codeforces EDU lecture 1: code
	vector<pair<char,int>> a(n);
	for(int i = 0; i<n; ++i){
		a[i] = {str[i], i};
	}
	
	sort(a.begin(), a.end());
	
	for(int i = 0; i<n; ++i){
		p[i] = a[i].second;
	}

	c[p[0]] = 0;
	for(int i = 1; i<n; ++i){
		if(a[i].first == a[i-1].first){
			c[p[i]] = c[p[i-1]];
		} else {
			c[p[i]] = c[p[i-1]] + 1;
		}
	}

	for(int k = 2; k<n*2; k*=2){
		vector<pair<pair<int,int>,int>> b(n);
		for(int j = 0; j<n; ++j){
			b[j] = {{c[j], c[(j+k/2)%n]},j};
		}

		sort(b.begin(), b.end());
	
		for(int i = 0; i<n; ++i){
			p[i] = b[i].second;
		}
		c[p[0]] = 0;
		for(int i = 1; i<n; ++i){
			if(b[i].first == b[i-1].first){
				c[p[i]] = c[p[i-1]];
			} else {
				c[p[i]] = c[p[i-1]] + 1;
			}
		}
	}
	
	int k = 0;
	vector<int> lcp(n);

	for(int i = 0; i<n-1; ++i){
		int pi = c[i];
		int j = p[pi-1];

		while(k+max(i,j) < str.length() && 
			str[k+i] == str[k+j]) {
			++k;
		}
		
		lcp[pi] = k;
		k = max(k-1,0);
	}

	return {p,lcp};
}

int main(){
	string str;
	cin >> str;

	auto ret = suffixArray(str);
	vector<int>& lcp = ret.second;

	int sum = 0;
	for(int i = 1; i<lcp.size(); ++i){
		sum += lcp[i];
	}
	long long output = str.size();
	output *= str.size()-1;
	output /= 2;
	output -= sum;
	cout << output << endl;
	return 0;
}
