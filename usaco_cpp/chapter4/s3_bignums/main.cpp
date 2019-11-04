#include <fstream>
#include <iostream>

using namespace std;

int main() {
    ifstream fin;
    ofstream fout;

    fin.open("race3.in");
    fout.open("race3.out");

    int buf;
    bool outer = 1;
    bool inner = 1;

    while(outer) {
        while(inner) {
            fin >> buf;

            switch(buf) {
                case -2: cout << buf << ' '; inner = 0; break;
                case -1: cout << buf << ' '; outer = 0; break;
                default: cout << buf << ' ';
            }
        }
        inner = true;
    }

    return 0;
}