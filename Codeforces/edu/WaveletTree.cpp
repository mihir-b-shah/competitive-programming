
#include <iostream>
#include <vector>
#include <memory>

using namespace std;

class WTree {
	private:
		int partition = -1;
		vector<int> items;
		vector<int> prefix;	
		unique_ptr<WTree> left = nullptr;
		unique_ptr<WTree> right = nullptr;
	public:
		WTree(int low, int high, vector<int> front){
			if(low > high) return;
			if(low == high){
				items = front;
				return;
			}
			partition = (low+high)/2;
			items = front;

			vector<int> vleft;
			vleft.reserve(front.size()/2);
			vector<int> vright;
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
		void print(){
			for(int v: prefix){
				cout << v << ' ';
			} 
			if(prefix.size() > 1) cout << '\n';

			if(left != nullptr) {
				left->print();
			}
			if(right != nullptr){
				right->print();
			}
		}
		int freq(int L, int R, int v){
			if(partition == -1){
				return R-L;
			}
			return v > partition ? 
					right->freq(R-prefix[R], L-prefix[L], v) : 
					left->freq(prefix[L], prefix[R], v);
		}
		int range(int Li, int Ri, int T){
			if(partition == -1 || T == partition){
				return Ri-Li;
			}
			if(T > partition){
				return Ri-Li + right->range(Ri-prefix[Ri], Li-prefix[Li], T);
			}
			return left->range(prefix[Li], prefix[Ri], T);
		}
};

class WaveletTree {
	private:
		int H;
		unique_ptr<WTree> tree = nullptr;
	public:
		WaveletTree(int L, int H, vector<int> items){
			this->H = H;
			tree = make_unique<WTree>(L, H, items);
		}
		~WaveletTree(){
		}
		int freq(int L, int R, int v){
			return tree->freq(L,R,v);
		}
		int range(int L, int R, int T){
			return tree->range(L, R, T); 
		}
};

int main(){
	vector<int> data = {2,1,7,1,4,6};
	WaveletTree tree(1, 8, data);
	cout << tree.range(0, 4, 3) << '\n';
}
