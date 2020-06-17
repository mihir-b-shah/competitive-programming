
#ifndef DSU_H
#define DSU_H

using namespace std;

#include <vector>

struct Node {
    int rank;
    Node* parent;
};

class DisjointSet {
    private:
        vector<Node> nodes;
    public:
        DisjointSet(){ 
        }
        ~DisjointSet(){
        }
        void build(int N){
            nodes.resize(N);
            for(int i = 0; i<N; ++i) {
                nodes[i].rank = 0;
                nodes[i].parent = &nodes[i];
            }
        }
        Node* find(int u){
            Node* start = nodes.data()+u;
            Node* aux = start;
            while(start->parent != start) {
                start = start->parent;
            }
            // path compression
            aux->parent = start;
            return start;
        }
        void merge(int u, int v){
            Node* uTree = find(u);
            Node* vTree = find(v);

            // assume that rank(uTree) > rank(vTree)
            if(uTree->rank < vTree->rank) {
                uTree->parent = vTree;
                vTree->rank = 1+uTree->rank;
            } else {
                vTree->parent = uTree;
                uTree->rank = 1+vTree->rank;
            }
        }
};

#endif