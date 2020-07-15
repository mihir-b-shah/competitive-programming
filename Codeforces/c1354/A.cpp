
#include <iostream>

using namespace std;

long long ceilDiv(long long x, long long y){
    return (x/y) + (x%y != 0);
}

int main(){
    int N;
    cin >> N;
    
    for(int i = 0; i<N; ++i) {
        long long a,b,c,d;
        cin >> a >> b >> c >> d;
        long long totalSleep = 0;
        totalSleep += b;
        if(totalSleep >= a) {
            cout << b << '\n';
            continue;
        }
        if(d < c){
            totalSleep += c*ceilDiv(a-totalSleep,c-d);
            cout << totalSleep << '\n';
        } else {
            cout << -1 << '\n';
        }
    }
}