
#pragma GCC optimize("Ofast")

#include <iostream>

using namespace std;

class Matrix {
	bool* __restrict matrix;
	int V;

	public:
		Matrix(int V){
			this->V = V;
			matrix = new bool[V*V]();
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
};

int V,E;
int tgt1, tgt2, tgt3;

enum Color:char {NONE, RED, GREEN, BLUE};
class ColArray {
	Color* __restrict colors;
	int colorCts[4] = {0};
	public:
		ColArray(int V){
			colors = new Color[V]();
		}
		~ColArray(){
			delete colors;
		}
		static bool compatible(Color c1, Color c2){
			return c1 == NONE || c2 == NONE || c1-c2 == 1 || c2-c1 == 1;
		}
		Color get(int idx) const {
			return colors[idx];
		}
		void set(int idx, Color v){
			colors[idx] = v;
			++colorCts[v];
		}
		bool check() const {
			return colorCts[1] == tgt1 && colorCts[2] == tgt2 && colorCts[3] == tgt3;
		}
};

Matrix* matrix;
ColArray* colors;
bool* visited;

int scan(){
	int ctr = 0;
	while(ctr < V && visited[ctr]) {
		++ctr;
	}
	return ctr;
}

bool dfs(Color, int, int);

bool dfsHelper(Color pColor, int src, int edgeCt, Color sColor){	
	cout << "start: " << src+1 << " color: " << sColor << '\n';
	if(edgeCt == E){
		// check against targets
		return colors->check(); 
	}
	colors->set(src, sColor);
	bool check = false;
	for(int v = 0; v<V; ++v){
		if(matrix->get(src,v) && ColArray::compatible(colors->get(src), colors->get(v))){
			matrix->set(src, v, false);
			check |= dfs(sColor, v, edgeCt+1);
			matrix->set(src, v, true);
		}
	}
	colors->set(src, NONE);
	return check;
}

bool dfs(Color pColor, int src, int edgeCt) {
	visited[src] = true;
	switch(pColor){
		case RED:
		case BLUE:
			return dfsHelper(pColor, src, edgeCt, GREEN);
		case NONE:
			return dfsHelper(pColor, src, edgeCt, GREEN) || dfsHelper(pColor, src, edgeCt, RED) || dfsHelper(pColor, src, edgeCt, BLUE);
		case GREEN:
			return dfsHelper(pColor, src, edgeCt, RED) || dfsHelper(pColor, src, edgeCt, BLUE);
	}
	return true;
}

int main(){
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);

	cin >> V >> E;
	cin >> tgt1 >> tgt2 >> tgt3;

	Matrix m(V);
	ColArray cols(V);
	visited = new bool[V]();
	
	matrix = &m;
	colors = &cols;

	int u,v;
	for(int i = 0; i<E; ++i){
		cin >> u >> v;
		matrix->set(u-1,v-1,true);
		matrix->set(v-1,u-1,true);
	}

	int next;
	while((next = scan()) != V){
		std::cout << "One iteration completed.\n";
		dfs(NONE, next, 0); 
	}

	cout << dfs(NONE, 0, 0) << '\n';
	delete visited;
	return 0;
}
