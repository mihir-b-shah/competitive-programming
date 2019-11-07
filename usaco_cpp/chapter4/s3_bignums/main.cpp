
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
        cout << "Call to constructor, id=" << id << ", mem=" << mem << endl;
        this->id=id;
        mask += (1L << id) + mem;
    }
    
    void to_string() {
        cout << '(' << id << ", ";
        bool stack[64];
        int ctr = 0;
        if(mask == 0) {
            cout << "nooooo";
        }
        while(mask > 0) {
            stack[ctr++] = mask & 1;
            mask >>= 1;
        }
        for(int i = ctr-1; i>=0; --i) {
            cout << stack[i];
        }
        cout << ')' << endl;
    }
};

inline bool det(int i) {
    return (mask & 1 << i) != 0;
}

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
    vii.pop_back();
    
    queue<node> q;
    q.push(node(0));
    int d = vii.size();
    long agr = (1L << vii.size()) - 1;
    ctr = 0;
    long mask_curr;
    int id_curr;

    while(!q.empty()) {
        node& n = q.front();
        id_curr = n.id;
        mask_curr = n.mask;
        
        ++ctr;
        cout << ctr << ": ";
        n.to_string();

        q.pop();

        if(id_curr==d) {
            agr &= mask_curr;
            continue;
        }

        n.to_string();

        vi& next = vii[id_curr];
        const int lim = next.size();

        for(int i = 0; i<lim; ++i) {
            if(!n.det(next.at(i))) {
                q.push(node(mask_curr,next[i]));
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

    fout.close();
    return 0;
}
