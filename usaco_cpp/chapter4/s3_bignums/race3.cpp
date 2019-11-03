
#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

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

int solve() {
    ifstream fin;
    fin.open("race3.in");
    
    ofstream fout;
    fout.open("race3.out");

    vector<vi> vii;
    int buf;
    bool outer = 1;
    bool inner = 1;

    while(outer) {
        vi vect;
        while(inner) {
            fin >> buf;

            switch(buf) {
                case -2: inner = true; break;
                case -1: outer = true; break;
                default: vect.push_back(buf);
            }
        }
        vii.push_back(vect);
    }

    vii.pop_back();
    
    queue<node*> q;
    q.push(&node(0));
    int d = vii.size()-1;
    long agr = (1 << vii.size()) - 1;

    while(!q.empty) {
        node* n = q.back();
        q.pop();
        if(n->id=d) {
            agr &= n->mask;
        }

        vi* next = &vii[n->id];
        const int lim = next->size();
        for(int i = 0; i<lim; ++i) {
            if(!n->det(next->at(i))) {
                q.push(&node(n->mask,next->at(i)));
            }
        }
    }

    fout.close();
    fin.close();
}
