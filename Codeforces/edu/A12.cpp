
#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <utility>

using namespace std;

void printInts(vector<int>& v){
	for(int e: v){
		cout << e << ' ';
	} cout << '\n';
}

void printPairs(vector<pair<int,int>>& v){
	for(pair<int,int>& p : v){
		cout << '(' << p.first << ' ' << p.second << ')' << ", ";
	} cout << '\n';
}

void sortPairs(int MAX_1, int MAX_2, vector<pair<char,int>>& vect){
	vector<int> table(max(MAX_1, MAX_2)+2);
	for(pair<char,int>& mypair: vect){
		++table[1+mypair.second];	
	}
	for(int i = 1; i<=MAX_2; ++i){
		table[i] += table[i-1];
	}
	vector<pair<char,int>> aux(vect.size());
	for(pair<char,int>& mypair: vect){
		aux[table[mypair.second]++] = mypair;
	}

	for(pair<char,int>& mypair: aux){
		++table[1+mypair.first];	
	}
	for(int i = 1; i<=MAX_1; ++i){
		table[i] += table[i-1];
	}
	for(pair<char,int>& mypair: aux){
		vect[table[mypair.first]++] = mypair;
	}
}
int main(){
	string str;
	cin >> str;
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

	for(int i = 0; i<n; ++i){
		cout << p[i] << ' ';
	} cout << '\n';

	return 0;
}
