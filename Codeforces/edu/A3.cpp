
#include <iostream>
#include <string>
#include <vector>
#include <utility>
#include <algorithm>

using namespace std;

vector<char*> suffixArray(string& str){
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

	vector<char*> out(n);
	for(int i = 0; i<n; ++i){
		out[i] = const_cast<char*>(str.data()) + p[i];
	}

	return out;
}

struct Compare {
	int len;
	Compare(int l){len = l;}
	inline bool operator()(char* s1, char* s2){
		int ctr = 0;
		while(ctr < len && *s1 != '\0' && *s2 != '\0'){
			if(*s1 != *s2) return *s1 < *s2;
			++s1;
			++s2;
			++ctr;
		}
		return ctr == len; 
	}
};

int main(){
	string str;
	cin >> str;

	vector<char*> suffix = suffixArray(str);
	int T;
	cin >> T;
	for(int i = 0; i<T; ++i){
		string sstr;
		cin >> sstr;

		cout << (binary_search(suffix.begin(), suffix.end(), 
						const_cast<char*>(sstr.data()),
						Compare(sstr.length())) ? "Yes" : "No") << endl;
	}

	return 0;
}
