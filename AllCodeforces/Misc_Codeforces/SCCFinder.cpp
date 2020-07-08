
#include <vector>
#include <iostream>
#include <fstream>
#include <unordered_set>
#include <unordered_map>

using namespace std;

struct Node {
    int rank;
    Node* parent;
};

struct Vector {
    vector<int> vect;
    int msize;
    
    vector<int>& v(){
        return vect;
    }
};

class DisjointSet {
    private:
        vector<Node> nodes;
        int card;
    public:
        DisjointSet(){ 
        }
        ~DisjointSet(){
        }
        int cardinality(){
            return card;
        }
        void build(int N){
            nodes.resize(N);
            card = N;
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

            if(uTree == vTree){
                return;
            }
            
            // assume that rank(uTree) > rank(vTree)
            if(uTree->rank < vTree->rank) {
                uTree->parent = vTree;
                vTree->rank = 1+uTree->rank;
            } else {
                vTree->parent = uTree;
                uTree->rank = 1+vTree->rank;
            }
            
            --card;
        }
};

static DisjointSet dsu;

class Stack {
    private:
        vector<int> stack;
        vector<bool> set;
    public:
        Stack() {
        }
        ~Stack(){
        }
        void resize(int N) {
            set.resize(N);
        }
        void push(int e){
            stack.push_back(e);
            set[e] = true;
        }
        void pop(){
            set[stack.back()] = false;
            stack.pop_back();
        }
        int top(){
            return stack.back();
        }
        int operator [] (int idx){
            return stack[idx];
        }
        int size(){
            return stack.size();
        }
        bool empty(){
            return stack.empty();
        }
        bool find(int e){
            return set[e];
        }
};

static vector<Vector> adjList;

static void dfs(Stack& stack, int v){
    // cout << "dfs: " << (v+1) << '\n';
    if(stack.find(v)){
        // unwind the stack and view the cycle.
        int idx = stack.size()-1;
        cout << "Cycle: " << (v+1) << ' ';
        int aux1 = v;
        int aux2;
        while(stack[idx] != v){
            cout << (stack[idx]+1) << ' ';
            aux2 = stack[idx];
            dsu.merge(aux1, aux2);
            aux1 = aux2;
            --idx;
        }
        cout << '\n';
        return;
    }
    
    stack.push(v);
    Vector& loc = adjList[v];

    for(auto iter = loc.v().rbegin()+loc.msize; iter != loc.v().rend(); ++iter){
        dfs(stack, *iter);
        ++loc.msize;
    }

    stack.pop();
    // cout << "backtrack: " << (v+1) << '\n';
}

long long token(int ii, int ie){
    long long v = ii;
    v <<= 32;
    v |= ie;
    return v;
}

int main() {
    ifstream fin("testfile.txt");
    int V,E;
    fin >> V >> E;
    
    adjList.resize(V);
    for(int i = 0; i<E; ++i) {
        int buf1,buf2;
        fin >> buf1 >> buf2;
        adjList[buf1-1].vect.push_back(buf2-1);
        adjList[buf1-1].msize = 0;
    }
    fin.close();
    
    Stack stack;
    stack.resize(V);
    dsu.build(V);
    dfs(stack, 0);
    
    //cout << '\n';
    /*
    for(int i = 0; i<V; ++i) {
        cout << dsu.find(i) << '\n';
    }
    cout << "Cardinality: " << dsu.cardinality() << '\n';
    */
    
    vector<vector<int>> compList;
    compList.resize(dsu.cardinality());
    
    unordered_set<long long> edgeCheck;
    unordered_map<Node*,int> dsuMap;
    int ctr = 0;
    
    for(int i = 0; i<V; ++i){
        if(dsuMap.find(dsu.find(i)) == dsuMap.end()){
            dsuMap[dsu.find(i)] = ctr++;
        }
    }        
    
    for(int i = 0; i<V; ++i) {
        vector<int>& edges = adjList[i].v();
        // create method.
        for(int edge: edges){
            int ii = dsuMap[dsu.find(i)];
            int ie = dsuMap[dsu.find(edge)];
            if(ii != ie && edgeCheck.find(token(ii,ie)) == edgeCheck.end()){
                compList[ii].push_back(ie);
                edgeCheck.insert(token(ii,ie));
            } 
        }
    }
    
    for(int i = 0; i<compList.size(); ++i){
        vector<int>& edges = compList[i];
        cout << i << ": ";
        for(int j: edges){
            cout << j << ' ';
        }
        cout << '\n';
    }
    cout << '\n';
    
    // run a topological sort now??
    
    
    return 0;
}