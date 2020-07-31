
#include <iostream>

using namespace std;

const char* const comp = "abacaba";

enum Match {NONE, QUESTION, PURE};

static inline Match min(Match m1, Match m2){
	return m1<m2?m1:m2;
}

Match strcomp(char* buf){
	Match level = PURE;
	for(int i = 0; i<7; ++i){
		if(comp[i] != buf[i]) {
			level = min(level, QUESTION);
			if(buf[i] != '?'){
				level = min(level, NONE);
			}
		}
	}
	return level;
}

int main(){
	int T;
	cin >> T;

	const char* const comp = "abacaba";

	for(int i = 0; i<T; ++i){
		int N;
		char str[51] = {'\0'};
		char* ptr = str;

		cin >> N;
		cin >> str;

		int ctrPure = 0;
		int ctrQuestion = 0;
		Match res;
		char* found = nullptr;
		char* backup = nullptr;

		while(*(ptr+6) != '\0'){
			// full comparison possible
			res = strcomp(ptr);
			if(res == PURE){
				found = ptr;
			}
			if(res == QUESTION){
				backup = ptr;
			}
			ctrPure += res == PURE;
			ctrQuestion += res == QUESTION;
			++ptr;
		}
		found = found == nullptr ? backup : found;
		cout << (ctrPure == 1 || ctrPure == 0 && ctrQuestion > 0 ? "Yes" : "No") << '\n';
		if(ctrPure == 1 || ctrPure == 0 && ctrQuestion > 0){
			for(int fillptr = found-str; fillptr<found-str+7; ++fillptr){
				str[fillptr] = comp[fillptr-(found-str)];
			}	
			for(int i = 0; i<50; ++i){
				if(str[i] == '?'){
					str[i] = 'z';
				}
			}
			cout << str << '\n';
		}
	}

	return 0;
}

