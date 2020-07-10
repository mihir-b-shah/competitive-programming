
#include <iostream>
#include <vector>
#include <algorithm>
#include <unordered_map>

using namespace std;

template<typename T>
class Matrix {
	T* matrix;
	int V;
	int D;

	public:
		Matrix(int V, int D, T init){
			this->V = V;
			this->D = D;
			matrix = new T[V*D];
			fill(matrix, matrix+V*D, init); 
		}
		~Matrix(){
			delete matrix;
		}
		T get(int i, int j) const {
			return matrix[D*i+j];
		}
		void set(int i, int j, T v){
			matrix[D*i+j] = v;
		}
		void print(std::ostream& out){
			for(int i = 0; i<V; ++i){
				for(int j = 0; j<D; ++j){
					out << '(' << (get(i,j) >> 32) << ',' << (get(i,j)&0xffffffff) << ')' << ' ';
				}
				out << '\n';
			}
		}
};

int V,E;
Matrix<bool>* matrix;

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

/**Target first, then option index. */
long long myhash(long long tgt, int v){
	return (tgt << 32) + v;
}

vector<Pair> options;

#define EMPTY 2147483647
int main(){
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);

	cin >> V >> E;
	int _tgt, tgtRed, tgtBlue;
	cin >> tgtRed >> tgtBlue >> _tgt;
	tgtRed += _tgt;

	Matrix<bool> m(V,V,false);	
	matrix = &m;
	int u,v;
	for(int i = 0; i<E; ++i){
		cin >> u >> v;
		matrix->set(u-1,v-1,true);
		matrix->set(v-1,u-1,true);
	}

	int next;
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

	Matrix<long long> dp(options.size()+1, V+1, EMPTY);
	
	dp.set(0, 0, true);
	bool pred1, pred2;
	for(int i = 1; i<options.size()+1; ++i){
		for(int j = 0; j<V+1; ++j){
			if(j == 0){
				if(options[i-1].one == 0 || options[i-1].two == 0){
					dp.set(i, j, myhash(j, i-1));
				}
				continue;
			}
			pred1 = j >= options[i-1].one ? (dp.get(i-1, j-options[i-1].one) != EMPTY) : false;
			pred2 = j >= options[i-1].two ? (dp.get(i-1, j-options[i-1].two) != EMPTY) : false;
			
			if(pred1){
				dp.set(i, j, myhash(j-options[i-1].one, i-1));
			} else if(pred2){
				dp.set(i, j, myhash(j-options[i-1].two, i-1));
			}
		}
	}
	
	dp.print(cout);
	dp.set(0, 0, EMPTY);
	cout << options.size() << ' ' << tgtRed << '\n';

	cout << (dp.get(options.size(), tgtRed) != EMPTY ? "YES" : "NO") << endl;
	if(dp.get(options.size(), tgtRed) != EMPTY){
		// time to backtrace.
		long long hash = myhash(tgtRed, options.size());
		int v = tgtRed; int idx = options.size();
		int newV, newIdx;
		int backCtr = 0;
		while(dp.get(idx, v) != EMPTY){
			long long next = dp.get(idx, v);
			newV = next & 0xffffffffLL;
			newIdx = next >> 32;
			hash = myhash(newV, newIdx);
			int option = v-newV;
			if(options[idx-1].one != option){
				vector<int>& myvect = groups[groups.size()-1-backCtr];
				for(int e: myvect){
					if(colors[e] == RED){
						cout << "RAAAAR red" << endl;
						colors[e] = BLUE;
					} else {
						cout << "RAAAAR blue" << endl;
						colors[e] = RED;
					}
				}	
			}
			v = newV;
			idx = newIdx;
			++backCtr;
		}

		// allocate enough for green.
		int redCtr = 0;
		for(int i = 0; i<V; ++i){
			if(redCtr < tgtRed-_tgt && colors[i] == RED){
				++redCtr;
			} else if(colors[i] == RED){
				colors[i] = GREEN;
			}
			cout << colors[i];
		} cout << '\n';
	}
	return 0;
}
