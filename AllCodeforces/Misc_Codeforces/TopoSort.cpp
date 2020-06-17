
#include <vector>
#include <forward_list>
#include <iostream>

using namespace std;

/*
enum State {
	UNVISITED, STACKED, DONE
};

struct Edge {
	int dest;
	Edge* pair;
};

void dfs(int src, vector<int>& sort, vector<forward_list<Edge>>& adjList, vector<forward_list<Edge>>& orig, vector<State>& vis) {
	forward_list<Edge>& neighbors = adjList[src];
	if(neighbors.empty() && vis[src] == UNVISITED) {
		sort.push_back(src);
        vis[src] = STACKED;
	}
	for(auto iter = neighbors.begin(); iter!=neighbors.end(); ++iter) {
		if(vis[iter->dest] != DONE) {
			dfs(iter->dest, sort, adjList, orig, vis);
            orig[iter->dest].erase_after(iter->pair);
			neighbors.erase_after(iter);
            if(orig[iter->dest].empty()) 
                vis[src] = DONE;
            }
		}
	}
}

void toposort(vector<int>& sort, vector<forward_list<Edge>>& adjList) {
	vector<forward_list<Edge>> reverse(adjList.size());
	vector<int> sources;
	for(int i = 0; i<adjList.size(); ++i){ 
		for(Edge& j: adjList[i]) {
			reverse[j.dest].push_front({i,&j});
		}
	}
	for(int i = 0; i<adjList.size(); ++i) {
		if(adjList[i].size() == 0) {
			sources.push_back(i);
		}
	}

	vector<State> vis(N, UNVISITED);
	for(int src: sources) {
		dfs(src, sort, reverse, adjList, vis);
	}
} */

#include <typeinfo>
int main() {
    /*
	vector<forward_list<Edge>> adjList(5);
	adjList[0].push_front({1,nullptr}); 
	adjList[0].push_front({1,nullptr});
	adjList[1].push_front({2,nullptr});
	adjList[2].push_front({3,nullptr}); 
	adjList[2].push_front({4,nullptr});
	adjList[3].push_front({4,nullptr});
	vector<int> tsort;
	toposort(tsort, adjList); */
    forward_list<int> yeet;
    std::cout << typeid(yeet).name() << '\n';
}
