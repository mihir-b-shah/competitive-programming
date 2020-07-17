
/**
 * This implements the O(n lg^2 n) algorithm for finding
 * the suffix tree of a string.
 */

#include <iostream>
#include <cstring>
#include <algorithm>

using namespace std;

const int MAX_STR_SIZE = 100000;
char* str = new char[MAX_STR_SIZE+1];
int* refsorted;
int* refeq;
int len;

struct OrigComparator {
	inline bool operator() (const int& i1, const int& i2){
		return str[i1] < str[i2];
	}
};

static inline bool strequal(int i, int j){
	return i < len-1 && refeq[i] == j || j < len-1 && refeq[j] == i;
}

struct IterComparator {
	int jump;

	IterComparator(int j){jump = j;}

	inline bool operator() (const int& i1, const int& i2){
		if(strequal(i1, i2)){
			cout << "equal: " << i1 << ' ' << i2 << '\n';
			return refsorted[(i1+jump)%len] < refsorted[(i2+jump)%len];  
		} else {
			return refsorted[i1] < refsorted[i2];
		}
	}
};

template<bool start>
static inline void fillEq(int* ptr, int jmp){
	if(start){
		for(int i = 0; i<len-1; ++i){
			ptr[i] = str[refsorted[i]] == str[refsorted[i+1]] ? refsorted[i+1] : 0;
		}
	} else {
		for(int i = 0; i<len-1; ++i){
			ptr[i] = strequal(refsorted[i], refsorted[i+1]) && 
					strequal((jmp+refsorted[i])%len-1, (jmp+refsorted[i])%len-1);
				//	str[refsorted[i]] == str[refsorted[i+1]];
			// use the auxEq handle to get equality tests.
		}	
	}
}

int main(){
	cin >> str;
	len = strlen(str)+1;

	// make sure to handle case of not pow(2)-1 type string

	int* sorted = new int[len];
	int* eqtest = new int[len-1];
	for(int i = 0; i<len; ++i){
		sorted[i] = i;
	}

	sort(sorted, sorted+len, OrigComparator());
	refsorted = sorted;
	refeq = eqtest;
	fillEq<true>(eqtest, 1);

	IterComparator comp(1);
	for(int i = 2; i<=len; i*=2){
		for(int j = 0; j<len; ++j){
			cout << refsorted[j] << ' ';
		} cout << '\n';
		for(int j = 0; j<len-1; ++j){
			cout << refeq[j] << ' ';
		} cout << '\n';
		sorted = new int[len];
		copy(refsorted, refsorted+len, sorted);
		eqtest = new int[len-1];
		sort(sorted, sorted+len, comp);

		delete refsorted;
		refsorted = sorted;

		fillEq<false>(eqtest, i);
		delete refeq;
		refeq = eqtest;
		comp.jump *= 2;
	}
	
	for(int j = 0; j<len; ++j){
		cout << sorted[j] << ' ';
	} cout << '\n';
}
