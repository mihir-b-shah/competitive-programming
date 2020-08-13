
#include <iostream>
#include <vector>
#include <deque>

using namespace std;

static inline int max(int a, int b){
    return a>b?a:b;
}

static inline int min(int a, int b){
    return a<b?a:b;
}

void printPtrs(vector<int>& v, deque<int>& deq){
    for(auto iter = deq.begin(); iter != deq.end(); ++iter){
        cout << '(' << *iter << ' ' << v[*iter] << ')' << ' ';
    }
    cout << '\n';
}

int main(){
    vector<int> v;
    vector<int> dp;
    int n,s,l;
    
    cin >> n >> s >> l;
    
    v.resize(n);
    dp.resize(n+1);

    for(int i = 0; i<n; ++i){
        cin >> v[i];
    }

    dp[n] = 0;
    for(int p = n-1; p>n-l; --p){
        dp[p] = 0x7fff'ffff;
    }

    deque<int> maxWdw;
    deque<int> minWdw;
    deque<int> dpMin;

    int fast = v.size()-1;
    int slow = v.size()-1;

    while(fast >= 0){
        cout << "POS: " << fast << '\n';
        while(dpMin.size() > 0 && dp[fast+l] <= dp[dpMin.back()]){
            dpMin.pop_back();
        }
        if(fast + l < dp.size()) {
            dpMin.push_back(fast+l);
        }
        while(maxWdw.size() > 0 && v[fast] >= v[maxWdw.back()]){
            maxWdw.pop_back();
        }
        maxWdw.push_back(fast);
        while(minWdw.size() > 0 && v[fast] <= v[minWdw.back()]){
            minWdw.pop_back();
        }
        minWdw.push_back(fast);
       
        // keep in mind reverse order
        while(slow >= fast && v[maxWdw.front()]-v[minWdw.front()]>s){
            if(maxWdw.size() > 0 && maxWdw.front() >= slow){
                maxWdw.pop_front();
            }
            if(minWdw.size() > 0 && minWdw.front() >= slow){
                minWdw.pop_front();
            }
            cout << "DpMinSize: " << dpMin.size() << " Slow > Fast: " << (slow > fast) << '\n';
            if(dpMin.size() > 0 && slow > fast && dpMin.front() >= slow){
                cout << "DpFront: " << dpMin.front() << "Slow: " << slow << '\n';
                dpMin.pop_front();
            }
            --slow;
        } 

        cout << "dpMin: ";
        printPtrs(dp, dpMin);
        cout << "maxWdw: ";
        printPtrs(v, maxWdw);
        cout << "minWdw: ";
        printPtrs(v, minWdw);
        cout << "check range: " << fast << ' ' << slow << '\n';

        int mmax = dpMin.size() > 0 ? dp[dpMin.front()] : 0x7fff'ffff;
        dp[fast] = mmax == 0x7fff'ffff ? 0x7fff'ffff : 1+mmax;
        --fast;
    }

    for(auto iter = dp.begin(); iter!=dp.end(); ++iter){
        cout << *iter << ' ';
    } cout << '\n'; 

    cout << (dp[0] == 0x7fff'ffff ? -1 : dp[0]) << '\n';  
}

