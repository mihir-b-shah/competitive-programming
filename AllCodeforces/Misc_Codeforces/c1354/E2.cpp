
#pragma GCC optimize("Ofast")

#include <iostream>
#include <vector>
#include <cstring>
#include <unordered_map>

using namespace std;

class Matrix {
	bool* matrix;
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
			return matrix[D*i+j];
		}
		void set(int i, int j, bool v){
			matrix[D*i+j] = v;
		}
		void print(std::ostream& out){
			for(int i = 0; i<V; ++i){
				for(int j = 0; j<D; ++j){
					out << get(i,j) << ' ';
				}
				out << '\n';
			}
		}
};

int V,E;
Matrix* matrix;

enum Color {WHITE, RED, BLUE, GREEN};
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

vector<vector<int>> groups;
int dfsCtr = 0;

bool dfs(int src, Color color){
	colors[src] = color;
	groups[dfsCtr].push_back(src);
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

long long myhash(long long tgt, int v){
	return (tgt << 32) + v;
}

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
	groups.resize(V);

	while((next = scan()) < V){
		colorCts[1] = 0; colorCts[2] = 0;
		bool res = dfs(next, RED);
		if(!res){
			cout << "NO" << endl;
			return 0;
		}
		
		++dfsCtr;
		options.push_back(Pair(colorCts[RED], colorCts[BLUE]));
	}

	Matrix dp(options.size()+1, V+1);
	dp.set(0, 0, true);
	unordered_map<long long, long long> lookup;

	bool pred1,pred2;
	for(int i = 1; i<options.size()+1; ++i){
		for(int j = 1; j<V+1; ++j){
			pred1 = j >= options[i-1].one ? dp.get(i-1, j-options[i-1].one) : false;
			pred2 = j >= options[i-1].two ? dp.get(i-1, j-options[i-1].two) : false;
			if(pred1){
				lookup[myhash(j,i)] = myhash(j-options[i-1].one, i-1); 
			} else if(pred2){
				lookup[myhash(j,i)] = myhash(j-options[i-1].two, i-1);
			}
			dp.set(i, j, pred1 || pred2);
		}
	}

	cout << (dp.get(options.size(), tgtRed) ? "YES" : "NO") << endl;
	if(dp.get(options.size(), tgtRed)){
		// time to backtrace.
		long long hash = myhash(tgtRed, options.size());
		int v = tgtRed; int idx = options.size();
		int newV, newIdx;
		int backCtr = 0;
		while(lookup.find(hash) != lookup.end()){
			long long next = lookup[hash];
			newIdx = next & 0xffffffffLL;
			newV = next >> 32;
			hash = myhash(newV, newIdx);
			int option = v-newV;
			if(options[idx-1].one != option){
				vector<int>& myvect = groups[groups.size()-1-backCtr];
				for(int e: myvect){
					if(colors[e] == RED){
						colors[e] = BLUE;
					} else {
						colors[e] = RED;
					}
				}	
			}
			v = newV;
			idx = newIdx;
			++backCtr;
		}

		// allocate evenly between red/green.
		int redCtr = 0;
		for(int i = 0; i<V; ++i){
			if(redCtr < tgtRed/2 && colors[i] == RED){
				++redCtr;
			} else if(colors[i] == RED){
				colors[i] = GREEN;
			}
			cout << colors[i];
		} cout << '\n';
	}
	return 0;
}
