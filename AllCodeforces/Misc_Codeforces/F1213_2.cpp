
#include <vector>
#include <iostream>

using std::vector;
using std::cin;
using std::cout;

vector<vector<int>> adjList;
vector<vector<int>> reversed;

int main() {
    // define an adjacency list   
    int N,K;
    cin >> N >> K;
    adjList.reserve(N);
	
    for(int l = 0; l<2; ++l) {
        int n1,n2,buf;
        cin >> n1 >> n2;
        for(int i = 0; i<N-2; ++i) {
            cin >> buf;
            adjList[n1-1].push_back(n2-1);
			reversed[n2-1].push_back(n1-1);
            n1 = n2;
            n2 = buf;
        }
    }
    return 0;
}