
#pragma GCC optimize("Ofast")

#include <iostream>
#include <vector>
#include <cstring>

using namespace std;

class Matrix {
	bool* __restrict matrix;
	int V;
	int D;

	public:
		Matrix(int V, int D){
			this->V = V;
			this->D = D;
			matrix = new bool[V*D]();
		}
		~Matrix(){
			delete matrix;
		}
		bool get(int i, int j) const {
			return matrix[V*i+j];
		}
		void set(int i, int j, bool v){
			matrix[V*i+j] = v;
		}
		void print(std::ostream& out){
			for(int i = 0; i<D; ++i){
				for(int j = 0; j<V; ++j){
					out << get(i,j) << ' ';
				}
				out << '\n';
			}
		}
};

int V,E;
Matrix* matrix;

enum Color {WHITE, RED, BLUE};
Color colors[5000] = {WHITE};

int colorCts[3] = {0};

static inline bool check(Color c1, Color c2){
	switch(c1){
		case RED: return c2 != RED;
		case BLUE: return c2 != BLUE;
		default: return true;
	}
}

static inline Color next(Color c){
	switch(c){
		case RED: return BLUE;
		case BLUE: return RED;
		default: return WHITE;
	}
}

bool dfs(int src, Color color){
	colors[src] = color;
	++colorCts[color];
	bool chk;
	bool aggr = true;
	for(int i = 0; i<V; ++i){
		chk = check(color, colors[i]);
		if(matrix->get(src,i) && colors[i] == WHITE){
			aggr &= dfs(i, next(color)); 
		} else if(matrix->get(src,i) && !chk){
			return false;
		}
	}	
	return aggr;	
}

int scan(){
	int idx = 0;
	while(idx < V && colors[idx] != WHITE){
		++idx;
	}
	return idx;
}

struct Pair {
	int one;
	int two;

	Pair(int o, int t){
		one = o;
		two = t;
	}
};

int main(){
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);

	cin >> V >> E;
	int _tgt, tgtRed, tgtBlue;
	cin >> tgtRed >> tgtBlue >> _tgt;
	tgtRed += _tgt;

	Matrix m(V,V);
	
	matrix = &m;
	int u,v;
	for(int i = 0; i<E; ++i){
		cin >> u >> v;
		matrix->set(u-1,v-1,true);
		matrix->set(v-1,u-1,true);
	}

	int next;
	vector<Pair> options;

	while((next = scan()) < V){
		colorCts[1] = 0; colorCts[2] = 0;
		dfs(next, RED);

		options.push_back(Pair(colorCts[RED], colorCts[BLUE]));
		cout << "Hi im here.\n";
	}

	Matrix dp(V+1, options.size()+1);
	dp.set(0, 0, true);

	bool pred1,pred2;
	for(int i = 1; i<options.size()+1; ++i){
		for(int j = 1; j<V+1; ++j){
			pred1 = j >= options[i].one ? dp.get(j-options[i].one, i-1) : false;
			pred2 = j >= options[i].two ? dp.get(j-options[i].two, i-1) : false;
			dp.set(j, i, dp.get(j,i) || pred1 || pred2);
		}
	}
	
	dp.print(cout);
	cout << (dp.get(options.size(), V) ? "YES" : "NO") << '\n';
	return 0;
}
