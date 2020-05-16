
#include <iostream>
#include <fstream>

using namespace std;

int main() {
	ifstream fin("starry.in");
	int buf;
	fin >> buf;
	const int W = buf;
	fin >> buf;
	const int H = buf;
	
	char grid[W][H];
	for(int i = 0; i<H; ++i) {
		for(int j = 0; j<W; ++j) {
			fin >> grid[i][j];
		}
	}
	fin.close();

	// generate a bit-pattern for the 100x100 matrix
	// there are max=26 clusters of 8 rotationse each
	
	ofstream fout("starry.out");
	fout.flush();
	fout.close();
}
