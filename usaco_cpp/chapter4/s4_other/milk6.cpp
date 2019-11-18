#include <fstream>
#include <iostream>
#include <vector>
#include <utility>
#include <queue>

using namespace std;

typedef vector<pair<int,int>> vi;

int main() {
    ifstream fin;
    fin.open("milk6.in");
    int N,M;
    fin >> N >> M;
    int src,dest,wt;

    vector<vi> adjlist;
    for(int i = 0; i<N; ++i) {
        vi vect;
        adjlist.push_back(vect);
    }

    for(int i = 0; i<M; ++i) {        
        fin >> src >> dest >> wt;
        adjlist[src-1].push_back(make_pair(dest-1,wt));
    }


    
    return 0;
}