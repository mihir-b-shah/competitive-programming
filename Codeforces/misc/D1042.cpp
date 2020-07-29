
#include <iostream>
#include <vector>
#include <memory>

using namespace std;

class WTree {
	private:
		long long partition = -1;
		vector<int> prefix;	
		unique_ptr<WTree> left = nullptr;
		unique_ptr<WTree> right = nullptr;
	public:
		WTree(long long low, long long high, vector<long long> front){
			if(low > high) return;
			if(low == high){
				prefix.resize(1+front.size());
				for(int i = 0; i<=front.size(); ++i){
					prefix[i] = i;
				}
				return;
			}
			partition = (low+high)/2;

			vector<long long> vleft;
			vleft.reserve(front.size()/2);
			vector<long long> vright;
			vright.reserve(front.size()/2);
			prefix.resize(front.size()+1);

			for(int i = 0; i<front.size(); ++i){
				if(front[i] > partition){
					vright.push_back(front[i]);
					prefix[i+1] = prefix[i];
				} else {
					vleft.push_back(front[i]);
					prefix[i+1] = prefix[i] + 1;
				}
			}	

			if(vleft.size() > 0) {
				left = make_unique<WTree>(low, partition, vleft);
			}
			if(vright.size() > 0) {
				right = make_unique<WTree>(partition+1, high, vright);
			}
		}
		~WTree(){
		}
		int range(int Li, int Ri, long long T){
			cout << "L: " << Li << " R: " << Ri << " T: " << T << '\n';
			if(partition == -1 || T == partition){
				return Ri-Li;
			}
			if(T > partition){
				return Ri-Li + right->range(Ri-prefix[Ri], Li-prefix[Li], T);
			}
			return left->range(prefix[Li], prefix[Ri], T);
		}
};

int main(){
	int N;
	long long T;
	cin >> N >> T; 

	vector<long long> ps(1+N);
	long long buf;

	for(int i = 0; i<N; ++i){
		cin >> buf;
		ps[i+1] = ps[i] + buf;
	}

	WTree tree(0, 25, ps);
	long long res = 0;
	for(int i = 1; i<=N; ++i){
		cout << i-tree.range(0,i-1,ps[i]-T) << '\n';
	}	
	return 0;
}
