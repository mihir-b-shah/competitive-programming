
#include <iostream>
#include <cmath>

using namespace std;

int main() {
    int T;
    cin >> T;
    
    for(int i = 0; i<T; ++i){
        int n;
        cin >> n;
        n *= 2;
        if(n%4 == 0){
            cout << 0.5*pow(tan(M_PI/n),-2) << '\n';
        } else {
            cout << 0.25+0.5*pow(tan(M_PI/n),-2) << '\n';
        }
    }
}