
/*
ID: mihirsh1
TASK: race3
LANG: C++
*/

/*
ERROR FOUND: ON THE CYCLE FINDING, ENDPOINTS OF THE CYCLE SHOULD NOT BE COUNTED!!
*/

#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <string>

using namespace std;

typedef vector<int> vi;
vector<vi> vii;

bool cycle(int start) {
    queue<int> q;
    q.push(start);
    long long mem = 1LL;
    int n;
    int ref;

    while(!q.empty()) {
        n = q.front();
        mem |= 1LL << n;
        q.pop();

        vi& next = vii.at(n);

        for(int i = 0; i<next.size(); ++i) {
            if((ref = next.at(i)) == start) {
                return 1;
            }
            if((mem & (1LL << ref)) == 0) {
                q.push(ref);
            }
        }
    }

    return 0;
}

int main() {
    ifstream fin;
    fin.open("race3.in");
    
    ofstream fout;
    fout.open("race3.out");

    int buf;
    bool outer = 1;
    bool inner = 1;
    int ctr;

    while(outer) {
        ctr = 0;
        vi vect;
        while(inner) {
            fin >> buf;

            switch(buf) {
                case -1: outer = 0;
                case -2: inner = 0; break;
                default: ++ctr; vect.push_back(buf);
            }
        }
        inner = 1;
        vii.push_back(vect);
    }

    fin.close();
    vii.pop_back();
    
    queue<pair<long long,int>> q;
    q.push(make_pair(1LL,0));
    int d = vii.size()-1;
    long long agr = (1LL << vii.size()) - 1;
    ctr = 0;

    while(!q.empty()) {
        pair<long long,int>& n = q.front(); 
        q.pop();

        if(n.second==d) {
            agr &= n.first;
            continue;
        }

        vi& next = vii.at(n.second);
        const int lim = next.size();

        for(int i = 0; i<lim; ++i) {
            if((n.first & 1LL << next.at(i)) == 0) {
                q.push(make_pair(n.first+(1LL << next.at(i)),next.at(i)));
            }
        }
    }

    vi aux;
    string split;
    split.reserve(200);
    string out1; 
    out1.reserve(200);
    int out1_ctr = 0;
    int split_ctr = 0;

    for(int i = 1; i < vii.size()-1; ++i) {
        if((agr & 1LL << i) != 0) {
            aux.push_back(i);
            out1 += to_string(i);
            out1 += ' ';
            ++out1_ctr;
        }
    }

    for(int i = 0; i<aux.size(); ++i) {
        if(!cycle(aux.at(i))) {
            split += to_string(aux.at(i)) + ' ';
            ++split_ctr;
        }
    }

    if(out1.size() > 0) {
        out1.pop_back();
        fout << out1_ctr << ' ' << out1 << endl;
    } else {
        fout << 0 << endl;
    }

    if(split.size() > 0) {
        split.pop_back();
        fout << split_ctr << ' ' << split << endl;
    } else {
        fout << 0 << endl;
    }

    fout.close();
    return 0;
}
