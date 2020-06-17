
#include <iostream>
#include "DSU.h"

int main() {
    const int size = 7;
    DisjointSet dsu(size);
    for(int i = 0; i<size; ++i) {
        cout << dsu.find(i) << '\n';
    }
    dsu.merge(1,2);
    dsu.merge(3,4);
    cout << '\n';
    for(int i = 0; i<size; ++i) {
        cout << dsu.find(i) << '\n';
    }
    dsu.merge(2,3);
    cout << '\n';
    for(int i = 0; i<size; ++i) {
        cout << dsu.find(i) << '\n';
    }
    dsu.merge(1,4); cout << '\n';
    for(int i = 0; i<size; ++i) {
        cout << dsu.find(i) << '\n';
    }
}