
#include <vector>
#include <iostream>

using std::vector;
using std::cin;
using std::cout;

struct AuxNode {
    int lowLink;
    int id;
};

static const int UNVISITED = 0;

vector<AuxNode> metadata;
vector<vector<int>> adjList;

// should be generic but whatever

struct LLNode {
    int next;
    int prev;
};

class OrderedStack {
    private:
        vector<LLNode> table;
        int front;
        int back;
        static const int FRONT_EMPTY = -2;
        static const int BACK_EMPTY = -1;
    public:
        OrderedStack(int N);
        ~OrderedStack();
        void pop();
        int top();
        void push(int v);
        bool find(int v);
        bool empty();
        void print(){
            cout << "Front: " << front << " Back: " << back << '\n';
            for(int i = 0; i<table.size(); ++i) {
                cout << i << ": " << '(' << table[i].prev << ',' << table[i].next << ")\n";
            }
            cout << '\n';
        }
};

OrderedStack::OrderedStack(int N){
    table.resize(N);
    for(int i = 0; i<N; ++i) {
        table[i].next = BACK_EMPTY;
        table[i].prev = FRONT_EMPTY;
    }
    front = FRONT_EMPTY;
    back = BACK_EMPTY;
}

OrderedStack::~OrderedStack(){
}

// undefined behavior if already exists.
void OrderedStack::push(int v) {
    if(this->empty()) {
        front = v;
        back = v;
    } else {
        table[back].next = v;
        table[v].prev = back;
        back = v;
    }
}

int OrderedStack::top(){
    return back;
}

// define if the stack is not empty.
// undefined behavior if stack is empty.
void OrderedStack::pop(){
    int temp = table[back].prev;
    table[back].next = BACK_EMPTY;
    table[back].prev = FRONT_EMPTY;
    if(front == back) {
        // size =1;
        back = BACK_EMPTY;
        front = FRONT_EMPTY;
    } else {
        back = temp;
        table[temp].next = BACK_EMPTY;
    }
}

bool OrderedStack::empty(){
    return back == BACK_EMPTY;
}

// just use the same tokens for convenience
bool OrderedStack::find(int v){
    return table[v].next != BACK_EMPTY || table[v].prev != FRONT_EMPTY;
}

OrderedStack* stk;
int N;
int idCtr = 0;

static inline int min(int a, int b) {
	return a<b?a:b;
}

void tarjanSCC(int node) {
	stk->push(node);
	metadata[node].lowlink = metadata[node].id = idCtr++;
    vector<int>& next = adjList[node];
	for(int v: next) {
		if(metadata[v].flag == UNVISITED){
			tarjanSCC(v);
		}				
		if(stk->find(v)) {
			metadata[node].lowlink = min(metadata[v].lowlink,metadata[node].lowlink);
		}
	}
}

int main() {
    // define an adjacency list   
    int K;
    cin >> N >> K;
    adjList.reserve(N);
    OrderedStack ordStack(N);
    stk = &ordStack;

    for(int l = 0; l<2; ++l) {
        int n1,n2,buf;
        cin >> n1 >> n2;
        for(int i = 0; i<N-2; ++i) {
            cin >> buf;
            adjList[n1-1].push_back(n2-1);
            n1 = n2;
            n2 = buf;
        }
    }

    // now run Tarjan's algorithm
    metadata.resize(N);
    for(int i = 0; i<N; ++i) {
        metadata[i].flag = UNVISITED;
        metadata[i].lowLink = UNVISITED;
    }
    return 0;
}