
#include <list>
#include <vector>
#include <iostream>
#include <fstream>
#include <unordered_set>

using namespace std;

static vector<list<int>> adjList;

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

static void printLinkList(int src, list<int>& loc){
    cout << "Link list of " << (src+1) << ": ";
    for(auto iter = loc.begin(); iter!=loc.end(); ++iter){
        cout << (*iter+1) << ' ';
    }
    cout << '\n';
}

static void dfs(Stack& stack, int v){
    cout << "dfs: " << (v+1) << '\n';
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
    list<int>& loc = adjList[v];
    auto iter = loc.begin();
    printLinkList(v, loc);
    while(iter != loc.end()){
        dfs(stack, *iter);
        iter = loc.erase(iter);
        printLinkList(v, loc);
    }
    stack.pop();
    cout << "backtrack: " << (v+1) << '\n';
}

int main() {
    ifstream fin("testfile.txt");
    int V,E;
    fin >> V >> E;
    
    adjList.resize(V);
    for(int i = 0; i<E; ++i) {
        int buf1,buf2;
        fin >> buf1 >> buf2;
        adjList[buf1-1].push_front(buf2-1);
    }
    
    Stack stack;
    stack.resize(V);
    dsu.build(V);
    dfs(stack, 0);
    
    cout << '\n';
    for(int i = 0; i<V; ++i) {
        cout << dsu.find(i) << '\n';
    }
}