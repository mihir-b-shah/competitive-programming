
#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <string>
#include <iostream>

using namespace std;

typedef struct node {
    unsigned long mask;
    int id; 
    
    node(int id) {
        this->id=id;
        mask=1L;
    }
    
    node(long mem, int id) {
        this->id=id;
        mask += (1L << id) + mem;
    }
    
    inline bool det(int i) {
        return (mask & 1 << i) != 0;
    }
};

typedef vector<int> vi;
vector<vi> vii;

bool cycle(int start) {
    queue<int> q;
    q.push(start);
    long mem = 1L;
    int n;
    int ref;

    while(!q.empty()) {
        n = q.front();
        vi next = vii[n];
        for(int i = 0; i<next.size(); ++i) {
            if((ref = next[i]) == start) {
                return 1;
            }
            if((mem & 1L << ref) == 0) {
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

    while(outer) {
        vi vect;
        while(inner) {
            fin >> buf;

            switch(buf) {
                case -2: inner = false; break;
                case -1: outer = false; break;
                default: vect.push_back(buf);
            }
        }
        vii.push_back(vect);
    }

    fin.close();
    vii.pop_back();

    /*

    queue<node> q;
    q.push(node(0));
    int d = vii.size()-1;
    long agr = (1 << vii.size()) - 1;

    while(!q.empty()) {
        node n = q.front();
        q.pop();
        if(n.id=d) {
            agr &= n.mask;
        }

        const int lim = vii[n.id].size();
        for(int i = 0; i<lim; ++i) {
            if(!n.det(vii[n.id].at(i))) {
                q.push(node(n.mask,vii[n.id][i]));
            }
        }
    }

    string split;
    split.reserve(100);
    string out1; 
    out1.reserve(100);

    for(int i = 1; i < vii.size(); ++i) {
        if((agr & 1 << i) != 0) {
            out1 += i;
            out1 += ' ';
        }
    }

    int loc_ref;
    for(int i = 0; i<out1.size(); i+=2) {
        if(!cycle(loc_ref = out1.at(i)-'0')) {
            split += loc_ref;
            split += ' ';
        }
    }

    if(out1.size() > 0) {
        out1.pop_back();
        fout << (1 + out1.size() >> 1) << ' ' << out1 << endl;
    } else {
        fout << 0 << endl;
    }

    if(split.size() > 0) {
        split.pop_back();
        fout << (1 + split.size() >> 1) << ' ' << split << endl;
    } else {
        fout << 0 << endl;
    }

    */

    fout.close();
    return 0;
}
