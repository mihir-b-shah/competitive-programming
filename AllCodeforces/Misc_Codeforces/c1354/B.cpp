
#include <iostream>
#include <string>

using namespace std;

bool check(int ct[3]){
    return ct[0] > 0 && ct[1] > 0 && ct[2] > 0;
}

int min(int v1, int v2){
    return v1<v2?v1:v2;
}

int main() {
    int N;
    cin >> N;
    
    for(int i = 0; i<N; ++i) {
        string s;
        cin >> s;
        
        auto fast = s.begin();
        auto slow = s.begin();
        
        int cts[3] = {0};
        int best = 1000000000;
        
        int found = 0;
        for(auto iter = s.begin(); iter!=s.end(); ++iter){
            found |= 1 << (*iter-'1');
        }
        if(found != 0b111){
            cout << 0 << '\n';
            continue;
        }
        
        while(fast != s.end()){
            // get bounds for string
            while(fast != s.end() && !check(cts)){
                ++cts[(*fast)-'1'];
                ++fast;
            }
            while(slow != fast && check(cts)){
                --cts[(*slow)-'1'];
                ++slow;
            }
            best = min(best, fast-slow+1);
        }
        
        cout << (best==1000000000?0:best) << '\n';
    }
}