
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
int* aux;
int* sorted;
int* auxEq;
int* eqtest;

struct OrigComparator {
	inline bool operator() (const int& i1, const int& i2){
		return str[i1] < str[i2];
	}
};

struct IterComparator {
	int jump;
	int len;

	IterComparator(int j, int l){jump = j; len = l;}

	inline bool operator() (const int& i1, const int& i2){
		if(aux[i1] == aux[i2]){
			return aux[(i1+jump)%len] < aux[(i2+jump)%len];  
		} else {
			return aux[i1] < aux[i2];
		}
	}
};

static inline void swapArrays(int** loc1, int** loc2){
	int* temp = *loc1;
	*loc1 = *loc2;
	*loc2 = temp;
}	

static inline void fillSeq(int* ptr, int len){
	for(int i = 0; i<len; ++i){
		ptr[i] = i;
	}
}

static inline void fillEq(int len, int jmp){
	for(int i = 0; i<len; ++i){
		// use the auxEq handle to get equality tests.
	}	
}

int main(){
	cin >> str;
	int len = strlen(str)+1;

	// make sure to handle case of not pow(2)-1 type string

	aux = new int[len];
	sorted = new int[len];
	eqtest = new int[len];
	auxEq = new int[len];
	fillSeq(sorted, len);

	sort(sorted, sorted+len, OrigComparator());
	swapArrays(&aux, &sorted);
	fillSeq(sorted, len);	

	fillEq(len);
	swapArrays(&auxEq, &eqtest);

	IterComparator comp(1, len);
	for(int i = 2; i<=len; i*=2){
		for(int j = 0; j<len; ++j){
			cout << aux[j] << ' ';
		} cout << '\n';
		sort(sorted, sorted+len, comp);
		fillEq(len);
		swapArrays(&auxEq, &eqtest);
		swapArrays(&aux, &sorted);
		fillSeq(sorted, len);
		comp.jump *= 2; 	
	}
	
	for(int j = 0; j<len; ++j){
		cout << aux[j] << ' ';
	} cout << '\n';
}
